# HIS 前后端接口对比分析报告

> 日期: 2026-06-19
> 分析范围: 前端 API (`yudao-ai-his-admin-ui/apps/web-antd/src/api/his`) vs 后端 Controller (`yudao-module-his`)

---

## 一、404 问题汇总

### 类型 A：后端 Controller 完全缺失（P0 - 最高优先级）

| 前端文件 | 前端路径 | 缺失的后端接口 | 影响功能 |
|---------|---------|---------------|---------|
| `op/cds/index.ts` | `/his/cds/*` | **全部接口缺失** | CDS 合理用药校验 |
| `op/medicalRecord/index.ts` | `/his/medical-record/*` | **全部接口缺失** | 电子病历管理 |
| `op/examination/index.ts` | `/his/examination/*` | **全部接口缺失** | 检查申请（旧路径） |
| `op/diagnosis/index.ts` | `/his/op/visit/diagnosis/*` | **诊断接口路径不匹配** | 门诊诊断（旧路径） |
| `op/diagnosis/index.ts` | `/his/icd10/*` | **ICD-10字典接口缺失** | ICD-10 编码查询 |
| `outpatient/payment/index.ts` | `/his/payment/*` | **支付接口缺失** | 门诊支付（非op-payment） |
| `pharmacy/stock/index.ts` | `/his/pharmacy/stock/*` | **库存接口路径不匹配** | 药品库存（旧路径） |
| `pharmacy/outbound/index.ts` | `/his/pharmacy/outbound/*` | **出库接口路径不匹配** | 药品出库（旧路径） |
| `pharmacy/inventory/index.ts` | `/his/pharmacy/inventory/*` | **盘点接口路径不匹配** | 药品盘点（旧路径） |
| `pharmacy/inbound/index.ts` | `/his/pharmacy/inbound/*` | **入库接口路径不匹配** | 药品入库（旧路径） |
| `pharmacy/purchase/index.ts` | `/his/pharmacy/purchase/*` | **采购接口路径不匹配** | 药品采购（旧路径） |
| `pharmacy/supplier/index.ts` | `/his/pharmacy/supplier/*` | **供应商接口路径不匹配** | 供应商管理（旧路径） |
| `pharmacy/drug-return/index.ts` | `/his/pharmacy/drug-return/*` | **退药接口路径不匹配** | 退药管理（旧路径） |
| `outpatient/dispense/index.ts` | `/his/op-pharmacy/*` | **门诊药房接口缺失** | 门诊药房发药 |
| `lab/labRequest/index.ts` | `/his/lab/request/*` | **检验申请路径不匹配** | 检验申请（旧路径） |
| `lab/labItem/index.ts` | `/his/lab/item/*` | **检验项目接口缺失** | 检验项目字典 |
| `lab/criticalValue/index.ts` | `/his/lab/critical-value/*` | **危急值接口缺失** | 危急值管理 |
| `inpatient/nursing/index.ts` | `/his/nursing-assessment/*` | **护理评估路径不匹配** | 护理评估（双重路径） |
| `inpatient/nursing/index.ts` | `/his/handover/*` | **交接班路径不匹配** | 护理交接班（旧路径） |
| `inpatient/nursing/index.ts` | `/his/intake-output/*` | **出入量接口缺失** | 入出量记录 |
| `inpatient/nursing/index.ts` | `/his/vital-signs/*` | **生命体征路径不匹配** | 生命体征（旧路径） |
| `inpatient/ward/index.ts` | `/his/ward/*` | **病区路径不匹配** | 病区管理（旧路径） |
| `inpatient/bed/index.ts` | `/his/bed/available` | **路径不匹配** | 空闲床位查询 |
| `inpatient/bed/index.ts` | `/his/bed/overview` | **接口缺失** | 床位总览统计 |
| `inpatient/bed/index.ts` | `/his/bed/transfer` | **接口缺失** | 转床功能 |
| `inpatient/bed/index.ts` | `/his/bed/assign` | **路径不匹配** | 分配床位 |
| `outpatient/exam-request/index.ts` | `/his/exam-request/*` | **大部分接口已实现，record-result缺失** | 检查申请结果录入 |
| `outpatient/exam-item/index.ts` | `/his/exam-item/*` | **启用/禁用接口缺失** | 检查项目字典 |
| `outpatient/refund/index.ts` | `/his/refund/*` | **退费路径不匹配** | 门诊退费（旧路径） |
| `nursing-assessment/index.ts` | `/his/nursing-assessment/*` | **部分接口缺失** | 护理评估管理 |
| `nursing-record/index.ts` | `/his/nursing-record/*` | **审核/模板接口缺失** | 护理记录管理 |
| `vital-sign/index.ts` | `/his/vital-sign/*` | **统计接口缺失** | 生命体征管理 |

