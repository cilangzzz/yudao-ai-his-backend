# HIS 前端接口改动清单

> 日期: 2026-06-19
> 状态: 待前端修改

---

## 一、需修改路径的文件（P1 优先级）

### 1. 病区管理
**文件**: `inpatient/ward/index.ts`

| 当前路径 | 应改为 | 说明 |
|---------|-------|------|
| `/his/ward/page` | `/his/basic/ward/page` | 后端已修正为 `/his/basic/ward` |
| `/his/ward/get` | `/his/basic/ward/get` | 同上 |
| `/his/ward/list` | `/his/basic/ward/list` | 同上 |
| `/his/ward/list-by-dept` | `/his/basic/ward/list-by-dept` | 同上 |
| `/his/ward/create` | `/his/basic/ward/create` | 同上 |
| `/his/ward/update` | `/his/basic/ward/update` | 同上 |
| `/his/ward/delete` | `/his/basic/ward/delete` | 同上 |
| `/his/ward/update-status` | `/his/basic/ward/update-status` | 同上 |
| `/his/ward/bed-stats` | `/his/basic/ward/bed-stats` | 同上 |

**修改示例**:
```typescript
// 修改前
export function getWardPage(params: HisWardApi.WardPageReqVO) {
  return requestClient.get<PageResult<HisWardApi.Ward>>('/his/ward/page', { params });
}

// 修改后
export function getWardPage(params: HisWardApi.WardPageReqVO) {
  return requestClient.get<PageResult<HisWardApi.Ward>>('/his/basic/ward/page', { params });
}
```

---

### 2. 门诊支付
**文件**: `outpatient/payment/index.ts`

| 当前路径 | 应改为 | 说明 |
|---------|-------|------|
| `/his/payment/page` | `/his/op-payment/page` | 后端使用 `op-payment` |
| `/his/payment/get` | `/his/op-payment/get` | 同上 |
| `/his/payment/confirm` | `/his/op-payment/confirm` | 同上 |
| `/his/payment/cancel` | `/his/op-payment/cancel` | 同上 |
| `/his/payment/pending-by-patient` | `/his/op-payment/list-by-patient` | 后端接口名不同 |
| `/his/payment/get-by-register` | `/his/op-payment/get-by-register` | 同上 |
| `/his/payment/print-receipt` | `/his/op-payment/print-receipt` | 同上 |

---

### 3. 门诊退费
**文件**: `outpatient/refund/index.ts`

| 当前路径 | 应改为 | 说明 |
|---------|-------|------|
| `/his/refund/*` | `/his/op-refund/*` | 后端使用 `op-refund` |

---

### 4. 药品库存
**文件**: `pharmacy/stock/index.ts`

| 当前路径 | 应改为 | 说明 |
|---------|-------|------|
| `/his/pharmacy/stock/page` | `/his/drug-stock/page` | 后端路径不同 |
| `/his/pharmacy/stock/get` | `/his/drug-stock/get` | 同上 |
| `/his/pharmacy/stock/list-by-drug` | `/his/drug-stock/list-by-drug` | 同上 |
| `/his/pharmacy/stock/warning-list` | `/his/drug-stock/warning-list` | 同上 |
| `/his/pharmacy/stock/expired-list` | `/his/drug-stock/expired-list` | 同上 |
| `/his/pharmacy/stock/near-expiry-list` | `/his/drug-stock/near-expiry-list` | 同上 |
| `/his/pharmacy/stock/low-stock-list` | `/his/drug-stock/low-stock-list` | 同上 |
| `/his/pharmacy/stock/export-excel` | `/his/drug-stock/export-excel` | 同上 |

---

### 5. 药品入库
**文件**: `pharmacy/inbound/index.ts`

| 当前路径 | 应改为 |
|---------|-------|
| `/his/pharmacy/inbound/*` | `/his/drug-inbound/*` |

---

### 6. 药品出库
**文件**: `pharmacy/outbound/index.ts`

| 当前路径 | 应改为 |
|---------|-------|
| `/his/pharmacy/outbound/*` | `/his/drug-outbound/*` |

---

### 7. 药品盘点
**文件**: `pharmacy/inventory/index.ts`

| 当前路径 | 应改为 |
|---------|-------|
| `/his/pharmacy/inventory/*` | `/his/drug-inventory/*` |

