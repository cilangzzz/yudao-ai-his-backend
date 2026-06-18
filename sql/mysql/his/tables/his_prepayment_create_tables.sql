-- ============================================
-- 文件名：his_prepayment_create.sql
-- 描述：住院预交金管理表
-- 作者：yudao
-- 日期：2026-06-18
-- ============================================

-- ============================================
-- 预交金记录表
-- ============================================
CREATE TABLE `his_prepayment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',

    -- 业务字段
    `prepayment_no` VARCHAR(64) NOT NULL COMMENT '预交金编号',
    `admission_id` BIGINT NOT NULL COMMENT '入院记录ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(100) NOT NULL COMMENT '患者姓名',
    `inpatient_no` VARCHAR(64) DEFAULT NULL COMMENT '住院号',

    `amount` DECIMAL(18,2) NOT NULL COMMENT '预交金额',
    `balance_amount` DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT '剩余金额',
    `payment_type` TINYINT NOT NULL COMMENT '支付方式:1-现金,2-银行卡,3-医保卡,4-微信,5-支付宝,6-混合支付',
    `payment_channel` VARCHAR(64) DEFAULT NULL COMMENT '支付渠道编码',
    `payment_no` VARCHAR(128) DEFAULT NULL COMMENT '支付流水号',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',

    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-已缴纳,2-已使用,3-已退还,4-已结算',
    `used_amount` DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT '已使用金额',
    `refund_amount` DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT '已退还金额',

    `operator_id` BIGINT DEFAULT NULL COMMENT '操作员ID',
    `operator_name` VARCHAR(64) DEFAULT NULL COMMENT '操作员姓名',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `ward_id` BIGINT DEFAULT NULL COMMENT '病区ID',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',

    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',

    -- 通用字段
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_prepayment_no` (`tenant_id`, `prepayment_no`, `deleted`),
    KEY `idx_tenant_admission` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_status` (`tenant_id`, `status`),
    KEY `idx_tenant_pay_time` (`tenant_id`, `pay_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住院预交金记录表';

-- ============================================
-- 预交金使用记录表
-- ============================================
CREATE TABLE `his_prepayment_usage` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',

    -- 业务字段
    `usage_no` VARCHAR(64) NOT NULL COMMENT '使用记录编号',
    `prepayment_id` BIGINT NOT NULL COMMENT '预交金记录ID',
    `prepayment_no` VARCHAR(64) DEFAULT NULL COMMENT '预交金编号',
    `admission_id` BIGINT NOT NULL COMMENT '入院记录ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',

    `use_amount` DECIMAL(18,2) NOT NULL COMMENT '使用金额',
    `use_type` TINYINT NOT NULL COMMENT '使用类型:1-结算费用,2-退费转出,3-其他扣减',
    `fee_item_id` BIGINT DEFAULT NULL COMMENT '费用明细ID',
    `fee_item_name` VARCHAR(200) DEFAULT NULL COMMENT '费用项目名称',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',

    `operator_id` BIGINT DEFAULT NULL COMMENT '操作员ID',
    `operator_name` VARCHAR(64) DEFAULT NULL COMMENT '操作员姓名',
    `use_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '使用时间',

    -- 通用字段
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_usage_no` (`tenant_id`, `usage_no`, `deleted`),
    KEY `idx_tenant_prepayment` (`tenant_id`, `prepayment_id`),
    KEY `idx_tenant_admission` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预交金使用记录表';

-- ============================================
-- 预交金退还记录表
-- ============================================
CREATE TABLE `his_prepayment_refund` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',

    -- 业务字段
    `refund_no` VARCHAR(64) NOT NULL COMMENT '退还编号',
    `prepayment_id` BIGINT NOT NULL COMMENT '预交金记录ID',
    `prepayment_no` VARCHAR(64) DEFAULT NULL COMMENT '预交金编号',
    `admission_id` BIGINT NOT NULL COMMENT '入院记录ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(100) DEFAULT NULL COMMENT '患者姓名',

    `refund_amount` DECIMAL(18,2) NOT NULL COMMENT '退还金额',
    `refund_reason` VARCHAR(500) DEFAULT NULL COMMENT '退还原因',
    `refund_type` TINYINT NOT NULL COMMENT '退还方式:1-现金,2-银行卡,3-原路退回',
    `refund_channel` VARCHAR(64) DEFAULT NULL COMMENT '退还渠道',
    `refund_no_external` VARCHAR(128) DEFAULT NULL COMMENT '外部退还流水号',

    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-申请中,2-已审批,3-已退还,4-已拒绝',
    `apply_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    `approve_time` DATETIME DEFAULT NULL COMMENT '审批时间',
    `approve_user_id` BIGINT DEFAULT NULL COMMENT '审批人ID',
    `approve_user_name` VARCHAR(64) DEFAULT NULL COMMENT '审批人姓名',
    `approve_remark` VARCHAR(500) DEFAULT NULL COMMENT '审批备注',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退还时间',

    `operator_id` BIGINT DEFAULT NULL COMMENT '申请人ID',
    `operator_name` VARCHAR(64) DEFAULT NULL COMMENT '申请人姓名',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',

    -- 通用字段
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_refund_no` (`tenant_id`, `refund_no`, `deleted`),
    KEY `idx_tenant_prepayment` (`tenant_id`, `prepayment_id`),
    KEY `idx_tenant_admission` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_patient` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_status` (`tenant_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预交金退还记录表';

-- ============================================
-- 初始化数据：预交金支付方式字典
-- ============================================
