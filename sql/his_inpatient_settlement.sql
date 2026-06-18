-- ============================================
-- 文件名：his_inpatient_settlement.sql
-- 描述：住院费用明细表和住院结算表
-- 作者：AI Assistant
-- 日期：2026-06-18
-- ============================================

-- ============================================
-- 1. 住院费用明细表
-- ============================================
CREATE TABLE `his_inpatient_fee` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `fee_no` VARCHAR(64) NOT NULL COMMENT '费用单号',
    `admission_id` BIGINT NOT NULL COMMENT '入院记录ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(100) NOT NULL COMMENT '患者姓名',
    `inpatient_no` VARCHAR(64) DEFAULT NULL COMMENT '住院号',

    -- 医嘱关联
    `order_id` BIGINT DEFAULT NULL COMMENT '医嘱ID',
    `order_no` VARCHAR(64) DEFAULT NULL COMMENT '医嘱编号',

    -- 费用项目信息
    `item_code` VARCHAR(64) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(255) NOT NULL COMMENT '项目名称',
    `item_type` TINYINT NOT NULL DEFAULT 1 COMMENT '项目类型:1-药品,2-检查,3-检验,4-诊疗,5-护理,6-手术,7-床位,8-其他',
    `item_category` VARCHAR(64) DEFAULT NULL COMMENT '项目分类',

    -- 费用明细
    `spec` VARCHAR(255) DEFAULT NULL COMMENT '规格',
    `unit` VARCHAR(64) DEFAULT NULL COMMENT '单位',
    `quantity` DECIMAL(10,4) NOT NULL DEFAULT 1 COMMENT '数量',
    `unit_price` DECIMAL(18,4) NOT NULL COMMENT '单价',
    `total_amount` DECIMAL(18,2) NOT NULL COMMENT '总金额',
    `discount_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '优惠金额',
    `pay_amount` DECIMAL(18,2) NOT NULL COMMENT '应付金额',

    -- 医保信息
    `insurance_code` VARCHAR(64) DEFAULT NULL COMMENT '医保编码',
    `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型:1-甲类,2-乙类,3-自费',
    `insurance_ratio` DECIMAL(5,2) DEFAULT NULL COMMENT '医保报销比例',
    `insurance_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '医保支付金额',
    `self_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '自付金额',

    -- 执行信息
    `dept_id` BIGINT DEFAULT NULL COMMENT '执行科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '执行科室名称',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '开单医生ID',
    `doctor_name` VARCHAR(100) DEFAULT NULL COMMENT '开单医生姓名',
    `execute_time` DATETIME DEFAULT NULL COMMENT '执行时间',

    -- 费用状态
    `fee_status` TINYINT NOT NULL DEFAULT 0 COMMENT '费用状态:0-未结算,1-已结算,2-已退费',
    `settlement_id` BIGINT DEFAULT NULL COMMENT '结算单ID',

    -- 费用日期
    `fee_date` DATE NOT NULL COMMENT '费用日期',

    -- 审计字段
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_fee_no` (`tenant_id`, `fee_no`, `deleted`),
    KEY `idx_tenant_admission` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_status` (`tenant_id`, `fee_status`),
    KEY `idx_tenant_date` (`tenant_id`, `fee_date`),
    KEY `idx_tenant_settlement` (`tenant_id`, `settlement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住院费用明细表';

-- ============================================
-- 2. 住院结算主表
-- ============================================
CREATE TABLE `his_inpatient_settlement` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `settlement_no` VARCHAR(64) NOT NULL COMMENT '结算单号',
    `admission_id` BIGINT NOT NULL COMMENT '入院记录ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(100) NOT NULL COMMENT '患者姓名',
    `inpatient_no` VARCHAR(64) DEFAULT NULL COMMENT '住院号',
    `id_card_no` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',

    -- 住院时间
    `admission_date` DATETIME NOT NULL COMMENT '入院日期',
    `discharge_date` DATETIME DEFAULT NULL COMMENT '出院日期',
    `hospital_days` INT DEFAULT 0 COMMENT '住院天数',

    -- 科室信息
    `dept_id` BIGINT NOT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `ward_id` BIGINT DEFAULT NULL COMMENT '病区ID',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',
    `bed_id` BIGINT DEFAULT NULL COMMENT '床位ID',
    `bed_no` VARCHAR(64) DEFAULT NULL COMMENT '床号',

    -- 医生信息
    `attending_doctor_id` BIGINT DEFAULT NULL COMMENT '主治医师ID',
    `attending_doctor_name` VARCHAR(100) DEFAULT NULL COMMENT '主治医师姓名',

    -- 医保信息
    `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型:1-城镇职工,2-城镇居民,3-新农合,4-自费',
    `insurance_no` VARCHAR(64) DEFAULT NULL COMMENT '医保号',

    -- 费用汇总
    `total_amount` DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT '费用总额',
    `western_medicine_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '西药费',
    `chinese_medicine_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '中药费',
    `examination_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '检查费',
    `laboratory_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '检验费',
    `treatment_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '诊疗费',
    `nursing_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '护理费',
    `surgery_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '手术费',
    `bed_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '床位费',
    `material_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '材料费',
    `other_fee` DECIMAL(18,2) DEFAULT 0 COMMENT '其他费用',

    -- 结算金额
    `discount_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '优惠金额',
    `payable_amount` DECIMAL(18,2) NOT NULL COMMENT '应付金额',
    `prepayment_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '预交金抵扣',
    `insurance_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '医保支付',
    `self_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '自付金额',
    `refund_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '退费金额',
    `actual_amount` DECIMAL(18,2) NOT NULL COMMENT '实收/应退金额',

    -- 支付信息
    `payment_type` TINYINT DEFAULT NULL COMMENT '支付方式:1-现金,2-银行卡,3-医保卡,4-微信,5-支付宝,6-混合支付',
    `payment_no` VARCHAR(128) DEFAULT NULL COMMENT '支付流水号',
    `payment_time` DATETIME DEFAULT NULL COMMENT '支付时间',

    -- 诊断信息
    `admission_diagnosis` VARCHAR(500) DEFAULT NULL COMMENT '入院诊断',
    `discharge_diagnosis` VARCHAR(500) DEFAULT NULL COMMENT '出院诊断',
    `diagnosis_code` VARCHAR(64) DEFAULT NULL COMMENT 'ICD-10编码',

    -- 结算状态
    `settlement_status` TINYINT NOT NULL DEFAULT 0 COMMENT '结算状态:0-未结算,1-已结算,2-已退费,3-已作废',
    `settlement_type` TINYINT NOT NULL DEFAULT 1 COMMENT '结算类型:1-出院结算,2-中途结算,3-挂账结算',
    `invoice_no` VARCHAR(64) DEFAULT NULL COMMENT '发票号码',

    -- 操作信息
    `operator_id` BIGINT DEFAULT NULL COMMENT '操作员ID',
    `operator_name` VARCHAR(100) DEFAULT NULL COMMENT '操作员姓名',
    `settlement_time` DATETIME DEFAULT NULL COMMENT '结算时间',

    -- 审计字段
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_settlement_no` (`tenant_id`, `settlement_no`, `deleted`),
    KEY `idx_tenant_admission` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_status` (`tenant_id`, `settlement_status`),
    KEY `idx_tenant_date` (`tenant_id`, `settlement_time`),
    KEY `idx_tenant_invoice` (`tenant_id`, `invoice_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住院结算主表';

-- ============================================
-- 3. 住院结算明细表
-- ============================================
CREATE TABLE `his_inpatient_settlement_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `settlement_id` BIGINT NOT NULL COMMENT '结算单ID',
    `settlement_no` VARCHAR(64) NOT NULL COMMENT '结算单号',
    `fee_id` BIGINT NOT NULL COMMENT '费用明细ID',
    `fee_no` VARCHAR(64) NOT NULL COMMENT '费用单号',
    `admission_id` BIGINT NOT NULL COMMENT '入院记录ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',

    -- 费用项目信息
    `item_code` VARCHAR(64) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(255) NOT NULL COMMENT '项目名称',
    `item_type` TINYINT NOT NULL COMMENT '项目类型',
    `spec` VARCHAR(255) DEFAULT NULL COMMENT '规格',
    `unit` VARCHAR(64) DEFAULT NULL COMMENT '单位',
    `quantity` DECIMAL(10,4) NOT NULL COMMENT '数量',
    `unit_price` DECIMAL(18,4) NOT NULL COMMENT '单价',
    `total_amount` DECIMAL(18,2) NOT NULL COMMENT '总金额',
    `discount_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '优惠金额',
    `pay_amount` DECIMAL(18,2) NOT NULL COMMENT '应付金额',

    -- 医保信息
    `insurance_code` VARCHAR(64) DEFAULT NULL COMMENT '医保编码',
    `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型',
    `insurance_ratio` DECIMAL(5,2) DEFAULT NULL COMMENT '医保报销比例',
    `insurance_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '医保支付金额',
    `self_amount` DECIMAL(18,2) DEFAULT 0 COMMENT '自付金额',

    -- 费用日期
    `fee_date` DATE NOT NULL COMMENT '费用日期',

    -- 审计字段
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_settlement` (`tenant_id`, `settlement_id`),
    KEY `idx_tenant_fee` (`tenant_id`, `fee_id`),
    KEY `idx_tenant_admission` (`tenant_id`, `admission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住院结算明细表';

-- ============================================
-- 4. 住院费用退费表
-- ============================================
CREATE TABLE `his_inpatient_fee_refund` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `refund_no` VARCHAR(64) NOT NULL COMMENT '退费单号',
    `settlement_id` BIGINT DEFAULT NULL COMMENT '原结算单ID',
    `admission_id` BIGINT NOT NULL COMMENT '入院记录ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(100) NOT NULL COMMENT '患者姓名',

    -- 退费信息
    `refund_amount` DECIMAL(18,2) NOT NULL COMMENT '退费金额',
    `refund_reason` VARCHAR(500) DEFAULT NULL COMMENT '退费原因',
    `refund_type` TINYINT NOT NULL DEFAULT 1 COMMENT '退费类型:1-费用退费,2-结算退费',

    -- 审核信息
    `apply_user_id` BIGINT DEFAULT NULL COMMENT '申请人ID',
    `apply_user_name` VARCHAR(100) DEFAULT NULL COMMENT '申请人姓名',
    `apply_time` DATETIME DEFAULT NULL COMMENT '申请时间',
    `audit_user_id` BIGINT DEFAULT NULL COMMENT '审核人ID',
    `audit_user_name` VARCHAR(100) DEFAULT NULL COMMENT '审核人姓名',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `audit_status` TINYINT NOT NULL DEFAULT 0 COMMENT '审核状态:0-待审核,1-已通过,2-已拒绝',
    `audit_remark` VARCHAR(500) DEFAULT NULL COMMENT '审核备注',

    -- 退费状态
    `refund_status` TINYINT NOT NULL DEFAULT 0 COMMENT '退费状态:0-待审核,1-已退费,2-已取消',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退费时间',

    -- 操作信息
    `operator_id` BIGINT DEFAULT NULL COMMENT '操作员ID',
    `operator_name` VARCHAR(100) DEFAULT NULL COMMENT '操作员姓名',

    -- 审计字段
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_refund_no` (`tenant_id`, `refund_no`, `deleted`),
    KEY `idx_tenant_admission` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_status` (`tenant_id`, `refund_status`),
    KEY `idx_tenant_settlement` (`tenant_id`, `settlement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住院费用退费表';

-- ============================================
-- 5. 住院费用退费明细表
-- ============================================
CREATE TABLE `his_inpatient_fee_refund_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `refund_id` BIGINT NOT NULL COMMENT '退费单ID',
    `refund_no` VARCHAR(64) NOT NULL COMMENT '退费单号',
    `fee_id` BIGINT NOT NULL COMMENT '费用明细ID',
    `fee_no` VARCHAR(64) NOT NULL COMMENT '费用单号',

    -- 费用项目信息
    `item_code` VARCHAR(64) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(255) NOT NULL COMMENT '项目名称',
    `item_type` TINYINT NOT NULL COMMENT '项目类型',
    `unit` VARCHAR(64) DEFAULT NULL COMMENT '单位',
    `quantity` DECIMAL(10,4) NOT NULL COMMENT '退费数量',
    `unit_price` DECIMAL(18,4) NOT NULL COMMENT '单价',
    `refund_amount` DECIMAL(18,2) NOT NULL COMMENT '退费金额',

    -- 审计字段
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_refund` (`tenant_id`, `refund_id`),
    KEY `idx_tenant_fee` (`tenant_id`, `fee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住院费用退费明细表';
