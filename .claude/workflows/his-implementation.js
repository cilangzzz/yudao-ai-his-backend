/**
 * HIS 模块实现工作流脚本
 *
 * 基于已设计文档自动化实现 M01、M02、M06 三个 HIS 核心模块
 * 注意: M09 系统管理由 yudao-module-system 提供，无需重复开发
 *
 * 执行方式:
 * 1. 完整执行: 运行整个工作流
 * 2. 单 Sprint 执行: args = { sprint: "sprint1" }
 * 3. 单模块执行: args = { module: "M01", submodule: "M01-01" }
 */

export const meta = {
  name: 'his-module-implementation',
  description: 'HIS 核心模块自动化实现工作流（M01门诊/M02住院/M06药品）',
  whenToUse: '需要实现 HIS 系统核心业务模块时使用，系统管理模块(M09)由yudao框架自带',
  phases: [
    { title: '项目准备', detail: '创建HIS模块骨架和数据库初始化' },
    { title: 'Sprint 1', detail: 'M01-01 挂号管理实现' },
    { title: 'Sprint 2', detail: 'M01-02 门诊医生工作站实现' },
    { title: 'Sprint 3', detail: 'M01-03 门诊收费管理实现' },
    { title: 'Sprint 4', detail: 'M01-04 门诊药房管理实现' },
    { title: 'Sprint 5', detail: 'M02-01 入院管理实现' },
    { title: 'Sprint 6', detail: 'M02-02 住院医生工作站实现' },
    { title: 'Sprint 7', detail: 'M02-03 护理工作站实现' },
    { title: 'Sprint 8', detail: 'M02-04/05 床位+出院管理实现' },
    { title: 'Sprint 9', detail: 'M06-01/02 药库+采购管理实现' },
    { title: 'Sprint 10', detail: 'M06-03/04 处方审核+特殊药品实现' },
    { title: '集成验证', detail: '跨模块集成测试和业务流程验证' }
  ]
}

// ============================================
// 配置定义
// ============================================

// Sprint 配置（M09由yudao-system模块提供，跳过）
const SPRINT_CONFIG = {
  sprint1: {
    name: 'Sprint 1: 挂号管理',
    weeks: '第1-2周',
    modules: ['M01'],
    submodules: ['M01-01'],
    milestone: null
  },
  sprint2: {
    name: 'Sprint 2: 门诊医生工作站',
    weeks: '第3-4周',
    modules: ['M01'],
    submodules: ['M01-02'],
    milestone: null
  },
  sprint3: {
    name: 'Sprint 3: 门诊收费管理',
    weeks: '第5-6周',
    modules: ['M01'],
    submodules: ['M01-03'],
    milestone: null
  },
  sprint4: {
    name: 'Sprint 4: 门诊药房管理',
    weeks: '第7-8周',
    modules: ['M01'],
    submodules: ['M01-04'],
    milestone: 'M1' // 门诊闭环完成
  },
  sprint5: {
    name: 'Sprint 5: 入院管理',
    weeks: '第9-11周',
    modules: ['M02'],
    submodules: ['M02-01'],
    milestone: null
  },
  sprint6: {
    name: 'Sprint 6: 住院医生工作站',
    weeks: '第12-14周',
    modules: ['M02'],
    submodules: ['M02-02'],
    milestone: null
  },
  sprint7: {
    name: 'Sprint 7: 护理工作站',
    weeks: '第15-17周',
    modules: ['M02'],
    submodules: ['M02-03'],
    milestone: null
  },
  sprint8: {
    name: 'Sprint 8: 床位+出院管理',
    weeks: '第18-20周',
    modules: ['M02'],
    submodules: ['M02-04', 'M02-05'],
    milestone: 'M2' // 住院闭环完成
  },
  sprint9: {
    name: 'Sprint 9: 药库+采购管理',
    weeks: '第21-24周',
    modules: ['M06'],
    submodules: ['M06-01', 'M06-02'],
    milestone: null
  },
  sprint10: {
    name: 'Sprint 10: 处方审核+特殊药品',
    weeks: '第25-28周',
    modules: ['M06'],
    submodules: ['M06-03', 'M06-04'],
    milestone: 'M3' // 药品管理完成
  }
}

