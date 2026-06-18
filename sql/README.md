# SQL 目录说明

本目录包含项目所需的 MySQL 数据库脚本。

## 目录结构

```
sql/mysql/
├── framework/              # 框架基础 SQL
│   ├── quartz.sql          # 定时任务表结构
│   └── ruoyi-vue-pro.sql   # 框架基础表结构和初始化数据
│
└── his/                    # HIS 系统业务 SQL
    ├── his_init.sql                    # HIS 系统初始化主脚本
    ├── his_database_init_full.sql      # HIS 数据库完整初始化
    │
    # 门诊模块
    ├── his_op_fee_item.sql             # 门诊费用项目
    ├── his_op_refund_item.sql          # 门诊退费项目
    │
    # 住院模块
    ├── his_admission_menu.sql          # 住院入院菜单
    ├── his_discharge_apply.sql         # 出院申请
    ├── his_inpatient_settlement.sql    # 住院结算
    ├── his_prepayment_create.sql       # 预交金创建
    │
    # 医嘱执行
    ├── his_order_execution.sql         # 医嘱执行
    ├── his_order_template.sql          # 医嘱模板
    │
    # 检查检验
    ├── his_exam.sql                    # 检查模块
    ├── his_exam_menu.sql               # 检查菜单
    ├── his_lab.sql                     # 检验模块
    ├── his_lab_menu.sql                # 检验菜单
    │
    # 护理模块
    ├── his_nursing.sql                 # 护理模块
    ├── his_nursing_handover.sql        # 护理交接班
    │
    # 药品管理
    ├── his_drug_return.sql             # 药品退药
    ├── his_drug_return_menu.sql        # 退药菜单
    │
    # 支付结算
    └── his_payment_menu.sql            # 支付菜单
```

## 命名规范

所有 HIS 业务 SQL 文件统一采用以下命名规范：
- **前缀**: `his_` 开头
- **分隔符**: 使用下划线 `_` 分隔单词
- **格式**: `his_模块名_功能名.sql`

示例：
- `his_exam.sql` - 检查模块
- `his_exam_menu.sql` - 检查模块菜单
- `his_op_fee_item.sql` - 门诊费用项目

## 使用说明

### 1. 首次部署

按以下顺序执行 SQL 脚本：

```bash
# 1. 创建数据库
CREATE DATABASE `ruoyi-vue-pro` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 2. 执行框架基础脚本
mysql -u root -p ruoyi-vue-pro < sql/mysql/framework/ruoyi-vue-pro.sql
mysql -u root -p ruoyi-vue-pro < sql/mysql/framework/quartz.sql

# 3. 执行 HIS 业务脚本
mysql -u root -p ruoyi-vue-pro < sql/mysql/his/his_init.sql
```

### 2. 增量更新

根据业务需要，执行 `his/` 目录下对应的 SQL 脚本即可。

## 注意事项

- 所有脚本均为 MySQL 语法，建议使用 MySQL 8.0+
- 执行前请备份数据库
- 菜单相关 SQL 需要在框架基础数据导入后执行
