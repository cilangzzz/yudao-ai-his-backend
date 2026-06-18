# 挂号管理 API 接口文档

> **模块**: 挂号管理
> **Controller**: `OpRegisterController`
> **基础路径**: `/his/register`
> **版本**: v1.0
> **更新日期**: 2026-06-18

---

## 接口概览

| 接口 | 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|------|
| 现场挂号 | POST | `/create` | `his:register:create` | 创建挂号记录 |
| 退号 | POST | `/refund` | `his:register:refund` | 退号处理 |
| 获取挂号详情 | GET | `/get` | `his:register:query` | 根据ID查询挂号 |
| 获取挂号分页 | GET | `/page` | `his:register:query` | 分页查询挂号列表 |
| 开始就诊 | POST | `/start-visit` | `his:register:visit` | 医生开始接诊 |
| 结束就诊 | POST | `/end-visit` | `his:register:visit` | 医生结束接诊 |
| 获取候诊列表 | GET | `/waiting-list` | `his:register:query` | 查询候诊患者列表 |
| 叫号 | POST | `/call-next` | `his:register:visit` | 获取下一个候诊患者 |

---

## 接口详情

### 1. 现场挂号

**请求**
- 方法: `POST`
- 路径: `/his/register/create`
- 权限: `his:register:create`
- Content-Type: `application/json`

**请求体** (`OpRegisterSaveReqVO`)

```json
{
  "patientId": 1,
  "deptId": 10,
  "doctorId": 100,
  "scheduleId": 1,
  "registerType": 1,
  "visitDate": "2024-06-18",
  "timeSlot": "AM",
  "queueNo": 1,
  "fee": 50.00,
  "remark": "初诊"
}
```

**参数说明**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| patientId | Long | 是 | 患者ID |
| deptId | Long | 是 | 科室ID |
| doctorId | Long | 是 | 医生ID |
| scheduleId | Long | 是 | 排班ID |
| registerType | Integer | 是 | 挂号类型：1-普通，2-专家，3-急诊 |
| visitDate | LocalDate | 是 | 就诊日期 |
| timeSlot | String | 否 | 时间段：AM-上午，PM-下午 |
| fee | BigDecimal | 否 | 挂号费用 |
| remark | String | 否 | 备注 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": 1  // 挂号ID
}
```

---

### 2. 退号

**请求**
- 方法: `POST`
- 路径: `/his/register/refund`
- 权限: `his:register:refund`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 挂号ID |
| reason | String | 否 | 退号原因 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": true
}
```

**业务规则**
- 只有「已挂号」状态可以退号
- 已就诊的挂号不能退号
- 退号后自动触发退费流程

---

### 3. 获取挂号详情

**请求**
- 方法: `GET`
- 路径: `/his/register/get`
- 权限: `his:register:query`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 挂号ID |

**响应** (`OpRegisterRespVO`)

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "registerNo": "GH202406180001",
    "patientId": 1,
    "patientName": "张三",
    "idCardNo": "320102199001011234",
    "gender": 1,
    "age": 36,
    "phone": "13800138000",
    "deptId": 10,
    "deptName": "内科",
    "doctorId": 100,
    "doctorName": "王医生",
    "scheduleId": 1,
    "registerType": 1,
    "registerTypeName": "普通",
    "visitDate": "2024-06-18",
    "timeSlot": "AM",
    "queueNo": 1,
    "status": 1,
    "statusName": "已挂号",
    "fee": 50.00,
    "payStatus": 1,
    "payStatusName": "已支付",
    "createTime": "2024-06-18 08:00:00",
    "visitStartTime": null,
    "visitEndTime": null
  }
}
```

---

### 4. 获取挂号分页

**请求**
- 方法: `GET`
- 路径: `/his/register/page`
- 权限: `his:register:query`

**参数** (`OpRegisterPageReqVO`)

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| pageNo | Integer | 是 | 页码 |
| pageSize | Integer | 是 | 每页条数 |
| registerNo | String | 否 | 挂号单号 |
| patientName | String | 否 | 患者姓名 |
| idCardNo | String | 否 | 身份证号 |
| deptId | Long | 否 | 科室ID |
| doctorId | Long | 否 | 医生ID |
| status | Integer | 否 | 挂号状态 |
| visitDate | LocalDate | 否 | 就诊日期 |
| registerType | Integer | 否 | 挂号类型 |

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

### 5. 开始就诊

**请求**
- 方法: `POST`
- 路径: `/his/register/start-visit`
- 权限: `his:register:visit`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 挂号ID |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": true
}
```

