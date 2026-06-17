-- ============================================
-- 门诊费用明细表（补充）
-- ============================================

-- ----------------------------
-- 门诊费用明细表
-- ----------------------------
DROP TABLE IF EXISTS `op_fee_item`;
CREATE TABLE `op_fee_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `fee_id` BIGINT NOT NULL COMMENT '费用ID',
    `register_id` BIGINT NOT NULL COMMENT '挂号ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `charge_item_id` BIGINT DEFAULT NULL COMMENT '收费项目ID',
    `item_code` VARCHAR(20) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) NOT NULL COMMENT '项目名称',
    `item_category` TINYINT NOT NULL COMMENT '项目类别:1-挂号费 2-诊查费 3-检查费 4-治疗费 5-手术费 6-化验费 7-材料费 8-药品费 9-床位费 10-护理费 11-其他',
    `spec` VARCHAR(100) DEFAULT NULL COMMENT '规格',
    `unit` VARCHAR(20) NOT NULL COMMENT '计价单位',
    `unit_price` DECIMAL(12,4) NOT NULL COMMENT '单价',
    `quantity` DECIMAL(10,2) NOT NULL COMMENT '数量',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '金额',
    `discount_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '优惠金额',
    `pay_amount` DECIMAL(12,2) NOT NULL COMMENT '实收金额',
    `insurance_code` VARCHAR(20) DEFAULT NULL COMMENT '医保编码',
    `insurance_price` DECIMAL(12,4) DEFAULT NULL COMMENT '医保定价',
    `insurance_category` TINYINT DEFAULT NULL COMMENT '医保类别:1-甲类 2-乙类 3-丙类',
    `execution_dept_id` BIGINT DEFAULT NULL COMMENT '执行科室ID',
    `execution_dept_name` VARCHAR(100) DEFAULT NULL COMMENT '执行科室名称',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '开单医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '开单医生姓名',
    `source_type` TINYINT NOT NULL COMMENT '来源类型:1-挂号 2-处方 3-检查 4-检验 5-治疗 6-手术 7-其他',
    `source_id` BIGINT DEFAULT NULL COMMENT '来源ID',
    `source_item_id` BIGINT DEFAULT NULL COMMENT '来源明细ID',
    `fee_item_status` TINYINT NOT NULL DEFAULT 0 COMMENT '收费状态:0-未收费 1-已收费 2-已退费',
    `fee_time` DATETIME DEFAULT NULL COMMENT '收费时间',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退费时间',
    `sort_order` INT DEFAULT 0 COMMENT '排序号',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_fee_id` (`tenant_id`, `fee_id`),
    KEY `idx_tenant_register_id` (`tenant_id`, `register_id`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_source` (`tenant_id`, `source_type`, `source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='门诊费用明细表';