// 子模块配置
const SUBMODULE_CONFIG = {
  // M01 门诊管理
  'M01-01': {
    name: '挂号管理',
    domain: 'register',
    module: 'M01',
    tables: ['his_patient', 'his_patient_card', 'op_schedule', 'op_appointment', 'op_register'],
    description: '现场挂号、预约挂号、号源管理、分诊排队、退号处理'
  },
  'M01-02': {
    name: '门诊医生工作站',
    domain: 'op-doctor',
    module: 'M01',
    tables: ['op_prescription', 'op_prescription_item', 'his_diagnosis'],
    description: '接诊管理、诊断录入、处方开立、CDS校验、检验申请'
  },
  'M01-03': {
    name: '门诊收费管理',
    domain: 'op-fee',
    module: 'M01',
    tables: ['op_fee', 'op_payment', 'op_refund'],
    description: '费用汇总、医保结算、退费管理、日结对账'
  },
  'M01-04': {
    name: '门诊药房管理',
    domain: 'op-pharmacy',
    module: 'M01',
    tables: ['op_dispense', 'op_dispense_item'],
    description: '处方接收、审核、调配、发药、退药'
  },

  // M02 住院管理
  'M02-01': {
    name: '入院管理',
    domain: 'admission',
    module: 'M02',
    tables: ['his_admission', 'his_bed', 'his_prepayment', 'his_admission_assess'],
    description: '入院登记、床位分配、预交金、入院评估、医保登记'
  },
  'M02-02': {
    name: '住院医生工作站',
    domain: 'ip-doctor',
    module: 'M02',
    tables: ['his_order', 'his_order_item', 'his_order_exec', 'his_diagnosis'],
    description: '医嘱开立、医嘱审核、诊断管理、病历书写、手术申请'
  },
  'M02-03': {
    name: '护理工作站',
    domain: 'nursing',
    module: 'M02',
    tables: ['his_medication_admin', 'his_nursing_record', 'his_nursing_shift', 'his_vital_sign'],
    description: '医嘱执行、eMAR给药、护理记录、交接班、生命体征'
  },
  'M02-04': {
    name: '床位管理',
    domain: 'bed',
    module: 'M02',
    tables: ['his_bed', 'his_bed_history', 'his_ward'],
    description: '床位图、床位状态管理、床位调动'
  },
  'M02-05': {
    name: '出院管理',
    domain: 'discharge',
    module: 'M02',
    tables: ['his_discharge', 'his_discharge_summary', 'his_discharge_fee'],
    description: '出院申请、出院结算、带药管理、病案归档'
  },

  // M06 药品管理
  'M06-01': {
    name: '药库管理',
    domain: 'drug-store',
    module: 'M06',
    tables: ['his_drug', 'his_drug_stock', 'his_drug_inbound', 'his_drug_outbound', 'his_drug_inventory'],
    description: '入库验收、出库、盘点、效期管理、批号追踪'
  },
  'M06-02': {
    name: '采购管理',
    domain: 'drug-purchase',
    module: 'M06',
    tables: ['his_drug_purchase', 'his_drug_purchase_item', 'his_supplier'],
    description: '采购申请、审批、采购订单、供应商管理'
  },
  'M06-03': {
    name: '处方审核与合理用药',
    domain: 'drug-audit',
    module: 'M06',
    tables: ['his_drug_interaction', 'his_drug_contraindication', 'his_drug_dose_rule'],
    description: '合理用药审核、药物相互作用、配伍禁忌、剂量校验'
  },
  'M06-04': {
    name: '特殊药品管理',
    domain: 'drug-special',
    module: 'M06',
    tables: ['his_special_drug', 'his_special_drug_record', 'his_narcotic_prescription'],
    description: '麻醉药品五专管理、专用处方、追溯管理'
  }
}

// 里程碑配置
const MILESTONES = {
  M1: {
    name: '门诊闭环完成',
    description: '挂号→接诊→收费→发药流程贯通',
   验收标准: ['挂号功能可用', '医生工作站可用', '收费结算可用', '药房发药可用']
  },
  M2: {
    name: '住院闭环完成',
    description: '入院→医嘱→护理→出院闭环，eMAR核对率100%',
    验收标准: ['入院登记可用', '医嘱管理可用', '护理执行可用', '出院结算可用']
  },
  M3: {
    name: '药品管理完成',
    description: '药库入库→出库→处方审核→特殊药品管理可用',
    验收标准: ['药库管理可用', '采购管理可用', '处方审核可用', '特殊药品管理可用']
  }
}

