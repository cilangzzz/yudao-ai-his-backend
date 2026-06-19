/**
 * HIS 需求文档同步工作流
 *
 * 从外部设计文档目录同步需求、设计和数据文档到项目 docs/his 目录
 * 确保项目文档与设计文档保持一致
 *
 * 执行方式:
 * 1. 完整同步: /his-doc-sync
 * 2. 同步指定模块: /his-doc-sync {"module": "M01"}
 * 3. 同步指定类型: /his-doc-sync {"type": "需求"}
 */

export const meta = {
  name: 'his-doc-sync',
  description: '同步 HIS 需求文档、设计文档和数据文档到项目',
  whenToUse: '当外部设计文档更新后，需要同步到项目文档目录时使用',
  phases: [
    { title: '扫描源文档', detail: '扫描外部设计文档目录' },
    { title: '分析差异', detail: '对比现有文档找出变更' },
    { title: '同步文档', detail: '复制更新的文档到项目' },
    { title: '更新索引', detail: '更新文档索引和目录' }
  ]
}

// 文档源目录配置（根据实际情况调整）
const DOC_SOURCES = {
  requirements: {
    name: '需求文档',
    sourcePath: 'F:/projects/yudao-ai-his/01-需求开发',
    targetPath: 'docs/his',
    pattern: '**/*.md'
  },
  design: {
    name: '设计文档',
    sourcePath: 'F:/projects/yudao-ai-his/02-系统设计',
    targetPath: 'docs/his',
    pattern: '**/*.md'
  },
  database: {
    name: '数据库设计',
    sourcePath: 'F:/projects/yudao-ai-his/03-概要设计',
    targetPath: 'docs/his',
    pattern: '**/*.md'
  }
}

// 模块配置
const MODULES = ['M01', 'M02', 'M03', 'M04', 'M05', 'M06', 'M07', 'M08', 'M09', 'M10', 'M11', 'M12', 'M13']

const SYNC_RESULT_SCHEMA = {
  type: 'object',
  properties: {
    success: { type: 'boolean' },
    scanned: { type: 'number' },
    added: { type: 'number' },
    updated: { type: 'number' },
    skipped: { type: 'number' },
    errors: { type: 'array', items: { type: 'string' } },
    details: {
      type: 'array',
      items: {
        type: 'object',
        properties: {
          file: { type: 'string' },
          action: { type: 'string' },
          status: { type: 'string' }
        }
      }
    }
  }
}

// ============================================
// Phase 1: 扫描源文档
// ============================================

async function scanSourceDocs(sourcePath, pattern) {
  phase('扫描源文档')

  const result = await agent(`扫描 ${sourcePath} 目录`, {
    prompt: `扫描目录 ${sourcePath} 下所有匹配 ${pattern} 的文档文件。

    列出所有找到的文件，包括:
    - 文件路径
    - 文件大小
    - 修改时间
    - 文件内容摘要（前50行）

    输出结构化的文件清单。`,
    phase: '扫描源文档'
  })

  return result
}

// ============================================
// Phase 2: 分析差异
// ============================================

async function analyzeDifferences(sourceFiles, targetPath) {
  phase('分析差异')

  const result = await agent('对比文档差异', {
    prompt: `对比源文件列表与目标目录 ${targetPath} 的差异:

    源文件列表:
    ${JSON.stringify(sourceFiles, null, 2)}

    需要:
    1. 检查目标目录中是否存在同名文件
    2. 对比文件内容是否有变化
    3. 标记每个文件的状态: 新增/更新/无变化

    输出差异报告。`,
    phase: '分析差异',
    schema: SYNC_RESULT_SCHEMA
  })

  return result
}

// ============================================
// Phase 3: 同步文档
// ============================================

async function syncDocuments(diffReport, sourcePath, targetPath) {
  phase('同步文档')

  const result = await agent('执行文档同步', {
    prompt: `根据差异报告执行文档同步:

    源目录: ${sourcePath}
    目标目录: ${targetPath}

    差异报告:
    ${JSON.stringify(diffReport, null, 2)}

    执行操作:
    1. 对于新增文件: 复制到目标目录
    2. 对于更新文件: 覆盖目标文件
    3. 对于无变化文件: 跳过

    记录每个文件的操作结果。`,
    phase: '同步文档',
    schema: SYNC_RESULT_SCHEMA
  })

  return result
}

// ============================================
// Phase 4: 更新索引
// ============================================

async function updateIndex() {
  phase('更新索引')

  const result = await agent('更新文档索引', {
    prompt: `更新 HIS 文档索引文件:

    需要更新:
    1. docs/his/README.md - 主索引
    2. 各模块目录下的 README.md

    确保索引文件包含:
    - 所有文档的链接
    - 文档分类（需求/设计/数据）
    - 最后更新时间

    输出更新结果。`,
    phase: '更新索引'
  })

  return result
}

// ============================================
// 主执行流程
// ============================================

async function main() {
  log('====================================')
  log('HIS 文档同步工作流启动')
  log('====================================')

  const args = globalThis.args || {}
  const module = args.module || null
  const type = args.type || null

  const results = {
    requirements: null,
    design: null,
    database: null
  }

  // 根据参数决定同步范围
  const typesToSync = type ? [type] : Object.keys(DOC_SOURCES)

  for (const docType of typesToSync) {
    const config = DOC_SOURCES[docType]
    if (!config) {
      log(`未知的文档类型: ${docType}`)
      continue
    }

    log(`同步 ${config.name}...`)

    // Phase 1: 扫描
    const sourceFiles = await scanSourceDocs(config.sourcePath, config.pattern)

    // Phase 2: 分析差异
    const diffReport = await analyzeDifferences(sourceFiles, config.targetPath)

    // Phase 3: 同步
    const syncResult = await syncDocuments(diffReport, config.sourcePath, config.targetPath)
    results[docType] = syncResult

    log(`${config.name} 同步完成: 新增 ${syncResult.added || 0}, 更新 ${syncResult.updated || 0}`)
  }

  // Phase 4: 更新索引
  const indexResult = await updateIndex()

  // 生成报告
  const report = {
    success: Object.values(results).every(r => r && r.success !== false),
    results,
    indexUpdated: indexResult ? indexResult.success : false,
    summary: {
      totalScanned: Object.values(results).reduce((sum, r) => sum + (r?.scanned || 0), 0),
      totalAdded: Object.values(results).reduce((sum, r) => sum + (r?.added || 0), 0),
      totalUpdated: Object.values(results).reduce((sum, r) => sum + (r?.updated || 0), 0)
    }
  }

  log('====================================')
  log('文档同步工作流完成')
  log(`总计: 扫描 ${report.summary.totalScanned}, 新增 ${report.summary.totalAdded}, 更新 ${report.summary.totalUpdated}`)
  log('====================================')

  return report
}

main()