---

### 类型 B：路径前缀不匹配（P1）

| 前端路径 | 后端实际路径 | 解决方案 |
|---------|-------------|---------|
| `/his/ward/*` | `/his/basic/ward` | 前端需改用 `/his/basic/ward` 或后端添加兼容路径 |
| `/his/op-fee/*` | `/his/op-fee` | ✅ 已匹配 |
| `/his/payment/*` | `/his/op-payment` | 前端需改用 `/his/op-payment` |
| `/his/refund/*` | `/his/op-refund` | 前端需改用 `/his/op-refund` |
| `/his/pharmacy/stock/*` | `/his/drug-stock` | 前端需改用 `/his/drug-stock` |
| `/his/pharmacy/inbound/*` | `/his/drug-inbound` | 前端需改用 `/his/drug-inbound` |
| `/his/pharmacy/outbound/*` | `/his/drug-outbound` | 前端需改用 `/his/drug-outbound` |
| `/his/pharmacy/inventory/*` | `/his/drug-inventory` | 前端需改用 `/his/drug-inventory` |
| `/his/pharmacy/supplier/*` | `/his/supplier` | 前端需改用 `/his/supplier` |
| `/his/pharmacy/purchase/*` | `/his/drug-purchase` | 前端需改用 `/his/drug-purchase` |
| `/his/pharmacy/drug-return/*` | `/his/drug-return` | 前端需改用 `/his/drug-return` |
| `/his/lab/request/*` | `/his/lab-request` | 前端需改用 `/his/lab-request` |
| `/his/lab/item/*` | 无 | 后端需创建 HisLabItemController |
| `/his/lab/critical-value/*` | 无 | 后端需创建 HisCriticalValueController |
| `/his/bed/available` | `/his/bed/empty-beds` | 接口名称不同 |
| `/his/bed/assign` | `/his/bed/occupy` | 接口名称不同 |

---

### 类型 C：单个接口缺失（P2）

| 前端路径 | 后端状态 | 缺失接口 |
|---------|---------|---------|
| `/his/bed/overview` | 无 | 床位总览统计 |
| `/his/bed/transfer` | 无 | 转床功能 |
| `/his/exam-request/record-result` | 无 | 检查结果录入 |
| `/his/exam-item/enable` | 无 | 启用检查项目 |
| `/his/exam-item/disable` | 无 | 禁用检查项目 |
| `/his/op-fee/item/page` | ✅ 有 | - |
| `/his/op-fee/item/list-by-source` | ✅ 有 | - |
| `/his/nursing-assessment/pending-list` | 无 | 待评估列表 |
| `/his/nursing-assessment/submit-review` | 无 | 提交审核 |
| `/his/nursing-assessment/statistics` | 无 | 护理评估统计 |
| `/his/nursing-record/submit-review` | 无 | 提交审核 |
| `/his/nursing-record/review` | 无 | 审核护理记录 |
| `/his/nursing-record/template/list` | 无 | 护理记录模板 |
| `/his/vital-sign/statistics` | 无 | 生命体征统计 |

---

## 二、详细对比表

### 门诊管理模块 (M01)

