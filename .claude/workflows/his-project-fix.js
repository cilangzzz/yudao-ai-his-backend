/**
 * HIS 项目修复工作流
 *
 * 使用 Maven 编译检测代码问题，并自动修复
 * 包括: 编译错误、代码规范问题、依赖问题
 *
 * 执行方式:
 * 1. 完整修复: /his-project-fix
 * 2. 仅编译检测: /his-project-fix {"mode": "check"}
 * 3. 指定模块修复: /his-project-fix {"module": "his"}
 */

export const meta = {
  name: 'his-project-fix',
  description: '修复 HIS 项目代码问题，使用 Maven 编译检测',
  whenToUse: '项目编译出错或需要修复代码问题时使用',
  phases: [
    { title: '编译检测', detail: '执行 Maven compile 检查编译错误' },
    { title: '问题分析', detail: '分析编译输出找出问题' },
    { title: '问题修复', detail: '自动修复检测到的问题' },
    { title: '验证修复', detail: '重新编译验证修复结果' }
  ]
}

// Maven 命令配置
const MAVEN_COMMANDS = {
  compile: {
    command: 'mvn compile -DskipTests',
    description: '编译项目，检测编译错误'
  },
  compileWithDeps: {
    command: 'mvn compile -DskipTests -U',
    description: '编译并更新依赖'
  },
  testCompile: {
    command: 'mvn test-compile -DskipTests',
    description: '编译测试代码'
  },
  cleanCompile: {
    command: 'mvn clean compile -DskipTests',
    description: '清理后重新编译'
  }
}

// 问题类型配置
const ISSUE_TYPES = {
  compilation: {
    name: '编译错误',
    severity: 'critical',
    autoFix: true
  },
  dependency: {
    name: '依赖问题',
    severity: 'high',
    autoFix: true
  },
  syntax: {
    name: '语法错误',
    severity: 'critical',
    autoFix: true
  },
  import: {
    name: '导入错误',
    severity: 'medium',
    autoFix: true
  },
  annotation: {
    name: '注解问题',
    severity: 'medium',
    autoFix: true
  },
  naming: {
    name: '命名规范',
    severity: 'low',
    autoFix: false
  }
}

const FIX_RESULT_SCHEMA = {
  type: 'object',
  properties: {
    success: { type: 'boolean' },
    compileSuccess: { type: 'boolean' },
    issuesFound: { type: 'number' },
    issuesFixed: { type: 'number' },
    issuesRemaining: { type: 'number' },
    fixDetails: {
      type: 'array',
      items: {
        type: 'object',
        properties: {
          file: { type: 'string' },
          issue: { type: 'string' },
          fix: { type: 'string' },
          status: { type: 'string' }
        }
      }
    }
  }
}

// ============================================
// Phase 1: 编译检测
// ============================================

async function runMavenCompile(command = 'compile') {
  phase('编译检测')

  const config = MAVEN_COMMANDS[command] || MAVEN_COMMANDS.compile

  log(`执行 Maven 命令: ${config.command}`)

  // 直接执行 Maven 命令
  const result = await agent('执行 Maven 编译', {
    prompt: `在项目根目录执行 Maven 编译命令:

    命令: ${config.command}

    执行步骤:
    1. 使用 Bash 工具执行 mvn compile -DskipTests
    2. 捕获编译输出（包括错误和警告）
    3. 如果编译失败，提取所有错误信息:
       - 错误文件路径
       - 错误行号
       - 错误类型
       - 错误描述

    输出编译结果和问题清单。`,
    phase: '编译检测'
  })

  return result
}

// ============================================
// Phase 2: 问题分析
// ============================================

async function analyzeIssues(compileResult) {
  phase('问题分析')

  const result = await agent('分析编译问题', {
    prompt: `分析 Maven 编译输出，提取所有问题:

    编译输出:
    ${JSON.stringify(compileResult, null, 2)}

    分析步骤:
    1. 解析错误信息，提取文件路径和行号
    2. 分类问题类型:
       - 编译错误 (symbol not found, cannot find symbol)
       - 依赖问题 (package not found, class not found)
       - 语法错误 (syntax error)
       - 导入错误 (import cannot be resolved)
       - 注解问题 (annotation processor error)

    3. 确定每个问题的修复策略:
       - 缺少导入: 添加正确的 import 语句
       - 缺少依赖: 添加 Maven 依赖
       - 类型错误: 修正类型
       - 方法不存在: 检查并修正方法调用

    输出结构化的问题清单和修复建议。`,
    phase: '问题分析',
    schema: FIX_RESULT_SCHEMA
  })

  return result
}

// ============================================
// Phase 3: 问题修复
// ============================================

