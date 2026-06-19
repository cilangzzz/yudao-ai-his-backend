# 菜单图标修复 SQL

> 本目录包含 HIS 医院信息系统菜单图标修复的 SQL 脚本。

---

## 文件说明

| 文件 | 说明 | 生成时间 |
|------|------|----------|
| `fix_menu_icons.sql` | 修复菜单图标的主 SQL 文件 | 2026-06-19 |

---

## 修复内容

### 修复统计

- **总修复数量**: 191 个菜单
- **精确匹配**: 183 个 (根据业务功能和操作类型匹配)
- **模糊匹配**: 35 个 (通过关键词匹配)
- **默认图标**: 8 个 (使用通用文件夹图标)

### 图标来源

图标格式为 `{图标集前缀}:{图标名称}`,使用的图标集包括:

| 图标集 | 前缀 | 说明 |
|--------|------|------|
| Element Plus | `ep:` | Element Plus 官方图标,适合后台管理系统 |
| Lucide | `lucide:` | 现代、简洁、风格统一,推荐优先使用 |
| Material Design | `mdi:` | Google Material Design 风格,图标丰富全面 |
| Carbon | `carbon:` | IBM Carbon Design System,适合企业级应用 |

### 图标映射规则

#### 1. 业务模块图标

根据 HIS 系统业务模块功能匹配:

```python
# 门诊管理模块
'患者管理': 'ep:user'
'预约挂号': 'lucide:calendar'
'处方管理': 'lucide:file-text'
'门诊收费': 'lucide:wallet'

# 住院管理模块
'入院管理': 'lucide:log-in'
'出院管理': 'lucide:log-out'
'床位管理': 'mdi:bed'
'医嘱管理': 'mdi:clipboard-text'

# 药品管理模块
'药品目录': 'mdi:pill'
'药品库存': 'lucide:package'
'入库管理': 'lucide:download'
'出库管理': 'lucide:upload'

# 检查检验模块
'检查申请': 'lucide:search'
'检验申请': 'lucide:search'
'标本管理': 'lucide:test-tube'

# 护理管理模块
'护理记录': 'lucide:heart'
'护理评估': 'lucide:clipboard-list'
```

#### 2. 操作按钮图标

根据操作类型匹配:

```python
# 通用操作
'新增': 'lucide:plus'
'修改': 'lucide:edit'
'删除': 'lucide:trash-2'
'查询': 'lucide:search'
'导出': 'lucide:download'
'导入': 'lucide:upload'

# 审批操作
'审核': 'lucide:file-check'
'确认': 'lucide:check'
'签名': 'lucide:pen-tool'

# 状态操作
'停止': 'lucide:square'
'取消': 'lucide:x'
'作废': 'lucide:file-x'
'执行': 'lucide:play'

# 特殊操作
'重置': 'lucide:refresh-cw'
'退款': 'lucide:undo-2'
'办理': 'lucide:clipboard-check'
'采集': 'lucide:test-tube'
```

---

## 使用方法

### 方法一: 使用 Python 脚本 (推荐)

```bash
# 1. 进入脚本目录
cd f:/projects/yudao-ai-his-backend/_ignore/sql

# 2. 安装依赖 (如果未安装)
pip install pymysql -i https://mirrors.aliyun.com/pypi/simple/

# 3. 执行脚本生成 SQL
python fix_menu_icons.py

# 4. 查看生成的 SQL 文件
# 文件位置: F:\projects\yudao-ai-his-backend\sql\mysql\20260619\fix_menu_icons.sql
```

### 方法二: 手动执行 SQL

```bash
# 1. 备份菜单表
mysql -h 127.0.0.1 -P 3306 -u root -p123456 yudao-ai-his -e "
  CREATE TABLE system_menu_backup_20260619 AS SELECT * FROM system_menu;
"

# 2. 执行修复 SQL
mysql -h 127.0.0.1 -P 3306 -u root -p123456 yudao-ai-his < sql/mysql/20260619/fix_menu_icons.sql

# 3. 清除 Redis 缓存
redis-cli DEL permission_menu_ids

# 4. 重启应用或刷新浏览器
```

---

## 验证修复结果

### SQL 验证

```sql
-- 查看修复后仍缺少图标的菜单
SELECT id, name, icon, parent_id, path
FROM system_menu
WHERE deleted = 0 AND status = 0 AND (icon IS NULL OR icon = '');

-- 查看 HIS 模块菜单图标配置
SELECT id, name, icon, path
FROM system_menu
WHERE (id = 6735 OR parent_id IN (SELECT id FROM system_menu WHERE parent_id = 6735) OR parent_id = 6735)
  AND deleted = 0
ORDER BY parent_id, id;

-- 统计图标使用情况
SELECT icon, COUNT(*) as count
FROM system_menu
WHERE deleted = 0 AND status = 0 AND icon IS NOT NULL AND icon != ''
GROUP BY icon
ORDER BY count DESC;
```

### 前端验证

1. 登录系统
2. 查看左侧菜单栏,确认图标是否正确显示
3. 如果图标未更新:
   - 清除浏览器缓存 (Ctrl + F5)
   - 清除 Redis 缓存
   - 重启应用服务

---

## 部署到其他环境

### 步骤

1. **备份数据库**

```bash
mysqldump -h {host} -P {port} -u {user} -p{password} {database} system_menu > system_menu_backup_$(date +%Y%m%d).sql
```

2. **执行修复 SQL**

```bash
mysql -h {host} -P {port} -u {user} -p{password} {database} < sql/mysql/20260619/fix_menu_icons.sql
```

3. **清除 Redis 缓存**

```bash
redis-cli -h {redis_host} -p {redis_port} DEL permission_menu_ids
```

4. **重启应用服务**

---

## 注意事项

1. **执行前备份**: 务必备份 `system_menu` 表
2. **清除缓存**: 执行 SQL 后必须清除 Redis 缓存
3. **权限要求**: 需要数据库写权限和 Redis 操作权限
4. **图标验证**: 执行后在前端验证图标是否正确显示
5. **兼容性**: 图标格式与前端 Iconify 组件兼容

---

## 图标参考资源

- [Iconify 图标搜索](https://icon-sets.iconify.design/) - 支持 200+ 图标集
- [Lucide 图标](https://lucide.dev/icons/) - 现代简洁风格,3000+ 图标
- [Element Plus 图标](https://element-plus.org/zh-CN/component/icon.html) - Element Plus 官方图标
- [Carbon Design 图标](https://carbondesignsystem.com/elements/icons/library/) - IBM 设计系统
- [Material Design Icons](https://materialdesignicons.com/) - Google 风格图标,7000+ 图标

---

## 相关文档

- [HIS系统-可用图标列表](../../../../_ignore/sql/README.md) - 图标使用指南
- [数据库操作文档](../../../../_ignore/sql/README.md) - 数据库配置和方法
- [菜单管理文档](../../../../docs/system/README.md) - 菜单功能说明

---

## 更新日志

| 版本 | 日期 | 更新内容 |
|------|------|----------|
| V1.0 | 2026-06-19 | 初始版本,修复 191 个菜单图标 |

---

> **最后更新**: 2026-06-19
> **维护团队**: HIS 后端开发组