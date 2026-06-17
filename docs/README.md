# YUDAO-AI-HIS 文档索引

> 医院信息系统（Hospital Information System）完整文档索引

---

## 📚 文档结构

### HIS 需求文档

| 文档 | 说明 | 路径 |
|------|------|------|
| **需求规格说明书** | 系统功能需求、非功能需求、接口需求、验收标准 | [his/HIS系统-需求规格说明书.md](his/HIS系统-需求规格说明书.md) |
| **功能模块清单** | 13个子系统、各模块功能点、优先级定义 | [his/HIS系统-功能模块清单.md](his/HIS系统-功能模块清单.md) |
| **数据字典** | 核心数据实体、表结构、字段定义、FHIR映射 | [his/HIS系统-数据字典.md](his/HIS系统-数据字典.md) |
| **业务流程图** | 门诊流程、住院流程、药品流程、检验检查流程 | [his/HIS系统-业务流程图.md](his/HIS系统-业务流程图.md) |

### 模块技术文档

每个模块文档包含：
- **README.md** — 模块概述、核心功能点、API 索引、数据模型概览
- **api-{domain}.md** — 按业务域拆分的 API 详情
- **data-model.md** — 完整数据模型（表结构、字段定义）
- **pitfalls.md** — 已知踩坑点和注意事项

---

## 🏥 HIS 系统模块索引

### 第一期（MVP）核心模块

| 模块 | HIS用途 | 核心功能 | 文档路径 |
|------|---------|---------|---------|
| **system** | M09 系统管理 | 用户、角色、权限、部门、租户、字典、日志 | [system/](system/README.md) |
| **infra** | M09/M10 基础设施 | 文件、配置、定时任务、代码生成、接口监控 | [infra/](infra/README.md) |

### 第二期扩展模块（按需启用）

| 模块 | HIS用途 | 核心功能 | 文档路径 |
|------|---------|---------|---------|
| **pay** | M01/M08 收费支付 | 订单、退款、钱包、转账、渠道 → 改造为门诊收费、住院预交金 | [pay/](pay/README.md) |
| **bpm** | M03/M07 流程审批 | 流程定义、实例、任务 → 改造为手术审批、退费审批 | [bpm/](bpm/README.md) |
| **mp** | M11 患者服务 | 公众号、消息、菜单 → 改造为预约挂号、报告查询 | [mp/](mp/README.md) |
| **report** | M08/M12 报表分析 | GoView报表、数据查询 → 改造为运营报表、财务报表 | [report/](report/README.md) |
| **ai** | M13 AI辅助 | 对话、图像、知识库 → 改造为智能分诊、影像AI | [ai/](ai/README.md) |

---

## 📊 HIS 系统功能模块总览

```
YUDAO-AI-HIS
├── M01 门诊管理子系统      # 挂号、医生工作站、收费、药房
├── M02 住院管理子系统      # 入院、医生工作站、护理、床位
├── M03 电子病历子系统      # 病历文书、质控、临床路径
├── M04 检验管理子系统(LIS) # 申请、标本、结果、危急值
├── M05 影像管理子系统(PACS) # 影像采集、存储、查看、报告
├── M06 药品管理子系统      # 药库、采购、处方审核
├── M07 手术麻醉子系统      # 手术申请、排期、麻醉记录
├── M08 财务管理子系统      # 收费、医保结算、报表
├── M09 系统管理子系统      # 用户、角色、权限、字典 ← system模块
├── M10 集成平台            # EMPI、主数据、消息引擎 ← infra模块
├── M11 患者服务子系统      # 患者门户、预约、查询 ← mp模块
├── M12 运营管理子系统      # 人力资源、绩效、质量 ← report模块
└── M13 人工智能辅助子系统  # 智能分诊、影像AI ← ai模块
```

---

## 📐 模块依赖关系

```
system (系统管理) ← 所有HIS模块依赖
  │
  ├── infra (基础设施) ← 所有模块
  │   ├── 文件存储：病历文档、影像文件
  │   ├── 定时任务：报表生成、数据同步
  │   └── 接口监控：医保接口、LIS/PACS接口
  │
  ├── pay (支付) → 改造为 HIS收费模块
  │   └── 用于：门诊收费(M01)、住院预交金(M02)、医保结算(M08)
  │
  ├── bpm (工作流) → 改造为 HIS审批流程
  │   └── 用于：手术审批(M07)、退费审批(M01)、病历质控(M03)
  │
  ├── mp (公众号) → 改造为 患者服务
  │   └── 用于：预约挂号(M11)、报告查询(M11)、消息推送
  │
  ├── report (报表) → 改造为 HIS报表
  │   └── 用于：财务报表(M08)、运营报表(M12)
  │
  └── ai (AI) → 改造为 HIS AI辅助
      └── 用于：智能分诊(M13)、影像AI(M13)、病历质控AI(M13)
```

---

## 🗄️ HIS 数据模型概览

### 核心数据实体（参考 HL7 FHIR R4）

| 实体 | FHIR映射 | 说明 |
|------|----------|------|
| his_patient | Patient | 患者主索引（EMPI） |
| his_encounter | Encounter | 就诊记录（门诊/住院） |
| his_admission | Encounter | 住院入院信息 |
| his_order | ServiceRequest | 医嘱（药品/检验/检查） |
| his_prescription | MedicationRequest | 处方 |
| his_diagnosis | Condition | 诊断信息（ICD-10） |
| his_drug | Medication | 药品目录 |
| his_exam | DiagnosticReport | 检验/检查报告 |

### 技术模块数据模型统计

| 模块 | 表数量 | 关键表 |
|------|--------|--------|
| system | 18 | admin_user, system_role, system_menu, system_dept, system_dict_data |
| infra | 11 | infra_file, infra_config, infra_job, infra_api_access_log |
| pay | 9 | pay_order, pay_refund, pay_wallet, pay_channel |
| bpm | 16 | bpm_model, bpm_process_definition, bpm_task |
| ai | 14 | ai_model, ai_chat_message, ai_image, ai_knowledge |
| mp | 8 | mp_account, mp_user, mp_message |
| report | 1 | report_go_view_project |

---

## 🚀 开发阶段规划

| 阶段 | 模块 | 预计工期 |
|------|------|----------|
| **第一期（MVP）** | M09 + M01核心 + M02核心 + M06核心 | 4个月 |
| **第二期** | M03 + M04 + M05 + M08 | 3个月 |
| **第三期** | M07 + M10 + M11 | 2个月 |
| **第四期** | M12 + M13 + 增强功能 | 持续迭代 |

---

## 📖 参考标准

- 《全国医院信息化建设标准与规范》（国卫规划发〔2018〕4号）
- 《电子病历应用管理规范（试行）》
- HL7 FHIR R4 Specification
- ICD-10 国际疾病分类
- DICOM 医学数字影像和通信标准
- HIMSS EMRAM 电子病历采纳模型

---

> **文档维护**：需求文档由项目组维护，技术文档由 AI 辅助生成