async function fixIssues(issues) {
  phase('问题修复')

  log(`开始修复问题，共 ${issues.issuesFound || 0} 个`)

  // 按文件分组问题
  const issuesByFile = {}
  for (const issue of (issues.fixDetails || [])) {
    if (!issuesByFile[issue.file]) {
      issuesByFile[issue.file] = []
    }
    issuesByFile[issue.file].push(issue)
  }

  // 并行修复每个文件
  const fixResults = await parallel(
    Object.entries(issuesByFile).map(([file, fileIssues]) =>
      () => agent(`修复 ${file}`, {
        prompt: `修复文件 ${file} 中的问题:

        问题列表:
        ${JSON.stringify(fileIssues, null, 2)}

        执行修复:
        1. 读取文件内容
        2. 针对每个问题应用修复:
           - 缺少导入: 在 import 区域添加正确的导入语句
           - 类型错误: 修改错误的类型为正确类型
           - 方法不存在: 检查并修改方法调用
           - 缺少注解: 添加必要的注解

        3. 保存修改后的文件

        输出修复结果。`,
        phase: '问题修复',
        schema: FIX_RESULT_SCHEMA
      })
    )
  )

  return fixResults.filter(Boolean)
}

// ============================================
// Phase 4: 验证修复
// ============================================

async function verifyFix() {
  phase('验证修复')

  log('重新编译验证修复结果...')

  // 执行清理后重新编译
  const result = await agent('验证修复结果', {
    prompt: `验证修复结果:

    执行步骤:
    1. 运行 mvn clean compile -DskipTests 清理并重新编译
    2. 检查是否还有编译错误
    3. 如果仍有错误，列出剩余问题

    输出验证结果。`,
    phase: '验证修复',
    schema: FIX_RESULT_SCHEMA
  })

  return result
}

// ============================================
// Phase 5: 循环修复（如果需要）
// ============================================

async function iterativeFix(maxIterations = 3) {
  let iteration = 0
  let allFixed = false
  const fixHistory = []

  while (!allFixed && iteration < maxIterations) {
    iteration++
    log(`第 ${iteration} 次迭代...`)

    // 编译检测
    const compileResult = await runMavenCompile('compile')

    // 如果编译成功，结束
    if (compileResult.success) {
      allFixed = true
      log('编译成功!')
      break
    }

    // 分析问题
    const issues = await analyzeIssues(compileResult)

    // 如果没有问题，结束
    if (!issues.issuesFound || issues.issuesFound === 0) {
      allFixed = true
      log('无问题发现')
      break
    }

    // 修复问题
    const fixResults = await fixIssues(issues)
    fixHistory.push({
      iteration,
      issuesFound: issues.issuesFound,
      issuesFixed: fixResults.reduce((sum, r) => sum + (r.issuesFixed || 0), 0)
    })

    // 检查预算
    if (budget.total && budget.remaining() < 30000) {
      log('预算不足，停止迭代')
      break
    }
  }

  return {
    iterations: iteration,
    allFixed,
    fixHistory
  }
}

// ============================================
// 主执行流程
// ============================================

async function main() {
  log('====================================')
  log('HIS 项目修复工作流启动')
  log('====================================')

  const args = globalThis.args || {}
  const mode = args.mode || 'fix'
  const module = args.module || null

  let result

  if (mode === 'check') {
    // 仅编译检测模式
    log('模式: 仅编译检测')

    const compileResult = await runMavenCompile('compile')
    const issues = await analyzeIssues(compileResult)

    result = {
      mode: 'check',
      compileSuccess: compileResult.success,
      issuesFound: issues.issuesFound || 0,
      issues: issues.fixDetails || []
    }

  } else {
    // 完整修复模式
    log('模式: 完整修复')

    // 迭代修复
    const fixResult = await iterativeFix()

    // 最终验证
    const verifyResult = await verifyFix()

    result = {
      mode: 'fix',
      success: verifyResult.compileSuccess,
      iterations: fixResult.iterations,
      totalIssuesFound: fixResult.fixHistory.reduce((sum, h) => sum + h.issuesFound, 0),
      totalIssuesFixed: fixResult.fixHistory.reduce((sum, h) => sum + h.issuesFixed, 0),
      fixHistory: fixResult.fixHistory,
      remainingIssues: verifyResult.issuesRemaining || 0
    }
  }

  log('====================================')
  log('项目修复工作流完成')
  log(`模式: ${result.mode}`)
  log(`编译状态: ${result.compileSuccess ? '成功' : '失败'}`)
  if (result.issuesFound) log(`发现问题: ${result.issuesFound}`)
  if (result.issuesFixed) log(`已修复: ${result.issuesFixed}`)
  if (result.remainingIssues) log(`剩余问题: ${result.remainingIssues}`)
  log('====================================')

  return result
}

main()