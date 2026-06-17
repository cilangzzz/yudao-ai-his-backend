-- ----------------------------
-- 医嘱模板主表
-- ----------------------------
DROP TABLE IF EXISTS `his_order_template`;
CREATE TABLE `his_order_template` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `template_code` varchar(50) NOT NULL COMMENT '模板编码',
    `template_name` varchar(200) NOT NULL COMMENT '模板名称',
    `template_category` tinyint NOT NULL DEFAULT 1 COMMENT '模板分类: 1-个人模板 2-科室模板 3-全院模板 4-疾病模板',
    `order_type` tinyint DEFAULT NULL COMMENT '医嘱类型: 1-药品 2-检验 3-检查 4-护理 5-手术 6-饮食 7-其他',
    `dept_id` bigint DEFAULT NULL COMMENT '科室ID',
    `dept_name` varchar(100) DEFAULT NULL COMMENT '科室名称',
    `doctor_id` bigint DEFAULT NULL COMMENT '创建医生ID',
    `doctor_name` varchar(100) DEFAULT NULL COMMENT '创建医生姓名',
    `applicable_dept_ids` varchar(500) DEFAULT NULL COMMENT '适用科室ID（多个用逗号分隔）',
    `diagnosis_code` varchar(50) DEFAULT NULL COMMENT '疾病编码（ICD-10）',
    `diagnosis_name` varchar(200) DEFAULT NULL COMMENT '疾病名称',
    `content_summary` varchar(500) DEFAULT NULL COMMENT '模板内容摘要',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `sort` int DEFAULT 0 COMMENT '排序号',
    `use_count` int DEFAULT 0 COMMENT '使用次数',
    `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    `creator` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_template_code` (`template_code`, `tenant_id`),
    KEY `idx_template_category` (`template_category`),
    KEY `idx_order_type` (`order_type`),
    KEY `idx_dept_id` (`dept_id`),
    KEY `idx_doctor_id` (`doctor_id`),
    KEY `idx_diagnosis_code` (`diagnosis_code`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='医嘱模板主表';

-- ----------------------------
-- 医嘱模板明细表
-- ----------------------------
DROP TABLE IF EXISTS `his_order_template_item`;
CREATE TABLE `his_order_template_item` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `template_id` bigint NOT NULL COMMENT '模板ID',
    `order_content` varchar(500) DEFAULT NULL COMMENT '医嘱内容',
    `item_code` varchar(50) DEFAULT NULL COMMENT '项目编码',
    `item_name` varchar(200) DEFAULT NULL COMMENT '项目名称',
    `order_type` tinyint NOT NULL COMMENT '医嘱类型: 1-药品 2-检验 3-检查 4-护理 5-手术 6-饮食 7-其他',
    `order_category` tinyint NOT NULL DEFAULT 1 COMMENT '医嘱分类: 1-长期 2-临时',
    `dosage` varchar(50) DEFAULT NULL COMMENT '剂量',
    `dosage_unit` varchar(20) DEFAULT NULL COMMENT '剂量单位',
    `frequency_code` varchar(50) DEFAULT NULL COMMENT '频次编码',
    `frequency_name` varchar(50) DEFAULT NULL COMMENT '频次名称',
    `route_code` varchar(50) DEFAULT NULL COMMENT '途径编码',
    `route_name` varchar(50) DEFAULT NULL COMMENT '途径名称',
    `duration` int DEFAULT NULL COMMENT '疗程(天)',
    `price` decimal(10,2) DEFAULT NULL COMMENT '单价',
    `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
    `urgency` tinyint DEFAULT 0 COMMENT '紧急标志: 0-常规 1-紧急',
    `skin_test_flag` tinyint DEFAULT 0 COMMENT '皮试标志: 0-不需要 1-需要',
    `self_provide_flag` tinyint DEFAULT 0 COMMENT '自备标志: 0-否 1-是',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `sort` int DEFAULT 0 COMMENT '排序号',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    `creator` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_template_id` (`template_id`),
    KEY `idx_item_code` (`item_code`),
    KEY `idx_order_type` (`order_type`),
    KEY `idx_order_category` (`order_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='医嘱模板明细表';

-- ----------------------------
-- 初始化示例数据
-- ----------------------------
-- 示例：肺炎入院常规医嘱模板
INSERT INTO `his_order_template` (`template_code`, `template_name`, `template_category`, `order_type`, `dept_id`, `dept_name`, `diagnosis_code`, `diagnosis_name`, `content_summary`, `status`, `tenant_id`, `creator`)
VALUES ('TPL_PNEUMONIA_001', '肺炎入院常规医嘱', 3, 1, NULL, NULL, 'J18.9', '肺炎', '包含抗感染、止咳化痰等医嘱', 1, 1, '1');

-- 示例：肺炎医嘱模板明细
INSERT INTO `his_order_template_item` (`template_id`, `order_content`, `item_code`, `item_name`, `order_type`, `order_category`, `dosage`, `dosage_unit`, `frequency_code`, `frequency_name`, `route_code`, `route_name`, `duration`, `urgency`, `skin_test_flag`, `tenant_id`, `creator`)
VALUES
(1, '注射用头孢曲松钠 2g qd', 'DRUG001', '注射用头孢曲松钠', 1, 1, '2', 'g', 'FREQ001', '每日一次', 'ROUTE001', '静脉滴注', 7, 0, 1, 1, '1'),
(1, '氨溴索口服液 10ml tid', 'DRUG002', '氨溴索口服液', 1, 1, '10', 'ml', 'FREQ002', '每日三次', 'ROUTE002', '口服', 7, 0, 0, 1, '1'),
(1, '血常规', 'LAB001', '血常规', 2, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 1, '1'),
(1, '胸部CT', 'EXAM001', '胸部CT', 3, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 1, '1');
