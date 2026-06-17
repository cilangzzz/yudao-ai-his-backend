# YUDAO-AI-HIS Agent 任务分配配置

> **文档编号**: YUDAO-HIS-AGENT-001
> **版本**: V1.0
> **创建日期**: 2026-06-17

---

## 1. Agent 概述

本项目采用多 Agent 并行开发模式，每个 Agent 负责一个或多个相关模块的开发任务。

---

## 2. Agent 定义

### 2.1 核心Agent (P0)

| Agent名称 | 负责模块 | 主要职责 | 并发数 |
|-----------|----------|----------|--------|
| system-agent | M09 | 用户、角色、权限、字典、科室管理 | 1 |
| outpatient-agent | M01 | 挂号、门诊工作站、收费、药房 | 1 |
| inpatient-agent | M02 | 入院、医嘱、护理、eMAR、出院 | 1 |
| pharmacy-agent | M06 | 药库、药房、处方审核、CDS | 1 |

### 2.2 重要Agent (P1)

| Agent名称 | 负责模块 | 主要职责 | 并发数 |
|-----------|----------|----------|--------|
| emr-agent | M03 | 病历模板、编辑、审签、归档 | 1 |
| lis-agent | M04 | 检验申请、标本、结果、报告 | 1 |
| ris-agent | M05 | 影像申请、检查、存储、报告 | 1 |
| finance-agent | M08 | 收费项目、医保结算、费用记账 | 1 |
| integration-agent | M10 | EMPI、主数据、消息引擎 | 1 |
| surgery-agent | M07 | 手术排期、麻醉记录 | 1 |
| patient-agent | M11 | 患者门户、预约、报告查询 | 1 |

### 2.3 增强Agent (P2)

| Agent名称 | 负责模块 | 主要职责 | 并发数 |
|-----------|----------|----------|--------|
| operation-agent | M12 | 运营看板、统计报表 | 1 |
| ai-agent | M13 | 智能分诊、影像AI、病历质控AI | 1 |

---

## 3. Agent 执行顺序

### 3.1 第一阶段 (Sprint 1)

```
system-agent 执行 M09 系统管理
```

**依赖说明**: 无前置依赖，所有模块依赖此模块

### 3.2 第二阶段 (Sprint 2-3)

```
outpatient-agent 执行 M01 门诊管理
pharmacy-agent 执行 M06 药品管理
```

**并行执行**: 两个 Agent 可同时启动

**依赖说明**: 依赖 M09 已完成

### 3.3 第三阶段 (Sprint 4-5)

```
inpatient-agent 执行 M02 住院管理
```

**依赖说明**: 依赖 M09, M01, M06

### 3.4 第四阶段 (Sprint 6-8)

```
emr-agent 执行 M03 电子病历
lis-agent 执行 M04 检验管理
ris-agent 执行 M05 影像管理
finance-agent 执行 M08 财务管理
```

**并行执行**: 四个 Agent 可同时启动

**依赖说明**: 依赖 M09, M02

### 3.5 第五阶段 (Sprint 9)

```
integration-agent 执行 M10 集成平台
```

**依赖说明**: 依赖 M09

### 3.6 第六阶段 (Sprint 9-10)

```
surgery-agent 执行 M07 手术麻醉
patient-agent 执行 M11 患者服务
operation-agent 执行 M12 运营管理
ai-agent 执行 M13 AI辅助
```

**并行执行**: 四个 Agent 可同时启动

---

## 4. Agent 任务清单

### 4.1 system-agent (M09)

**Sprint**: 1

**任务列表**:
| 任务编号 | 任务名称 | 预估工时 | 状态 |
|----------|----------|----------|------|
| SYS-001 | 用户管理CRUD | 3天 | 待开始 |
| SYS-002 | 角色管理CRUD | 2天 | 待开始 |
| SYS-003 | 权限管理CRUD | 2天 | 待开始 |
| SYS-004 | 数据字典管理 | 2天 | 待开始 |
| SYS-005 | 科室管理CRUD | 2天 | 待开始 |
| SYS-006 | 审计日志记录 | 2天 | 待开始 |

**输出产物**:
- 数据库表 DDL
- Service/Mapper/Controller 代码
- API 文档
- 单元测试

---

### 4.2 outpatient-agent (M01)

**Sprint**: 2-3

**任务列表**:
| 任务编号 | 任务名称 | 预估工时 | 状态 |
|----------|----------|----------|------|
| OP-001 | 挂号管理 | 4天 | 待开始 |
| OP-002 | 排班管理 | 2天 | 待开始 |
| OP-003 | 医生工作站-接诊 | 3天 | 待开始 |
| OP-004 | 医生工作站-处方 | 3天 | 待开始 |
| OP-005 | 收费管理 | 3天 | 待开始 |
| OP-006 | 药房管理 | 3天 | 待开始 |

**输出产物**:
- 数据库表 DDL
- Service/Mapper/Controller 代码
- API 文档
- 单元测试

---

### 4.3 inpatient-agent (M02)

**Sprint**: 4-5

**任务列表**:
| 任务编号 | 任务名称 | 预估工时 | 状态 |
|----------|----------|----------|------|
| IP-001 | 入院管理 | 3天 | 待开始 |
| IP-002 | 医嘱管理 | 4天 | 待开始 |
| IP-003 | 护理工作站 | 4天 | 待开始 |
| IP-004 | eMAR给药 | 4天 | 待开始 |
| IP-005 | 床位管理 | 2天 | 待开始 |
| IP-006 | 出院管理 | 3天 | 待开始 |

**输出产物**:
- 数据库表 DDL
- Service/Mapper/Controller 代码
- API 文档
- 单元测试

---

### 4.4 pharmacy-agent (M06)

**Sprint**: 5

**任务列表**:
| 任务编号 | 任务名称 | 预估工时 | 状态 |
|----------|----------|----------|------|
| PHARM-001 | 药库管理 | 3天 | 待开始 |
| PHARM-002 | 药品目录 | 2天 | 待开始 |
| PHARM-003 | 药房库存 | 2天 | 待开始 |
| PHARM-004 | 处方审核 | 3天 | 待开始 |
| PHARM-005 | CDS引擎 | 3天 | 待开始 |

**输出产物**:
- 数据库表 DDL
- Service/Mapper/Controller 代码
- CDS规则引擎
- API 文档
- 单元测试

---

## 5. Agent 协作规范

### 5.1 代码规范

- 遵循 yudao-vue-pro 框架规范
- DO 类命名: `XxxDO`
- Service 接口命名: `XxxService`
- Service 实现命名: `XxxServiceImpl`
- Controller 命名: `XxxController`

### 5.2 接口规范

- REST API 路径: `/api/{module}/{function}`
- 权限标识: `{module}:{function}:{operation}`
- 错误码前缀: `1_{模块编号}_XXX_XXX`

### 5.3 提交规范

```
feat(module): 功能描述

- 详细变更说明
- 关联任务编号
```

---

## 6. 变更历史

| 版本 | 日期 | 变更内容 | 变更人 |
|------|------|----------|--------|
| V1.0 | 2026-06-17 | 初始版本 | Claude AI |