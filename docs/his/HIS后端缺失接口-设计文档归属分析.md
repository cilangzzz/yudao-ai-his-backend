# HIS 后端缺失接口 - 数据库设计文档归属分析

> 日期: 2026-06-19
> 分析范围: 仍需实现的后端接口与数据库设计文档对应关系

---

## 一、缺失接口与设计文档对应表

### 1. 电子病历管理 (HisMedicalRecordController)

| 项目 | 内容 |
|-----|------|
| **前端路径** | `/his/medical-record/*` |
| **所属模块** | **M03-电子病历** |
| **设计文档** | ✅ 有状态机设计：`M03-电子病历/M03-电子病历-状态机设计.md` |
| **数据库设计** | ❌ 无独立表设计（病历数据分布在 `his_admission`、`his_order` 等表中） |
| **状态机** | SM-008 病历状态机（草稿→已签名→已审核→已归档→已封存） |
| **优先级** | P0 |

**说明**: 电子病历模块有状态机设计，但病历文书表 `his_medical_record` 未在全局数据库设计文档中定义，需要补充表结构设计。

---

### 2. ICD-10 字典 (HisIcd10Controller)

| 项目 | 内容 |
|-----|------|
| **前端路径** | `/his/icd10/*` |
| **所属模块** | **基础字典**（不属于特定模块） |
| **设计文档** | ❌ 无 |
| **数据库设计** | ❌ 无独立表设计 |
| **参考表** | `his_diagnosis` 表有 `diagnosis_code` 字段引用 ICD-10 |
| **优先级** | P1 |

