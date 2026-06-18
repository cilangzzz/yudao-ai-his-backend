# SQL 目录说明

本目录包含项目所需的 MySQL 数据库脚本。

## 目录结构

```
sql/mysql/
├── framework/                    # 框架基础 SQL
│   ├── quartz.sql               # 定时任务表结构
│   └── ruoyi-vue-pro.sql        # 框架基础表结构和初始化数据
│
└── his/                         # HIS 系统业务 SQL
    ├── tables/                  # 表结构 DDL
    │   ├── 20260618_his_init_tables.sql           # HIS 核心表结构（科室、人员、患者等）
    │   ├── 20260618_his_exam_tables.sql           # 检查模块表结构
    │   ├── 20260618_his_lab_tables.sql            # 检验模块表结构
    │   ├── 20260618_his_nursing_tables.sql        # 护理模块表结构
    │   ├── 20260618_his_drug_return_tables.sql    # 退药模块表结构
    │   ├── 20260618_his_inpatient_settlement_tables.sql  # 住院结算表结构
    │   ├── 20260618_his_order_template_tables.sql # 医嘱模板表结构
    │   ├── 20260618_his_prepayment_tables.sql     # 预交金表结构
    │   └── ... (其他表结构文件)
    │
    ├── data/                    # 初始数据 DML
    │   ├── 20260618_his_menu_data.sql           # HIS 菜单数据（合并）
    │   ├── 20260618_his_dict_data.sql           # HIS 字典数据（合并）
    │   ├── 20260618_his_init_data.sql           # 完整初始化数据
    │   └── 20260618_his_order_template_data.sql # 医嘱模板初始数据
    │
    └── migrations/              # 增量迁移脚本（待添加）
```

## 文件命名规范

所有 SQL 文件统一使用日期前缀命名：

### 格式
```
{YYYYMMDD}_{表名/模块名}_{类型}.sql
```

### 示例
| 文件名 | 说明 |
|--------|------|
| `20260618_his_init_tables.sql` | 2026年6月18日创建的HIS 核心表结构 |
| `20260618_his_exam_tables.sql` | 2026年6月18日创建的检查模块表结构 |
| `20260618_his_menu_data.sql` | 2026年6月18日的菜单初始数据 |
| `20260618_his_dict_data.sql` | 2026年6月18日的字典初始数据 |

### 迁移文件
- **格式**: `{YYYYMMDD}_{description}.sql`
- **示例**: `20260620_add_refund_type.sql`

## 使用说明

### 1. 首次部署

按以下顺序执行 SQL 脚本：

```bash
# 1. 创建数据库
mysql -u root -p -e "CREATE DATABASE \`ruoyi-vue-pro\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 2. 执行框架基础脚本
mysql -u root -p ruoyi-vue-pro < sql/mysql/framework/ruoyi-vue-pro.sql
mysql -u root -p ruoyi-vue-pro < sql/mysql/framework/quartz.sql

# 3. 执行 HIS 表结构脚本
mysql -u root -p ruoyi-vue-pro < sql/mysql/his/tables/20260618_his_init_tables.sql
mysql -u root -p ruoyi-vue-pro < sql/mysql/his/tables/20260618_his_exam_tables.sql
mysql -u root -p ruoyi-vue-pro < sql/mysql/his/tables/20260618_his_lab_tables.sql
# ... 其他表结构文件

# 4. 执行 HIS 初始数据脚本
mysql -u root -p ruoyi-vue-pro < sql/mysql/his/data/20260618_his_dict_data.sql
mysql -u root -p ruoyi-vue-pro < sql/mysql/his/data/20260618_his_menu_data.sql
```

### 2. 增量更新

将增量脚本放入 `migrations/` 目录，按日期命名执行。

## 表前缀说明

| 前缀 | 说明 | 示例 |
|------|------|------|
| `his_` | HIS 系统核心业务表 | `his_patient`, `his_department` |
| `op_` | 门诊相关表 | `op_register`, `op_prescription` |
| `ip_` | 住院相关表 | `ip_admission`, `ip_discharge` |

## 注意事项

- 所有脚本均为 MySQL 语法，建议使用 MySQL 8.0+
- 执行前请备份数据库
- 菜单和字典数据需要在框架基础数据导入后执行
- 表结构文件可独立执行，数据文件存在依赖关系
