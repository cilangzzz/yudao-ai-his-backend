# HIS 模块菜单文档

> **模块**: {模块名称}
> **模块编号**: {模块编号}
> **更新日期**: {日期}
> **说明**: 本文档供前端开发人员参考，包含菜单配置、路由信息、组件路径等信息

---

## 菜单层级结构

```
HIS医院信息系统
├── {一级菜单1}
│   ├── {二级菜单1}
│   │   ├── {按钮权限1}
│   │   ├── {按钮权限2}
│   │   └── ...
│   ├── {二级菜单2}
│   └── ...
├── {一级菜单2}
└── ...
```

---

## 菜单配置表

### 一级目录菜单

| 菜单名称 | 类型 | 路由路径 | 图标 | 排序 | 说明 |
|----------|------|----------|------|------|------|
| {菜单名} | 目录(type=1) | {path} | {icon} | {sort} | {描述} |

### 二级页面菜单

| 菜单名称 | 路由路径 | 组件路径 | 组件名称 | 图标 | 排序 | 父菜单 |
|----------|----------|----------|----------|------|------|--------|
| {菜单名} | {path} | {component} | {component_name} | {icon} | {sort} | {父菜单名} |

### 按钮权限配置

| 菜单名称 | 权限标识 | 父菜单 | 排序 |
|----------|----------|--------|------|
| {操作名} | {permission} | {父菜单名} | {sort} |

---

## 前端路由配置示例

```javascript
// router/modules/{模块}.js
export default {
  path: '/his/{模块路径}',
  component: Layout,
  redirect: '/his/{模块路径}/{默认页面}',
  name: '{模块名称}',
  meta: {
    title: '{模块标题}',
    icon: '{图标}'
  },
  children: [
    {
      path: '{页面路径}',
      component: () => import('@/views/{组件路径}/index.vue'),
      name: '{组件名称}',
      meta: {
        title: '{页面标题}',
        icon: '{图标}',
        noCache: true  // 对应 keep_alive=false
      }
    }
  ]
}
```

---

## Vue 组件创建模板

每个菜单页面组件应遵循以下结构：

```
src/views/{模块路径}/{页面路径}/
├── index.vue          # 主页面组件
├── components/        # 子组件
│   ├── SearchForm.vue # 搜索表单
│   ├── Table.vue      # 数据表格
│   └── Dialog.vue     # 弹窗表单
└── api/               # API 接口
│   └── index.js       # 接口定义
```

---

## 权限标识对照表

| 权限标识 | 对应操作 | 前端按钮 |
|----------|----------|----------|
| `{模块}:{功能}:query` | 查询 | 搜索按钮、列表查看 |
| `{模块}:{功能}:create` | 新增 | 新增按钮 |
| `{模块}:{功能}:update` | 更新 | 编辑按钮 |
| `{模块}:{功能}:delete` | 删除 | 删除按钮 |
| `{模块}:{功能}:export` | 导出 | 导出按钮 |

### 前端权限校验示例

```vue
<template>
  <el-button v-hasPermi="['{模块}:{功能}:create']" type="primary">
    新增
  </el-button>
  <el-button v-hasPermi="['{模块}:{功能}:update']" type="success">
    编辑
  </el-button>
  <el-button v-hasPermi="['{模块}:{功能}:delete']" type="danger">
    删除
  </el-button>
</template>
```

---

## 菜单 SQL 插入模板

```sql
-- 创建一级目录
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`)
VALUES ('{目录名称}', '', 1, {sort}, {父ID}, '{path}', '{icon}', NULL, NULL, 0, b'1', b'1', b'1');

-- 创建二级菜单
SET @parent_id = LAST_INSERT_ID();
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`)
VALUES ('{菜单名称}', '', 2, {sort}, @parent_id, '{path}', '{icon}', '{component}', '{component_name}', 0, b'1', b'1', b'0');

-- 创建按钮权限
SET @menu_id = LAST_INSERT_ID();
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `status`, `visible`, `keep_alive`)
VALUES
('{操作名称}查询', '{模块}:{功能}:query', 3, 1, @menu_id, 0, b'1', b'1'),
('{操作名称}新增', '{模块}:{功能}:create', 3, 2, @menu_id, 0, b'1', b'1'),
('{操作名称}修改', '{模块}:{功能}:update', 3, 3, @menu_id, 0, b'1', b'1'),
('{操作名称}删除', '{模块}:{功能}:delete', 3, 4, @menu_id, 0, b'1', b'1');
```

---

## 变更历史

| 版本 | 日期 | 变更内容 | 变更人 |
|------|------|----------|--------|
| v1.0 | {日期} | 初始版本 | {作者} |