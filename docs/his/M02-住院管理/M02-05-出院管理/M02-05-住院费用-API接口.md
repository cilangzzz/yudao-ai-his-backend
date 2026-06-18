# 住院费用明细 API 接口文档

> **模块**: 住院费用明细
> **Controller**: `HisInpatientFeeController`
> **基础路径**: `/his/inpatient-fee`
> **版本**: v1.0
> **更新日期**: 2026-06-18

---

## 接口概览

| 接口 | 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|------|
| 创建费用明细 | POST | `/create` | `his:inpatient-fee:create` | 创建单条费用明细 |
| 批量创建费用明细 | POST | `/batch-create` | `his:inpatient-fee:create` | 批量创建费用明细 |
| 更新费用明细 | PUT | `/update` | `his:inpatient-fee:update` | 更新费用明细信息 |
| 删除费用明细 | DELETE | `/delete` | `his:inpatient-fee:delete` | 删除未结算的费用 |
| 获取费用明细详情 | GET | `/get` | `his:inpatient-fee:query` | 根据ID查询费用 |
| 获取费用明细分页 | GET | `/page` | `his:inpatient-fee:query` | 分页查询费用列表 |
| 根据入院ID查询 | GET | `/list-by-admission` | `his:inpatient-fee:query` | 查询入院的所有费用 |
| 费用汇总统计 | GET | `/summary` | `his:inpatient-fee:query` | 汇总费用统计 |

---

## 接口详情

### 1. 创建费用明细

**请求**
- 方法: `POST`
- 路径: `/his/inpatient-fee/create`
- 权限: `his:inpatient-fee:create`
- Content-Type: `application/json`

**请求体** (`HisInpatientFeeSaveReqVO`)

```json
{
  "admissionId": 100,
  "patientId": 1,
  "patientName": "张三",
  "inpatientNo": "ZY20240001",
  "orderId": 1,
  "orderNo": "ORD20240001",
  "itemCode": "ITEM001",
  "itemName": "血常规",
  "itemType": 3,
  "itemCategory": "检验",
  "spec": "",
  "unit": "次",
  "quantity": 1,
  "unitPrice": 50.00,
  "discountAmount": 0,
  "insuranceCode": "YB001",
  "insuranceType": 1,
  "insuranceRatio": 80,
  "deptId": 10,
  "deptName": "检验科",
  "doctorId": 100,
  "doctorName": "王医生",
  "executeTime": "2024-06-18 10:00:00",
  "feeDate": "2024-06-18",
  "remark": ""
}
```

**参数说明**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| admissionId | Long | 是 | 入院记录ID |
| patientId | Long | 是 | 患者ID |
| patientName | String | 否 | 患者姓名 |
| inpatientNo | String | 否 | 住院号 |
| orderId | Long | 否 | 医嘱ID |
| orderNo | String | 否 | 医嘱编号 |
| itemCode | String | 是 | 项目编码 |
| itemName | String | 是 | 项目名称 |
| itemType | Integer | 是 | 项目类型 |
| quantity | BigDecimal | 是 | 数量 |
| unitPrice | BigDecimal | 是 | 单价 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": 1  // 费用明细ID
}
```

---

### 2. 批量创建费用明细

**请求**
- 方法: `POST`
- 路径: `/his/inpatient-fee/batch-create`
- 权限: `his:inpatient-fee:create`
- Content-Type: `application/json`

**请求体**

```json
[
  { "...": "费用明细1" },
  { "...": "费用明细2" }
]
```

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": [1, 2, 3]  // 费用明细ID列表
}
```

---

### 3. 根据入院ID查询费用明细列表

**请求**
- 方法: `GET`
- 路径: `/his/inpatient-fee/list-by-admission`
- 权限: `his:inpatient-fee:query`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| admissionId | Long | 是 | 入院记录ID |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "feeNo": "FEE202406180001",
      "admissionId": 100,
      "patientId": 1,
      "patientName": "张三",
      "itemCode": "ITEM001",
      "itemName": "血常规",
      "itemType": 3,
      "itemTypeName": "检验",
      "quantity": 1,
      "unitPrice": 50.00,
      "totalAmount": 50.00,
      "discountAmount": 0,
      "payAmount": 50.00,
      "insuranceType": 1,
      "insuranceRatio": 80,
      "insuranceAmount": 40.00,
      "selfAmount": 10.00,
      "feeStatus": 0,
      "feeStatusName": "未结算",
      "feeDate": "2024-06-18",
      "createTime": "2024-06-18 10:00:00"
    }
  ]
}
```

---

### 4. 费用汇总统计

**请求**
- 方法: `GET`
- 路径: `/his/inpatient-fee/summary`
- 权限: `his:inpatient-fee:query`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| admissionId | Long | 是 | 入院记录ID |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "totalAmount": 10000.00
  }
}
```

---

## 费用项目类型

| 值 | 说明 |
|----|------|
| 1 | 药品 |
| 2 | 检查 |
| 3 | 检验 |
| 4 | 诊疗 |
| 5 | 护理 |
| 6 | 手术 |
| 7 | 床位 |
| 8 | 其他 |

---

## 费用状态

| 值 | 说明 | 可操作 |
|----|------|--------|
| 0 | 未结算 | 可删除、可结算 |
| 1 | 已结算 | 不可操作 |
| 2 | 已退费 | 不可操作 |

---

## 错误码

| 错误码 | 说明 |
|--------|------|
| 1_016_008_010 | 住院费用明细不存在 |
| 1_016_008_011 | 费用明细已结算，无法删除 |
| 1_016_008_012 | 费用明细已退费 |

---

## 前端对接示例

### 查询住院费用清单

```javascript
const getFeeList = async (admissionId) => {
  const res = await request({
    url: '/his/inpatient-fee/list-by-admission',
    method: 'GET',
    params: { admissionId }
  })
  return res.data
}
```

### 批量记账

```javascript
const batchCreateFee = async (feeList) => {
  const res = await request({
    url: '/his/inpatient-fee/batch-create',
    method: 'POST',
    data: feeList
  })
  return res.data
}
```

---

## 变更历史

| 版本 | 日期 | 变更内容 | 变更人 |
|------|------|----------|--------|
| v1.0 | 2026-06-18 | 初始版本 | Claude AI |