**业务规则**
- 只有「已挂号」状态可以开始就诊
- 开始就诊后状态变为「就诊中」
- 记录就诊开始时间

---

### 6. 结束就诊

**请求**
- 方法: `POST`
- 路径: `/his/register/end-visit`
- 权限: `his:register:visit`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 挂号ID |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": true
}
```

**业务规则**
- 只有「就诊中」状态可以结束就诊
- 结束就诊后状态变为「已就诊」
- 记录就诊结束时间

---

### 7. 获取候诊列表

**请求**
- 方法: `GET`
- 路径: `/his/register/waiting-list`
- 权限: `his:register:query`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| doctorId | Long | 是 | 医生ID |
| date | LocalDate | 否 | 日期（默认当天） |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "registerNo": "GH202406180001",
      "queueNo": 1,
      "patientName": "张三",
      "gender": 1,
      "age": 36,
      "status": 1,
      "statusName": "候诊",
      "waitTime": 15  // 预计等待分钟
    },
    {
      "id": 2,
      "queueNo": 2,
      "patientName": "李四",
      "..."
    }
  ]
}
```

---

### 8. 叫号

**请求**
- 方法: `POST`
- 路径: `/his/register/call-next`
- 权限: `his:register:visit`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| doctorId | Long | 是 | 医生ID |
| date | LocalDate | 否 | 日期（默认当天） |

**响应**

返回下一个候诊患者信息，并自动将该患者状态改为「就诊中」

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "registerNo": "GH202406180001",
    "queueNo": 1,
    "patientName": "张三",
    "..."
  }
}
```

---

## 状态流转

```
已挂号(1) → 就诊中(2) → 已就诊(3)
    ↓
  已退号(4)
```

| 状态值 | 说明 | 可操作 |
|--------|------|--------|
| 1 | 已挂号/候诊 | 开始就诊、退号 |
| 2 | 就诊中 | 结束就诊 |
| 3 | 已就诊 | 无 |
| 4 | 已退号 | 无 |

---

## 挂号类型

| 值 | 说明 | 备注 |
|----|------|------|
| 1 | 普通 | 普通门诊 |
| 2 | 专家 | 专家门诊 |
| 3 | 急诊 | 急诊挂号 |

---

## 错误码

| 错误码 | 说明 |
|--------|------|
| 1_015_001_000 | 挂号记录不存在 |
| 1_015_001_001 | 挂号已取消，无法操作 |
| 1_015_001_002 | 挂号已完成就诊，无法退号 |
| 1_015_001_003 | 排班不存在 |
| 1_015_001_004 | 该时段号源已满 |
| 1_015_001_005 | 科室不存在 |
| 1_015_001_006 | 医生不存在 |

---

## 前端对接示例

### 现场挂号

```javascript
const createRegister = async (data) => {
  const res = await request({
    url: '/his/register/create',
    method: 'POST',
    data
  })
  return res.data // 挂号ID
}
```

### 医生工作站 - 获取候诊列表

```javascript
const getWaitingList = async (doctorId, date) => {
  const res = await request({
    url: '/his/register/waiting-list',
    method: 'GET',
    params: { doctorId, date }
  })
  return res.data // 候诊患者列表
}
```

### 医生工作站 - 叫号接诊

```javascript
const callNextPatient = async (doctorId) => {
  const res = await request({
    url: '/his/register/call-next',
    method: 'POST',
    params: { doctorId }
  })
  return res.data // 下一个患者信息
}
```

---

## 变更历史

| 版本 | 日期 | 变更内容 | 变更人 |
|------|------|----------|--------|
| v1.0 | 2026-06-18 | 初始版本 | Claude AI |