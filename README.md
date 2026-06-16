# YUDAO-AI-HIS 医院信息系统

> 基于芋道源码（yudao-vue-pro）框架构建的医院信息系统（Hospital Information System）

## 🏥 项目简介

YUDAO-AI-HIS 是一套符合国家标准、满足三级及以上医院信息化建设需求的综合医院信息系统。系统遵循《全国医院信息化建设标准与规范》（国卫规划发〔2018〕4号），基于 HL7 FHIR R4 标准实现数据交换，目标达到 HIMSS EMRAM Stage 5 以上水平。

### 核心特性

- **全面业务覆盖**：门诊管理、住院管理、电子病历、检验(LIS)、影像(PACS)、药品管理、手术麻醉、财务管理
- **标准化设计**：遵循 HL7 FHIR R4、ICD-10、DICOM 等国际医疗信息标准
- **AI 辅助能力**：智能分诊、影像AI辅助诊断、病历质控AI
- **高可用架构**：基于微服务架构，支持容器化部署

## 📊 系统架构

```
YUDAO-AI-HIS
├── M01 门诊管理子系统      # 挂号、医生工作站、收费、药房、输液
├── M02 住院管理子系统      # 入院、医生工作站、护理、床位、出院
├── M03 电子病历子系统      # 病历文书、质控、临床路径
├── M04 检验管理子系统(LIS) # 申请、标本、结果、危急值
├── M05 影像管理子系统(RIS/PACS) # 检查申请、影像采集/存储/查看、报告
├── M06 药品管理子系统      # 药库、采购、处方审核、特殊药品
├── M07 手术麻醉子系统      # 手术申请、排期、麻醉记录、计费
├── M08 财务管理子系统      # 收费项目、医保结算、报表、成本
├── M09 系统管理子系统      # 用户、角色、权限、字典、日志
├── M10 集成平台            # EMPI、主数据、消息引擎、工作流
├── M11 患者服务子系统      # 患者门户、预约、报告查询、缴费
├── M12 运营管理子系统      # 人力资源、物资、设备、绩效、质量
└── M13 人工智能辅助子系统  # 智能分诊、影像AI、病历质控AI
```

## 📁 模块结构

```
yudao-ai-his-backend/
├── yudao-dependencies/      # 依赖管理
├── yudao-framework/         # 框架核心
├── yudao-server/            # 主服务启动模块
│
├── yudao-module-system/     # ✅ 系统管理（核心）
│   ├── 用户、角色、权限管理
│   ├── 组织架构、岗位管理
│   ├── 数据字典、参数配置
│   └── 操作日志、登录日志
│
├── yudao-module-infra/      # ✅ 基础设施（核心）
│   ├── 文件存储（MinIO/OSS）
│   ├── 配置管理
│   ├── 定时任务
│   ├── 代码生成
│   └── 接口监控
│
├── yudao-module-bpm/        # 🔄 流程审批（扩展）
│   └── 用于手术审批、退费审批等医疗流程
│
├── yudao-module-pay/        # 🔄 支付模块（扩展）
│   └── 用于门诊收费、住院预交金、医保结算
│
├── yudao-module-mp/         # 🔄 微信公众号（扩展）
│   └── 用于患者服务：预约挂号、报告查询
│
├── yudao-module-report/     # 🔄 报表模块（扩展）
│   └── 用于运营报表、财务报表、决策支持
│
└── yudao-module-ai/         # 🔄 AI模块（扩展）
    └── 用于智能分诊、影像AI、病历质控AI
```

## 🛠️ 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| **核心框架** | Spring Boot | 2.7.18 |
| **ORM框架** | MyBatis Plus | - |
| **数据库** | MySQL | 8.0+ |
| **缓存** | Redis | - |
| **消息队列** | RabbitMQ / Kafka | - |
| **文件存储** | MinIO | - |
| **工作流引擎** | Flowable | - |
| **容器化** | Docker / Kubernetes | - |

## 🚀 快速开始

### 环境要求

- JDK 1.8+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 启动步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/your-repo/yudao-ai-his-backend.git
   cd yudao-ai-his-backend
   ```

2. **初始化数据库**
   - 创建数据库：`yudao_his`
   - 执行 SQL 脚本：`sql/mysql/*.sql`

3. **修改配置**
   - 编辑 `yudao-server/src/main/resources/application-local.yaml`
   - 配置数据库、Redis 连接信息

4. **启动服务**
   ```bash
   mvn clean install -DskipTests
   cd yudao-server
   mvn spring-boot:run
   ```

5. **访问系统**
   - 后端接口文档：http://localhost:48080/doc.html
   - 默认账号：admin / admin123

## 📖 开发阶段规划

| 阶段 | 模块 | 预计工期 |
|------|------|----------|
| 第一期（MVP） | M09系统管理 + M01门诊核心 + M02住院核心 + M06药品核心 | 4个月 |
| 第二期 | M03电子病历 + M04检验 + M05影像 + M08财务 | 3个月 |
| 第三期 | M07手术麻醉 + M10集成平台 + M11患者服务 | 2个月 |
| 第四期 | M12运营管理 + M13 AI辅助 + 增强功能 | 持续迭代 |

## 📐 设计规范

### 命名规范

| 类型 | 命名规则 | 示例 |
|------|---------|------|
| 数据库表 | 小写下划线，模块前缀 | `his_patient`, `op_register` |
| DO 类 | `XxxDO` | `HisPatientDO` |
| Mapper | `XxxMapper` | `HisPatientMapper` |
| Service | `XxxService` | `HisPatientService` |
| Controller | `XxxController` | `HisPatientController` |
| VO | `XxxSaveReqVO` / `XxxRespVO` | `HisPatientSaveReqVO` |

### 分层架构

```
cn.iocoder.yudao.module.{模块}
├── controller/admin/{功能}/    # Controller + VO
├── service/{功能}/             # Service 接口 + 实现
├── dal/dataobject/{功能}/      # DO 实体
├── dal/mysql/{功能}/           # Mapper
└── enums/                      # 错误码 + 枚举
```

## 📚 参考标准

- 《全国医院信息化建设标准与规范》（国卫规划发〔2018〕4号）
- 《电子病历应用管理规范（试行）》（国卫办医发〔2017〕8号）
- HL7 FHIR R4 Specification
- ICD-10 国际疾病分类
- DICOM 医学数字影像和通信标准
- HIMSS EMRAM 电子病历采纳模型

## 🔗 相关链接

- [芋道源码](https://github.com/YunaiV/ruoyi-vue-pro) - 基础框架
- [芋道官方文档](https://doc.iocoder.cn/) - 开发指南
- [HL7 FHIR R4](http://hl7.org/fhir/R4/) - 医疗数据交换标准

## 📄 开源协议

本项目基于 [MIT License](LICENSE) 开源。

---

> **文档维护**：详细需求文档请参阅 `docs/` 目录
