-- ============================================
-- 护理交接班表
-- 版本: V1.0
-- 日期: 2026-06-18
-- 说明: 护理交接班管理功能
-- ============================================

-- ----------------------------
-- 护理交接班表
-- ----------------------------
DROP TABLE IF EXISTS `his_nursing_handover`;
CREATE TABLE `his_nursing_handover` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `handover_no` VARCHAR(20) NOT NULL COMMENT '交接班编号',
    `ward_id` BIGINT NOT NULL COMMENT '病区ID',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',
    `handover_nurse_id` BIGINT NOT NULL COMMENT '交班护士ID',
    `handover_nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '交班护士姓名',
    `takeover_nurse_id` BIGINT DEFAULT NULL COMMENT '接班护士ID',
    `takeover_nurse_name` VARCHAR(50) DEFAULT NULL COMMENT '接班护士姓名',
    `handover_time` DATETIME NOT NULL COMMENT '交班时间',
    `takeover_time` DATETIME DEFAULT NULL COMMENT '接班时间',
    `shift_type` TINYINT NOT NULL COMMENT '班次类型:1-白班 2-小夜班 3-大夜班',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0-待接班 1-已接班 2-已作废',
    -- 患者概况
    `original_patient_count` INT DEFAULT 0 COMMENT '原有患者数',
    `new_admission_count` INT DEFAULT 0 COMMENT '新入院患者数',
    `discharge_count` INT DEFAULT 0 COMMENT '出院患者数',
    `transfer_in_count` INT DEFAULT 0 COMMENT '转入患者数',
    `transfer_out_count` INT DEFAULT 0 COMMENT '转出患者数',
    `current_patient_count` INT DEFAULT 0 COMMENT '现有患者数',
    `special_care_count` INT DEFAULT 0 COMMENT '特护患者数',
    `primary_care_count` INT DEFAULT 0 COMMENT '一级护理患者数',
    `secondary_care_count` INT DEFAULT 0 COMMENT '二级护理患者数',
    `tertiary_care_count` INT DEFAULT 0 COMMENT '三级护理患者数',
    -- 交接内容
    `key_patient_situation` TEXT DEFAULT NULL COMMENT '重点患者情况',
    `todo_items` TEXT DEFAULT NULL COMMENT '待办事项',
    `special_situation` TEXT DEFAULT NULL COMMENT '特殊情况记录',
    `goods_handover` TEXT DEFAULT NULL COMMENT '物品交接情况',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    -- 签名信息
    `handover_signature_status` TINYINT DEFAULT 0 COMMENT '交班签名状态:0-未签名 1-已签名',
    `handover_signature_time` DATETIME DEFAULT NULL COMMENT '交班签名时间',
    `handover_signature` VARCHAR(500) DEFAULT NULL COMMENT '交班电子签名',
    `takeover_signature_status` TINYINT DEFAULT 0 COMMENT '接班签名状态:0-未签名 1-已签名',
    `takeover_signature_time` DATETIME DEFAULT NULL COMMENT '接班签名时间',
    `takeover_signature` VARCHAR(500) DEFAULT NULL COMMENT '接班电子签名',
    -- 基础字段
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_handover_no` (`tenant_id`, `handover_no`, `deleted`),
    KEY `idx_tenant_ward_id` (`tenant_id`, `ward_id`),
    KEY `idx_tenant_handover_nurse` (`tenant_id`, `handover_nurse_id`),
    KEY `idx_tenant_takeover_nurse` (`tenant_id`, `takeover_nurse_id`),
    KEY `idx_handover_time` (`handover_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理交接班表';
