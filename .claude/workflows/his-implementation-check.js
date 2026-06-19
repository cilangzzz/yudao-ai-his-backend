/**
 * HIS 实现核查工作流
 *
 * 核查代码实现是否与设计文档一致
 * 检查: 数据库表、实体类、API接口、业务逻辑
 *
 * 执行方式:
 * 1. 完整核查: /his-implementation-check
 * 2. 核查指定模块: /his-implementation-check {"module": "M01"}
 * 3. 核查指定类型: /his-implementation-check {"checkType": "database"}
 */

export const meta = {
  name: 'his-implementation-check',
  description: '核查 HIS 代码实现是否与设计文档一致',
  whenToUse: '验证代码实现是否符合设计文档要求时使用',
  phases: [
    { title: '读取设计文档', detail: '解析数据库设计和API设计' },
    { title: '检查数据库', detail: '对比表结构与设计文档' },
    { title: '检查实体类', detail: '验证DO类与表结构对应' },
    { title: '检查API接口', detail: '验证Controller与设计一致' },
    { title: '生成报告', detail: '输出核查结果和问题清单' }
  ]
}

// 检查类型
const CHECK_TYPES = {
  database: {
    name: '数据库检查',
    description: '检查数据库表结构与设计文档是否一致'
  },
  entity: {
    name: '实体类检查',
    description: '检查DO实体类与数据库表是否对应'
  },
  api: {
    name: 'API接口检查',
    description: '检查Controller接口与设计文档是否一致'
  },
  business: {
    name: '业务逻辑检查',
    description: '检查业务逻辑与功能需求是否一致'
  }
}

// 模块配置
const MODULE_CONFIG = {
  M01: { name: '门诊管理', domain: 'op', tables: ['op_schedule', 'op_appointment', 'op_register', 'op_prescription'] },
  M02: { name: '住院管理', domain: 'ip', tables: ['his_admission', 'his_order', 'his_medication_admin', 'his_bed'] },
  M06: { name: '药品管理', domain: 'drug', tables: ['his_drug', 'his_drug_stock', 'his_drug_interaction'] }
}

const CHECK_RESULT_SCHEMA = {
  type: 'object',
  properties: {
    success: { type: 'boolean' },
    module: { type: 'string' },
    checkType: { type: 'string' },
    passed: { type: 'number' },
    failed: { type: 'number' },
    warnings: { type: 'number' },
    issues: {
      type: 'array',
      items: {
        type: 'object',
        properties: {
          severity: { type: 'string' },
          category: { type: 'string' },
          description: { type: 'string' },
          location: { type: 'string' },
          suggestion: { type: 'string' }
        }
      }
    }
  }
}

// ============================================
// Phase 1: 读取设计文档
// ============================================

async function readDesignDocs(module) {
  phase('读取设计文档')

  const docs = await parallel([
    () => agent(`读取 ${module} 数据库设计`, {
      prompt: `读取 docs/his/${module}* 目录下的数据库设计文档:
      - 提取所有表定义
      - 提取字段定义（名称、类型、约束）
      - 提取索引定义
      - 提取外键关系

      输出结构化的表定义清单。`,
      phase: '读取设计文档'
    }),
    () => agent(`读取 ${module} 功能需求`, {
      prompt: `读取 docs/his/${module}* 目录下的功能点需求文档:
      - 提取功能点列表
      - 提取业务规则
      - 提取API接口需求

      输出结构化的需求清单。`,
      phase: '读取设计文档'
    })
  ])

  return {
    database: docs[0],
    requirements: docs[1]
  }
}

// ============================================
// Phase 2: 检查数据库
// ============================================

async function checkDatabase(module, designDocs) {
  phase('检查数据库')

  const result = await agent('对比数据库表结构', {
    prompt: `检查数据库实现与设计文档的一致性:

    模块: ${module}
    设计文档中的表定义:
    ${JSON.stringify(designDocs.database, null, 2)}

    执行检查:
    1. 读取 sql/his-init.sql 或 sql/mysql/ 目录下的建表脚本
    2. 对比每个表是否存在
    3. 对比每个字段是否存在且类型正确
    4. 对比索引是否存在
    5. 对比外键约束是否正确

    输出检查结果，列出所有不一致的地方。`,
    phase: '检查数据库',
    schema: CHECK_RESULT_SCHEMA
  })

  return result
}

// ============================================
// Phase 3: 检查实体类
// ============================================

async function checkEntities(module, designDocs) {
  phase('检查实体类')

  const config = MODULE_CONFIG[module]
  if (!config) {
    log(`未找到模块配置: ${module}`)
    return null
  }

  const result = await agent('检查实体类实现', {
    prompt: `检查 DO 实体类实现:

    模块: ${module}
    预期的表: ${config.tables.join(', ')}

    执行检查:
    1. 扫描 yudao-module-his-biz/src/main/java/**/dal/dataobject/ 目录
    2. 检查每个表是否有对应的 DO 类
    3. 检查 DO 类的字段是否与表字段对应
    4. 检查注解是否正确 (@TableName, @TableField, @TableId)
    5. 检查是否继承 BaseDO
    6. 检查字段类型映射是否正确

    输出检查结果，列出所有问题。`,
    phase: '检查实体类',
    schema: CHECK_RESULT_SCHEMA
  })

  return result
}

