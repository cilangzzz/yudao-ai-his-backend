# M02-住院管理 API 接口汇总

> **模块编号**: M02
> **模块名称**: 住院管理子系统
> **版本**: v1.0
> **更新日期**: 2026-06-18

---

## 模块概览

| 子模块 | 文档 | 接口数量 | 说明 |
|--------|------|----------|------|
| 入院管理 | api-admission.md | 8 | 入院登记、床位分配 |
| 预入院管理 | api-preAdmission.md | 5 | 预入院管理 |
| 床位管理 | api-bed.md | 6 | 床位状态管理 |
| 病区管理 | api-ward.md | 5 | 病区信息管理 |
| 预交金管理 | api-prepayment.md | 8 | 预交金缴纳、退还 |
| 医嘱管理 | api-order.md | 10 | 医嘱开立、停止、作废 |
| 医嘱模板 | api-orderTemplate.md | 5 | 医嘱模板管理 |
| 给药管理 | api-medication.md | 8 | eMAR 给药记录 |
| 生命体征 | api-vital.md | 6 | 体温单记录 |
| 护理管理 | api-nursing.md | 8 | 护理记录、交接班 |
| 出院管理 | api-discharge.md | 7 | 出院申请、结算 |
| 住院结算 | [api-settlement.md](api-settlement.md) | 10 | 费用结算、退费 |
| 住院费用 | api-inpatient-fee.md | 7 | 费用明细管理 |

**接口总数**: 约 93 个

---

## 接口总览

### 入院管理 (/his/admission)

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 创建入院记录 | POST | /create | his:admission:create |
| 更新入院记录 | PUT | /update | his:admission:update |
| 获取入院详情 | GET | /get | his:admission:query |
| 分页查询入院 | GET | /page | his:admission:query |
| 分配床位 | POST | /assign-bed | his:admission:assign-bed |
| 入科确认 | POST | /confirm-admission | his:admission:confirm |
| 转科 | POST | /transfer | his:admission:transfer |
| 取消入院 | POST | /cancel | his:admission:cancel |

### 医嘱管理 (/his/order)

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 创建医嘱 | POST | /create | his:order:create |
| 批量创建医嘱 | POST | /batch-create | his:order:create |
| 更新医嘱 | PUT | /update | his:order:update |
| 停止医嘱 | POST | /stop | his:order:stop |
| 作废医嘱 | POST | /cancel | his:order:cancel |
| 获取医嘱详情 | GET | /get | his:order:query |
| 分页查询医嘱 | GET | /page | his:order:query |
| 根据入院ID查询 | GET | /list-by-admission | his:order:query |

### 住院结算 (/his/settlement)

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 创建结算单 | POST | /create | his:settlement:create |
| 更新结算单 | PUT | /update | his:settlement:update |
| 删除结算单 | DELETE | /delete | his:settlement:delete |
| 获取结算单详情 | GET | /get | his:settlement:query |
| 分页查询结算单 | GET | /page | his:settlement:query |
| 根据入院ID查询 | GET | /list-by-admission | his:settlement:query |
| 结算确认 | POST | /confirm | his:settlement:confirm |
| 退费处理 | POST | /refund | his:settlement:refund |
| 作废处理 | POST | /cancel | his:settlement:cancel |
| 费用汇总 | GET | /fee-summary | his:settlement:query |

---

## 前端对接指南

### 1. 认证方式

所有接口需要在请求头携带 Token：

```
Authorization: Bearer {token}
```

### 2. 响应格式

统一响应格式：

```json
{
  "code": 0,
  "msg": "success",
  "data": {}
}
```

### 3. 错误码说明

HIS 住院模块错误码范围：`1_016_XXX_XXX`

常见错误码：
- `1_016_000_000` - 入院记录不存在
- `1_016_003_000` - 医嘱不存在
- `1_016_008_000` - 住院结算单不存在

---

## 业务流程图

### 住院就诊流程

```
入院登记 → 分配床位 → 入科 → 医嘱开立 → 护理执行 → 出院申请 → 出院结算 → 离院
              ↓             ↓           ↓
           转床          停止医嘱     退费
```

### 接口调用顺序

1. **入院阶段**
   - `POST /his/admission/create` - 创建入院记录
   - `GET /his/bed/available` - 查询可用床位
   - `POST /his/admission/assign-bed` - 分配床位
   - `POST /his/prepayment/create` - 缴纳预交金

2. **住院期间**
   - `POST /his/order/create` - 开立医嘱
   - `POST /his/medication/execute` - 执行给药
   - `POST /his/vital/create` - 记录生命体征
   - `POST /his/nursing/create` - 书写护理记录

3. **出院阶段**
   - `POST /his/discharge/apply` - 提交出院申请
   - `GET /his/settlement/fee-summary` - 查看费用汇总
   - `POST /his/settlement/create` - 创建结算单
   - `POST /his/settlement/confirm` - 确认结算

---

## 权限说明

| 权限标识 | 说明 | 角色 |
|----------|------|------|
| his:admission:create | 创建入院 | 入院处 |
| his:admission:assign-bed | 分配床位 | 护士长 |
| his:order:create | 开立医嘱 | 医生 |
| his:order:stop | 停止医嘱 | 医生 |
| his:medication:execute | 执行给药 | 护士 |
| his:vital:create | 记录体征 | 护士 |
| his:settlement:create | 创建结算 | 出院处 |
| his:settlement:confirm | 确认结算 | 出院处 |

---

## 变更历史

| 版本 | 日期 | 变更内容 | 变更人 |
|------|------|----------|--------|
| v1.0 | 2026-06-18 | 初始版本 | Claude AI |