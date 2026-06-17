-- ============================================
-- 退费明细表
-- 支持部分退费功能
-- ============================================
DROP TABLE IF EXISTS `op_refund_item`;
CREATE TABLE `op_refund_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `refund_id` BIGINT NOT NULL COMMENT '退费记录ID',
    `refund_no` VARCHAR(30) NOT NULL COMMENT '退费单号',
    `payment_id` BIGINT NOT NULL COMMENT '原支付ID',
    `payment_item_id` BIGINT NOT NULL COMMENT '原支付明细ID',
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
    `unit_price` DECIMAL(12,4) NOT NULL COMMENT '单价',
    `original_quantity` DECIMAL(10,2) NOT NULL COMMENT '原数量',
    `refund_quantity` DECIMAL(10,2) NOT NULL COMMENT '退费数量',
    `original_amount` DECIMAL(12,2) NOT NULL COMMENT '原金额',
    `refund_amount` DECIMAL(12,2) NOT NULL COMMENT '退费金额',
    `insurance_refund_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '医保退费金额',
    `self_refund_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '自费退费金额',
    `pay_type` TINYINT NOT NULL COMMENT '支付方式:1-现金 2-微信 3-支付宝 4-医保 5-银行卡',
    `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型',
    `insurance_no` VARCHAR(30) DEFAULT NULL COMMENT '医保卡号',
    `insurance_code` VARCHAR(20) DEFAULT NULL COMMENT '医保编码',
    `insurance_category` TINYINT DEFAULT NULL COMMENT '医保类别:1-甲类 2-乙类 3-丙类',
    `refund_status` TINYINT NOT NULL DEFAULT 1 COMMENT '退费状态:1-待审核 2-已通过 3-已拒绝 4-已完成',
    `refund_reason` VARCHAR(200) DEFAULT NULL COMMENT '退费原因',
    `execution_dept_id` BIGINT DEFAULT NULL COMMENT '执行科室ID',
    `execution_dept_name` VARCHAR(100) DEFAULT NULL COMMENT '执行科室名称',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '开单医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '开单医生姓名',
    `source_type` TINYINT DEFAULT NULL COMMENT '来源类型:1-挂号 2-处方 3-检查 4-检验 5-治疗 6-手术 7-其他',
    `source_id` BIGINT DEFAULT NULL COMMENT '来源ID',
    `source_item_id` BIGINT DEFAULT NULL COMMENT '来源明细ID',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退费完成时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_refund_id` (`tenant_id`, `refund_id`),
    KEY `idx_tenant_payment_id` (`tenant_id`, `payment_id`),
    KEY `idx_tenant_payment_item_id` (`tenant_id`, `payment_item_id`),
    KEY `idx_tenant_fee_item_id` (`tenant_id`, `fee_item_id`),
    KEY `idx_tenant_register_id` (`tenant_id`, `register_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退费明细表';

-- ============================================
-- 更新退费记录表添加更多字段
-- ============================================
ALTER TABLE `op_refund` ADD COLUMN `refund_type` TINYINT NOT NULL DEFAULT 1 COMMENT '退费类型:1-全额退费 2-部分退费' AFTER `refund_no`;
ALTER TABLE `op_refund` ADD COLUMN `insurance_refund_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '医保退费金额' AFTER `refund_amount`;
ALTER TABLE `op_refund` ADD COLUMN `self_refund_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '自费退费金额' AFTER `insurance_refund_amount`;
ALTER TABLE `op_refund` ADD COLUMN `original_payment_amount` DECIMAL(12,2) DEFAULT NULL COMMENT '原支付总金额' AFTER `self_refund_amount`;
ALTER TABLE `op_refund` ADD COLUMN `refund_item_count` INT DEFAULT 0 COMMENT '退费明细数量' AFTER `original_payment_amount`;
ALTER TABLE `op_refund` ADD COLUMN `pay_type` TINYINT DEFAULT NULL COMMENT '支付方式:1-现金 2-微信 3-支付宝 4-医保 5-银行卡' AFTER `refund_item_count`;
ALTER TABLE `op_refund` ADD COLUMN `insurance_type` TINYINT DEFAULT NULL COMMENT '医保类型' AFTER `pay_type`;
ALTER TABLE `op_refund` ADD COLUMN `invoice_no` VARCHAR(30) DEFAULT NULL COMMENT '原发票号' AFTER `insurance_type`;
ALTER TABLE `op_refund` ADD COLUMN `cancel_invoice_no` VARCHAR(30) DEFAULT NULL COMMENT '退费发票号' AFTER `invoice_no`;
ALTER TABLE `op_refund` ADD COLUMN `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID' AFTER `cancel_invoice_no`;
ALTER TABLE `op_refund` ADD COLUMN `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称' AFTER `dept_id`;