// ============================================
// Phase 4: 检查API接口
// ============================================

async function checkAPIs(module, designDocs) {
  phase('检查API接口')

  const result = await agent('检查 Controller API 实现', {
    prompt: `检查 API 接口实现:

    模块: ${module}
    功能需求:
    ${JSON.stringify(designDocs.requirements, null, 2)}

    执行检查:
    1. 扫描 yudao-module-his-biz/src/main/java/**/controller/ 目录
    2. 检查每个功能点是否有对应的 Controller 方法
    3. 检查请求路径是否符合规范 (/his/{domain}/xxx)
    4. 检查权限标识是否符合规范 (his:{domain}:xxx)
    5. 检查 Swagger 注解是否完整
    6. 检查请求/响应 VO 是否存在

    输出检查结果，列出所有缺失或不符合规范的接口。`,
    phase: '检查API接口',
    schema: CHECK_RESULT_SCHEMA
  })

  return result
}

// ============================================
// Phase 5: 检查业务逻辑
// ============================================

async function checkBusinessLogic(module, designDocs) {
  phase('检查业务逻辑')

  const result = await agent('检查 Service 业务逻辑', {
    prompt: `检查 Service 业务逻辑实现:

    模块: ${module}
    功能需求和业务规则:
    ${JSON.stringify(designDocs.requirements, null, 2)}

    执行检查:
    1. 扫描 yudao-module-his-biz/src/main/java/**/service/ 目录
    2. 检查每个功能点是否有对应的 Service 方法
    3. 检查业务规则是否在代码中实现
    4. 检查异常处理是否正确
    5. 检查事务注解是否正确使用
    6. 检查是否有 TODO 或未实现的方法

    输出检查结果，列出所有未实现的业务逻辑。`,
    phase: '检查业务逻辑',
    schema: CHECK_RESULT_SCHEMA
  })

  return result
}

// ============================================
// Phase 6: 生成报告
// ============================================

async function generateReport(results) {
  phase('生成报告')

  const report = await agent('生成核查报告', {
    prompt: `生成实现核查报告:

    检查结果:
    ${JSON.stringify(results, null, 2)}

    生成报告内容:
    1. 执行摘要: 通过/失败/警告数量
    2. 详细问题清单: 按模块和检查类型分类
    3. 修复建议: 针对每个问题的修复方案
    4. 优先级排序: 按严重程度排序

    将报告保存到: docs/his/IMPLEMENTATION_CHECK_REPORT.md`,
    phase: '生成报告'
  })

  return report
}

// ============================================
// 主执行流程
// ============================================

async function main() {
  log('====================================')
  log('HIS 实现核查工作流启动')
  log('====================================')

  const args = globalThis.args || {}
  const module = args.module || null
  const checkType = args.checkType || null

  // 确定要检查的模块
  const modulesToCheck = module ? [module] : Object.keys(MODULE_CONFIG)

  // 确定要执行的检查类型
  const checksToRun = checkType ? [checkType] : Object.keys(CHECK_TYPES)

  const allResults = []

  for (const mod of modulesToCheck) {
    log(`核查模块: ${mod}`)

    // Phase 1: 读取设计文档
    const designDocs = await readDesignDocs(mod)

    // 执行各项检查
    const moduleResults = {
      module: mod,
      checks: {}
    }

    for (const type of checksToRun) {
      log(`执行 ${CHECK_TYPES[type].name}...`)

      switch (type) {
        case 'database':
          moduleResults.checks.database = await checkDatabase(mod, designDocs)
          break
        case 'entity':
          moduleResults.checks.entity = await checkEntities(mod, designDocs)
          break
        case 'api':
          moduleResults.checks.api = await checkAPIs(mod, designDocs)
          break
        case 'business':
          moduleResults.checks.business = await checkBusinessLogic(mod, designDocs)
          break
      }
    }

    allResults.push(moduleResults)
  }

  // 生成报告
  const report = await generateReport(allResults)

  // 汇总结果
  const summary = {
    success: allResults.every(r =>
      Object.values(r.checks).every(c => c && c.success !== false)
    ),
    modulesChecked: modulesToCheck.length,
    checksRun: checksToRun.length,
    totalIssues: allResults.reduce((sum, r) =>
      sum + Object.values(r.checks).reduce((s, c) => s + (c?.failed || 0), 0), 0
    )
  }

  log('====================================')
  log('实现核查工作流完成')
  log(`模块: ${summary.modulesChecked}, 检查项: ${summary.checksRun}`)
  log(`总问题数: ${summary.totalIssues}`)
  log(`结果: ${summary.success ? '全部通过' : '存在问题'}`)
  log('====================================')

  return { summary, results: allResults, report }
}

main()
