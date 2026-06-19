/**
 * HIS 菜单文档生成工作流
 *
 * 从菜单 SQL 文件或数据库提取菜单信息，生成前端开发人员可阅读的菜单文档
 *
 * 执行方式:
 * 1. 完整执行: 生成所有 HIS 模块的菜单文档
 * 2. 单模块执行: args = { module: "M01" }
 */

export const meta = {
  name: 'his-menu-doc-generator',
  description: 'HIS 菜单文档生成工作流 - 从 SQL/数据库提取菜单信息生成前端文档',
  whenToUse: '需要生成前端菜单配置文档时使用',
  phases: [
    { title: '解析菜单数据', detail: '读取 SQL 文件或数据库提取菜单结构' },
    { title: '生成菜单树', detail: '构建菜单层级关系' },
    { title: '生成文档', detail: '按模块生成菜单配置文档' }
  ]
}

// ============================================
// 配置定义
// ============================================

const MODULE_CONFIG = {
  'M01': {
    name: '门诊管理',
    code: 'outpatient',
    path: '/his/outpatient',
    parentMenuName: '门诊管理'
  },
  'M02': {
    name: '住院管理',
    code: 'inpatient',
    path: '/his/inpatient',
    parentMenuName: '住院管理'
  },
  'M06': {
    name: '药品管理',
    code: 'pharmacy',
    path: '/his/pharmacy',
    parentMenuName: '药品管理'
  },
  'M04': {
    name: '检验管理',
    code: 'lab',
    path: '/his/lab',
    parentMenuName: '检验管理'
  },
  'M05': {
    name: '影像管理',
    code: 'exam',
    path: '/his/exam',
    parentMenuName: '影像管理'
  }
}

const MENU_TYPE_MAP = {
  1: '目录',
  2: '菜单',
  3: '按钮'
}

// JSON Schema 定义
const MENU_SCHEMA = {
  type: 'object',
  properties: {
    menus: {
      type: 'array',
      items: {
        type: 'object',
        properties: {
          id: { type: 'integer' },
          name: { type: 'string' },
          permission: { type: 'string' },
          type: { type: 'integer' },
          sort: { type: 'integer' },
          parentId: { type: 'integer' },
          path: { type: 'string' },
          icon: { type: 'string' },
          component: { type: 'string' },
          componentName: { type: 'string' },
          status: { type: 'integer' },
          visible: { type: 'boolean' },
          keepAlive: { type: 'boolean' },
          alwaysShow: { type: 'boolean' }
        }
      }
    }
  }
}

const DOC_RESULT_SCHEMA = {
  type: 'object',
  properties: {
    success: { type: 'boolean' },
    moduleName: { type: 'string' },
    fileCreated: { type: 'string' },
    menuCount: { type: 'integer' },
    buttonCount: { type: 'integer' }
  }
}

// ============================================
// Phase 1: 解析菜单数据
// ============================================

async function phase1ParseMenuData() {
  phase('解析菜单数据')

  log('开始解析菜单数据...')

  // 从 SQL 文件解析菜单数据
  const menuData = await agent('解析菜单 SQL 文件', {
    prompt: `读取 sql/mysql/his/data/20260618_his_menu_data.sql 文件
    提取所有 INSERT INTO system_menu 语句中的菜单信息
    解析每个菜单的字段值，输出结构化的菜单列表

    需要提取的字段:
    - name: 菜单名称
    - permission: 权限标识
    - type: 菜单类型(1目录/2菜单/3按钮)
    - sort: 排序
    - path: 路由路径
    - icon: 图标
    - component: 组件路径
    - component_name: 组件名称
    - status: 状态
    - visible: 是否可见
    - keep_alive: 是否缓存
    - always_show: 是否总是显示

    注意：SQL 使用变量 @parent_id 等表示父子关系，需要根据上下文推断父子关系`,
    phase: '解析菜单数据',
    schema: MENU_SCHEMA
  })

  log(`解析完成: ${menuData.menus?.length || 0} 个菜单项`)

  return menuData
}

// ============================================
// Phase 2: 生成菜单树
// ============================================

async function phase2BuildMenuTree(menuData) {
  phase('生成菜单树')

  log('构建菜单层级关系...')

  const menuTree = await agent('构建菜单树结构', {
    prompt: `基于以下菜单数据，构建树形结构:

    菜单数据: ${JSON.stringify(menuData.menus, null, 2)}

    要求:
    1. 根据 parentId 或上下文关系构建父子关系
    2. 按照 type 分类: 目录(type=1) → 菜单(type=2) → 按钮(type=3)
    3. 按 sort 排序
    4. 输出每个模块的完整菜单树`,
    phase: '生成菜单树',
    schema: {
      type: 'object',
      properties: {
        modules: {
          type: 'array',
          items: {
            type: 'object',
            properties: {
              moduleCode: { type: 'string' },
              moduleName: { type: 'string' },
              tree: {
                type: 'array',
                items: {
                  type: 'object',
                  properties: {
                    name: { type: 'string' },
                    type: { type: 'integer' },
                    path: { type: 'string' },
                    component: { type: 'string' },
                    children: { type: 'array' }
                  }
                }
              }
            }
          }
        }
      }
    }
  })

  log(`菜单树构建完成: ${menuTree.modules?.length || 0} 个模块`)

  return menuTree
}

