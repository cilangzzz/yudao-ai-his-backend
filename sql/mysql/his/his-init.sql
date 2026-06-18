-- ============================================
-- HIS 医院信息系统数据库初始化脚本
-- 版本: V1.0
-- 日期: 2026-06-17
-- 说明: 包含门诊管理、住院管理、药品管理核心表
-- ============================================

-- ============================================
-- 第一部分: 基础字典表
-- ============================================

-- ----------------------------
-- 科室信息表
-- ----------------------------
DROP TABLE IF EXISTS `his_department`;
CREATE TABLE `his_department` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `dept_code` VARCHAR(20) NOT NULL COMMENT '科室编码',
    `dept_name` VARCHAR(100) NOT NULL COMMENT '科室名称',
    `dept_short_name` VARCHAR(50) DEFAULT NULL COMMENT '科室简称',
    `dept_type` TINYINT NOT NULL COMMENT '科室类型:1-临床 2-医技 3-行政 4-后勤',
    `parent_id` BIGINT DEFAULT 0 COMMENT '上级科室ID',
    `dept_level` TINYINT DEFAULT NULL COMMENT '科室层级',
    `sort_order` INT DEFAULT 0 COMMENT '排序号',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '科室电话',
    `location` VARCHAR(100) DEFAULT NULL COMMENT '科室位置',
    `bed_count` INT DEFAULT 0 COMMENT '编制床位数',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-正常 2-停用',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_dept_code` (`tenant_id`, `dept_code`, `deleted`),
    KEY `idx_tenant_parent_id` (`tenant_id`, `parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='科室信息表';

-- ----------------------------
-- 医护人员表
-- ----------------------------
DROP TABLE IF EXISTS `his_staff`;
CREATE TABLE `his_staff` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `staff_code` VARCHAR(20) NOT NULL COMMENT '人员编码',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` TINYINT NOT NULL COMMENT '性别:1-男 2-女',
    `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
    `id_card_no` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `dept_id` BIGINT NOT NULL COMMENT '所属科室',
    `position` VARCHAR(50) DEFAULT NULL COMMENT '职务',
    `title` VARCHAR(50) DEFAULT NULL COMMENT '职称',
    `specialty` VARCHAR(100) DEFAULT NULL COMMENT '专业特长',
    `doctor_flag` TINYINT DEFAULT 0 COMMENT '是否医生:0-否 1-是',
    `nurse_flag` TINYINT DEFAULT 0 COMMENT '是否护士:0-否 1-是',
    `pharmacist_flag` TINYINT DEFAULT 0 COMMENT '是否药师:0-否 1-是',
    `lab_tech_flag` TINYINT DEFAULT 0 COMMENT '是否检验技师:0-否 1-是',
    `rad_tech_flag` TINYINT DEFAULT 0 COMMENT '是否放射技师:0-否 1-是',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-在职 2-离职 3-退休',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_staff_code` (`tenant_id`, `staff_code`, `deleted`),
    KEY `idx_tenant_dept_id` (`tenant_id`, `dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='医护人员表';

-- ----------------------------
-- 收费项目字典表
-- ----------------------------
DROP TABLE IF EXISTS `his_charge_item`;
CREATE TABLE `his_charge_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `item_code` VARCHAR(20) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) NOT NULL COMMENT '项目名称',
    `item_category` TINYINT NOT NULL COMMENT '项目类别:1-挂号费 2-诊查费 3-检查费 4-治疗费 5-手术费 6-化验费 7-材料费 8-药品费 9-床位费 10-护理费 11-其他',
    `item_subcategory` VARCHAR(50) DEFAULT NULL COMMENT '子类别',
    `spec` VARCHAR(100) DEFAULT NULL COMMENT '规格',
    `unit` VARCHAR(20) NOT NULL COMMENT '计价单位',
    `unit_price` DECIMAL(12,4) NOT NULL COMMENT '单价',
    `insurance_code` VARCHAR(20) DEFAULT NULL COMMENT '医保编码',
    `insurance_price` DECIMAL(12,4) DEFAULT NULL COMMENT '医保定价',
    `insurance_category` TINYINT DEFAULT NULL COMMENT '医保类别:1-甲类 2-乙类 3-丙类',
    `execution_dept` BIGINT DEFAULT NULL COMMENT '执行科室',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-在用 2-停用',
    `effective_date` DATE DEFAULT NULL COMMENT '生效日期',
    `expire_date` DATE DEFAULT NULL COMMENT '失效日期',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_item_code` (`tenant_id`, `item_code`, `deleted`),
    KEY `idx_tenant_category` (`tenant_id`, `item_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收费项目字典表';

-- ============================================
-- 第二部分: M01 门诊管理模块
-- ============================================

-- ----------------------------
-- 患者主索引表
-- ----------------------------
DROP TABLE IF EXISTS `his_patient`;
CREATE TABLE `his_patient` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `patient_no` VARCHAR(20) NOT NULL COMMENT '患者编号(院内唯一)',
    `id_card_no` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` TINYINT NOT NULL COMMENT '性别:1-男 2-女 9-未知',
    `birth_date` DATE NOT NULL COMMENT '出生日期',
    `age` INT DEFAULT NULL COMMENT '年龄(系统计算)',
    `age_unit` TINYINT DEFAULT 1 COMMENT '年龄单位:1-岁 2-月 3-天',
    `nation` VARCHAR(20) DEFAULT NULL COMMENT '民族',
    `nationality` VARCHAR(20) DEFAULT '中国' COMMENT '国籍',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `address` VARCHAR(200) DEFAULT NULL COMMENT '家庭住址',
    `marital_status` TINYINT DEFAULT NULL COMMENT '婚姻状况:1-未婚 2-已婚 3-离异 4-丧偶',
    `occupation` VARCHAR(50) DEFAULT NULL COMMENT '职业',
    `contact_name` VARCHAR(50) DEFAULT NULL COMMENT '联系人姓名',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系人电话',
    `contact_relation` VARCHAR(20) DEFAULT NULL COMMENT '联系人关系',
    `blood_type` TINYINT DEFAULT NULL COMMENT '血型:1-A 2-B 3-AB 4-O 5-不详',
    `rh_blood` TINYINT DEFAULT NULL COMMENT 'RH血型:1-阳性 2-阴性',
    `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型:1-城镇职工 2-城镇居民 3-新农合 4-自费',
    `insurance_no` VARCHAR(30) DEFAULT NULL COMMENT '医保卡号',
    `allergy_history` TEXT DEFAULT NULL COMMENT '过敏史',
    `medical_history` TEXT DEFAULT NULL COMMENT '既往病史',
    `family_history` TEXT DEFAULT NULL COMMENT '家族史',
    `photo_url` VARCHAR(200) DEFAULT NULL COMMENT '照片URL',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-正常 2-注销 3-死亡',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_patient_no` (`tenant_id`, `patient_no`, `deleted`),
    UNIQUE KEY `uk_tenant_id_card` (`tenant_id`, `id_card_no`, `deleted`),
    KEY `idx_tenant_name` (`tenant_id`, `name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='患者主索引表';

-- ----------------------------
-- 患者就诊卡表
-- ----------------------------
DROP TABLE IF EXISTS `his_patient_card`;
CREATE TABLE `his_patient_card` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `card_no` VARCHAR(20) NOT NULL COMMENT '卡号',
    `card_type` TINYINT NOT NULL COMMENT '卡类型:1-就诊卡 2-医保卡 3-身份证',
    `card_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-正常 2-挂失 3-注销',
    `bind_time` DATETIME DEFAULT NULL COMMENT '绑定时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_card_no` (`tenant_id`, `card_no`, `deleted`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='患者就诊卡表';

-- ----------------------------
-- 医生排班表
-- ----------------------------
DROP TABLE IF EXISTS `op_schedule`;
CREATE TABLE `op_schedule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `doctor_id` BIGINT NOT NULL COMMENT '医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '医生姓名',
    `dept_id` BIGINT NOT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `schedule_date` DATE NOT NULL COMMENT '排班日期',
    `time_period` TINYINT NOT NULL COMMENT '时段:1-上午 2-下午 3-晚上',
    `register_type` TINYINT NOT NULL COMMENT '挂号类型:1-普通 2-专家 3-急诊',
    `total_quota` INT NOT NULL DEFAULT 0 COMMENT '总号源数',
    `used_quota` INT NOT NULL DEFAULT 0 COMMENT '已用号源数',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-正常 2-停诊 3-已满',
    `consultation_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '诊查费',
    `register_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '挂号费',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_doctor_date` (`tenant_id`, `doctor_id`, `schedule_date`),
    KEY `idx_tenant_dept_date` (`tenant_id`, `dept_id`, `schedule_date`),
    KEY `idx_tenant_date_period` (`tenant_id`, `schedule_date`, `time_period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='医生排班表';

-- ----------------------------
-- 预约挂号表
-- ----------------------------
DROP TABLE IF EXISTS `op_appointment`;
CREATE TABLE `op_appointment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `appointment_no` VARCHAR(30) NOT NULL COMMENT '预约编号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `schedule_id` BIGINT NOT NULL COMMENT '排班ID',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '医生姓名',
    `dept_id` BIGINT NOT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `appointment_date` DATE NOT NULL COMMENT '预约日期',
    `time_period` TINYINT NOT NULL COMMENT '时段:1-上午 2-下午 3-晚上',
    `queue_no` INT DEFAULT NULL COMMENT '排队序号',
    `register_type` TINYINT NOT NULL COMMENT '挂号类型:1-普通 2-专家 3-急诊',
    `source_type` TINYINT NOT NULL COMMENT '来源:1-现场 2-微信 3-APP 4-网站 5-电话',
    `appointment_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-已预约 2-已取号 3-已就诊 4-已取消 5-已过期',
    `cancel_reason` VARCHAR(200) DEFAULT NULL COMMENT '取消原因',
    `cancel_time` DATETIME DEFAULT NULL COMMENT '取消时间',
    `cancel_by` BIGINT DEFAULT NULL COMMENT '取消人',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_appointment_no` (`tenant_id`, `appointment_no`, `deleted`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_schedule_id` (`tenant_id`, `schedule_id`),
    KEY `idx_tenant_date_status` (`tenant_id`, `appointment_date`, `appointment_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预约挂号表';

-- ----------------------------
-- 挂号记录表
-- ----------------------------
DROP TABLE IF EXISTS `op_register`;
CREATE TABLE `op_register` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `register_no` VARCHAR(30) NOT NULL COMMENT '挂号编号',
    `appointment_id` BIGINT DEFAULT NULL COMMENT '预约ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `patient_phone` VARCHAR(20) DEFAULT NULL COMMENT '患者电话',
    `id_card_no` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    `schedule_id` BIGINT NOT NULL COMMENT '排班ID',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '医生姓名',
    `dept_id` BIGINT NOT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `register_date` DATE NOT NULL COMMENT '挂号日期',
    `time_period` TINYINT NOT NULL COMMENT '时段:1-上午 2-下午 3-晚上',
    `queue_no` INT NOT NULL COMMENT '排队序号',
    `register_type` TINYINT NOT NULL COMMENT '挂号类型:1-普通 2-专家 3-急诊',
    `source_type` TINYINT NOT NULL COMMENT '来源:1-现场 2-微信 3-APP 4-网站 5-自助机',
    `register_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '挂号费',
    `consultation_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '诊查费',
    `total_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '总费用',
    `pay_status` TINYINT NOT NULL DEFAULT 0 COMMENT '支付状态:0-未支付 1-已支付 2-已退费',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `pay_type` TINYINT DEFAULT NULL COMMENT '支付方式:1-现金 2-微信 3-支付宝 4-医保',
    `register_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-候诊 2-就诊中 3-已完成 4-已退号',
    `visit_time` DATETIME DEFAULT NULL COMMENT '就诊时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '就诊结束时间',
    `refund_amount` DECIMAL(10,2) DEFAULT NULL COMMENT '退费金额',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退号时间',
    `refund_by` BIGINT DEFAULT NULL COMMENT '退号操作人',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_register_no` (`tenant_id`, `register_no`, `deleted`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_doctor_date` (`tenant_id`, `doctor_id`, `register_date`),
    KEY `idx_tenant_dept_date` (`tenant_id`, `dept_id`, `register_date`),
    KEY `idx_tenant_date_status` (`tenant_id`, `register_date`, `register_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='挂号记录表';

-- ----------------------------
-- 诊断记录表
-- ----------------------------
DROP TABLE IF EXISTS `his_diagnosis`;
CREATE TABLE `his_diagnosis` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `encounter_id` BIGINT DEFAULT NULL COMMENT '就诊ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `diagnosis_type` TINYINT NOT NULL COMMENT '诊断类型:1-门诊 2-入院 3-出院 4-修正 5-补充',
    `diagnosis_code` VARCHAR(20) NOT NULL COMMENT 'ICD-10诊断编码',
    `diagnosis_name` VARCHAR(200) NOT NULL COMMENT '诊断名称',
    `diagnosis_seq` INT NOT NULL DEFAULT 1 COMMENT '诊断序号(1=主诊断)',
    `diagnosis_status` TINYINT NOT NULL COMMENT '状态:1-初步 2-确定 3-修正',
    `diagnose_doctor` BIGINT NOT NULL COMMENT '诊断医生',
    `diagnose_time` DATETIME NOT NULL COMMENT '诊断时间',
    `remarks` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_encounter_id` (`tenant_id`, `encounter_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='诊断记录表';

-- ----------------------------
-- 处方主表
-- ----------------------------
DROP TABLE IF EXISTS `op_prescription`;
CREATE TABLE `op_prescription` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `prescription_no` VARCHAR(30) NOT NULL COMMENT '处方编号',
    `register_id` BIGINT NOT NULL COMMENT '挂号ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `prescription_type` TINYINT NOT NULL COMMENT '处方类型:1-普通 2-急诊 3-儿科 4-麻醉 5-精神 6-中药',
    `dept_id` BIGINT NOT NULL COMMENT '开方科室',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `doctor_id` BIGINT NOT NULL COMMENT '开方医生',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '医生姓名',
    `diagnose_code` VARCHAR(20) DEFAULT NULL COMMENT '诊断编码',
    `diagnose_name` VARCHAR(100) DEFAULT NULL COMMENT '诊断名称',
    `total_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '处方总金额',
    `pharmacist_id` BIGINT DEFAULT NULL COMMENT '审方药师',
    `pharmacist_name` VARCHAR(50) DEFAULT NULL COMMENT '审方药师姓名',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审方时间',
    `audit_result` TINYINT DEFAULT NULL COMMENT '审方结果:1-通过 2-退回',
    `audit_remark` VARCHAR(500) DEFAULT NULL COMMENT '审核意见',
    `dispense_pharmacist` BIGINT DEFAULT NULL COMMENT '调配药师',
    `dispense_pharmacist_name` VARCHAR(50) DEFAULT NULL COMMENT '调配药师姓名',
    `dispense_time` DATETIME DEFAULT NULL COMMENT '调配时间',
    `send_pharmacist` BIGINT DEFAULT NULL COMMENT '发药药师',
    `send_pharmacist_name` VARCHAR(50) DEFAULT NULL COMMENT '发药药师姓名',
    `send_time` DATETIME DEFAULT NULL COMMENT '发药时间',
    `prescription_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-开立 2-审核通过 3-审核退回 4-已调配 5-已发药 6-已退药',
    `valid_days` INT DEFAULT 3 COMMENT '有效天数',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_prescription_no` (`tenant_id`, `prescription_no`, `deleted`),
    KEY `idx_tenant_register_id` (`tenant_id`, `register_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_status` (`tenant_id`, `prescription_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='处方主表';

-- ----------------------------
-- 处方明细表
-- ----------------------------
DROP TABLE IF EXISTS `op_prescription_item`;
CREATE TABLE `op_prescription_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `prescription_id` BIGINT NOT NULL COMMENT '处方ID',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(20) NOT NULL COMMENT '药品编码',
    `drug_name` VARCHAR(100) NOT NULL COMMENT '药品通用名',
    `drug_spec` VARCHAR(100) DEFAULT NULL COMMENT '药品规格',
    `manufacturer` VARCHAR(100) DEFAULT NULL COMMENT '生产厂家',
    `dosage` DECIMAL(10,4) NOT NULL COMMENT '单次剂量',
    `dosage_unit` VARCHAR(20) NOT NULL COMMENT '剂量单位',
    `frequency_code` VARCHAR(20) NOT NULL COMMENT '频次编码',
    `frequency_name` VARCHAR(50) NOT NULL COMMENT '频次名称',
    `route_code` VARCHAR(20) NOT NULL COMMENT '用药途径编码',
    `route_name` VARCHAR(50) NOT NULL COMMENT '用药途径名称',
    `quantity` DECIMAL(10,2) NOT NULL COMMENT '数量',
    `unit` VARCHAR(20) NOT NULL COMMENT '单位',
    `unit_price` DECIMAL(10,4) NOT NULL COMMENT '单价',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '金额',
    `days` INT DEFAULT NULL COMMENT '用药天数',
    `skin_test` TINYINT DEFAULT 0 COMMENT '皮试标志:0-不需要 1-需要 2-已做通过 3-已做未通过',
    `skin_test_result` VARCHAR(50) DEFAULT NULL COMMENT '皮试结果',
    `skin_test_time` DATETIME DEFAULT NULL COMMENT '皮试时间',
    `skin_test_nurse` BIGINT DEFAULT NULL COMMENT '皮试护士',
    `remarks` VARCHAR(200) DEFAULT NULL COMMENT '用药备注',
    `sort_order` INT DEFAULT 0 COMMENT '排序号',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_prescription_id` (`tenant_id`, `prescription_id`),
    KEY `idx_tenant_drug_id` (`tenant_id`, `drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='处方明细表';

-- ----------------------------
-- 门诊费用表
-- ----------------------------
DROP TABLE IF EXISTS `op_fee`;
CREATE TABLE `op_fee` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `fee_no` VARCHAR(30) NOT NULL COMMENT '费用单号',
    `register_id` BIGINT NOT NULL COMMENT '挂号ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `dept_id` BIGINT NOT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `total_amount` DECIMAL(12,2) NOT NULL COMMENT '总金额',
    `discount_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '优惠金额',
    `pay_amount` DECIMAL(12,2) NOT NULL COMMENT '应付金额',
    `insurance_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '医保支付',
    `self_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '自付金额',
    `fee_status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0-未收费 1-已收费 2-已退费',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_fee_no` (`tenant_id`, `fee_no`, `deleted`),
    KEY `idx_tenant_register_id` (`tenant_id`, `register_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='门诊费用表';

-- ----------------------------
-- 支付记录表
-- ----------------------------
DROP TABLE IF EXISTS `op_payment`;
CREATE TABLE `op_payment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `payment_no` VARCHAR(30) NOT NULL COMMENT '支付单号',
    `fee_id` BIGINT NOT NULL COMMENT '费用ID',
    `register_id` BIGINT NOT NULL COMMENT '挂号ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `pay_amount` DECIMAL(12,2) NOT NULL COMMENT '支付金额',
    `pay_type` TINYINT NOT NULL COMMENT '支付方式:1-现金 2-微信 3-支付宝 4-医保 5-银行卡',
    `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型',
    `insurance_no` VARCHAR(30) DEFAULT NULL COMMENT '医保卡号',
    `insurance_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '医保支付金额',
    `self_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '自付金额',
    `pay_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-成功 2-失败 3-已退费',
    `pay_time` DATETIME NOT NULL COMMENT '支付时间',
    `cashier_id` BIGINT NOT NULL COMMENT '收费员',
    `cashier_name` VARCHAR(50) DEFAULT NULL COMMENT '收费员姓名',
    `invoice_no` VARCHAR(30) DEFAULT NULL COMMENT '发票号',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_payment_no` (`tenant_id`, `payment_no`, `deleted`),
    KEY `idx_tenant_fee_id` (`tenant_id`, `fee_id`),
    KEY `idx_tenant_register_id` (`tenant_id`, `register_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付记录表';

-- ----------------------------
-- 支付明细表
-- ----------------------------
DROP TABLE IF EXISTS `op_payment_item`;
CREATE TABLE `op_payment_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `payment_id` BIGINT NOT NULL COMMENT '支付记录ID',
    `fee_id` BIGINT NOT NULL COMMENT '费用ID',
    `fee_item_id` BIGINT NOT NULL COMMENT '费用明细ID',
    `register_id` BIGINT NOT NULL COMMENT '挂号ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `charge_item_id` BIGINT DEFAULT NULL COMMENT '收费项目ID',
    `item_code` VARCHAR(20) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(100) NOT NULL COMMENT '项目名称',
    `item_category` TINYINT NOT NULL COMMENT '项目类别:1-挂号费 2-诊查费 3-检查费 4-治疗费 5-手术费 6-化验费 7-材料费 8-药品费 9-床位费 10-护理费 11-其他',
    `spec` VARCHAR(100) DEFAULT NULL COMMENT '规格',
    `unit` VARCHAR(20) NOT NULL COMMENT '计价单位',
    `unit_price` DECIMAL(10,4) NOT NULL COMMENT '单价',
    `quantity` DECIMAL(10,2) NOT NULL COMMENT '数量',
    `fee_amount` DECIMAL(12,2) NOT NULL COMMENT '费用明细金额',
    `pay_amount` DECIMAL(12,2) NOT NULL COMMENT '本次支付金额',
    `discount_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '优惠金额',
    `insurance_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '医保支付金额',
    `self_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '自付金额',
    `pay_type` TINYINT NOT NULL COMMENT '支付方式:1-现金 2-微信 3-支付宝 4-医保 5-银行卡',
    `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型',
    `insurance_no` VARCHAR(30) DEFAULT NULL COMMENT '医保卡号',
    `insurance_code` VARCHAR(20) DEFAULT NULL COMMENT '医保编码',
    `insurance_category` TINYINT DEFAULT NULL COMMENT '医保类别:1-甲类 2-乙类 3-丙类',
    `pay_status` TINYINT NOT NULL DEFAULT 0 COMMENT '支付状态:0-待支付 1-已支付 2-已退费',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退费时间',
    `refund_amount` DECIMAL(12,2) DEFAULT NULL COMMENT '退费金额',
    `execution_dept_id` BIGINT DEFAULT NULL COMMENT '执行科室ID',
    `execution_dept_name` VARCHAR(100) DEFAULT NULL COMMENT '执行科室名称',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '开单医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '开单医生姓名',
    `sort_order` INT DEFAULT 0 COMMENT '排序号',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_payment_id` (`tenant_id`, `payment_id`),
    KEY `idx_tenant_fee_id` (`tenant_id`, `fee_id`),
    KEY `idx_tenant_fee_item_id` (`tenant_id`, `fee_item_id`),
    KEY `idx_tenant_register_id` (`tenant_id`, `register_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付明细表';

-- ----------------------------
-- 退费记录表
-- ----------------------------
DROP TABLE IF EXISTS `op_refund`;
CREATE TABLE `op_refund` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `refund_no` VARCHAR(30) NOT NULL COMMENT '退费单号',
    `payment_id` BIGINT NOT NULL COMMENT '原支付ID',
    `fee_id` BIGINT NOT NULL COMMENT '费用ID',
    `register_id` BIGINT NOT NULL COMMENT '挂号ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `refund_amount` DECIMAL(12,2) NOT NULL COMMENT '退费金额',
    `refund_reason` VARCHAR(200) DEFAULT NULL COMMENT '退费原因',
    `refund_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-待审核 2-已通过 3-已拒绝 4-已完成',
    `apply_time` DATETIME NOT NULL COMMENT '申请时间',
    `apply_by` BIGINT NOT NULL COMMENT '申请人',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `audit_by` BIGINT DEFAULT NULL COMMENT '审核人',
    `audit_remark` VARCHAR(500) DEFAULT NULL COMMENT '审核意见',
    `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    `operator_id` BIGINT DEFAULT NULL COMMENT '操作员',
    `operator_name` VARCHAR(50) DEFAULT NULL COMMENT '操作员姓名',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_refund_no` (`tenant_id`, `refund_no`, `deleted`),
    KEY `idx_tenant_payment_id` (`tenant_id`, `payment_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退费记录表';

-- ----------------------------
-- 发药记录表
-- ----------------------------
DROP TABLE IF EXISTS `op_dispense`;
CREATE TABLE `op_dispense` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `dispense_no` VARCHAR(30) NOT NULL COMMENT '发药单号',
    `prescription_id` BIGINT NOT NULL COMMENT '处方ID',
    `register_id` BIGINT NOT NULL COMMENT '挂号ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `pharmacy_id` BIGINT NOT NULL COMMENT '药房ID',
    `pharmacy_name` VARCHAR(100) DEFAULT NULL COMMENT '药房名称',
    `dispense_pharmacist` BIGINT DEFAULT NULL COMMENT '调配药师',
    `dispense_pharmacist_name` VARCHAR(50) DEFAULT NULL COMMENT '调配药师姓名',
    `dispense_time` DATETIME DEFAULT NULL COMMENT '调配时间',
    `send_pharmacist` BIGINT DEFAULT NULL COMMENT '发药药师',
    `send_pharmacist_name` VARCHAR(50) DEFAULT NULL COMMENT '发药药师姓名',
    `send_time` DATETIME DEFAULT NULL COMMENT '发药时间',
    `dispense_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-待调配 2-已调配 3-已发药 4-已退药',
    `total_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总金额',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_dispense_no` (`tenant_id`, `dispense_no`, `deleted`),
    KEY `idx_tenant_prescription_id` (`tenant_id`, `prescription_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='发药记录表';

-- ----------------------------
-- 发药明细表
-- ----------------------------
DROP TABLE IF EXISTS `op_dispense_item`;
CREATE TABLE `op_dispense_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `dispense_id` BIGINT NOT NULL COMMENT '发药ID',
    `prescription_item_id` BIGINT NOT NULL COMMENT '处方明细ID',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(20) NOT NULL COMMENT '药品编码',
    `drug_name` VARCHAR(100) NOT NULL COMMENT '药品名称',
    `drug_spec` VARCHAR(100) DEFAULT NULL COMMENT '药品规格',
    `batch_no` VARCHAR(50) DEFAULT NULL COMMENT '批号',
    `quantity` DECIMAL(10,2) NOT NULL COMMENT '发药数量',
    `unit` VARCHAR(20) NOT NULL COMMENT '单位',
    `unit_price` DECIMAL(10,4) NOT NULL COMMENT '单价',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '金额',
    `expiry_date` DATE DEFAULT NULL COMMENT '有效期',
    `storage_location` VARCHAR(50) DEFAULT NULL COMMENT '货架位置',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_dispense_id` (`tenant_id`, `dispense_id`),
    KEY `idx_tenant_drug_id` (`tenant_id`, `drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='发药明细表';

-- ============================================
-- 第三部分: M02 住院管理模块
-- ============================================

-- ----------------------------
-- 病区表
-- ----------------------------
DROP TABLE IF EXISTS `his_ward`;
CREATE TABLE `his_ward` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `ward_code` VARCHAR(20) NOT NULL COMMENT '病区编码',
    `ward_name` VARCHAR(100) NOT NULL COMMENT '病区名称',
    `dept_id` BIGINT NOT NULL COMMENT '所属科室',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `floor` VARCHAR(20) DEFAULT NULL COMMENT '楼层',
    `bed_count` INT DEFAULT 0 COMMENT '床位总数',
    `used_bed_count` INT DEFAULT 0 COMMENT '已用床位数',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-正常 2-停用',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_ward_code` (`tenant_id`, `ward_code`, `deleted`),
    KEY `idx_tenant_dept_id` (`tenant_id`, `dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='病区表';

-- ----------------------------
-- 床位表
-- ----------------------------
DROP TABLE IF EXISTS `his_bed`;
CREATE TABLE `his_bed` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `bed_no` VARCHAR(10) NOT NULL COMMENT '床号',
    `ward_id` BIGINT NOT NULL COMMENT '病区ID',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',
    `room_no` VARCHAR(10) DEFAULT NULL COMMENT '房间号',
    `bed_type` TINYINT DEFAULT 1 COMMENT '床位类型:1-普通 2-抢救 3-ICU 4-隔离',
    `bed_class` TINYINT DEFAULT 1 COMMENT '床位等级:1-普通 2-双人 3-单人 4-VIP',
    `daily_price` DECIMAL(10,2) DEFAULT 0.00 COMMENT '床位日单价',
    `bed_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-空床 2-占用 3-预约 4-清洁 5-维修',
    `current_patient_id` BIGINT DEFAULT NULL COMMENT '当前患者ID',
    `current_patient_name` VARCHAR(50) DEFAULT NULL COMMENT '当前患者姓名',
    `admission_id` BIGINT DEFAULT NULL COMMENT '当前住院ID',
    `sort_order` INT DEFAULT 0 COMMENT '排序号',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_ward_bed` (`tenant_id`, `ward_id`, `bed_no`, `deleted`),
    KEY `idx_tenant_ward_id` (`tenant_id`, `ward_id`),
    KEY `idx_tenant_status` (`tenant_id`, `bed_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='床位表';

-- ----------------------------
-- 入院记录表
-- ----------------------------
DROP TABLE IF EXISTS `his_admission`;
CREATE TABLE `his_admission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `admission_no` VARCHAR(20) NOT NULL COMMENT '住院号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `patient_phone` VARCHAR(20) DEFAULT NULL COMMENT '患者电话',
    `id_card_no` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    `register_id` BIGINT DEFAULT NULL COMMENT '关联门诊挂号ID',
    `admission_date` DATETIME NOT NULL COMMENT '入院日期',
    `admission_dept` BIGINT NOT NULL COMMENT '入院科室',
    `admission_dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `ward_id` BIGINT DEFAULT NULL COMMENT '病区ID',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',
    `bed_id` BIGINT DEFAULT NULL COMMENT '床位ID',
    `bed_no` VARCHAR(10) DEFAULT NULL COMMENT '床号',
    `admission_diagnosis` VARCHAR(200) DEFAULT NULL COMMENT '入院诊断',
    `diagnosis_code` VARCHAR(20) DEFAULT NULL COMMENT '入院诊断ICD-10编码',
    `attending_doctor` BIGINT NOT NULL COMMENT '主治医师',
    `attending_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '主治医师姓名',
    `resident_doctor` BIGINT DEFAULT NULL COMMENT '住院医师',
    `resident_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '住院医师姓名',
    `nurse_id` BIGINT DEFAULT NULL COMMENT '责任护士',
    `nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '责任护士姓名',
    `admission_way` TINYINT DEFAULT 1 COMMENT '入院方式:1-门诊 2-急诊 3-转院',
    `admission_condition` TINYINT DEFAULT 3 COMMENT '入院情况:1-危 2-急 3-一般',
    `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型',
    `insurance_no` VARCHAR(30) DEFAULT NULL COMMENT '医保号',
    `deposit_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '预交金总额',
    `total_fee` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总费用',
    `discharge_date` DATETIME DEFAULT NULL COMMENT '出院日期',
    `discharge_dept` BIGINT DEFAULT NULL COMMENT '出院科室',
    `discharge_diagnosis` VARCHAR(200) DEFAULT NULL COMMENT '出院诊断',
    `discharge_code` VARCHAR(20) DEFAULT NULL COMMENT '出院诊断ICD-10编码',
    `discharge_way` TINYINT DEFAULT NULL COMMENT '出院方式:1-医嘱出院 2-自动出院 3-转院 4-死亡',
    `admission_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-在院 2-出院 3-转科',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_admission_no` (`tenant_id`, `admission_no`, `deleted`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_dept_status` (`tenant_id`, `admission_dept`, `admission_status`),
    KEY `idx_tenant_date` (`tenant_id`, `admission_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入院记录表';

-- ----------------------------
-- 预交金表
-- ----------------------------
DROP TABLE IF EXISTS `his_prepayment`;
CREATE TABLE `his_prepayment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `prepay_no` VARCHAR(30) NOT NULL COMMENT '预交金单号',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '金额',
    `pay_type` TINYINT NOT NULL COMMENT '支付方式:1-现金 2-微信 3-支付宝 4-银行卡',
    `pay_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-正常 2-已退',
    `pay_time` DATETIME NOT NULL COMMENT '支付时间',
    `operator_id` BIGINT NOT NULL COMMENT '操作员',
    `operator_name` VARCHAR(50) DEFAULT NULL COMMENT '操作员姓名',
    `refund_amount` DECIMAL(12,2) DEFAULT NULL COMMENT '退款金额',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退款时间',
    `refund_by` BIGINT DEFAULT NULL COMMENT '退款人',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_prepay_no` (`tenant_id`, `prepay_no`, `deleted`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预交金表';

-- ----------------------------
-- 入院评估表
-- ----------------------------
DROP TABLE IF EXISTS `his_admission_assess`;
CREATE TABLE `his_admission_assess` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `assess_type` TINYINT NOT NULL COMMENT '评估类型:1-入院评估 2-跌倒评估 3-压疮评估 4-疼痛评估 5-营养评估',
    `assess_score` INT DEFAULT NULL COMMENT '评估得分',
    `assess_result` VARCHAR(200) DEFAULT NULL COMMENT '评估结果',
    `risk_level` TINYINT DEFAULT NULL COMMENT '风险等级:1-低 2-中 3-高',
    `assess_content` TEXT DEFAULT NULL COMMENT '评估内容(JSON)',
    `assess_time` DATETIME NOT NULL COMMENT '评估时间',
    `assess_nurse` BIGINT NOT NULL COMMENT '评估护士',
    `assess_nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '评估护士姓名',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入院评估表';

-- ----------------------------
-- 医嘱主表
-- ----------------------------
DROP TABLE IF EXISTS `his_order`;
CREATE TABLE `his_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `order_no` VARCHAR(30) NOT NULL COMMENT '医嘱编号',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `order_type` TINYINT NOT NULL COMMENT '医嘱类型:1-药品 2-检验 3-检查 4-护理 5-手术 6-饮食 7-其他',
    `order_category` TINYINT NOT NULL COMMENT '医嘱分类:1-长期 2-临时',
    `order_content` VARCHAR(500) NOT NULL COMMENT '医嘱内容',
    `item_code` VARCHAR(20) DEFAULT NULL COMMENT '项目编码',
    `item_name` VARCHAR(100) DEFAULT NULL COMMENT '项目名称',
    `dosage` VARCHAR(50) DEFAULT NULL COMMENT '剂量',
    `dosage_unit` VARCHAR(20) DEFAULT NULL COMMENT '剂量单位',
    `frequency_code` VARCHAR(20) DEFAULT NULL COMMENT '频次编码',
    `frequency_name` VARCHAR(50) DEFAULT NULL COMMENT '频次名称',
    `route_code` VARCHAR(20) DEFAULT NULL COMMENT '途径编码',
    `route_name` VARCHAR(50) DEFAULT NULL COMMENT '途径名称',
    `duration` INT DEFAULT NULL COMMENT '疗程(天)',
    `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
    `stop_time` DATETIME DEFAULT NULL COMMENT '停止时间',
    `prescribing_doctor` BIGINT NOT NULL COMMENT '开立医生',
    `prescribing_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '开立医生姓名',
    `audit_nurse` BIGINT DEFAULT NULL COMMENT '审核护士',
    `audit_nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '审核护士姓名',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `execute_nurse` BIGINT DEFAULT NULL COMMENT '执行护士',
    `execute_nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '执行护士姓名',
    `execute_time` DATETIME DEFAULT NULL COMMENT '执行时间',
    `order_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-开立 2-审核 3-执行中 4-已完成 5-已停止 6-已作废',
    `stop_doctor` BIGINT DEFAULT NULL COMMENT '停止医生',
    `stop_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '停止医生姓名',
    `stop_reason` VARCHAR(200) DEFAULT NULL COMMENT '停止原因',
    `urgency` TINYINT DEFAULT 0 COMMENT '紧急标志:0-常规 1-紧急',
    `remarks` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_order_no` (`tenant_id`, `order_no`, `deleted`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_status` (`tenant_id`, `order_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='医嘱主表';

-- ----------------------------
-- 给药记录表(eMAR)
-- ----------------------------
DROP TABLE IF EXISTS `his_medication_admin`;
CREATE TABLE `his_medication_admin` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `order_id` BIGINT NOT NULL COMMENT '医嘱ID',
    `prescription_item_id` BIGINT DEFAULT NULL COMMENT '处方明细ID',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_name` VARCHAR(100) NOT NULL COMMENT '药品名称',
    `dosage` DECIMAL(10,4) NOT NULL COMMENT '给药剂量',
    `dosage_unit` VARCHAR(20) NOT NULL COMMENT '剂量单位',
    `route_code` VARCHAR(20) NOT NULL COMMENT '给药途径',
    `route_name` VARCHAR(50) DEFAULT NULL COMMENT '给药途径名称',
    `scheduled_time` DATETIME NOT NULL COMMENT '预定给药时间',
    `actual_time` DATETIME DEFAULT NULL COMMENT '实际给药时间',
    `nurse_id` BIGINT DEFAULT NULL COMMENT '给药护士',
    `nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '给药护士姓名',
    `patient_wristband_scan` TINYINT DEFAULT 0 COMMENT '患者腕带扫描:0-未扫描 1-已扫描匹配 2-不匹配',
    `drug_barcode_scan` TINYINT DEFAULT 0 COMMENT '药品条码扫描:0-未扫描 1-已扫描匹配 2-不匹配',
    `admin_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-待执行 2-已执行 3-未执行(拒服) 4-延迟执行',
    `skip_reason` VARCHAR(200) DEFAULT NULL COMMENT '未执行原因',
    `remarks` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_order_id` (`tenant_id`, `order_id`),
    KEY `idx_tenant_scheduled_time` (`tenant_id`, `scheduled_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='给药记录表(eMAR)';

-- ----------------------------
-- 护理记录表
-- ----------------------------
DROP TABLE IF EXISTS `his_nursing_record`;
CREATE TABLE `his_nursing_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `record_type` TINYINT NOT NULL COMMENT '记录类型:1-一般护理 2-危重护理 3-手术护理 4-出入量 5-健康教育',
    `record_time` DATETIME NOT NULL COMMENT '记录时间',
    `nurse_id` BIGINT NOT NULL COMMENT '记录护士',
    `nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '记录护士姓名',
    `temperature` DECIMAL(4,1) DEFAULT NULL COMMENT '体温(°C)',
    `pulse` INT DEFAULT NULL COMMENT '脉搏(次/分)',
    `respiration` INT DEFAULT NULL COMMENT '呼吸(次/分)',
    `blood_pressure_high` INT DEFAULT NULL COMMENT '收缩压(mmHg)',
    `blood_pressure_low` INT DEFAULT NULL COMMENT '舒张压(mmHg)',
    `blood_oxygen` INT DEFAULT NULL COMMENT '血氧饱和度(%)',
    `consciousness` TINYINT DEFAULT NULL COMMENT '意识状态:1-清醒 2-嗜睡 3-昏迷',
    `intake_amount` DECIMAL(8,1) DEFAULT NULL COMMENT '入量(ml)',
    `output_amount` DECIMAL(8,1) DEFAULT NULL COMMENT '出量(ml)',
    `pain_score` INT DEFAULT NULL COMMENT '疼痛评分(0-10)',
    `nursing_content` TEXT DEFAULT NULL COMMENT '护理内容',
    `nursing_measures` TEXT DEFAULT NULL COMMENT '护理措施',
    `patient_condition` TEXT DEFAULT NULL COMMENT '病情观察',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_record_time` (`tenant_id`, `record_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理记录表';

-- ----------------------------
-- 生命体征表
-- ----------------------------
DROP TABLE IF EXISTS `his_vital_sign`;
CREATE TABLE `his_vital_sign` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `record_time` DATETIME NOT NULL COMMENT '记录时间',
    `temperature` DECIMAL(4,1) DEFAULT NULL COMMENT '体温(°C)',
    `pulse` INT DEFAULT NULL COMMENT '脉搏(次/分)',
    `respiration` INT DEFAULT NULL COMMENT '呼吸(次/分)',
    `blood_pressure_high` INT DEFAULT NULL COMMENT '收缩压(mmHg)',
    `blood_pressure_low` INT DEFAULT NULL COMMENT '舒张压(mmHg)',
    `blood_oxygen` INT DEFAULT NULL COMMENT '血氧饱和度(%)',
    `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重(kg)',
    `height` DECIMAL(5,2) DEFAULT NULL COMMENT '身高(cm)',
    `pain_score` INT DEFAULT NULL COMMENT '疼痛评分(0-10)',
    `nurse_id` BIGINT DEFAULT NULL COMMENT '记录护士',
    `nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '记录护士姓名',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_record_time` (`tenant_id`, `record_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='生命体征表';

-- ----------------------------
-- 护理交接班表
-- ----------------------------
DROP TABLE IF EXISTS `his_nursing_shift`;
CREATE TABLE `his_nursing_shift` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `ward_id` BIGINT NOT NULL COMMENT '病区ID',
    `shift_type` TINYINT NOT NULL COMMENT '班次:1-白班 2-夜班',
    `shift_date` DATE NOT NULL COMMENT '交接班日期',
    `from_nurse_id` BIGINT NOT NULL COMMENT '交班护士',
    `from_nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '交班护士姓名',
    `to_nurse_id` BIGINT NOT NULL COMMENT '接班护士',
    `to_nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '接班护士姓名',
    `patient_count` INT DEFAULT 0 COMMENT '在院患者数',
    `new_count` INT DEFAULT 0 COMMENT '新入院数',
    `discharge_count` INT DEFAULT 0 COMMENT '出院数',
    `surgery_count` INT DEFAULT 0 COMMENT '手术数',
    `critical_count` INT DEFAULT 0 COMMENT '危重患者数',
    `shift_content` TEXT DEFAULT NULL COMMENT '交接班内容',
    `special_notes` TEXT DEFAULT NULL COMMENT '特殊情况',
    `shift_time` DATETIME NOT NULL COMMENT '交接班时间',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_ward_date` (`tenant_id`, `ward_id`, `shift_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理交接班表';

-- ----------------------------
-- 出院记录表
-- ----------------------------
DROP TABLE IF EXISTS `his_discharge`;
CREATE TABLE `his_discharge` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `discharge_no` VARCHAR(30) NOT NULL COMMENT '出院编号',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `apply_time` DATETIME DEFAULT NULL COMMENT '申请时间',
    `apply_doctor` BIGINT DEFAULT NULL COMMENT '申请医生',
    `apply_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '申请医生姓名',
    `discharge_date` DATETIME DEFAULT NULL COMMENT '出院日期',
    `discharge_way` TINYINT NOT NULL COMMENT '出院方式:1-医嘱出院 2-自动出院 3-转院 4-死亡',
    `discharge_diagnosis` VARCHAR(200) DEFAULT NULL COMMENT '出院诊断',
    `discharge_code` VARCHAR(20) DEFAULT NULL COMMENT '出院诊断ICD-10编码',
    `discharge_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-待结算 2-已结算 3-已出院',
    `total_fee` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总费用',
    `insurance_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '医保支付',
    `self_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '自付金额',
    `deposit_used` DECIMAL(12,2) DEFAULT 0.00 COMMENT '使用预交金',
    `refund_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '退款金额',
    `settle_time` DATETIME DEFAULT NULL COMMENT '结算时间',
    `settle_by` BIGINT DEFAULT NULL COMMENT '结算人',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_discharge_no` (`tenant_id`, `discharge_no`, `deleted`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出院记录表';

-- ----------------------------
-- 出院小结表
-- ----------------------------
DROP TABLE IF EXISTS `his_discharge_summary`;
CREATE TABLE `his_discharge_summary` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `discharge_id` BIGINT NOT NULL COMMENT '出院ID',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `admission_diagnosis` VARCHAR(200) DEFAULT NULL COMMENT '入院诊断',
    `discharge_diagnosis` VARCHAR(200) DEFAULT NULL COMMENT '出院诊断',
    `admission_condition` TEXT DEFAULT NULL COMMENT '入院情况',
    `treatment_process` TEXT DEFAULT NULL COMMENT '诊疗经过',
    `discharge_condition` TEXT DEFAULT NULL COMMENT '出院情况',
    `discharge_advice` TEXT DEFAULT NULL COMMENT '出院医嘱',
    `follow_up` VARCHAR(200) DEFAULT NULL COMMENT '随访建议',
    `medication_advice` TEXT DEFAULT NULL COMMENT '用药指导',
    `author_doctor` BIGINT NOT NULL COMMENT '书写医生',
    `author_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '书写医生姓名',
    `author_time` DATETIME DEFAULT NULL COMMENT '书写时间',
    `review_doctor` BIGINT DEFAULT NULL COMMENT '审核医生',
    `review_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '审核医生姓名',
    `review_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `summary_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-待审核 2-已审核',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_discharge_id` (`tenant_id`, `discharge_id`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出院小结表';

-- ============================================
-- 第四部分: M06 药品管理模块
-- ============================================

-- ----------------------------
-- 药品目录表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug`;
CREATE TABLE `his_drug` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `drug_code` VARCHAR(20) NOT NULL COMMENT '药品编码(唯一)',
    `drug_name` VARCHAR(100) NOT NULL COMMENT '药品通用名',
    `drug_trade_name` VARCHAR(100) DEFAULT NULL COMMENT '药品商品名',
    `drug_type` TINYINT NOT NULL COMMENT '药品类型:1-西药 2-中成药 3-中草药 4-生物制品',
    `drug_category` VARCHAR(50) DEFAULT NULL COMMENT '药品类别',
    `dosage_form` VARCHAR(50) DEFAULT NULL COMMENT '剂型(片剂、注射剂等)',
    `spec` VARCHAR(100) DEFAULT NULL COMMENT '规格',
    `unit` VARCHAR(20) NOT NULL COMMENT '基本单位',
    `manufacturer` VARCHAR(100) DEFAULT NULL COMMENT '生产厂家',
    `approval_no` VARCHAR(50) DEFAULT NULL COMMENT '批准文号',
    `retail_price` DECIMAL(10,4) NOT NULL COMMENT '零售价',
    `purchase_price` DECIMAL(10,4) DEFAULT NULL COMMENT '采购价',
    `insurance_code` VARCHAR(20) DEFAULT NULL COMMENT '医保目录编码',
    `insurance_category` TINYINT DEFAULT NULL COMMENT '医保类别:1-甲类 2-乙类 3-丙类',
    `otc_flag` TINYINT DEFAULT 0 COMMENT 'OTC标志:0-处方药 1-OTC',
    `narcotic_flag` TINYINT DEFAULT 0 COMMENT '麻醉药品标志:0-否 1-是',
    `psychotropic_flag` TINYINT DEFAULT 0 COMMENT '精神药品标志:0-否 1-是',
    `toxic_flag` TINYINT DEFAULT 0 COMMENT '毒性药品标志:0-否 1-是',
    `antibiotic_flag` TINYINT DEFAULT 0 COMMENT '抗菌药物标志:0-否 1-是',
    `storage_condition` VARCHAR(100) DEFAULT NULL COMMENT '储存条件',
    `shelf_life` INT DEFAULT NULL COMMENT '有效期(月)',
    `barcode` VARCHAR(30) DEFAULT NULL COMMENT '条形码',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-在用 2-停用',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_drug_code` (`tenant_id`, `drug_code`, `deleted`),
    KEY `idx_tenant_name` (`tenant_id`, `drug_name`),
    KEY `idx_tenant_type` (`tenant_id`, `drug_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='药品目录表';

-- ----------------------------
-- 药品库存表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_stock`;
CREATE TABLE `his_drug_stock` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(20) DEFAULT NULL COMMENT '药品编码',
    `drug_name` VARCHAR(100) DEFAULT NULL COMMENT '药品名称',
    `batch_no` VARCHAR(50) NOT NULL COMMENT '批号',
    `expiry_date` DATE NOT NULL COMMENT '有效期',
    `production_date` DATE DEFAULT NULL COMMENT '生产日期',
    `quantity` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '库存数量',
    `unit` VARCHAR(20) DEFAULT NULL COMMENT '单位',
    `retail_price` DECIMAL(10,4) DEFAULT NULL COMMENT '零售价',
    `purchase_price` DECIMAL(10,4) DEFAULT NULL COMMENT '采购价',
    `storage_location` VARCHAR(50) DEFAULT NULL COMMENT '货架位置',
    `warehouse_id` BIGINT DEFAULT NULL COMMENT '仓库ID',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-正常 2-近效期 3-过期',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_drug_id` (`tenant_id`, `drug_id`),
    KEY `idx_tenant_batch_no` (`tenant_id`, `batch_no`),
    KEY `idx_tenant_expiry_date` (`tenant_id`, `expiry_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='药品库存表';

-- ----------------------------
-- 入库记录表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_inbound`;
CREATE TABLE `his_drug_inbound` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `inbound_no` VARCHAR(30) NOT NULL COMMENT '入库单号',
    `inbound_type` TINYINT NOT NULL COMMENT '入库类型:1-采购入库 2-退货入库 3-调拨入库',
    `supplier_id` BIGINT DEFAULT NULL COMMENT '供应商ID',
    `supplier_name` VARCHAR(100) DEFAULT NULL COMMENT '供应商名称',
    `purchase_id` BIGINT DEFAULT NULL COMMENT '采购单ID',
    `warehouse_id` BIGINT DEFAULT NULL COMMENT '仓库ID',
    `warehouse_name` VARCHAR(100) DEFAULT NULL COMMENT '仓库名称',
    `total_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总金额',
    `inbound_time` DATETIME DEFAULT NULL COMMENT '入库时间',
    `operator_id` BIGINT DEFAULT NULL COMMENT '操作员',
    `operator_name` VARCHAR(50) DEFAULT NULL COMMENT '操作员姓名',
    `audit_status` TINYINT NOT NULL DEFAULT 1 COMMENT '审核状态:1-待审核 2-已审核 3-已拒绝',
    `audit_by` BIGINT DEFAULT NULL COMMENT '审核人',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_inbound_no` (`tenant_id`, `inbound_no`, `deleted`),
    KEY `idx_tenant_supplier_id` (`tenant_id`, `supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入库记录表';

-- ----------------------------
-- 出库记录表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_outbound`;
CREATE TABLE `his_drug_outbound` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `outbound_no` VARCHAR(30) NOT NULL COMMENT '出库单号',
    `outbound_type` TINYINT NOT NULL COMMENT '出库类型:1-药房领用 2-报损出库 3-调拨出库',
    `target_id` BIGINT DEFAULT NULL COMMENT '出库目标ID',
    `target_name` VARCHAR(100) DEFAULT NULL COMMENT '出库目标名称',
    `warehouse_id` BIGINT DEFAULT NULL COMMENT '仓库ID',
    `warehouse_name` VARCHAR(100) DEFAULT NULL COMMENT '仓库名称',
    `total_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总金额',
    `outbound_time` DATETIME DEFAULT NULL COMMENT '出库时间',
    `operator_id` BIGINT DEFAULT NULL COMMENT '操作员',
    `operator_name` VARCHAR(50) DEFAULT NULL COMMENT '操作员姓名',
    `audit_status` TINYINT NOT NULL DEFAULT 1 COMMENT '审核状态:1-待审核 2-已审核 3-已拒绝',
    `audit_by` BIGINT DEFAULT NULL COMMENT '审核人',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_outbound_no` (`tenant_id`, `outbound_no`, `deleted`),
    KEY `idx_tenant_target_id` (`tenant_id`, `target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出库记录表';

-- ----------------------------
-- 盘点记录表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_inventory`;
CREATE TABLE `his_drug_inventory` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `inventory_no` VARCHAR(30) NOT NULL COMMENT '盘点单号',
    `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
    `warehouse_name` VARCHAR(100) DEFAULT NULL COMMENT '仓库名称',
    `inventory_date` DATE NOT NULL COMMENT '盘点日期',
    `total_items` INT DEFAULT 0 COMMENT '盘点品种数',
    `profit_items` INT DEFAULT 0 COMMENT '盘盈品种数',
    `loss_items` INT DEFAULT 0 COMMENT '盘亏品种数',
    `profit_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '盘盈金额',
    `loss_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '盘亏金额',
    `inventory_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-进行中 2-已完成',
    `operator_id` BIGINT DEFAULT NULL COMMENT '盘点人',
    `operator_name` VARCHAR(50) DEFAULT NULL COMMENT '盘点人姓名',
    `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_inventory_no` (`tenant_id`, `inventory_no`, `deleted`),
    KEY `idx_tenant_warehouse_id` (`tenant_id`, `warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='盘点记录表';

-- ----------------------------
-- 供应商表
-- ----------------------------
DROP TABLE IF EXISTS `his_supplier`;
CREATE TABLE `his_supplier` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `supplier_code` VARCHAR(20) NOT NULL COMMENT '供应商编码',
    `supplier_name` VARCHAR(100) NOT NULL COMMENT '供应商名称',
    `short_name` VARCHAR(50) DEFAULT NULL COMMENT '简称',
    `contact_name` VARCHAR(50) DEFAULT NULL COMMENT '联系人',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `address` VARCHAR(200) DEFAULT NULL COMMENT '地址',
    `license_no` VARCHAR(50) DEFAULT NULL COMMENT '营业执照号',
    `gsp_no` VARCHAR(50) DEFAULT NULL COMMENT 'GSP证书号',
    `bank_name` VARCHAR(100) DEFAULT NULL COMMENT '开户银行',
    `bank_account` VARCHAR(50) DEFAULT NULL COMMENT '银行账号',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-正常 2-停用',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_supplier_code` (`tenant_id`, `supplier_code`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商表';

-- ----------------------------
-- 采购计划表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_purchase`;
CREATE TABLE `his_drug_purchase` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `purchase_no` VARCHAR(30) NOT NULL COMMENT '采购单号',
    `supplier_id` BIGINT DEFAULT NULL COMMENT '供应商ID',
    `supplier_name` VARCHAR(100) DEFAULT NULL COMMENT '供应商名称',
    `purchase_type` TINYINT NOT NULL COMMENT '采购类型:1-计划采购 2-紧急采购',
    `total_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总金额',
    `purchase_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-草稿 2-已提交 3-已审批 4-已采购 5-已完成',
    `apply_by` BIGINT DEFAULT NULL COMMENT '申请人',
    `apply_time` DATETIME DEFAULT NULL COMMENT '申请时间',
    `audit_by` BIGINT DEFAULT NULL COMMENT '审核人',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_purchase_no` (`tenant_id`, `purchase_no`, `deleted`),
    KEY `idx_tenant_supplier_id` (`tenant_id`, `supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购计划表';

-- ----------------------------
-- 药物相互作用表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_interaction`;
CREATE TABLE `his_drug_interaction` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `drug_id_a` BIGINT NOT NULL COMMENT '药品A ID',
    `drug_code_a` VARCHAR(20) DEFAULT NULL COMMENT '药品A编码',
    `drug_name_a` VARCHAR(100) DEFAULT NULL COMMENT '药品A名称',
    `drug_id_b` BIGINT NOT NULL COMMENT '药品B ID',
    `drug_code_b` VARCHAR(20) DEFAULT NULL COMMENT '药品B编码',
    `drug_name_b` VARCHAR(100) DEFAULT NULL COMMENT '药品B名称',
    `interaction_type` TINYINT NOT NULL COMMENT '相互作用类型:1-禁忌 2-慎重 3-注意',
    `severity` TINYINT NOT NULL COMMENT '严重程度:1-轻微 2-中等 3-严重',
    `mechanism` VARCHAR(500) DEFAULT NULL COMMENT '作用机制',
    `consequence` VARCHAR(500) DEFAULT NULL COMMENT '后果',
    `suggestion` VARCHAR(500) DEFAULT NULL COMMENT '处理建议',
    `reference` VARCHAR(200) DEFAULT NULL COMMENT '参考文献',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-有效 2-停用',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_drug_a` (`tenant_id`, `drug_id_a`),
    KEY `idx_tenant_drug_b` (`tenant_id`, `drug_id_b`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='药物相互作用表';

-- ----------------------------
-- 配伍禁忌表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_contraindication`;
CREATE TABLE `his_drug_contraindication` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(20) DEFAULT NULL COMMENT '药品编码',
    `drug_name` VARCHAR(100) DEFAULT NULL COMMENT '药品名称',
    `contraindication_type` TINYINT NOT NULL COMMENT '禁忌类型:1-疾病禁忌 2-过敏禁忌 3-妊娠禁忌 4-哺乳禁忌 5-年龄禁忌',
    `contraindication_content` VARCHAR(200) NOT NULL COMMENT '禁忌内容',
    `severity` TINYINT NOT NULL COMMENT '严重程度:1-禁用 2-慎用',
    `reason` VARCHAR(500) DEFAULT NULL COMMENT '原因',
    `alternative` VARCHAR(200) DEFAULT NULL COMMENT '替代药物建议',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-有效 2-停用',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_drug_id` (`tenant_id`, `drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='配伍禁忌表';

-- ----------------------------
-- 剂量规则表
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_dose_rule`;
CREATE TABLE `his_drug_dose_rule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(20) DEFAULT NULL COMMENT '药品编码',
    `drug_name` VARCHAR(100) DEFAULT NULL COMMENT '药品名称',
    `age_min` INT DEFAULT NULL COMMENT '最小年龄',
    `age_max` INT DEFAULT NULL COMMENT '最大年龄',
    `weight_min` DECIMAL(5,2) DEFAULT NULL COMMENT '最小体重(kg)',
    `weight_max` DECIMAL(5,2) DEFAULT NULL COMMENT '最大体重(kg)',
    `renal_function` TINYINT DEFAULT NULL COMMENT '肾功能:1-正常 2-轻度损害 3-中度损害 4-重度损害',
    `hepatic_function` TINYINT DEFAULT NULL COMMENT '肝功能:1-正常 2-轻度损害 3-中度损害 4-重度损害',
    `single_dose_min` DECIMAL(10,4) DEFAULT NULL COMMENT '单次最小剂量',
    `single_dose_max` DECIMAL(10,4) DEFAULT NULL COMMENT '单次最大剂量',
    `daily_dose_min` DECIMAL(10,4) DEFAULT NULL COMMENT '每日最小剂量',
    `daily_dose_max` DECIMAL(10,4) DEFAULT NULL COMMENT '每日最大剂量',
    `dosage_unit` VARCHAR(20) DEFAULT NULL COMMENT '剂量单位',
    `frequency_max` INT DEFAULT NULL COMMENT '每日最大频次',
    `route` VARCHAR(50) DEFAULT NULL COMMENT '给药途径',
    `adjustment_rule` VARCHAR(500) DEFAULT NULL COMMENT '剂量调整规则',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-有效 2-停用',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_drug_id` (`tenant_id`, `drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='剂量规则表';

-- ----------------------------
-- 特殊药品表
-- ----------------------------
DROP TABLE IF EXISTS `his_special_drug`;
CREATE TABLE `his_special_drug` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(20) DEFAULT NULL COMMENT '药品编码',
    `drug_name` VARCHAR(100) DEFAULT NULL COMMENT '药品名称',
    `special_type` TINYINT NOT NULL COMMENT '特殊类型:1-麻醉药品 2-精神药品I类 3-精神药品II类 4-毒性药品 5-放射性药品',
    `control_level` TINYINT NOT NULL COMMENT '管控级别:1-严格管控 2-一般管控',
    `max_quantity` DECIMAL(10,2) DEFAULT NULL COMMENT '单次最大处方量',
    `max_days` INT DEFAULT NULL COMMENT '最大处方天数',
    `require_approval` TINYINT DEFAULT 1 COMMENT '是否需要审批:0-否 1-是',
    `require_double_sign` TINYINT DEFAULT 1 COMMENT '是否需要双签名:0-否 1-是',
    `require_return` TINYINT DEFAULT 1 COMMENT '是否需要回收空安瓿:0-否 1-是',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-有效 2-停用',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_drug_id` (`tenant_id`, `drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='特殊药品表';

-- ============================================
-- 第五部分: 初始化字典数据
-- ============================================

-- 初始化科室类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS科室类型', 'his_dept_type', 0, 'HIS科室类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '临床科室', '1', 'his_dept_type', 0, 'primary', '', '临床科室', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '医技科室', '2', 'his_dept_type', 0, 'success', '', '医技科室', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '行政科室', '3', 'his_dept_type', 0, 'warning', '', '行政科室', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '后勤科室', '4', 'his_dept_type', 0, 'info', '', '后勤科室', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化挂号类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS挂号类型', 'his_register_type', 0, 'HIS挂号类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '普通号', '1', 'his_register_type', 0, 'default', '', '普通号', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '专家号', '2', 'his_register_type', 0, 'primary', '', '专家号', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '急诊号', '3', 'his_register_type', 0, 'danger', '', '急诊号', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化处方类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS处方类型', 'his_prescription_type', 0, 'HIS处方类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '普通处方', '1', 'his_prescription_type', 0, 'default', '', '普通处方', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '急诊处方', '2', 'his_prescription_type', 0, 'warning', '', '急诊处方', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '儿科处方', '3', 'his_prescription_type', 0, 'success', '', '儿科处方', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '麻醉处方', '4', 'his_prescription_type', 0, 'danger', '', '麻醉处方', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '精神处方', '5', 'his_prescription_type', 0, 'danger', '', '精神处方', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '中药处方', '6', 'his_prescription_type', 0, 'info', '', '中药处方', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化用药频次字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS用药频次', 'his_frequency', 0, 'HIS用药频次字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '每日一次(QD)', 'QD', 'his_frequency', 0, 'default', '', '每日一次', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '每日两次(BID)', 'BID', 'his_frequency', 0, 'default', '', '每日两次', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '每日三次(TID)', 'TID', 'his_frequency', 0, 'default', '', '每日三次', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '每日四次(QID)', 'QID', 'his_frequency', 0, 'default', '', '每日四次', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '每晚一次(QN)', 'QN', 'his_frequency', 0, 'default', '', '每晚一次', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '每12小时(Q12H)', 'Q12H', 'his_frequency', 0, 'default', '', '每12小时', 'admin', NOW(), 'admin', NOW(), b'0'),
(7, '每8小时(Q8H)', 'Q8H', 'his_frequency', 0, 'default', '', '每8小时', 'admin', NOW(), 'admin', NOW(), b'0'),
(8, '每6小时(Q6H)', 'Q6H', 'his_frequency', 0, 'default', '', '每6小时', 'admin', NOW(), 'admin', NOW(), b'0'),
(9, '需要时(PRN)', 'PRN', 'his_frequency', 0, 'warning', '', '需要时', 'admin', NOW(), 'admin', NOW(), b'0'),
(10, '立即(ST)', 'ST', 'his_frequency', 0, 'danger', '', '立即', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化给药途径字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS给药途径', 'his_route', 0, 'HIS给药途径字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '口服(PO)', 'PO', 'his_route', 0, 'default', '', '口服', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '静脉注射(IV)', 'IV', 'his_route', 0, 'primary', '', '静脉注射', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '静脉滴注(IVGTT)', 'IVGTT', 'his_route', 0, 'primary', '', '静脉滴注', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '肌肉注射(IM)', 'IM', 'his_route', 0, 'success', '', '肌肉注射', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '皮下注射(IH)', 'IH', 'his_route', 0, 'success', '', '皮下注射', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '皮内注射(ID)', 'ID', 'his_route', 0, 'warning', '', '皮内注射', 'admin', NOW(), 'admin', NOW(), b'0'),
(7, '外用(TOP)', 'TOP', 'his_route', 0, 'info', '', '外用', 'admin', NOW(), 'admin', NOW(), b'0'),
(8, '吸入(INH)', 'INH', 'his_route', 0, 'info', '', '吸入', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化医嘱类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS医嘱类型', 'his_order_type', 0, 'HIS医嘱类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '药品医嘱', '1', 'his_order_type', 0, 'primary', '', '药品医嘱', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '检验医嘱', '2', 'his_order_type', 0, 'success', '', '检验医嘱', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '检查医嘱', '3', 'his_order_type', 0, 'warning', '', '检查医嘱', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '护理医嘱', '4', 'his_order_type', 0, 'info', '', '护理医嘱', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '手术医嘱', '5', 'his_order_type', 0, 'danger', '', '手术医嘱', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '饮食医嘱', '6', 'his_order_type', 0, 'default', '', '饮食医嘱', 'admin', NOW(), 'admin', NOW(), b'0'),
(7, '其他医嘱', '7', 'his_order_type', 0, 'default', '', '其他医嘱', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化医保类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS医保类型', 'his_insurance_type', 0, 'HIS医保类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '城镇职工医保', '1', 'his_insurance_type', 0, 'primary', '', '城镇职工医保', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '城镇居民医保', '2', 'his_insurance_type', 0, 'success', '', '城镇居民医保', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '新农合', '3', 'his_insurance_type', 0, 'warning', '', '新农合', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '自费', '4', 'his_insurance_type', 0, 'default', '', '自费', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化药品类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS药品类型', 'his_drug_type', 0, 'HIS药品类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '西药', '1', 'his_drug_type', 0, 'primary', '', '西药', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '中成药', '2', 'his_drug_type', 0, 'success', '', '中成药', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '中草药', '3', 'his_drug_type', 0, 'warning', '', '中草药', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '生物制品', '4', 'his_drug_type', 0, 'info', '', '生物制品', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 初始化完成
-- ============================================
