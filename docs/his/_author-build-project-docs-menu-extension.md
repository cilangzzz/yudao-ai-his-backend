# author-build-project-docs 菜单文档生成功能扩展建议

> 本文档说明如何在 `author-build-project-docs` skill 中添加菜单文档生成功能

---

## 问题背景

当前 `author-build-project-docs` skill 生成 API 文档时，缺少前端开发人员需要的**菜单配置文档**。

菜单文档应包含：
- 菜单层级结构
- 路由配置
- 组件路径
- 权限标识
- 按钮权限列表

---

## 建议修改

### 1. 在 skill 中添加菜单文档生成阶段

在现有的 API 文档生成流程中，增加菜单文档生成阶段：

```yaml
# author-build-project-docs.yaml (建议添加的内容)

phases:
  - name: "解析菜单数据"
    description: "从 SQL 文件或数据库提取菜单结构"
    input:
      - "sql/mysql/his/data/*_menu_data.sql"
      - "或数据库 system_menu 表"
    output:
      - "菜单层级结构 JSON"

  - name: "生成菜单文档"
    description: "按模块生成前端菜单配置文档"
    template: "references/templates/menu-doc-template.md"
    output:
      - "docs/his/{模块编号}-{模块名称}/{模块编号}-菜单配置文档.md"
```

### 2. 添加菜单文档模板

创建 `references/templates/menu-doc-template.md` 模板文件，用于生成菜单文档。

模板结构参考本项目中的 `docs/his/_menu-doc-template.md`。

### 3. 在文档索引中添加菜单文档链接

生成的 `docs/his/README.md` 中应包含菜单文档的索引：

```markdown
## 菜单配置文档

| 模块 | 菜单文档 | 说明 |
|------|----------|------|
| M01 门诊管理 | [M01-菜单配置文档.md](M01-门诊管理/M01-菜单配置文档.md) | 门诊管理菜单配置 |
| M02 住院管理 | [M02-菜单配置文档.md](M02-住院管理/M02-菜单配置文档.md) | 住院管理菜单配置 |
| M06 药品管理 | [M06-菜单配置文档.md](M06-药品管理/M06-菜单配置文档.md) | 药品管理菜单配置 |
```

---

## 菜单文档格式规范

### 文档结构

```markdown
# {模块名称} 菜单配置文档

> **模块**: {模块名称}
> **模块编号**: {模块编号}
> **更新日期**: {日期}

## 菜单层级结构

```
HIS医院信息系统
├── {一级菜单}
│   ├── {二级菜单}
│   │   ├── {按钮权限}
│   │   └── ...
│   └── ...
└── ...
```

## 页面菜单配置表

| 菜单名称 | 路由路径 | 组件路径 | 组件名称 | 图标 | 排序 |
|----------|----------|----------|----------|------|------|
| {name} | {path} | {component} | {component_name} | {icon} | {sort} |

## 按钮权限配置表

| 操作名称 | 权限标识 | 父菜单 |
|----------|----------|--------|
| {操作} | {permission} | {父菜单名} |

## 前端路由配置

// Vue Router 配置代码示例

## Vue 组件目录结构

// 组件目录结构建议
```

### 数据来源

菜单数据可从以下来源获取：

1. **SQL 文件**: `sql/mysql/his/data/*_menu_data.sql`
2. **数据库查询**: `SELECT * FROM system_menu WHERE deleted = 0 ORDER BY parent_id, sort`
3. **Controller 注解**: 解析 `@RequestMapping` 注解中的路径信息

---

## 本地替代方案

如果无法修改外部 skill，本项目提供了本地替代方案：

### 菜单文档生成工作流

文件: `.claude/workflows/his-menu-doc-generator.js`

执行方式:
```bash
/Workflow his-menu-doc-generator
```

### 菜单文档模板

文件: `docs/his/_menu-doc-template.md`

---

## 变更历史

| 版本 | 日期 | 变更内容 |
|------|------|----------|
| v1.0 | 2026-06-19 | 初始版本，添加菜单文档生成功能建议 |