// ============================================
// Phase 3: 生成文档
// ============================================

async function phase3GenerateDocs(menuTree) {
  phase('生成文档')

  log('开始生成菜单文档...')

  const args = globalThis.args || {}
  const modules = args.module ? [args.module] : Object.keys(MODULE_CONFIG)

  const results = await parallel(
    modules.map(moduleCode => () => generateModuleMenuDoc(moduleCode, menuTree))
  )

  const summary = {
    totalModules: modules.length,
    completed: results.filter(r => r && r.success).length,
    totalMenus: results.reduce((sum, r) => sum + (r?.menuCount || 0), 0),
    totalButtons: results.reduce((sum, r) => sum + (r?.buttonCount || 0), 0)
  }

  log(`文档生成完成: ${summary.completed}/${summary.totalModules} 模块`)
  log(`总计: ${summary.totalMenus} 个菜单, ${summary.totalButtons} 个按钮权限`)

  return { results, summary }
}

async function generateModuleMenuDoc(moduleCode, menuTree) {
  const config = MODULE_CONFIG[moduleCode]
  if (!config) {
    log(`未找到模块配置: ${moduleCode}`)
    return null
  }

  log(`生成 ${config.name} 模块菜单文档`)

  // 找到该模块的菜单数据
  const moduleMenus = menuTree.modules?.find(m => m.moduleCode === moduleCode)?.tree || []

  // 生成文档内容
  const docResult = await agent(`生成 ${config.name} 菜单文档`, {
    prompt: `生成 ${config.name} 模块的菜单配置文档

    模块配置:
    - 模块编号: ${moduleCode}
    - 模块名称: ${config.name}
    - 路由前缀: ${config.path}
    - 日期: 2026-06-19

    菜单数据:
    ${JSON.stringify(moduleMenus, null, 2)}

    按照以下格式生成 markdown 文档:

    # {模块名称} 菜单配置文档

    > **模块**: {模块名称}
    > **模块编号**: {模块编号}
    > **更新日期**: {日期}

    ## 菜单层级结构

    ```
    HIS医院信息系统
    ├── {模块名称}
    │   ├── {二级菜单1}
    │   │   ├── {按钮权限1}
    │   │   └── {按钮权限2}
    │   └── {二级菜单2}
    ```

    ## 页面菜单配置表

    | 菜单名称 | 路由路径 | 组件路径 | 组件名称 | 图标 | 排序 |
    |----------|----------|----------|----------|------|------|

    ## 按钮权限配置表

    | 操作名称 | 权限标识 | 父菜单 |
    |----------|----------|--------|

    ## 前端路由配置

    ```javascript
    // 完整的路由配置代码
    ```

    ## Vue 组件目录结构

    ```
    src/views/{模块路径}/
    ├── {页面1}/
    │   ├── index.vue
    │   └── components/
    └── {页面2}/
    ```

    将文档保存到: docs/his/${moduleCode}-${config.name}/${moduleCode}-菜单配置文档.md`,
    phase: '生成文档',
    schema: DOC_RESULT_SCHEMA
  })

  if (docResult && docResult.success) {
    log(`${config.name} 菜单文档生成成功: ${docResult.menuCount} 个菜单`)
  }

  return docResult
}

// ============================================
// 主执行流程
// ============================================

async function main() {
  log('====================================')
  log('HIS 菜单文档生成工作流启动')
  log('====================================')

  const args = globalThis.args || {}

  // Phase 1: 解析菜单数据
  const menuData = await phase1ParseMenuData()
  if (!menuData.menus || menuData.menus.length === 0) {
    log('未找到菜单数据，终止执行')
    return { success: false, phase: 'parse', errors: ['未找到菜单数据'] }
  }

  // Phase 2: 构建菜单树
  const menuTree = await phase2BuildMenuTree(menuData)

  // Phase 3: 生成文档
  const docsResult = await phase3GenerateDocs(menuTree)

  // 生成最终报告
  const report = {
    success: docsResult.summary.completed > 0,
    menuData: {
      total: menuData.menus.length
    },
    docs: docsResult.results.filter(Boolean),
    summary: docsResult.summary
  }

  log('====================================')
  log('HIS 菜单文档生成工作流完成')
  log(`生成模块: ${report.summary.completed}/${report.summary.totalModules}`)
  log(`菜单总数: ${report.summary.totalMenus}`)
  log(`按钮总数: ${report.summary.totalButtons}`)
  log('====================================')

  return report
}

// 执行主流程
main()