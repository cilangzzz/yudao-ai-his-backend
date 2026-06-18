# 住院结算 API 接口文档

> **模块**: 住院结算
> **Controller**: `HisInpatientSettlementController`
> **基础路径**: `/his/settlement`
> **版本**: v1.0
> **更新日期**: 2026-06-18

---

## 接口概览

| 接口 | 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|------|
| 创建结算单 | POST | `/create` | `his:settlement:create` | 创建住院结算单 |
| 更新结算单 | PUT | `/update` | `his:settlement:update` | 更新结算单信息 |
| 删除结算单 | DELETE | `/delete` | `his:settlement:delete` | 删除未结算的结算单 |
| 获取结算单详情 | GET | `/get` | `his:settlement:query` | 根据ID查询结算单 |
| 获取结算单分页 | GET | `/page` | `his:settlement:query` | 分页查询结算单列表 |
| 根据入院ID查询 | GET | `/list-by-admission` | `his:settlement:query` | 查询入院记录的结算单 |
| 结算确认 | POST | `/confirm` | `his:settlement:confirm` | 确认结算并生成发票 |
| 退费处理 | POST | `/refund` | `his:settlement:refund` | 结算单退费 |
| 作废处理 | POST | `/cancel` | `his:settlement:cancel` | 作废结算单 |
| 费用汇总 | GET | `/fee-summary` | `his:settlement:query` | 计算费用汇总 |

---

## 接口详情

### 1. 创建结算单

**请求**
- 方法: `POST`
- 路径: `/his/settlement/create`
- 权限: `his:settlement:create`
- Content-Type: `application/json`

**请求体** (`HisInpatientSettlementSaveReqVO`)

```json
{
  "admissionId": 100,
  "patientId": 1,
  "patientName": "张三",
  "inpatientNo": "ZY20240001",
  "idCardNo": "320102199001011234",
  "admissionDate": "2024-06-10 10:00:00",
  "dischargeDate": "2024-06-18 09:00:00",
  "hospitalDays": 8,
  "deptId": 10,
  "deptName": "内科",
  "wardId": 1,
  "wardName": "一病区",
  "bedId": 1,
  "bedNo": "01",
  "attendingDoctorId": 100,
  "attendingDoctorName": "王医生",
  "insuranceType": 1,
  "insuranceNo": "YB123456",
  "settlementType": 1,
  "admissionDiagnosis": "肺炎",
  "dischargeDiagnosis": "肺炎治愈",
  "diagnosisCode": "J18.9",
  "remark": "正常出院"
}
```

**参数说明**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| admissionId | Long | 是 | 入院记录ID |
| patientId | Long | 是 | 患者ID |
| patientName | String | 是 | 患者姓名 |
| inpatientNo | String | 否 | 住院号 |
| idCardNo | String | 否 | 身份证号 |
| admissionDate | LocalDateTime | 否 | 入院日期 |
| dischargeDate | LocalDateTime | 否 | 出院日期 |
| hospitalDays | Integer | 否 | 住院天数 |
| deptId | Long | 否 | 科室ID |
| deptName | String | 否 | 科室名称 |
| wardId | Long | 否 | 病区ID |
| wardName | String | 否 | 病区名称 |
| bedId | Long | 否 | 床位ID |
| bedNo | String | 否 | 床号 |
| attendingDoctorId | Long | 否 | 主治医师ID |
| attendingDoctorName | String | 否 | 主治医师姓名 |
| insuranceType | Integer | 否 | 医保类型：1-城镇职工，2-城镇居民，3-新农合，4-自费 |
| insuranceNo | String | 否 | 医保号 |
| settlementType | Integer | 否 | 结算类型：1-出院结算，2-中途结算，3-挂账结算 |
| admissionDiagnosis | String | 否 | 入院诊断 |
| dischargeDiagnosis | String | 否 | 出院诊断 |
| diagnosisCode | String | 否 | ICD-10编码 |
| remark | String | 否 | 备注 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": 1  // 结算单ID
}
```

---

### 2. 结算确认

**请求**
- 方法: `POST`
- 路径: `/his/settlement/confirm`
- 权限: `his:settlement:confirm`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 结算单ID |
| paymentType | Integer | 是 | 支付方式：1-现金，2-银行卡，3-医保卡，4-微信，5-支付宝，6-混合 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": true
}
```

**业务规则**
- 只有「未结算」状态可以确认
- 确认后状态变为「已结算」
- 自动生成发票号
- 更新所有关联费用明细状态

---

### 3. 退费处理

