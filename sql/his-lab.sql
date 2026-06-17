-- ============================================
-- HIS 检验申请模块数据库表
-- 版本: V1.0
-- 日期: 2026-06-18
-- 说明: 包含检验申请、检验项目、检验报告相关表
-- ============================================

-- ----------------------------
-- 检验申请主表
-- ----------------------------
DROP TABLE IF EXISTS `his_lab_request`;
CREATE TABLE `his_lab_request` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `request_no` VARCHAR(30) NOT NULL COMMENT '申请单号',
    `source_type` TINYINT NOT NULL COMMENT '就诊类型:1-门诊 2-住院 3-急诊',
    `source_id` BIGINT DEFAULT NULL COMMENT '就诊ID(挂号ID/住院ID)',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `patient_gender` TINYINT DEFAULT NULL COMMENT '患者性别:1-男 2-女',
    `patient_age` INT DEFAULT NULL COMMENT '患者年龄',
    `patient_phone` VARCHAR(20) DEFAULT NULL COMMENT '患者电话',
    `bed_no` VARCHAR(20) DEFAULT NULL COMMENT '患者床号(住院)',
    `dept_id` BIGINT NOT NULL COMMENT '申请科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '申请科室名称',
    `doctor_id` BIGINT NOT NULL COMMENT '申请医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '申请医生姓名',
    `request_time` DATETIME NOT NULL COMMENT '申请时间',
    `lab_category` VARCHAR(50) DEFAULT NULL COMMENT '检验类别(血常规/尿常规/生化/免疫等)',
    `lab_type` TINYINT DEFAULT NULL COMMENT '检验类型:1-常规检验 2-急诊检验 3-特检',
    `diagnosis` VARCHAR(200) DEFAULT NULL COMMENT '临床诊断',
    `diagnosis_code` VARCHAR(20) DEFAULT NULL COMMENT '诊断编码(ICD-10)',
    `clinical_symptom` TEXT DEFAULT NULL COMMENT '临床症状',
    `clinical_purpose` VARCHAR(200) DEFAULT NULL COMMENT '检验目的',
    `urgency` TINYINT NOT NULL DEFAULT 0 COMMENT '紧急程度:0-普通 1-急诊 2-加急',
    `is_stat` TINYINT DEFAULT 0 COMMENT '是否急诊检验:0-否 1-是',
    -- 标本信息
    `specimen_type` TINYINT DEFAULT NULL COMMENT '标本类型:1-血液 2-尿液 3-粪便 4-痰液 5-其他',
    `sample_type` VARCHAR(50) DEFAULT NULL COMMENT '标本类型名称',
    `specimen_id` BIGINT DEFAULT NULL COMMENT '标本ID',
    `specimen_barcode` VARCHAR(50) DEFAULT NULL COMMENT '标本条码',
    `specimen_note` VARCHAR(200) DEFAULT NULL COMMENT '标本采集说明',
    `collect_time` DATETIME DEFAULT NULL COMMENT '标本采集时间',
    `collector_id` BIGINT DEFAULT NULL COMMENT '标本采集人ID',
    `collector_name` VARCHAR(50) DEFAULT NULL COMMENT '标本采集人姓名',
    `specimen_status` TINYINT DEFAULT 1 COMMENT '标本状态:1-待采集 2-已采集 3-已运送 4-已接收 5-已拒收 6-检验中 7-已完成 8-已销毁',
    `receive_time` DATETIME DEFAULT NULL COMMENT '标本接收时间',
    `receiver_id` BIGINT DEFAULT NULL COMMENT '标本接收人ID',
    `receiver_name` VARCHAR(50) DEFAULT NULL COMMENT '标本接收人姓名',
    `reject_reason` VARCHAR(200) DEFAULT NULL COMMENT '标本拒收原因',
    `reject_time` DATETIME DEFAULT NULL COMMENT '标本拒收时间',
    -- 执行信息
    `execution_dept_id` BIGINT DEFAULT NULL COMMENT '执行科室ID',
    `execution_dept_name` VARCHAR(100) DEFAULT NULL COMMENT '执行科室名称',
    `start_time` DATETIME DEFAULT NULL COMMENT '检验开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '检验结束时间',
    `technician_id` BIGINT DEFAULT NULL COMMENT '检验技师ID',
    `technician_name` VARCHAR(50) DEFAULT NULL COMMENT '检验技师姓名',
    -- 报告信息
    `report_id` BIGINT DEFAULT NULL COMMENT '报告ID',
    `report_no` VARCHAR(30) DEFAULT NULL COMMENT '报告编号',
    `report_doctor_id` BIGINT DEFAULT NULL COMMENT '报告医生ID',
    `report_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '报告医生姓名',
    `report_time` DATETIME DEFAULT NULL COMMENT '报告时间',
    `audit_doctor_id` BIGINT DEFAULT NULL COMMENT '审核医生ID',
    `audit_doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '审核医生姓名',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `report_status` TINYINT DEFAULT 0 COMMENT '报告状态:0-草稿 1-初步报告 2-最终报告 3-已审核 4-已发布 5-已撤回',
    -- 申请状态
    `request_status` TINYINT NOT NULL DEFAULT 1 COMMENT '申请状态:1-已申请 2-已采集 3-已接收 4-检验中 5-已完成 6-已报告 7-已审核 8-已取消 9-已退费',
    -- 危急值信息
    `critical_flag` TINYINT DEFAULT 0 COMMENT '是否有危急值:0-无 1-有',
    `critical_value_content` VARCHAR(500) DEFAULT NULL COMMENT '危急值内容',
    `critical_report_time` DATETIME DEFAULT NULL COMMENT '危急值报告时间',
    `critical_confirm_user_id` BIGINT DEFAULT NULL COMMENT '危急值确认人ID',
    `critical_confirm_user_name` VARCHAR(50) DEFAULT NULL COMMENT '危急值确认人姓名',
    `critical_confirm_time` DATETIME DEFAULT NULL COMMENT '危急值确认时间',
    -- 费用信息
    `item_count` INT DEFAULT 0 COMMENT '检验项目数',
    `total_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总金额',
    `insurance_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '医保支付金额',
    `self_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '自付金额',
    `pay_status` TINYINT DEFAULT 0 COMMENT '支付状态:0-未支付 1-已支付 2-已退费',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `pay_type` TINYINT DEFAULT NULL COMMENT '支付方式:1-现金 2-微信 3-支付宝 4-医保',
    -- 取消信息
    `cancel_reason` VARCHAR(200) DEFAULT NULL COMMENT '取消原因',
    `cancel_time` DATETIME DEFAULT NULL COMMENT '取消时间',
    `cancel_by` BIGINT DEFAULT NULL COMMENT '取消人',
    -- 其他信息
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `clinical_history` TEXT DEFAULT NULL COMMENT '临床病史',
    `allergy_history` VARCHAR(200) DEFAULT NULL COMMENT '过敏史',
    `medication_history` VARCHAR(200) DEFAULT NULL COMMENT '用药史',
    `fasting_flag` TINYINT DEFAULT 0 COMMENT '是否空腹:0-否 1-是',
    `special_instruction` VARCHAR(500) DEFAULT NULL COMMENT '特殊说明',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_request_no` (`tenant_id`, `request_no`, `deleted`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_source_id` (`tenant_id`, `source_id`),
    KEY `idx_tenant_dept_id` (`tenant_id`, `dept_id`),
    KEY `idx_tenant_status` (`tenant_id`, `request_status`),
    KEY `idx_tenant_specimen_barcode` (`tenant_id`, `specimen_barcode`),
    KEY `idx_tenant_request_time` (`tenant_id`, `request_time`),
    KEY `idx_tenant_create_time` (`tenant_id`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='检验申请主表';

-- ----------------------------
-- 检验申请明细表
-- ----------------------------
DROP TABLE IF EXISTS `his_lab_request_item`;
CREATE TABLE `his_lab_request_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `request_id` BIGINT NOT NULL COMMENT '申请ID',
    `item_code` VARCHAR(20) NOT NULL COMMENT '检验项目编码',
    `item_name` VARCHAR(100) NOT NULL COMMENT '检验项目名称',
    `item_short_name` VARCHAR(50) DEFAULT NULL COMMENT '项目简称',
    `item_category` VARCHAR(50) DEFAULT NULL COMMENT '项目类别',
    `test_method` VARCHAR(100) DEFAULT NULL COMMENT '检验方法',
    `sample_type` VARCHAR(50) DEFAULT NULL COMMENT '标本类型',
    `unit` VARCHAR(20) DEFAULT NULL COMMENT '单位',
    `quantity` DECIMAL(10,2) NOT NULL DEFAULT 1 COMMENT '数量',
    `unit_price` DECIMAL(10,4) NOT NULL COMMENT '单价',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '金额',
    `insurance_code` VARCHAR(20) DEFAULT NULL COMMENT '医保编码',
    `insurance_category` TINYINT DEFAULT NULL COMMENT '医保类别:1-甲类 2-乙类 3-丙类',
    `execution_dept_id` BIGINT DEFAULT NULL COMMENT '执行科室ID',
    `execution_dept_name` VARCHAR(100) DEFAULT NULL COMMENT '执行科室名称',
    `item_status` TINYINT NOT NULL DEFAULT 1 COMMENT '项目状态:1-待检验 2-检验中 3-已完成 4-已取消',
    `start_time` DATETIME DEFAULT NULL COMMENT '检验开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '检验结束时间',
    `technician_id` BIGINT DEFAULT NULL COMMENT '检验技师ID',
    `technician_name` VARCHAR(50) DEFAULT NULL COMMENT '检验技师姓名',
    -- 结果信息
    `result_value` VARCHAR(200) DEFAULT NULL COMMENT '检验结果值',
    `result_unit` VARCHAR(20) DEFAULT NULL COMMENT '结果单位',
    `reference_range_low` DECIMAL(10,4) DEFAULT NULL COMMENT '参考范围下限',
    `reference_range_high` DECIMAL(10,4) DEFAULT NULL COMMENT '参考范围上限',
    `reference_range_text` VARCHAR(50) DEFAULT NULL COMMENT '参考范围文本',
    `abnormal_flag` TINYINT DEFAULT NULL COMMENT '异常标志:0-正常 1-偏高 2-偏低 3-阳性 4-阴性 5-弱阳性',
    `critical_flag` TINYINT DEFAULT 0 COMMENT '危急值标志:0-无 1-有',
    `critical_low` DECIMAL(10,4) DEFAULT NULL COMMENT '危急值下限',
    `critical_high` DECIMAL(10,4) DEFAULT NULL COMMENT '危急值上限',
    `result_status` TINYINT DEFAULT NULL COMMENT '结果状态:1-正常 2-异常 3-危急',
    `result_remark` VARCHAR(500) DEFAULT NULL COMMENT '结果备注',
    -- 仪器信息
    `instrument_id` BIGINT DEFAULT NULL COMMENT '仪器设备ID',
    `instrument_name` VARCHAR(50) DEFAULT NULL COMMENT '仪器设备名称',
    `reagent_batch` VARCHAR(50) DEFAULT NULL COMMENT '试剂批号',
    `test_time` DATETIME DEFAULT NULL COMMENT '检验时间',
    `operator_id` BIGINT DEFAULT NULL COMMENT '操作员ID',
    `operator_name` VARCHAR(50) DEFAULT NULL COMMENT '操作员姓名',
    `sort_order` INT DEFAULT 0 COMMENT '排序号',
    `group_code` VARCHAR(20) DEFAULT NULL COMMENT '分组编码',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_request_id` (`tenant_id`, `request_id`),
    KEY `idx_tenant_item_code` (`tenant_id`, `item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='检验申请明细表';

-- ============================================
-- 检验模块字典数据
-- ============================================

-- 初始化检验类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS检验类型', 'his_lab_type', 0, 'HIS检验类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '常规检验', '1', 'his_lab_type', 0, 'primary', '', '常规检验', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '急诊检验', '2', 'his_lab_type', 0, 'danger', '', '急诊检验', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '特检', '3', 'his_lab_type', 0, 'warning', '', '特检', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化检验申请状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS检验申请状态', 'his_lab_request_status', 0, 'HIS检验申请状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '已申请', '1', 'his_lab_request_status', 0, 'default', '', '已申请', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '已采集', '2', 'his_lab_request_status', 0, 'primary', '', '已采集', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '已接收', '3', 'his_lab_request_status', 0, 'info', '', '已接收', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '检验中', '4', 'his_lab_request_status', 0, 'warning', '', '检验中', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '已完成', '5', 'his_lab_request_status', 0, 'success', '', '已完成', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '已报告', '6', 'his_lab_request_status', 0, 'primary', '', '已报告', 'admin', NOW(), 'admin', NOW(), b'0'),
(7, '已审核', '7', 'his_lab_request_status', 0, 'success', '', '已审核', 'admin', NOW(), 'admin', NOW(), b'0'),
(8, '已取消', '8', 'his_lab_request_status', 0, 'danger', '', '已取消', 'admin', NOW(), 'admin', NOW(), b'0'),
(9, '已退费', '9', 'his_lab_request_status', 0, 'info', '', '已退费', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化标本类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS标本类型', 'his_specimen_type', 0, 'HIS标本类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '血液', '1', 'his_specimen_type', 0, 'danger', '', '血液', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '尿液', '2', 'his_specimen_type', 0, 'warning', '', '尿液', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '粪便', '3', 'his_specimen_type', 0, 'info', '', '粪便', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '痰液', '4', 'his_specimen_type', 0, 'default', '', '痰液', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '其他', '5', 'his_specimen_type', 0, 'primary', '', '其他', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化标本状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS标本状态', 'his_specimen_status', 0, 'HIS标本状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '待采集', '1', 'his_specimen_status', 0, 'default', '', '待采集', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '已采集', '2', 'his_specimen_status', 0, 'primary', '', '已采集', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '已运送', '3', 'his_specimen_status', 0, 'info', '', '已运送', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '已接收', '4', 'his_specimen_status', 0, 'success', '', '已接收', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '已拒收', '5', 'his_specimen_status', 0, 'danger', '', '已拒收', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '检验中', '6', 'his_specimen_status', 0, 'warning', '', '检验中', 'admin', NOW(), 'admin', NOW(), b'0'),
(7, '已完成', '7', 'his_specimen_status', 0, 'success', '', '已完成', 'admin', NOW(), 'admin', NOW(), b'0'),
(8, '已销毁', '8', 'his_specimen_status', 0, 'info', '', '已销毁', 'admin', NOW(), 'admin', NOW(), b'0');

-- 初始化检验报告状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS检验报告状态', 'his_lab_report_status', 0, 'HIS检验报告状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '草稿', '0', 'his_lab_report_status', 0, 'default', '', '草稿', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '初步报告', '1', 'his_lab_report_status', 0, 'warning', '', '初步报告', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '最终报告', '2', 'his_lab_report_status', 0, 'primary', '', '最终报告', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '已审核', '3', 'his_lab_report_status', 0, 'success', '', '已审核', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '已发布', '4', 'his_lab_report_status', 0, 'success', '', '已发布', 'admin', NOW(), 'admin', NOW(), b'0'),
(6, '已撤回', '5', 'his_lab_report_status', 0, 'danger', '', '已撤回', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 初始化完成
-- ============================================