// JSON Schema 定义
const ENTITY_SCHEMA = {
  type: 'object',
  properties: {
    entities: {
      type: 'array',
      items: {
        type: 'object',
        properties: {
          name: { type: 'string' },
          tableName: { type: 'string' },
          fields: {
            type: 'array',
            items: {
              type: 'object',
              properties: {
                name: { type: 'string' },
                type: { type: 'string' },
                comment: { type: 'string' },
                nullable: { type: 'boolean' }
              }
            }
          }
        }
      }
    },
    businessRules: {
      type: 'array',
      items: {
        type: 'object',
        properties: {
          code: { type: 'string' },
          description: { type: 'string' }
        }
      }
    }
  }
}

const IMPLEMENT_RESULT_SCHEMA = {
  type: 'object',
  properties: {
    success: { type: 'boolean' },
    filesCreated: { type: 'array', items: { type: 'string' } },
    filesModified: { type: 'array', items: { type: 'string' } },
    errors: { type: 'array', items: { type: 'string' } },
    warnings: { type: 'array', items: { type: 'string' } }
  }
}

// ============================================
// Phase 1: 项目准备
// ============================================

async function phase1ProjectSetup() {
  phase('项目准备')

  log('开始项目准备阶段...')
  log('注意: M09系统管理由yudao-module-system提供，无需开发')

  // 1.1 创建 HIS 模块骨架
  const moduleStructure = await agent('创建 HIS 模块目录结构', {
    prompt: `创建 yudao-module-his 模块的完整目录结构:

    需要创建的目录:
    - yudao-module-his/yudao-module-his-api/src/main/java/cn/iocoder/yudao/module/his/api/
    - yudao-module-his/yudao-module-his-api/src/main/java/cn/iocoder/yudao/module/his/enums/
    - yudao-module-his/yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/controller/admin/
    - yudao-module-his/yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/service/
    - yudao-module-his/yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/dal/dataobject/
    - yudao-module-his/yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/dal/mysql/
    - yudao-module-his/yudao-module-his-biz/src/test/java/cn/iocoder/yudao/module/his/

    需要创建的配置文件:
    - yudao-module-his/pom.xml (父模块)
    - yudao-module-his/yudao-module-his-api/pom.xml
    - yudao-module-his/yudao-module-his-biz/pom.xml

    参考 yudao-module-system 模块的结构和 pom.xml 配置。
    确保 Maven 依赖正确配置，依赖 yudao-module-system 用于用户、权限等功能。`,
    phase: '项目准备',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  log(`模块骨架创建: ${moduleStructure.success ? '成功' : '失败'}`)

  // 1.2 解析数据库设计文档
  const dbDesigns = await parallel([
    () => agent('解析 M01 数据库设计', {
      prompt: `读取 docs/his/M01-门诊管理/M01-门诊管理-数据库设计.md
      提取所有表的 DDL 定义，输出结构化的表定义清单。
      包括表名、字段列表、索引、外键约束。`,
      phase: '项目准备',
      schema: ENTITY_SCHEMA
    }),
    () => agent('解析 M02 数据库设计', {
      prompt: `读取 docs/his/M02-住院管理/M02-住院管理-数据库设计.md
      提取所有表的 DDL 定义，输出结构化的表定义清单。`,
      phase: '项目准备',
      schema: ENTITY_SCHEMA
    }),
    () => agent('解析 M06 数据库设计', {
      prompt: `读取 docs/his/M06-药品管理/M06-药品管理-数据库设计.md
      提取所有表的 DDL 定义，输出结构化的表定义清单。`,
      phase: '项目准备',
      schema: ENTITY_SCHEMA
    })
  ])

  log(`数据库设计解析完成: ${dbDesigns.filter(Boolean).length}/3 个模块`)

  // 1.3 生成数据库初始化脚本
  const dbInit = await agent('生成数据库初始化脚本', {
    prompt: `基于解析的数据库设计，生成完整的初始化 SQL 脚本:
    - 创建数据库 his_db
    - 创建所有表（按依赖顺序）
    - 插入 HIS 基础数据（字典、科室等）
    输出到: sql/his-init.sql`,
    phase: '项目准备',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  log(`数据库初始化脚本: ${dbInit.success ? '成功' : '失败'}`)

  return {
    moduleStructure: moduleStructure.success,
    dbDesigns: dbDesigns.filter(Boolean),
    dbInit: dbInit.success
  }
}

// ============================================
// Phase 2: 子模块实现
// ============================================

async function implementSubmodule(submoduleId) {
  const config = SUBMODULE_CONFIG[submoduleId]
  if (!config) {
    log(`未找到子模块配置: ${submoduleId}`)
    return null
  }

  log(`开始实现 ${submoduleId} - ${config.name}`)
  log(`功能范围: ${config.description}`)

  // 阶段1: 读取功能点需求
  const requirements = await agent(`读取 ${config.name} 功能点需求`, {
    prompt: `读取 docs/his/${config.module}/${submoduleId}/${submoduleId}-功能点需求.md
    提取:
    1. 功能点列表
    2. 业务规则
    3. 字段定义
    4. 接口需求
    输出结构化的需求定义。`,
    phase: '分析需求',
    schema: ENTITY_SCHEMA
  })

  // 阶段2: 生成实体类（并行生成所有表对应的DO）
  const entityResults = await parallel(
    config.tables.map(table => () => agent(`生成 ${table} 实体类`, {
      prompt: `根据数据库设计生成 ${table} 对应的 DO 实体类和 Mapper:

      1. DO 实体类:
         - 类名: 转换为驼峰命名 (如 his_patient -> HisPatientDO)
         - 继承 BaseDO
         - 使用 @TableName 注解
         - 字段使用 @TableField 注解
         - 生成路径: yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/dal/dataobject/${config.domain}/

      2. Mapper 接口:
         - 继承 BaseMapperX<DO类>
         - 实现分页查询方法
         - 实现常用查询方法
         - 生成路径: yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/dal/mysql/${config.domain}/

      遵循 yudao 代码规范，参考 skills/design/entity-designer.yaml`,
      phase: '生成实体类',
      schema: IMPLEMENT_RESULT_SCHEMA
    }))
  )

  // 阶段3: 生成 VO 类
  const domainName = config.domain.split('-').map(s => s.charAt(0).toUpperCase() + s.slice(1)).join('')
  const vos = await parallel([
    () => agent(`生成 ${config.name} SaveReqVO`, {
      prompt: `生成保存请求 VO:
      - 类名: ${domainName}SaveReqVO
      - 包含 id (更新时必填) 和所有可编辑字段
      - 使用 @Data + @ApiModel 注解
      - 添加校验注解 (@NotNull, @NotBlank 等)
      - 生成路径: yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/controller/admin/${config.domain}/vo/`,
      phase: '生成VO',
      schema: IMPLEMENT_RESULT_SCHEMA
    }),
    () => agent(`生成 ${config.name} PageReqVO`, {
      prompt: `生成分页请求 VO:
      - 类名: ${domainName}PageReqVO
      - 继承 PageParam
      - 包含常用查询条件字段
      - 使用 @Data + @ApiModel 注解
      - 生成路径: yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/controller/admin/${config.domain}/vo/`,
      phase: '生成VO',
      schema: IMPLEMENT_RESULT_SCHEMA
    }),
    () => agent(`生成 ${config.name} RespVO`, {
      prompt: `生成响应 VO:
      - 类名: ${domainName}RespVO
      - 包含所有展示字段
      - 使用 @Data + @ApiModel 注解
      - 生成路径: yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/controller/admin/${config.domain}/vo/`,
      phase: '生成VO',
      schema: IMPLEMENT_RESULT_SCHEMA
    })
  ])

  // 阶段4: 生成 Service
  const service = await parallel([
    () => agent(`生成 ${config.name} Service 接口`, {
      prompt: `生成 Service 接口:
      - 类名: ${domainName}Service
      - 定义 CRUD 方法 (create/update/delete/get/page)
      - 定义业务方法 (根据功能点需求)
      - 生成路径: yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/service/${config.domain}/
      参考 skills/design/crud-designer.yaml`,
      phase: '生成Service',
      schema: IMPLEMENT_RESULT_SCHEMA
    }),
    () => agent(`生成 ${config.name} ServiceImpl 实现类`, {
      prompt: `生成 Service 实现类:
      - 类名: ${domainName}ServiceImpl
      - 使用 @Service 注解
      - 实现所有接口方法
      - 注入需要的 Mapper
      - 实现业务逻辑和业务规则校验
      - 生成路径: yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/service/${config.domain}/`,
      phase: '生成Service',
      schema: IMPLEMENT_RESULT_SCHEMA
    })
  ])

  // 阶段5: 生成 Controller
  const controller = await agent(`生成 ${config.name} Controller`, {
    prompt: `生成 Controller:
    - 类名: ${domainName}Controller
    - 使用 @RestController + @RequestMapping("/his/${config.domain}") 注解
    - 实现 CRUD 接口
    - 添加 Swagger 注解 (@Tag, @Operation)
    - 添加权限注解 (@PreAuthorize)
    - 权限标识格式: his:${config.domain}:xxx
    - 生成路径: yudao-module-his-biz/src/main/java/cn/iocoder/yudao/module/his/controller/admin/${config.domain}/
    参考 skills/design/api-designer.yaml`,
    phase: '生成Controller',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  // 阶段6: 生成错误码枚举
  const errorCode = await agent(`生成 ${config.name} 错误码`, {
    prompt: `生成错误码枚举类:
    - 类名: ErrorCodeConstants (追加到现有文件或新建)
    - 错误码前缀: 根据 CLAUDE.md 中的规范
    - 定义业务错误码
    - 生成路径: yudao-module-his-api/src/main/java/cn/iocoder/yudao/module/his/enums/`,
    phase: '生成错误码',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  // 阶段7: 生成单元测试
  const tests = await agent(`生成 ${config.name} 单元测试`, {
    prompt: `生成单元测试类:
    - 测试类名: ${domainName}ServiceImplTest
    - 覆盖 CRUD 操作测试
    - 覆盖业务规则测试
    - 使用 @SpringBootTest 注解
    - 生成路径: yudao-module-his-biz/src/test/java/cn/iocoder/yudao/module/his/service/${config.domain}/`,
    phase: '生成测试',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  const result = {
    submoduleId,
    name: config.name,
    domain: config.domain,
    entities: entityResults.filter(Boolean).length,
    vos: vos.filter(Boolean).length,
    service: service.filter(Boolean).length,
    controller: controller ? controller.success : false,
    errorCode: errorCode ? errorCode.success : false,
    tests: tests ? tests.success : false,
    success: entityResults.some(r => r && r.success) &&
             (controller ? controller.success : false)
  }

  log(`${submoduleId} 实现完成: ${result.success ? '成功' : '有错误'}`)
  return result
}

async function runSprint(sprintId) {
  const config = SPRINT_CONFIG[sprintId]
  if (!config) {
    log(`未找到 Sprint 配置: ${sprintId}`)
    return null
  }

  phase(config.name)
  log(`开始 ${config.name} (${config.weeks})`)

  // 并行实现所有子模块
  const results = await parallel(
    config.submodules.map(sm => () => implementSubmodule(sm))
  )

  // Sprint 验证
  const verification = await agent(`验证 ${config.name} 模块集成`, {
    prompt: `验证 ${config.modules.join(', ')} 模块实现:
    1. 检查所有文件是否正确生成
    2. 检查模块依赖关系
    3. 运行 mvn compile 验证编译
    4. 运行单元测试
    输出验证结果和问题清单。`,
    phase: 'Sprint验证',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  log(`${config.name} 完成: ${verification.success ? '成功' : '有错误'}`)

  // 里程碑验收
  if (config.milestone) {
    const milestoneConfig = MILESTONES[config.milestone]
    log(`里程碑 ${config.milestone}: ${milestoneConfig.name}`)
    log(`验收标准: ${milestoneConfig.验收标准.join(', ')}`)
  }

  return {
    sprint: sprintId,
    name: config.name,
    submodules: results.filter(Boolean),
    milestone: config.milestone,
    verification
  }
}

// ============================================
// Phase 3: 集成验证
// ============================================

async function phase3IntegrationTest() {
  phase('集成验证')

  log('开始集成验证阶段...')

  // 门诊闭环测试
  const outpatientTest = await agent('门诊闭环集成测试', {
    prompt: `执行门诊业务流程集成测试:

    测试流程:
    1. 创建患者 (his_patient)
    2. 创建排班 (op_schedule)
    3. 挂号 (op_register)
    4. 医生接诊、开处方 (op_prescription)
    5. 收费结算 (op_payment)
    6. 药房发药 (op_dispense)

    验证点:
    - 状态流转正确
    - 数据关联完整
    - 业务规则生效

    输出测试报告。`,
    phase: '门诊闭环测试',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  // 住院闭环测试
  const inpatientTest = await agent('住院闭环集成测试', {
    prompt: `执行住院业务流程集成测试:

    测试流程:
    1. 入院登记 (his_admission)
    2. 床位分配 (his_bed)
    3. 开医嘱 (his_order)
    4. 护理执行/eMAR (his_medication_admin)
    5. 出院结算 (his_discharge)

    验证点:
    - 状态流转正确
    - eMAR 闭环核对
    - 数据关联完整

    输出测试报告。`,
    phase: '住院闭环测试',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  // 药品闭环测试
  const drugTest = await agent('药品闭环集成测试', {
    prompt: `执行药品业务流程集成测试:

    测试流程:
    1. 药品入库 (his_drug_inbound)
    2. 库存更新 (his_drug_stock)
    3. 处方审核 (his_drug_interaction)
    4. 药房发药
    5. 库存扣减

    验证点:
    - 库存一致性
    - 处方审核规则生效
    - 效期管理正确

    输出测试报告。`,
    phase: '药品闭环测试',
    schema: IMPLEMENT_RESULT_SCHEMA
  })

  const results = [outpatientTest, inpatientTest, drugTest].filter(Boolean)
  const allPassed = results.every(r => r && r.success)

  log(`集成验证: ${allPassed ? '全部通过' : '存在失败'}`)

  return {
    outpatient: outpatientTest,
    inpatient: inpatientTest,
    drug: drugTest,
    allPassed
  }
}

// ============================================
// 主执行流程
// ============================================

async function main() {
  log('====================================')
  log('HIS 模块实现工作流启动')
  log('====================================')
  log('M09 系统管理由 yudao-module-system 提供，无需开发')
  log('本次实现: M01门诊管理 + M02住院管理 + M06药品管理')

  const args = globalThis.args || {}

  // 根据参数决定执行范围
  if (args.submodule) {
    // 单子模块执行
    log(`单子模块模式: ${args.submodule}`)
    return await implementSubmodule(args.submodule)
  }

  if (args.sprint) {
    // 单 Sprint 执行
    log(`单 Sprint 模式: ${args.sprint}`)
    return await runSprint(args.sprint)
  }

  // 完整执行
  log('完整执行模式')

  // Phase 1: 项目准备
  const setup = await phase1ProjectSetup()
  if (!setup.moduleStructure || !setup.dbInit) {
    log('项目准备阶段失败，终止执行')
    return { success: false, phase: 'setup', errors: ['项目准备失败'] }
  }

  // Phase 2: Sprint 循环
  const sprintResults = []
  for (const sprintId of Object.keys(SPRINT_CONFIG)) {
    // 检查预算
    if (budget.total && budget.remaining() < 50000) {
      log('预算不足，暂停执行')
      break
    }

    const result = await runSprint(sprintId)
    sprintResults.push(result)
  }

  // Phase 3: 集成验证
  const integration = await phase3IntegrationTest()

  // 生成最终报告
  const report = {
    success: integration.allPassed,
    setup,
    sprints: sprintResults,
    integration,
    summary: {
      totalSubmodules: Object.keys(SUBMODULE_CONFIG).length,
      completedSubmodules: sprintResults.flatMap(s => s.submodules || []).filter(Boolean).length,
      milestones: sprintResults.filter(s => s.milestone).map(s => s.milestone),
      allTestsPassed: integration.allPassed
    }
  }

  log('====================================')
  log('HIS 模块实现工作流完成')
  log(`完成子模块: ${report.summary.completedSubmodules}/${report.summary.totalSubmodules}`)
  log(`里程碑: ${report.summary.milestones.join(', ')}`)
  log(`测试状态: ${report.summary.allTestsPassed ? '全部通过' : '存在失败'}`)
  log('====================================')

  return report
}

// 执行主流程
main()