**建议表结构**:
```sql
CREATE TABLE `his_icd10` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `icd_code` VARCHAR(10) NOT NULL COMMENT 'ICD-10编码',
    `icd_name` VARCHAR(200) NOT NULL COMMENT '疾病名称',
    `icd_category` VARCHAR(50) COMMENT '疾病类别',
    `parent_code` VARCHAR(10) COMMENT '父级编码',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_icd_code` (`icd_code`)
) COMMENT='ICD-10疾病诊断编码字典表';
```

---

### 3. 门诊药房发药 (HisOpPharmacyController)

| 项目 | 内容 |
|-----|------|
| **前端路径** | `/his/op-pharmacy/*` |
| **所属模块** | **M01-门诊管理**（发药流程） |
| **设计文档** | ❌ 无独立设计文档 |
| **数据库设计** | ⚠️ 依赖现有表：`op_prescription`、`his_drug_stock` |
| **业务流程** | 处方审核 → 调配 → 发药 |
| **优先级** | P1 |

**说明**: 门诊药房发药是门诊处方的后续流程，已有 `op_prescription` 表支持。发药记录可复用药品出库流程。

---

### 4. 检验项目字典 (HisLabItemController)

| 项目 | 内容 |
|-----|------|
| **前端路径** | `/his/lab/item/*` |
| **所属模块** | **M04-检验管理** |
| **设计文档** | ✅ 有状态机设计：`M04-检验管理/M04-检验管理-状态机设计.md` |
| **数据库设计** | ❌ 无独立表设计（全局设计文档中 `his_lab_result` 表有检验项目字段） |
| **优先级** | P1 |

**建议表结构**（基于 `his_lab_result` 表字段）:
```sql
CREATE TABLE `his_lab_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '检验项目ID',
    `item_code` VARCHAR(50) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(100) NOT NULL COMMENT '项目名称',
    `item_category` VARCHAR(50) COMMENT '项目分类',
    `sample_type` VARCHAR(50) COMMENT '标本类型',
    `unit` VARCHAR(20) COMMENT '单位',
    `ref_range` VARCHAR(100) COMMENT '参考范围',
    `ref_low` DECIMAL(20,6) COMMENT '参考下限',
    `ref_high` DECIMAL(20,6) COMMENT '参考上限',
    `price` DECIMAL(10,2) COMMENT '价格',
    `dept_id` BIGINT COMMENT '执行科室ID',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_item_code` (`item_code`)
) COMMENT='检验项目字典表';
```

---

### 5. 入出量记录 (HisIntakeOutputController)

| 项目 | 内容 |
|-----|------|
| **前端路径** | `/his/intake-output/*` |
| **所属模块** | **M02-住院管理**（护理） |
| **设计文档** | ❌ 无独立设计文档 |
| **数据库设计** | ❌ 无独立表设计 |
| **相关表** | `his_nursing_record`（护理记录） |
| **优先级** | P2 |

**建议表结构**:
```sql
CREATE TABLE `his_intake_output` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `record_no` VARCHAR(30) COMMENT '记录编号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `record_date` DATE NOT NULL COMMENT '记录日期',
    `record_time` DATETIME NOT NULL COMMENT '记录时间',
    `io_type` TINYINT NOT NULL COMMENT '类型: 1入量/2出量',
    `item_name` VARCHAR(100) NOT NULL COMMENT '项目名称',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '数量(ml)',
    `route` VARCHAR(50) COMMENT '途径(入量)',
    `nurse_id` BIGINT NOT NULL COMMENT '记录护士ID',
    `nurse_name` VARCHAR(50) COMMENT '记录护士姓名',
    `remark` VARCHAR(500) COMMENT '备注',
    PRIMARY KEY (`id`)
) COMMENT='入出量记录表';
```

---

## 二、汇总表

| Controller | 前端路径 | 设计文档 | 数据库设计 | 所属模块 | 优先级 |
|-----------|---------|---------|----------|---------|--------|
| HisMedicalRecordController | `/his/medical-record` | ✅ 状态机 SM-008 | ❌ 无独立表 | M03-电子病历 | P0 |
| HisIcd10Controller | `/his/icd10` | ❌ 无 | ❌ 无 | 基础字典 | P1 |
| HisOpPharmacyController | `/his/op-pharmacy` | ❌ 无 | ⚠️ 依赖现有表 | M01-门诊 | P1 |
| HisLabItemController | `/his/lab/item` | ✅ 状态机 | ❌ 无独立表 | M04-检验 | P1 |
| HisIntakeOutputController | `/his/intake-output` | ❌ 无 | ❌ 无 | M02-住院护理 | P2 |

---

## 三、实现建议

### P0 优先级 - 需补充表设计后实现

**电子病历管理**:
- 需要先设计 `his_medical_record` 表结构
- 参考 SM-008 状态机实现病历状态流转
- 核心字段：病历类型、病历内容、签名状态、审核状态等

### P1 优先级 - 可直接实现或需简单设计

**ICD-10 字典**:
- 标准字典表设计，可快速实现
- 建议导入国家卫健委发布的 ICD-10 标准数据

**门诊药房发药**:
- 基于现有 `op_prescription` 表
- 添加发药状态流转逻辑
- 关联药品库存扣减

**检验项目字典**:
- 提取 `his_lab_result` 表中项目相关字段为独立字典表
- 便于维护和查询

### P2 优先级 - 需新增表设计

**入出量记录**:
- 新增 `his_intake_output` 表
- 护理模块功能补充

---

## 四、数据库设计文档索引

| 模块 | 文档路径 | 包含内容 |
|-----|---------|---------|
| M01-门诊管理 | `M01-门诊管理/M01-门诊管理-数据库设计.md` | his_patient, op_schedule, op_register, op_prescription |
| M02-住院管理 | `M02-住院管理/M02-住院管理-数据库设计.md` | his_admission, his_order, his_bed, his_nursing_record, his_vital_sign |
| M03-电子病历 | `M03-电子病历/M03-电子病历-状态机设计.md` | SM-008 病历状态机 |
| M04-检验管理 | `M04-检验管理/M04-检验管理-状态机设计.md` | SM-006 标本状态机, SM-007 危急值状态机 |
| M06-药品管理 | `M06-药品管理/M06-药品管理-数据库设计.md` | his_drug, his_drug_stock, his_drug_batch, his_drug_interaction |
| 全局设计 | `00-全局/HIS系统-数据库设计文档.md` | 所有核心表 DDL |

---

> **最后更新**: 2026-06-19