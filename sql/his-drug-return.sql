-- ============================================
-- HIS 退药管理模块 - 数据库初始化脚本
-- 版本: V1.0
-- 日期: 2026-06-18
-- 说明: 门诊/住院退药管理核心表
-- ============================================

-- ----------------------------
-- 退药申请主表
-- ----------------------------
DROP TABLE IF EXISTS `op_drug_return`;
CREATE TABLE `op_drug_return` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `return_no` VARCHAR(30) NOT NULL COMMENT '退药单号',
    `return_type` TINYINT NOT NULL COMMENT '退药类型:1-门诊退药 2-住院退药',
    `prescription_id` BIGINT DEFAULT NULL COMMENT '原处方ID',
    `prescription_no` VARCHAR(30) DEFAULT NULL COMMENT '原处方编号',
    `dispense_id` BIGINT DEFAULT NULL COMMENT '原发药ID',
    `dispense_no` VARCHAR(30) DEFAULT NULL COMMENT '原发药单号',
    `register_id` BIGINT DEFAULT NULL COMMENT '挂号ID(门诊)',
    `admission_id` BIGINT DEFAULT NULL COMMENT '住院ID(住院)',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `patient_phone` VARCHAR(20) DEFAULT NULL COMMENT '患者电话',
    `id_card_no` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    `dept_id` BIGINT NOT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '开单医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '开单医生姓名',
    `pharmacy_id` BIGINT NOT NULL COMMENT '药房ID',
    `pharmacy_name` VARCHAR(100) DEFAULT NULL COMMENT '药房名称',
    `total_quantity` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '退药总数量',
    `total_amount` DECIMAL(12,2) NOT NULL DEFAULT 0 COMMENT '退药总金额',
    `refund_amount` DECIMAL(12,2) DEFAULT NULL COMMENT '退款金额',
    `return_reason` VARCHAR(500) NOT NULL COMMENT '退药原因',
    `return_reason_type` TINYINT NOT NULL COMMENT '退药原因类型:1-药品不良反应 2-医嘱变更 3-患者拒服 4-药品质量问题 5-重复开药 6-其他',
    `return_status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-待审核 2-审核通过 3-审核拒绝 4-已入库 5-已退款 6-已取消',
    `apply_time` DATETIME NOT NULL COMMENT '申请时间',
    `apply_by` BIGINT NOT NULL COMMENT '申请人ID',
    `apply_by_name` VARCHAR(50) DEFAULT NULL COMMENT '申请人姓名',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `audit_by` BIGINT DEFAULT NULL COMMENT '审核人ID',
    `audit_by_name` VARCHAR(50) DEFAULT NULL COMMENT '审核人姓名',
    `audit_remark` VARCHAR(500) DEFAULT NULL COMMENT '审核意见',
    `inbound_time` DATETIME DEFAULT NULL COMMENT '入库时间',
    `inbound_by` BIGINT DEFAULT NULL COMMENT '入库人ID',
    `inbound_by_name` VARCHAR(50) DEFAULT NULL COMMENT '入库人姓名',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退款时间',
    `refund_by` BIGINT DEFAULT NULL COMMENT '退款人ID',
    `refund_by_name` VARCHAR(50) DEFAULT NULL COMMENT '退款人姓名',
    `cancel_time` DATETIME DEFAULT NULL COMMENT '取消时间',
    `cancel_by` BIGINT DEFAULT NULL COMMENT '取消人ID',
    `cancel_reason` VARCHAR(500) DEFAULT NULL COMMENT '取消原因',
    `attachment_url` VARCHAR(500) DEFAULT NULL COMMENT '附件URL(药品照片等)',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_return_no` (`tenant_id`, `return_no`, `deleted`),
    KEY `idx_tenant_prescription_id` (`tenant_id`, `prescription_id`),
    KEY `idx_tenant_dispense_id` (`tenant_id`, `dispense_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_register_id` (`tenant_id`, `register_id`),
    KEY `idx_tenant_admission_id` (`tenant_id`, `admission_id`),
    KEY `idx_tenant_status` (`tenant_id`, `return_status`),
    KEY `idx_tenant_apply_time` (`tenant_id`, `apply_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退药申请主表';

-- ----------------------------
-- 退药明细表
-- ----------------------------
DROP TABLE IF EXISTS `op_drug_return_item`;
CREATE TABLE `op_drug_return_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `return_id` BIGINT NOT NULL COMMENT '退药ID',
    `return_no` VARCHAR(30) DEFAULT NULL COMMENT '退药单号',
    `prescription_item_id` BIGINT DEFAULT NULL COMMENT '原处方明细ID',
    `dispense_item_id` BIGINT DEFAULT NULL COMMENT '原发药明细ID',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(20) NOT NULL COMMENT '药品编码',
    `drug_name` VARCHAR(100) NOT NULL COMMENT '药品名称',
    `drug_spec` VARCHAR(100) DEFAULT NULL COMMENT '药品规格',
    `manufacturer` VARCHAR(100) DEFAULT NULL COMMENT '生产厂家',
    `batch_no` VARCHAR(50) DEFAULT NULL COMMENT '批号',
    `expiry_date` DATE DEFAULT NULL COMMENT '有效期',
    `original_quantity` DECIMAL(10,2) NOT NULL COMMENT '原发药数量',
    `return_quantity` DECIMAL(10,2) NOT NULL COMMENT '退药数量',
    `returned_quantity` DECIMAL(10,2) DEFAULT 0 COMMENT '已退数量',
    `unit` VARCHAR(20) NOT NULL COMMENT '单位',
    `unit_price` DECIMAL(10,4) NOT NULL COMMENT '单价',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '退药金额',
    `drug_condition` TINYINT DEFAULT 1 COMMENT '药品状态:1-完好 2-包装破损 3-过期 4-其他',
    `drug_condition_remark` VARCHAR(200) DEFAULT NULL COMMENT '药品状态说明',
    `can_reuse` TINYINT DEFAULT 1 COMMENT '是否可再利用:0-否 1-是',
    `storage_location` VARCHAR(50) DEFAULT NULL COMMENT '存放位置',
    `item_status` TINYINT NOT NULL DEFAULT 1 COMMENT '明细状态:1-待审核 2-审核通过 3-审核拒绝 4-已入库',
    `return_reason` VARCHAR(500) DEFAULT NULL COMMENT '退药原因',
    `sort_order` INT DEFAULT 0 COMMENT '排序号',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_return_id` (`tenant_id`, `return_id`),
    KEY `idx_tenant_drug_id` (`tenant_id`, `drug_id`),
    KEY `idx_tenant_batch_no` (`tenant_id`, `batch_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退药明细表';

-- ----------------------------
-- 退药审核记录表
-- ----------------------------
DROP TABLE IF EXISTS `op_drug_return_audit`;
CREATE TABLE `op_drug_return_audit` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `return_id` BIGINT NOT NULL COMMENT '退药ID',
    `return_no` VARCHAR(30) DEFAULT NULL COMMENT '退药单号',
    `audit_type` TINYINT NOT NULL COMMENT '审核类型:1-药师审核 2-科室审核 3-财务审核',
    `audit_result` TINYINT NOT NULL COMMENT '审核结果:1-通过 2-拒绝',
    `audit_remark` VARCHAR(500) DEFAULT NULL COMMENT '审核意见',
    `audit_time` DATETIME NOT NULL COMMENT '审核时间',
    `audit_by` BIGINT NOT NULL COMMENT '审核人ID',
    `audit_by_name` VARCHAR(50) DEFAULT NULL COMMENT '审核人姓名',
    `attachment_url` VARCHAR(500) DEFAULT NULL COMMENT '附件URL',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_return_id` (`tenant_id`, `return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退药审核记录表';

-- ----------------------------
-- 退药入库记录表
-- ----------------------------
DROP TABLE IF EXISTS `op_drug_return_inbound`;
CREATE TABLE `op_drug_return_inbound` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `inbound_no` VARCHAR(30) NOT NULL COMMENT '入库单号',
    `return_id` BIGINT NOT NULL COMMENT '退药ID',
    `return_no` VARCHAR(30) DEFAULT NULL COMMENT '退药单号',
    `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
    `warehouse_name` VARCHAR(100) DEFAULT NULL COMMENT '仓库名称',
    `total_quantity` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '入库总数量',
    `total_amount` DECIMAL(12,2) NOT NULL DEFAULT 0 COMMENT '入库总金额',
    `inbound_time` DATETIME NOT NULL COMMENT '入库时间',
    `operator_id` BIGINT NOT NULL COMMENT '操作员ID',
    `operator_name` VARCHAR(50) DEFAULT NULL COMMENT '操作员姓名',
    `audit_status` TINYINT NOT NULL DEFAULT 1 COMMENT '审核状态:1-待审核 2-已审核 3-已拒绝',
    `audit_by` BIGINT DEFAULT NULL COMMENT '审核人ID',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_inbound_no` (`tenant_id`, `inbound_no`, `deleted`),
    KEY `idx_tenant_return_id` (`tenant_id`, `return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退药入库记录表';

-- ----------------------------
-- 退药入库明细表
-- ----------------------------
DROP TABLE IF EXISTS `op_drug_return_inbound_item`;
CREATE TABLE `op_drug_return_inbound_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `inbound_id` BIGINT NOT NULL COMMENT '入库ID',
    `inbound_no` VARCHAR(30) DEFAULT NULL COMMENT '入库单号',
    `return_item_id` BIGINT NOT NULL COMMENT '退药明细ID',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(20) NOT NULL COMMENT '药品编码',
    `drug_name` VARCHAR(100) NOT NULL COMMENT '药品名称',
    `drug_spec` VARCHAR(100) DEFAULT NULL COMMENT '药品规格',
    `batch_no` VARCHAR(50) DEFAULT NULL COMMENT '批号',
    `expiry_date` DATE DEFAULT NULL COMMENT '有效期',
    `quantity` DECIMAL(10,2) NOT NULL COMMENT '入库数量',
    `unit` VARCHAR(20) NOT NULL COMMENT '单位',
    `unit_price` DECIMAL(10,4) NOT NULL COMMENT '单价',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '金额',
    `storage_location` VARCHAR(50) DEFAULT NULL COMMENT '货架位置',
    `stock_id` BIGINT DEFAULT NULL COMMENT '入库后库存ID',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_inbound_id` (`tenant_id`, `inbound_id`),
    KEY `idx_tenant_drug_id` (`tenant_id`, `drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退药入库明细表';

-- ============================================
-- 退药相关字典数据
-- ============================================

-- 退药原因类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS退药原因类型', 'his_drug_return_reason', 0, 'HIS退药原因类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '药品不良反应', '1', 'his_drug_return_reason', 0, 'danger', '', '药品不良反应', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '医嘱变更', '2', 'his_drug_return_reason', 0, 'primary', '', '医嘱变更', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '患者拒服', '3', 'his_drug_return_reason', 0, 'warning', '', '患者拒服', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '药品质量问题', '4', 'his_drug_return_reason', 0, 'danger', '', '药品质量问题', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '重复开药', '5', 'his_drug_return_reason', 0, 'warning', '', '重复开药', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '其他', '6', 'his_drug_return_reason', 0, 'default', '', '其他', 'admin', NOW(), 'admin', NOW(), b'0');

-- 退药状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS退药状态', 'his_drug_return_status', 0, 'HIS退药状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '待审核', '1', 'his_drug_return_status', 0, 'warning', '', '待审核', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '审核通过', '2', 'his_drug_return_status', 0, 'success', '', '审核通过', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '审核拒绝', '3', 'his_drug_return_status', 0, 'danger', '', '审核拒绝', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '已入库', '4', 'his_drug_return_status', 0, 'primary', '', '已入库', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '已退款', '5', 'his_drug_return_status', 0, 'info', '', '已退款', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '已取消', '6', 'his_drug_return_status', 0, 'default', '', '已取消', 'admin', NOW(), 'admin', NOW(), b'0');

-- 药品状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS退药药品状态', 'his_drug_condition', 0, 'HIS退药药品状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '完好', '1', 'his_drug_condition', 0, 'success', '', '完好', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '包装破损', '2', 'his_drug_condition', 0, 'warning', '', '包装破损', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '过期', '3', 'his_drug_condition', 0, 'danger', '', '过期', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '其他', '4', 'his_drug_condition', 0, 'default', '', '其他', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 初始化完成
-- ============================================