#### 患者管理 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/patient/page` | `/his/patient/page` | ✅ |
| `/his/patient/get` | `/his/patient/get` | ✅ |
| `/his/patient/create` | `/his/patient/create` | ✅ |
| `/his/patient/update` | `/his/patient/update` | ✅ |
| `/his/patient/delete` | `/his/patient/delete` | ✅ |
| `/his/patient/get-by-id-card` | `/his/patient/get-by-id-card` | ✅ |

#### 挂号管理 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/register/page` | `/his/register/page` | ✅ |
| `/his/register/get` | `/his/register/get` | ✅ |
| `/his/register/create` | `/his/register/create` | ✅ |
| `/his/register/refund` | `/his/register/refund` | ✅ |
| `/his/register/start-visit` | `/his/register/start-visit` | ✅ |
| `/his/register/end-visit` | `/his/register/end-visit` | ✅ |
| `/his/register/waiting-list` | `/his/register/waiting-list` | ✅ |
| `/his/register/call-next` | `/his/register/call-next` | ✅ |

#### 排班管理 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/schedule/page` | `/his/schedule/page` | ✅ |
| `/his/schedule/get` | `/his/schedule/get` | ✅ |
| `/his/schedule/create` | `/his/schedule/create` | ✅ |
| `/his/schedule/batch-create` | `/his/schedule/batch-create` | ✅ |
| `/his/schedule/calendar` | `/his/schedule/list-by-date` | ⚠️ 名称不同 |

#### 处方管理 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/prescription/*` | `/his/prescription/*` | ✅ 全部匹配 |

#### 门诊费用 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/op-fee/*` | `/his/op-fee/*` | ✅ 全部匹配 |

#### 支付管理 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/payment/page` | `/his/op-payment/page` | ⚠️ 路径不匹配 |
| `/his/payment/get` | `/his/op-payment/get` | ⚠️ 路径不匹配 |
| `/his/payment/confirm` | 无 | ❌ 缺失 |
| `/his/payment/cancel` | 无 | ❌ 缺失 |
| `/his/payment/pending-by-patient` | `/his/op-payment/list-by-patient` | ⚠️ 名称不同 |

#### 退费管理 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/refund/*` | `/his/op-refund/*` | ⚠️ 路径不匹配 |

#### CDS合理用药 ❌ 全部缺失
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/cds/check` | 无 | ❌ 缺失 |
| `/his/cds/check-interaction` | 无 | ❌ 缺失 |
| `/his/cds/check-allergy` | 无 | ❌ 缺失 |
| `/his/cds/check-dosage` | 无 | ❌ 缺失 |
| `/his/cds/check-contraindication` | 无 | ❌ 缺失 |

#### 电子病历 ❌ 全部缺失
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/medical-record/*` | 无 | ❌ 缺失 |

#### 检查申请 ✅ 大部分匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/exam-request/*` | `/his/exam-request/*` | ✅ 大部分匹配 |
| `/his/exam-request/record-result` | 无 | ❌ 缺失 |

#### 检查项目字典 ✅ 大部分匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/exam-item/page` | `/his/exam-item/page` | ✅ |
| `/his/exam-item/get` | `/his/exam-item/get` | ✅ |
| `/his/exam-item/list` | `/his/exam-item/list-active` | ⚠️ 名称不同 |
| `/his/exam-item/enable` | 无 | ❌ 缺失 |
| `/his/exam-item/disable` | 无 | ❌ 缺失 |

---

### 住院管理模块 (M02)

#### 入院管理 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/admission/*` | `/his/admission/*` | ✅ 全部匹配 |

#### 预住院管理 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/pre-admission/*` | `/his/pre-admission/*` | ✅ 全部匹配 |

#### 入院评估 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/assess/*` | `/his/assess/*` | ✅ 全部匹配 |

#### 医嘱管理 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/order/*` | `/his/order/*` | ✅ 全部匹配 |

