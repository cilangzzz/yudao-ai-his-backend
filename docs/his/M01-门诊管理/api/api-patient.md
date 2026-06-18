# 患者管理 API 接口文档

> **模块**: 患者管理
> **Controller**: `HisPatientController`
> **基础路径**: `/his/patient`
> **版本**: v1.0
> **更新日期**: 2026-06-18

---

## 接口概览

| 接口 | 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|------|
| 创建患者 | POST | `/create` | `his:patient:create` | 新建患者档案 |
| 更新患者 | PUT | `/update` | `his:patient:update` | 更新患者信息 |
| 删除患者 | DELETE | `/delete` | `his:patient:delete` | 删除患者档案 |
| 获取患者详情 | GET | `/get` | `his:patient:query` | 根据ID查询患者 |
| 获取患者分页 | GET | `/page` | `his:patient:query` | 分页查询患者列表 |
| 根据身份证查询 | GET | `/get-by-id-card` | `his:patient:query` | 根据身份证号查询患者 |

---

## 接口详情

### 1. 创建患者

**请求**
- 方法: `POST`
- 路径: `/his/patient/create`
- 权限: `his:patient:create`
- Content-Type: `application/json`

**请求体** (`HisPatientSaveReqVO`)

```json
{
  "name": "张三",
  "idCardNo": "320102199001011234",
  "gender": 1,
  "birthday": "1990-01-01",
  "phone": "13800138000",
  "address": "江苏省南京市玄武区xx路xx号",
  "emergencyContact": "李四",
  "emergencyPhone": "13900139000",
  "bloodType": "A",
  "marriageStatus": 1,
  "occupation": "工程师",
  "nation": "汉",
  "nativePlace": "江苏南京"
}
```

**参数说明**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | String | 是 | 患者姓名 |
| idCardNo | String | 是 | 身份证号（唯一） |
| gender | Integer | 是 | 性别：1-男，2-女 |
| birthday | LocalDate | 否 | 出生日期 |
| phone | String | 否 | 联系电话 |
| address | String | 否 | 家庭住址 |
| emergencyContact | String | 否 | 紧急联系人 |
| emergencyPhone | String | 否 | 紧急联系电话 |
| bloodType | String | 否 | 血型：A/B/AB/O |
| marriageStatus | Integer | 否 | 婚姻状态：1-未婚，2-已婚，3-离异，4-丧偶 |
| occupation | String | 否 | 职业 |
| nation | String | 否 | 民族 |
| nativePlace | String | 否 | 籍贯 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": 1  // 患者ID
}
```

---

### 2. 更新患者

**请求**
- 方法: `PUT`
- 路径: `/his/patient/update`
- 权限: `his:patient:update`
- Content-Type: `application/json`

**请求体** (`HisPatientSaveReqVO`)

```json
{
  "id": 1,
  "name": "张三",
  "phone": "13800138001",
  "address": "江苏省南京市玄武区xx路xx号",
  "...": "其他字段同创建"
}
```

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": true
}
```

---

### 3. 删除患者

**请求**
- 方法: `DELETE`
- 路径: `/his/patient/delete`
- 权限: `his:patient:delete`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 患者ID |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": true
}
```

---

### 4. 获取患者详情

**请求**
- 方法: `GET`
- 路径: `/his/patient/get`
- 权限: `his:patient:query`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 患者ID |

**响应** (`HisPatientRespVO`)

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "patientNo": "P202406180001",
    "name": "张三",
    "idCardNo": "320102199001011234",
    "gender": 1,
    "genderName": "男",
    "birthday": "1990-01-01",
    "age": 36,
    "phone": "13800138000",
    "address": "江苏省南京市玄武区xx路xx号",
    "emergencyContact": "李四",
    "emergencyPhone": "13900139000",
    "bloodType": "A",
    "marriageStatus": 1,
    "marriageStatusName": "未婚",
    "occupation": "工程师",
    "nation": "汉",
    "nativePlace": "江苏南京",
    "createTime": "2024-06-18 10:00:00"
  }
}
```

---

### 5. 获取患者分页

**请求**
- 方法: `GET`
- 路径: `/his/patient/page`
- 权限: `his:patient:query`

**参数** (`HisPatientPageReqVO`)

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| pageNo | Integer | 是 | 页码（默认1） |
| pageSize | Integer | 是 | 每页条数（默认10） |
| name | String | 否 | 患者姓名（模糊查询） |
| idCardNo | String | 否 | 身份证号 |
| phone | String | 否 | 联系电话 |
| patientNo | String | 否 | 患者编号 |
| gender | Integer | 否 | 性别 |
| createTime | String[] | 否 | 创建时间范围 |

**响应**

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "list": [
      {
        "id": 1,
        "patientNo": "P202406180001",
        "name": "张三",
        "idCardNo": "320102199001011234",
        "gender": 1,
        "genderName": "男",
        "phone": "13800138000",
        "..."
      }
    ],
    "total": 100
  }
}
```

---

### 6. 根据身份证号查询患者

**请求**
- 方法: `GET`
- 路径: `/his/patient/get-by-id-card`
- 权限: `his:patient:query`

**参数**

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| idCardNo | String | 是 | 身份证号 |

**响应**

同「获取患者详情」

---

## 错误码

| 错误码 | 说明 |
|--------|------|
| 1_015_000_000 | 患者不存在 |
| 1_015_000_002 | 身份证号已存在 |
| 1_015_000_003 | 患者姓名不能为空 |

---

## 状态说明

### 性别 (gender)

| 值 | 说明 |
|----|------|
| 1 | 男 |
| 2 | 女 |

### 婚姻状态 (marriageStatus)

| 值 | 说明 |
|----|------|
| 1 | 未婚 |
| 2 | 已婚 |
| 3 | 离异 |
| 4 | 丧偶 |

---

## 前端对接示例

### 创建患者

```javascript
// 创建患者
const createPatient = async (data) => {
  const res = await request({
    url: '/his/patient/create',
    method: 'POST',
    data
  })
  return res.data // 返回患者ID
}
```

### 查询患者分页

```javascript
// 查询患者分页
const getPatientPage = async (params) => {
  const res = await request({
    url: '/his/patient/page',
    method: 'GET',
    params
  })
  return res.data // { list: [], total: 100 }
}
```

---

## 变更历史

| 版本 | 日期 | 变更内容 | 变更人 |
|------|------|----------|--------|
| v1.0 | 2026-06-18 | 初始版本 | Claude AI |
