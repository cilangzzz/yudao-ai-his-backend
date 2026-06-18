-- ============================================
-- HIS 系统菜单数据合并文件
-- 生成时间: 2026-06-18 14:30:53
-- ============================================

SET NAMES utf8mb4;

-- 来源: his_admission_menu_data.sql
SET @inpatient_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '住院管理' AND `parent_id` = (SELECT id FROM `system_menu` WHERE `name` = 'HIS医院信息系统' AND `deleted` = b'0') AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('入院登记', '', 2, 1, @inpatient_menu_id, 'admission', 'ep:plus', 'his/inpatient/admission/index', 'HisAdmission', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位管理', '', 2, 2, @inpatient_menu_id, 'bed', 'ep:bed', 'his/inpatient/bed/index', 'HisBed', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区管理', '', 2, 3, @inpatient_menu_id, 'ward', 'ep:build', 'his/inpatient/ward/index', 'HisWard', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院管理', '', 2, 4, @inpatient_menu_id, 'preAdmission', 'ep:calendar', 'his/inpatient/preAdmission/index', 'HisPreAdmission', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入院评估', '', 2, 5, @inpatient_menu_id, 'assess', 'ep:document-checked', 'his/inpatient/assess/index', 'HisAdmissionAssess', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金管理', '', 2, 6, @inpatient_menu_id, 'prepayment', 'ep:wallet', 'his/inpatient/prepayment/index', 'HisPrepayment', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @admission_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '入院登记' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('入院登记查询', 'his:admission:query', 3, 1, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入院登记创建', 'his:admission:create', 3, 2, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入院登记更新', 'his:admission:update', 3, 3, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入院登记删除', 'his:admission:delete', 3, 4, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('出院办理', 'his:admission:discharge', 3, 5, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('转科办理', 'his:admission:transfer', 3, 6, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @bed_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '床位管理' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('床位查询', 'his:bed:query', 3, 1, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位创建', 'his:bed:create', 3, 2, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位更新', 'his:bed:update', 3, 3, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位删除', 'his:bed:delete', 3, 4, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位占用', 'his:bed:occupy', 3, 5, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位释放', 'his:bed:release', 3, 6, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @ward_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '病区管理' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('病区查询', 'his:ward:query', 3, 1, @ward_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区创建', 'his:ward:create', 3, 2, @ward_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区更新', 'his:ward:update', 3, 3, @ward_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区删除', 'his:ward:delete', 3, 4, @ward_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @pre_admission_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '预住院管理' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('预住院查询', 'his:pre-admission:query', 3, 1, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院创建', 'his:pre-admission:create', 3, 2, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院更新', 'his:pre-admission:update', 3, 3, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院删除', 'his:pre-admission:delete', 3, 4, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院取消', 'his:pre-admission:cancel', 3, 5, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院入院', 'his:pre-admission:convert', 3, 6, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @assess_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '入院评估' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('评估查询', 'his:assess:query', 3, 1, @assess_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('评估创建', 'his:assess:create', 3, 2, @assess_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('评估更新', 'his:assess:update', 3, 3, @assess_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('评估删除', 'his:assess:delete', 3, 4, @assess_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @prepayment_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '预交金管理' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('预交金查询', 'his:prepayment:query', 3, 1, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金创建', 'his:prepayment:create', 3, 2, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金删除', 'his:prepayment:delete', 3, 3, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金退款', 'his:prepayment:refund', 3, 4, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS入院状态', 'his_admission_status', 0, 'HIS入院状态字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '在院', '1', 'his_admission_status', 0, 'primary', '', '在院', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '出院', '2', 'his_admission_status', 0, 'success', '', '出院', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '转科', '3', 'his_admission_status', 0, 'warning', '', '转科', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS入院方式', 'his_admission_way', 0, 'HIS入院方式字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '门诊', '1', 'his_admission_way', 0, 'default', '', '门诊入院', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '急诊', '2', 'his_admission_way', 0, 'danger', '', '急诊入院', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '转院', '3', 'his_admission_way', 0, 'warning', '', '转院入院', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS入院情况', 'his_admission_condition', 0, 'HIS入院情况字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '危', '1', 'his_admission_condition', 0, 'danger', '', '危重', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '急', '2', 'his_admission_condition', 0, 'warning', '', '急诊', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '一般', '3', 'his_admission_condition', 0, 'default', '', '一般', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS床位状态', 'his_bed_status', 0, 'HIS床位状态字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '空床', '1', 'his_bed_status', 0, 'success', '', '空床可用', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '占用', '2', 'his_bed_status', 0, 'danger', '', '床位被占用', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '预约', '3', 'his_bed_status', 0, 'warning', '', '床位已预约', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '清洁', '4', 'his_bed_status', 0, 'info', '', '正在清洁', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '维修', '5', 'his_bed_status', 0, 'default', '', '维修中', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS床位类型', 'his_bed_type', 0, 'HIS床位类型字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '普通', '1', 'his_bed_type', 0, 'default', '', '普通床位', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '抢救', '2', 'his_bed_type', 0, 'danger', '', '抢救床位', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, 'ICU', '3', 'his_bed_type', 0, 'warning', '', 'ICU床位', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '隔离', '4', 'his_bed_type', 0, 'info', '', '隔离床位', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS床位等级', 'his_bed_class', 0, 'HIS床位等级字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '普通', '1', 'his_bed_class', 0, 'default', '', '普通床位', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '双人', '2', 'his_bed_class', 0, 'info', '', '双人房间', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '单人', '3', 'his_bed_class', 0, 'primary', '', '单人房间', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, 'VIP', '4', 'his_bed_class', 0, 'warning', '', 'VIP病房', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS出院方式', 'his_discharge_way', 0, 'HIS出院方式字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '医嘱出院', '1', 'his_discharge_way', 0, 'success', '', '医嘱出院', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '自动出院', '2', 'his_discharge_way', 0, 'warning', '', '自动出院', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '转院', '3', 'his_discharge_way', 0, 'info', '', '转院', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '死亡', '4', 'his_discharge_way', 0, 'danger', '', '死亡', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS评估类型', 'his_assess_type', 0, 'HIS评估类型字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '入院评估', '1', 'his_assess_type', 0, 'primary', '', '入院评估', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '跌倒评估', '2', 'his_assess_type', 0, 'warning', '', '跌倒风险评估', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '压疮评估', '3', 'his_assess_type', 0, 'danger', '', '压疮风险评估', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '疼痛评估', '4', 'his_assess_type', 0, 'info', '', '疼痛评估', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '营养评估', '5', 'his_assess_type', 0, 'success', '', '营养评估', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS风险等级', 'his_risk_level', 0, 'HIS风险等级字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '低', '1', 'his_risk_level', 0, 'success', '', '低风险', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '中', '2', 'his_risk_level', 0, 'warning', '', '中风险', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '高', '3', 'his_risk_level', 0, 'danger', '', '高风险', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS预交金状态', 'his_prepayment_status', 0, 'HIS预交金状态字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '正常', '1', 'his_prepayment_status', 0, 'success', '', '正常', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '已退', '2', 'his_prepayment_status', 0, 'danger', '', '已退', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS预住院状态', 'his_pre_admission_status', 0, 'HIS预住院状态字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '待入院', '1', 'his_pre_admission_status', 0, 'warning', '', '待入院', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '已入院', '2', 'his_pre_admission_status', 0, 'success', '', '已入院', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '已取消', '3', 'his_pre_admission_status', 0, 'danger', '', '已取消', 'admin', NOW(), 'admin', NOW(), b'0');
DROP TABLE IF EXISTS `his_pre_admission`;
CREATE TABLE `his_pre_admission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    `pre_admission_no` VARCHAR(30) NOT NULL COMMENT '预住院编号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `patient_phone` VARCHAR(20) DEFAULT NULL COMMENT '患者电话',
    `id_card_no` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    `register_id` BIGINT DEFAULT NULL COMMENT '关联门诊挂号ID',
    `dept_id` BIGINT NOT NULL COMMENT '预约科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `scheduled_date` DATETIME NOT NULL COMMENT '预约入院日期',
    `diagnosis` VARCHAR(200) DEFAULT NULL COMMENT '预约诊断',
    `diagnosis_code` VARCHAR(20) DEFAULT NULL COMMENT '诊断编码',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '主治医师ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '主治医师姓名',
    `deposit_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '预交金',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1-待入院 2-已入院 3-已取消',
    `admission_time` DATETIME DEFAULT NULL COMMENT '入院时间',
    `admission_id` BIGINT DEFAULT NULL COMMENT '入院记录ID',
    `cancel_reason` VARCHAR(200) DEFAULT NULL COMMENT '取消原因',
    `cancel_time` DATETIME DEFAULT NULL COMMENT '取消时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_pre_admission_no` (`tenant_id`, `pre_admission_no`, `deleted`),
    KEY `idx_tenant_patient_id` (`tenant_id`, `patient_id`),
    KEY `idx_tenant_dept_id` (`tenant_id`, `dept_id`),
    KEY `idx_tenant_status` (`tenant_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预住院记录表';

-- 来源: his_drug_return_menu_data.sql
SET @pharmacy_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '药品管理' AND `parent_id` = (SELECT id FROM `system_menu` WHERE `name` = 'HIS医院信息系统' AND `deleted` = b'0') AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('退药管理', '', 2, 8, @pharmacy_menu_id, 'drug-return', 'ep:return', 'his/pharmacy/drug-return/index', 'OpDrugReturn', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @drug_return_menu_id = LAST_INSERT_ID();
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('退药申请查询', 'his:drug-return:query', 3, 1, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('退药申请新增', 'his:drug-return:create', 3, 2, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('退药申请修改', 'his:drug-return:update', 3, 3, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('退药申请删除', 'his:drug-return:delete', 3, 4, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('退药申请审核', 'his:drug-return:audit', 3, 5, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('退药申请取消', 'his:drug-return:cancel', 3, 6, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('退药入库', 'his:drug-return:inbound', 3, 7, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('退药退款', 'his:drug-return:refund', 3, 8, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('退药申请导出', 'his:drug-return:export', 3, 9, @drug_return_menu_id, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
SET @pharmacist_role_id = (SELECT id FROM `system_role` WHERE `code` = 'his_pharmacist' AND `deleted` = b'0');
SET @doctor_role_id = (SELECT id FROM `system_role` WHERE `code` = 'his_doctor' AND `deleted` = b'0');
SET @cashier_role_id = (SELECT id FROM `system_role` WHERE `code` = 'his_cashier' AND `deleted` = b'0');
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(@pharmacist_role_id, @drug_return_menu_id, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT @pharmacist_role_id, id, 'admin', NOW(), 'admin', NOW(), b'0'
FROM `system_menu` WHERE `parent_id` = @drug_return_menu_id AND `deleted` = b'0';
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(@doctor_role_id, @drug_return_menu_id, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT @doctor_role_id, id, 'admin', NOW(), 'admin', NOW(), b'0'
FROM `system_menu` WHERE `parent_id` = @drug_return_menu_id AND `permission` IN ('his:drug-return:query', 'his:drug-return:create') AND `deleted` = b'0';
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(@cashier_role_id, @drug_return_menu_id, 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT @cashier_role_id, id, 'admin', NOW(), 'admin', NOW(), b'0'
FROM `system_menu` WHERE `parent_id` = @drug_return_menu_id AND `permission` IN ('his:drug-return:query', 'his:drug-return:refund') AND `deleted` = b'0';

-- 来源: his_exam_menu_data.sql
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('检查申请', '', 2, 8, (SELECT id FROM (SELECT id FROM `system_menu` WHERE `name` = '门诊管理' AND `deleted` = b'0') t), 'exam-request', 'ep:document-checked', 'his/outpatient/exam-request/index', 'HisExamRequest', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查项目', '', 2, 9, (SELECT id FROM (SELECT id FROM `system_menu` WHERE `name` = '门诊管理' AND `deleted` = b'0') t), 'exam-item', 'ep:list', 'his/outpatient/exam-item/index', 'HisExamItem', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @exam_request_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '检查申请' AND `deleted` = b'0' ORDER BY id DESC LIMIT 1);
SET @exam_item_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '检查项目' AND `deleted` = b'0' ORDER BY id DESC LIMIT 1);
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('检查申请查询', 'his:exam-request:query', 3, 1, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请新增', 'his:exam-request:create', 3, 2, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请修改', 'his:exam-request:update', 3, 3, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请删除', 'his:exam-request:delete', 3, 4, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请预约', 'his:exam-request:appoint', 3, 5, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请签到', 'his:exam-request:check-in', 3, 6, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请执行', 'his:exam-request:execute', 3, 7, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请取消', 'his:exam-request:cancel', 3, 8, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('检查项目查询', 'his:exam-item:query', 3, 1, @exam_item_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查项目新增', 'his:exam-item:create', 3, 2, @exam_item_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查项目修改', 'his:exam-item:update', 3, 3, @exam_item_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查项目删除', 'his:exam-item:delete', 3, 4, @exam_item_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- 来源: his_lab_menu_data.sql
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '检验管理', '', 1, 4, 0, '/his/lab', 'ep:document-checked', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `system_menu` WHERE `name` = '检验管理' AND `deleted` = b'0');
SET @lab_parent_id = (SELECT `id` FROM `system_menu` WHERE `name` = '检验管理' AND `deleted` = b'0' LIMIT 1);
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验申请管理', '', 1, 1, @lab_parent_id, 'lab-request', 'ep:document', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
SET @lab_request_dir_id = (SELECT `id` FROM `system_menu` WHERE `name` = '检验申请管理' AND `parent_id` = @lab_parent_id AND `deleted` = b'0' LIMIT 1);
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验申请查询', 'his:lab-request:query', 3, 1, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验申请创建', 'his:lab-request:create', 3, 2, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验申请更新', 'his:lab-request:update', 3, 3, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验申请删除', 'his:lab-request:delete', 3, 4, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('标本采集', 'his:lab-request:collect', 3, 5, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('标本接收', 'his:lab-request:receive', 3, 6, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验执行', 'his:lab-request:execute', 3, 7, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('报告生成', 'his:lab-request:report', 3, 8, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('报告审核', 'his:lab-request:audit', 3, 9, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('报告发布', 'his:lab-request:publish', 3, 10, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('危急值确认', 'his:lab-request:confirm-critical', 3, 11, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验申请取消', 'his:lab-request:cancel', 3, 12, @lab_request_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验项目字典', '', 1, 2, @lab_parent_id, 'lab-item', 'ep:list', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
SET @lab_item_dir_id = (SELECT `id` FROM `system_menu` WHERE `name` = '检验项目字典' AND `parent_id` = @lab_parent_id AND `deleted` = b'0' LIMIT 1);
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验项目查询', 'his:lab-item:query', 3, 1, @lab_item_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验项目创建', 'his:lab-item:create', 3, 2, @lab_item_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验项目更新', 'his:lab-item:update', 3, 3, @lab_item_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('检验项目删除', 'his:lab-item:delete', 3, 4, @lab_item_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('危急值管理', '', 1, 3, @lab_parent_id, 'critical-value', 'ep:warning', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
SET @critical_value_dir_id = (SELECT `id` FROM `system_menu` WHERE `name` = '危急值管理' AND `parent_id` = @lab_parent_id AND `deleted` = b'0' LIMIT 1);
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('危急值查询', 'his:critical-value:query', 3, 1, @critical_value_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('危急值处理', 'his:critical-value:handle', 3, 2, @critical_value_dir_id, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 来源: his_payment_menu_data.sql
SET @outpatient_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '门诊管理' AND `path` = '/his/outpatient' AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('支付管理', '', 2, 8, @outpatient_menu_id, 'payment', 'ep:money', 'his/outpatient/payment/index', 'OpPayment', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @payment_menu_id = LAST_INSERT_ID();
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('支付查询', 'his:payment:query', 3, 1, @payment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('支付创建', 'his:payment:create', 3, 2, @payment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('支付更新', 'his:payment:update', 3, 3, @payment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('支付删除', 'his:payment:delete', 3, 4, @payment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('退费管理', '', 2, 9, @outpatient_menu_id, 'refund', 'ep:refund', 'his/outpatient/refund/index', 'OpRefund', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @refund_menu_id = LAST_INSERT_ID();
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('退费查询', 'his:refund:query', 3, 1, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('退费申请', 'his:refund:create', 3, 2, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('退费审核', 'his:refund:audit', 3, 3, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('退费完成', 'his:refund:complete', 3, 4, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('退费删除', 'his:refund:delete', 3, 5, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @inpatient_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '住院管理' AND `path` = '/his/inpatient' AND `deleted` = b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('预交金管理', '', 2, 6, @inpatient_menu_id, 'prepayment', 'ep:wallet', 'his/inpatient/prepayment/index', 'HisPrepayment', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
SET @prepayment_menu_id = LAST_INSERT_ID();
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('预交金查询', 'his:prepayment:query', 3, 1, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金缴纳', 'his:prepayment:create', 3, 2, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金退款', 'his:prepayment:refund', 3, 3, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金删除', 'his:prepayment:delete', 3, 4, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS支付方式', 'his_pay_type', 0, 'HIS支付方式字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '现金', '1', 'his_pay_type', 0, 'default', '', '现金支付', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '微信', '2', 'his_pay_type', 0, 'success', '', '微信支付', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '支付宝', '3', 'his_pay_type', 0, 'primary', '', '支付宝支付', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '医保', '4', 'his_pay_type', 0, 'warning', '', '医保支付', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '银行卡', '5', 'his_pay_type', 0, 'info', '', '银行卡支付', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS支付状态', 'his_payment_status', 0, 'HIS支付状态字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '成功', '1', 'his_payment_status', 0, 'success', '', '支付成功', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '失败', '2', 'his_payment_status', 0, 'danger', '', '支付失败', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '已退费', '3', 'his_payment_status', 0, 'warning', '', '已退费', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS退费状态', 'his_refund_status', 0, 'HIS退费状态字典', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '待审核', '1', 'his_refund_status', 0, 'warning', '', '退费待审核', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '已通过', '2', 'his_refund_status', 0, 'success', '', '退费已通过', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '已拒绝', '3', 'his_refund_status', 0, 'danger', '', '退费已拒绝', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '已完成', '4', 'his_refund_status', 0, 'info', '', '退费已完成', 'admin', NOW(), 'admin', NOW(), b'0');

-- 来源: his_order_execution_menu.sql
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('医嘱执行', '', 2, 3, 0, '/his/order-exec', 'ep:document', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
SET @parentId = LAST_INSERT_ID();
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('医嘱管理', 'his:order:query', 2, 1, @parentId, 'order', 'ep:document-checked', 'his/order/index', 'HisOrder', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药记录', 'his:medication-admin:query', 2, 2, @parentId, 'medication-admin', 'ep:first-aid-kit', 'his/medication-admin/index', 'HisMedicationAdmin', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('生命体征', 'his:vital-sign:query', 2, 3, @parentId, 'vital-sign', 'ep:heart', 'his/vital-sign/index', 'HisVitalSign', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录', 'his:nursing-record:query', 2, 4, @parentId, 'nursing-record', 'ep:notebook', 'his/nursing-record/index', 'HisNursingRecord', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理评估', 'his:nursing-assessment:query', 2, 5, @parentId, 'nursing-assessment', 'ep:data-analysis', 'his/nursing-assessment/index', 'HisNursingAssessment', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('医嘱查询', 'his:order:query', 3, 1, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱审核', 'his:order:audit', 3, 2, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱执行', 'his:order:execute', 3, 3, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱停止', 'his:order:stop', 3, 4, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱作废', 'his:order:cancel', 3, 5, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('给药记录查询', 'his:medication-admin:query', 3, 1, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药记录新增', 'his:medication-admin:create', 3, 2, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药记录更新', 'his:medication-admin:update', 3, 3, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药记录删除', 'his:medication-admin:delete', 3, 4, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药执行', 'his:medication-admin:execute', 3, 5, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('生命体征查询', 'his:vital-sign:query', 3, 1, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:vital-sign:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('生命体征新增', 'his:vital-sign:create', 3, 2, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:vital-sign:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('生命体征更新', 'his:vital-sign:update', 3, 3, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:vital-sign:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('生命体征删除', 'his:vital-sign:delete', 3, 4, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:vital-sign:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('护理记录查询', 'his:nursing-record:query', 3, 1, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录新增', 'his:nursing-record:create', 3, 2, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录更新', 'his:nursing-record:update', 3, 3, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录删除', 'his:nursing-record:delete', 3, 4, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录签名', 'his:nursing-record:sign', 3, 5, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录审核', 'his:nursing-record:audit', 3, 6, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