#### 床位管理 ⚠️ 部分不匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/bed/page` | `/his/bed/page` | ✅ |
| `/his/bed/get` | `/his/bed/get` | ✅ |
| `/his/bed/create` | `/his/bed/create` | ✅ |
| `/his/bed/update` | `/his/bed/update` | ✅ |
| `/his/bed/delete` | `/his/bed/delete` | ✅ |
| `/his/bed/list-by-ward` | `/his/bed/list-by-ward` | ✅ |
| `/his/bed/available` | `/his/bed/empty-beds` | ⚠️ 名称不同 |
| `/his/bed/assign` | `/his/bed/occupy` | ⚠️ 名称不同 |
| `/his/bed/update-status` | `/his/bed/update-status` | ✅ |
| `/his/bed/release` | `/his/bed/release` | ✅ |
| `/his/bed/overview` | 无 | ❌ 缺失 |
| `/his/bed/transfer` | 无 | ❌ 缺失 |

#### 病区管理 ✅ 已修复
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/basic/ward/*` | `/his/basic/ward/*` | ✅ 已修复 |
| `/his/ward/*` | 无 | ⚠️ 旧路径，前端需更新 |

#### 预交金管理 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/prepayment/*` | `/his/prepayment/*` | ✅ 全部匹配 |

#### 给药记录 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/medication-admin/*` | `/his/medication-admin/*` | ✅ 全部匹配 |

#### 生命体征 ✅ 大部分匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/vital-sign/*` | `/his/vital-sign/*` | ✅ 大部分匹配 |
| `/his/vital-sign/statistics` | 无 | ❌ 缺失 |

#### 护理记录 ⚠️ 部分缺失
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/nursing-record/page` | `/his/nursing-record/page` | ✅ |
| `/his/nursing-record/get` | `/his/nursing-record/get` | ✅ |
| `/his/nursing-record/create` | `/his/nursing-record/create` | ✅ |
| `/his/nursing-record/submit-review` | 无 | ❌ 缺失 |
| `/his/nursing-record/review` | 无 | ❌ 缺失 |
| `/his/nursing-record/template/list` | 无 | ❌ 缺失 |

#### 护理交接班 ⚠️ 路径不匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/handover/*` | `/his/nursing-handover/*` | ⚠️ 路径不匹配 |

#### 护理评估 ⚠️ 部分缺失
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/nursing-assessment/*` | `/his/nursing-record/assessment/*` | ⚠️ 路径不同 |
| `/his/nursing-assessment/pending-list` | 无 | ❌ 缺失 |
| `/his/nursing-assessment/submit-review` | 无 | ❌ 缺失 |
| `/his/nursing-assessment/statistics` | 无 | ❌ 缺失 |

#### 出院申请 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/discharge/*` | `/his/discharge-apply/*` | ⚠️ 路径不同 |

#### 住院结算 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/settlement/*` | `/his/settlement/*` | ✅ 全部匹配 |

---

### 药品管理模块 (M06)

#### 药品目录 ✅ 匹配
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/drug/*` | `/his/drug/*` | ✅ 全部匹配 |

#### 药品库存 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/pharmacy/stock/*` | `/his/drug-stock/*` | ⚠️ 路径不匹配 |

#### 入库管理 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/pharmacy/inbound/*` | `/his/drug-inbound/*` | ⚠️ 路径不匹配 |

#### 出库管理 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/pharmacy/outbound/*` | `/his/drug-outbound/*` | ⚠️ 路径不匹配 |

#### 盘点管理 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/pharmacy/inventory/*` | `/his/drug-inventory/*` | ⚠️ 路径不匹配 |

#### 供应商管理 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/pharmacy/supplier/*` | `/his/supplier/*` | ⚠️ 路径不匹配 |

#### 采购管理 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/pharmacy/purchase/*` | `/his/drug-purchase/*` | ⚠️ 路径不匹配 |

#### 退药管理 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/pharmacy/drug-return/*` | `/his/drug-return/*` | ⚠️ 路径不匹配 |

#### 门诊药房 ❌ 全部缺失
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/op-pharmacy/*` | 无 | ❌ 全部缺失 |

---

### 检验管理模块 (M04)

#### 检验申请 ⚠️ 路径不同
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/lab/request/*` | `/his/lab-request/*` | ⚠️ 路径不匹配 |

#### 检验项目字典 ❌ 全部缺失
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/lab/item/*` | 无 | ❌ 全部缺失 |

#### 危急值管理 ❌ 全部缺失
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/lab/critical-value/*` | 无 | ️ 全部缺失 |

---

### 基础数据管理

#### 科室管理 ✅ 已实现
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/basic/department/*` | `/his/basic/department/*` | ✅ 已实现 |

#### 医护人员 ✅ 已实现
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/basic/staff/*` | `/his/basic/staff/*` | ✅ 已实现 |

#### 收费项目 ✅ 已实现
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/basic/charge-item/*` | `/his/basic/charge-item/*` | ✅ 已实现 |

#### 病区管理 ✅ 已修复
| 前端接口 | 后端接口 | 状态 |
|---------|---------|------|
| `/his/basic/ward/*` | `/his/basic/ward/*` | ✅ 已修复 |

---

## 三、解决方案汇总

### P0 - 后端需新增 Controller

| Controller | 前端调用路径 | 模块 |
|-----------|------------|------|
| HisCdsController | `/his/cds` | 合理用药校验 |
| HisMedicalRecordController | `/his/medical-record` | 电子病历 |
| HisExaminationController | `/his/examination` | 检查申请（旧路径兼容） |
| HisIcd10Controller | `/his/icd10` | ICD-10字典 |
| HisPaymentController | `/his/payment` | 门诊支付（兼容路径） |
| HisOpPharmacyController | `/his/op-pharmacy` | 门诊药房 |
| HisLabItemController | `/his/lab/item` | 检验项目字典 |
| HisCriticalValueController | `/his/lab/critical-value` | 危急值管理 |
| HisIntakeOutputController | `/his/intake-output` | 入出量记录 |
| HisBedStatsController | 补充床位统计 | 床位总览/转床 |

### P1 - 前端需修改路径

| 前端文件 | 当前路径 | 应改为 |
|---------|---------|-------|
| `inpatient/ward/index.ts` | `/his/ward` | `/his/basic/ward` |
| `outpatient/payment/index.ts` | `/his/payment` | `/his/op-payment` |
| `outpatient/refund/index.ts` | `/his/refund` | `/his/op-refund` |
| `pharmacy/stock/index.ts` | `/his/pharmacy/stock` | `/his/drug-stock` |
| `pharmacy/inbound/index.ts` | `/his/pharmacy/inbound` | `/his/drug-inbound` |
| `pharmacy/outbound/index.ts` | `/his/pharmacy/outbound` | `/his/drug-outbound` |
| `pharmacy/inventory/index.ts` | `/his/pharmacy/inventory` | `/his/drug-inventory` |
| `pharmacy/supplier/index.ts` | `/his/pharmacy/supplier` | `/his/supplier` |
| `pharmacy/purchase/index.ts` | `/his/pharmacy/purchase` | `/his/drug-purchase` |
| `pharmacy/drug-return/index.ts` | `/his/pharmacy/drug-return` | `/his/drug-return` |
| `lab/labRequest/index.ts` | `/his/lab/request` | `/his/lab-request` |

### P2 - 补充单个接口

| 后端 Controller | 需补充接口 |
|----------------|-----------|
| HisBedController | `overview`、`transfer` |
| HisExamRequestController | `record-result` |
| HisExamItemController | `enable`、`disable` |
| HisNursingAssessmentController | `pending-list`、`submit-review`、`statistics` |
| HisNursingRecordController | `submit-review`、`review`、`template/list` |
| HisVitalSignController | `statistics` |

---

## 四、统计

| 类型 | 数量 |
|------|------|
| ✅ 完全匹配 | 约 250 个接口 |
| ⚠️ 路径不匹配 | 约 60 个接口 |
| ❌ 完全缺失 | 约 40 个接口（涉及10个新Controller） |
| ❌ 单接口缺失 | 约 15 个接口 |

---

> **最后更新**: 2026-06-19