**请求**
- 方法: `POST`
- 路径: `/his/settlement/refund`
- 权限: `his:settlement:refund`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 结算单ID |
| refundAmount | BigDecimal | 是 | 退费金额 |
| remark | String | 否 | 备注 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": true
}
```

**业务规则**
- 只有「已结算」状态可以退费
- 退费金额不能超过实收金额
- 退费后状态变为「已退费」

---

### 4. 获取结算单详情

**请求**
- 方法: `GET`
- 路径: `/his/settlement/get`
- 权限: `his:settlement:query`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 结算单ID |

**响应** (`HisInpatientSettlementRespVO`)

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "settlementNo": "JS202406180001",
    "invoiceNo": "FP202406180001",
    "admissionId": 100,
    "patientId": 1,
    "patientName": "张三",
    "inpatientNo": "ZY20240001",
    "idCardNo": "320102199001011234",
    "admissionDate": "2024-06-10 10:00:00",
    "dischargeDate": "2024-06-18 09:00:00",
    "hospitalDays": 8,
    "deptId": 10,
    "deptName": "内科",
    "wardName": "一病区",
    "bedNo": "01",
    "attendingDoctorName": "王医生",
    "insuranceType": 1,
    "insuranceTypeName": "城镇职工",
    
    // 费用汇总
    "totalAmount": 10000.00,
    "westernMedicineFee": 2000.00,
    "chineseMedicineFee": 500.00,
    "examinationFee": 1500.00,
    "laboratoryFee": 800.00,
    "treatmentFee": 1000.00,
    "nursingFee": 300.00,
    "surgeryFee": 3000.00,
    "bedFee": 700.00,
    "materialFee": 200.00,
    "otherFee": 500.00,
    
    // 结算金额
    "discountAmount": 100.00,
    "payableAmount": 9900.00,
    "prepaymentAmount": 5000.00,
    "insuranceAmount": 3000.00,
    "selfAmount": 2000.00,
    "refundAmount": 0.00,
    "actualAmount": 2000.00,
    
    // 状态
    "settlementStatus": 1,
    "settlementStatusName": "已结算",
    "settlementType": 1,
    "settlementTypeName": "出院结算",
    
    // 支付信息
    "paymentType": 1,
    "paymentTypeName": "现金",
    "paymentTime": "2024-06-18 10:00:00",
    
    // 诊断
    "admissionDiagnosis": "肺炎",
    "dischargeDiagnosis": "肺炎治愈",
    "diagnosisCode": "J18.9",
    
    // 操作信息
    "operatorId": 1,
    "operatorName": "操作员",
    "settlementTime": "2024-06-18 10:00:00",
    "createTime": "2024-06-18 09:00:00"
  }
}
```

---

### 5. 获取费用汇总

**请求**
- 方法: `GET`
- 路径: `/his/settlement/fee-summary`
- 权限: `his:settlement:query`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| admissionId | Long | 是 | 入院记录ID |

**响应**

返回按费用类型汇总的结果，用于结算前预览

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "totalAmount": 10000.00,
    "westernMedicineFee": 2000.00,
    "chineseMedicineFee": 500.00,
    "examinationFee": 1500.00,
    "laboratoryFee": 800.00,
    "treatmentFee": 1000.00,
    "nursingFee": 300.00,
    "surgeryFee": 3000.00,
    "bedFee": 700.00,
    "materialFee": 200.00,
    "otherFee": 500.00,
    "payableAmount": 9900.00
  }
}
```

---

### 6. 获取结算单分页

**请求**
- 方法: `GET`
- 路径: `/his/settlement/page`
- 权限: `his:settlement:query`

**参数** (`HisInpatientSettlementPageReqVO`)

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| pageNo | Integer | 是 | 页码 |
| pageSize | Integer | 是 | 每页条数 |
| settlementNo | String | 否 | 结算单号 |
| patientName | String | 否 | 患者姓名 |
| inpatientNo | String | 否 | 住院号 |
| admissionId | Long | 否 | 入院记录ID |
| patientId | Long | 否 | 患者ID |
| deptId | Long | 否 | 科室ID |
| settlementStatus | Integer | 否 | 结算状态 |
| settlementType | Integer | 否 | 结算类型 |
| settlementTimeStart | LocalDateTime | 否 | 结算时间起始 |
| settlementTimeEnd | LocalDateTime | 否 | 结算时间结束 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "list": [...],
    "total": 100
  }
}
```

---

## 状态流转

```
未结算(0) → 已结算(1) → 已退费(2)
    ↓
  已作废(3)
```

| 状态值 | 说明 | 可操作 |
|--------|------|--------|
| 0 | 未结算 | 确认、作废、删除 |
| 1 | 已结算 | 退费 |
| 2 | 已退费 | 无 |
| 3 | 已作废 | 无 |

---

## 结算类型

| 值 | 说明 | 备注 |
|----|------|------|
| 1 | 出院结算 | 出院时一次性结算 |
| 2 | 中途结算 | 住院期间阶段性结算 |
| 3 | 挂账结算 | 暂不结算，费用记账 |

---

## 支付方式

| 值 | 说明 |
|----|------|
| 1 | 现金 |
| 2 | 银行卡 |
| 3 | 医保卡 |
| 4 | 微信 |
| 5 | 支付宝 |
| 6 | 混合支付 |

---

## 医保类型

| 值 | 说明 |
|----|------|
| 1 | 城镇职工医保 |
| 2 | 城镇居民医保 |
| 3 | 新农合 |
| 4 | 自费 |

---

## 错误码

| 错误码 | 说明 |
|--------|------|
| 1_016_008_000 | 住院结算单不存在 |
| 1_016_008_001 | 结算状态不正确，无法操作 |
| 1_016_008_002 | 结算单已确认，无法修改 |
| 1_016_008_003 | 结算单已退费 |
| 1_016_008_004 | 结算单已作废 |
| 1_016_008_011 | 费用明细已结算，无法删除 |

---

## 前端对接示例

### 出院结算流程

```javascript
// 1. 获取费用汇总（结算前预览）
const getFeeSummary = async (admissionId) => {
  const res = await request({
    url: '/his/settlement/fee-summary',
    method: 'GET',
    params: { admissionId }
  })
  return res.data
}

// 2. 创建结算单
const createSettlement = async (data) => {
  const res = await request({
    url: '/his/settlement/create',
    method: 'POST',
    data
  })
  return res.data // 结算单ID
}

// 3. 结算确认（支付）
const confirmSettlement = async (id, paymentType) => {
  const res = await request({
    url: '/his/settlement/confirm',
    method: 'POST',
    params: { id, paymentType }
  })
  return res.data
}
```

---

## 变更历史

| 版本 | 日期 | 变更内容 | 变更人 |
|------|------|----------|--------|
| v1.0 | 2026-06-18 | 初始版本 | Claude AI |