---

### 8. 供应商管理
**文件**: `pharmacy/supplier/index.ts`

| 当前路径 | 应改为 |
|---------|-------|
| `/his/pharmacy/supplier/*` | `/his/supplier/*` |

---

### 9. 药品采购
**文件**: `pharmacy/purchase/index.ts`

| 当前路径 | 应改为 |
|---------|-------|
| `/his/pharmacy/purchase/*` | `/his/drug-purchase/*` |

---

### 10. 退药管理
**文件**: `pharmacy/drug-return/index.ts`

| 当前路径 | 应改为 |
|---------|-------|
| `/his/pharmacy/drug-return/*` | `/his/drug-return/*` |

---

### 11. 检验申请
**文件**: `lab/labRequest/index.ts`

| 当前路径 | 应改为 |
|---------|-------|
| `/his/lab/request/*` | `/his/lab-request/*` |

---

## 二、后端缺失的接口（需后端实现）

### P0 优先级 - 需新建 Controller

| Controller | 路径 | 设计文档 | 表设计 |
|-----------|------|---------|--------|
| **HisCdsController** | `/his/cds` | M06药品管理（药物相互作用表） | ✅ 有 `his_drug_interaction` |
| **HisOpPharmacyController** | `/his/op-pharmacy` | M01门诊管理（门诊药房发药） | ❌ 无独立表（依赖 `op_prescription`） |
| **HisLabItemController** | `/his/lab/item` | M04检验管理（检验项目字典） | ❌ 无独立表设计 |
| **HisCriticalValueController** | `/his/lab/critical-value` | M04检验管理（危急值） | ✅ 有状态机设计 SM-007 |
| **HisIcd10Controller** | `/his/icd10` | 基础字典（ICD-10编码） | ❌ 无独立表设计 |
| **HisIntakeOutputController** | `/his/intake-output` | M02住院管理（入出量） | ❌ 无独立表设计 |
| **HisMedicalRecordController** | `/his/medical-record` | M03电子病历 | ✅ 有模块设计 |

### P2 优先级 - 补充单接口

| Controller | 缺失接口 | 说明 |
|-----------|---------|------|
| HisBedController | `overview`、`transfer` | 床位总览统计、转床功能 |
| HisExamRequestController | `record-result` | 检查结果录入 |
| HisExamItemController | `enable`、`disable` | 启用/禁用检查项目 |
| HisNursingAssessmentController | `pending-list`、`submit-review`、`statistics` | 护理评估扩展接口 |
| HisNursingRecordController | `submit-review`、`review`、`template/list` | 护理记录审核相关 |
| HisVitalSignController | `statistics` | 生命体征统计 |

---

## 三、后端有设计文档可实现的接口

### 可立即实现（有完整表设计）

1. **CDS 合理用药校验** - 基于 `his_drug_interaction` 表
2. **危急值管理** - 基于状态机设计 SM-007
3. **床位总览/转床** - 基于 `his_bed` 表扩展

### 需补充设计后实现

1. **检验项目字典** (`HisLabItemController`) - 无表设计
2. **ICD-10字典** (`HisIcd10Controller`) - 无表设计
3. **入出量记录** (`HisIntakeOutputController`) - 无表设计
4. **门诊药房发药** (`HisOpPharmacyController`) - 基于处方流程

---

## 四、快速修改脚本

可使用以下批量替换脚本快速修改前端路径：

```bash
# 病区路径修改
sed -i "s|'/his/ward/|'/his/basic/ward/|g" inpatient/ward/index.ts

# 门诊支付路径修改
sed -i "s|'/his/payment/|'/his/op-payment/|g" outpatient/payment/index.ts

# 药品库存路径修改
sed -i "s|'/his/pharmacy/stock/|'/his/drug-stock/|g" pharmacy/stock/index.ts

# 药品入库路径修改
sed -i "s|'/his/pharmacy/inbound/|'/his/drug-inbound/|g" pharmacy/inbound/index.ts

# 药品出库路径修改
sed -i "s|'/his/pharmacy/outbound/|'/his/drug-outbound/|g" pharmacy/outbound/index.ts

# 检验申请路径修改
sed -i "s|'/his/lab/request/|'/his/lab-request/|g" lab/labRequest/index.ts
```

---

> **最后更新**: 2026-06-19