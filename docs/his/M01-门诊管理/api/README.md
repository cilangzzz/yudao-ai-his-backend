# M01-门诊管理 API 接口汇总

> **模块编号**: M01
> **模块名称**: 门诊管理子系统
> **版本**: v1.0
> **更新日期**: 2026-06-18

---

## 模块概览

| 子模块 | 文档 | 接口数量 | 说明 |
|--------|------|----------|------|
| 患者管理 | [api-patient.md](api-patient.md) | 6 | 患者档案 CRUD |
| 挂号管理 | [api-register.md](api-register.md) | 8 | 挂号、退号、叫号 |
| 排班管理 | api-schedule.md | 5 | 医生排班管理 |
| 预约管理 | api-appointment.md | 5 | 预约挂号管理 |
| 处方管理 | api-prescription.md | 7 | 处方开立与审核 |
| 诊断管理 | api-diagnosis.md | 5 | 诊断录入管理 |
| 门诊收费 | api-fee.md | 8 | 费用计算与收费 |
| 门诊支付 | api-payment.md | 6 | 支付处理 |
| 门诊退费 | api-refund.md | 5 | 退费处理 |
| 门诊发药 | api-dispense.md | 6 | 处方发药 |
| 门诊退药 | api-drugreturn.md | 6 | 退药处理 |
| 检查申请 | api-exam.md | 6 | 检查申请管理 |
| 检验申请 | api-lab.md | 6 | 检验申请管理 |

**接口总数**: 约 79 个

---

## 接口总览

### 患者管理 (/his/patient)

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 创建患者 | POST | /create | his:patient:create |
| 更新患者 | PUT | /update | his:patient:update |
| 删除患者 | DELETE | /delete | his:patient:delete |
| 获取患者详情 | GET | /get | his:patient:query |
| 获取患者分页 | GET | /page | his:patient:query |
| 根据身份证查询 | GET | /get-by-id-card | his:patient:query |

### 挂号管理 (/his/register)

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 现场挂号 | POST | /create | his:register:create |
| 退号 | POST | /refund | his:register:refund |
| 获取挂号详情 | GET | /get | his:register:query |
| 获取挂号分页 | GET | /page | his:register:query |
| 开始就诊 | POST | /start-visit | his:register:visit |
| 结束就诊 | POST | /end-visit | his:register:visit |
| 获取候诊列表 | GET | /waiting-list | his:register:query |
| 叫号 | POST | /call-next | his:register:visit |

---

## 前端对接指南

### 1. 认证方式

所有接口需要在请求头携带 Token：

```
Authorization: Bearer {token}
```

### 2. 请求格式

- Content-Type: `application/json`
- 请求参数格式：Query 参数或 JSON Body

### 3. 响应格式

统一响应格式：

```json
{
  "code": 0,        // 0 表示成功，非 0 表示失败
  "msg": "success", // 响应消息
  "data": {}        // 响应数据
}
```

分页响应格式：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "list": [],
    "total": 100
  }
}
```

### 4. 错误码说明

HIS 门诊模块错误码范围：`1_015_XXX_XXX`

常见错误码：
- `1_015_000_000` - 患者不存在
- `1_015_001_000` - 挂号记录不存在
- `1_015_003_000` - 处方不存在

---

## 业务流程图

### 门诊就诊流程

```
患者建档 → 挂号 → 候诊 → 接诊 → 开处方 → 收费 → 发药 → 离院
          ↓                    ↓
        退号                  退费
```

### 接口调用顺序

1. **挂号阶段**
   - `GET /his/patient/get-by-id-card` - 查询患者
   - `POST /his/register/create` - 创建挂号

2. **接诊阶段**
   - `GET /his/register/waiting-list` - 获取候诊列表
   - `POST /his/register/call-next` - 叫号
   - `POST /his/register/start-visit` - 开始就诊
   - `POST /his/prescription/create` - 开处方
   - `POST /his/register/end-visit` - 结束就诊

3. **收费阶段**
   - `GET /his/fee/page` - 查询费用
   - `POST /his/payment/create` - 创建支付
   - `POST /his/payment/confirm` - 确认支付

4. **发药阶段**
   - `GET /his/dispense/page` - 获取待发药处方
   - `POST /his/dispense/confirm` - 确认发药

---

## 权限说明

| 权限标识 | 说明 | 角色 |
|----------|------|------|
| his:patient:create | 创建患者 | 挂号员 |
| his:patient:query | 查询患者 | 全员 |
| his:register:create | 创建挂号 | 挂号员 |
| his:register:refund | 退号 | 挂号员 |
| his:register:visit | 接诊操作 | 医生 |
| his:prescription:create | 开处方 | 医生 |
| his:fee:query | 查询费用 | 收费员 |
| his:payment:create | 创建支付 | 收费员 |
| his:dispense:confirm | 发药 | 药师 |

---

## 变更历史

| 版本 | 日期 | 变更内容 | 变更人 |
|------|------|----------|--------|
| v1.0 | 2026-06-18 | 初始版本 | Claude AI |