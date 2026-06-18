-- ============================================
-- HIS 入院管理模块菜单权限配置 SQL
-- 版本: V1.0
-- 日期: 2026-06-18
-- 说明: 入院管理、床位管理、预住院管理、入院评估、预交金管理等菜单配置
-- ============================================

SET NAMES utf8mb4;

-- ============================================
-- 入院管理子菜单
-- ============================================
-- 获取住院管理菜单ID
SET @inpatient_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '住院管理' AND `parent_id` = (SELECT id FROM `system_menu` WHERE `name` = 'HIS医院信息系统' AND `deleted` = b'0') AND `deleted` = b'0');

-- 入院管理菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('入院登记', '', 2, 1, @inpatient_menu_id, 'admission', 'ep:plus', 'his/inpatient/admission/index', 'HisAdmission', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位管理', '', 2, 2, @inpatient_menu_id, 'bed', 'ep:bed', 'his/inpatient/bed/index', 'HisBed', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区管理', '', 2, 3, @inpatient_menu_id, 'ward', 'ep:build', 'his/inpatient/ward/index', 'HisWard', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院管理', '', 2, 4, @inpatient_menu_id, 'preAdmission', 'ep:calendar', 'his/inpatient/preAdmission/index', 'HisPreAdmission', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入院评估', '', 2, 5, @inpatient_menu_id, 'assess', 'ep:document-checked', 'his/inpatient/assess/index', 'HisAdmissionAssess', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金管理', '', 2, 6, @inpatient_menu_id, 'prepayment', 'ep:wallet', 'his/inpatient/prepayment/index', 'HisPrepayment', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 入院登记按钮权限
-- ============================================
SET @admission_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '入院登记' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('入院登记查询', 'his:admission:query', 3, 1, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入院登记创建', 'his:admission:create', 3, 2, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入院登记更新', 'his:admission:update', 3, 3, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入院登记删除', 'his:admission:delete', 3, 4, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('出院办理', 'his:admission:discharge', 3, 5, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('转科办理', 'his:admission:transfer', 3, 6, @admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 床位管理按钮权限
-- ============================================
SET @bed_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '床位管理' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('床位查询', 'his:bed:query', 3, 1, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位创建', 'his:bed:create', 3, 2, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位更新', 'his:bed:update', 3, 3, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位删除', 'his:bed:delete', 3, 4, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位占用', 'his:bed:occupy', 3, 5, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位释放', 'his:bed:release', 3, 6, @bed_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 病区管理按钮权限
-- ============================================
SET @ward_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '病区管理' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('病区查询', 'his:ward:query', 3, 1, @ward_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区创建', 'his:ward:create', 3, 2, @ward_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区更新', 'his:ward:update', 3, 3, @ward_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区删除', 'his:ward:delete', 3, 4, @ward_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 预住院管理按钮权限
-- ============================================
SET @pre_admission_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '预住院管理' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('预住院查询', 'his:pre-admission:query', 3, 1, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院创建', 'his:pre-admission:create', 3, 2, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院更新', 'his:pre-admission:update', 3, 3, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院删除', 'his:pre-admission:delete', 3, 4, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院取消', 'his:pre-admission:cancel', 3, 5, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预住院入院', 'his:pre-admission:convert', 3, 6, @pre_admission_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 入院评估按钮权限
-- ============================================
SET @assess_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '入院评估' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('评估查询', 'his:assess:query', 3, 1, @assess_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('评估创建', 'his:assess:create', 3, 2, @assess_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('评估更新', 'his:assess:update', 3, 3, @assess_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('评估删除', 'his:assess:delete', 3, 4, @assess_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 预交金管理按钮权限
-- ============================================
SET @prepayment_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '预交金管理' AND `parent_id` = @inpatient_menu_id AND `deleted` = b'0');

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('预交金查询', 'his:prepayment:query', 3, 1, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金创建', 'his:prepayment:create', 3, 2, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金删除', 'his:prepayment:delete', 3, 3, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金退款', 'his:prepayment:refund', 3, 4, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 入院管理字典数据
-- ============================================

-- 入院状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS入院状态', 'his_admission_status', 0, 'HIS入院状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '在院', '1', 'his_admission_status', 0, 'primary', '', '在院', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '出院', '2', 'his_admission_status', 0, 'success', '', '出院', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '转科', '3', 'his_admission_status', 0, 'warning', '', '转科', 'admin', NOW(), 'admin', NOW(), b'0');

-- 入院方式字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS入院方式', 'his_admission_way', 0, 'HIS入院方式字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '门诊', '1', 'his_admission_way', 0, 'default', '', '门诊入院', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '急诊', '2', 'his_admission_way', 0, 'danger', '', '急诊入院', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '转院', '3', 'his_admission_way', 0, 'warning', '', '转院入院', 'admin', NOW(), 'admin', NOW(), b'0');

-- 入院情况字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS入院情况', 'his_admission_condition', 0, 'HIS入院情况字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '危', '1', 'his_admission_condition', 0, 'danger', '', '危重', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '急', '2', 'his_admission_condition', 0, 'warning', '', '急诊', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '一般', '3', 'his_admission_condition', 0, 'default', '', '一般', 'admin', NOW(), 'admin', NOW(), b'0');

-- 床位状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS床位状态', 'his_bed_status', 0, 'HIS床位状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '空床', '1', 'his_bed_status', 0, 'success', '', '空床可用', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '占用', '2', 'his_bed_status', 0, 'danger', '', '床位被占用', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '预约', '3', 'his_bed_status', 0, 'warning', '', '床位已预约', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '清洁', '4', 'his_bed_status', 0, 'info', '', '正在清洁', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '维修', '5', 'his_bed_status', 0, 'default', '', '维修中', 'admin', NOW(), 'admin', NOW(), b'0');

-- 床位类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS床位类型', 'his_bed_type', 0, 'HIS床位类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '普通', '1', 'his_bed_type', 0, 'default', '', '普通床位', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '抢救', '2', 'his_bed_type', 0, 'danger', '', '抢救床位', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, 'ICU', '3', 'his_bed_type', 0, 'warning', '', 'ICU床位', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '隔离', '4', 'his_bed_type', 0, 'info', '', '隔离床位', 'admin', NOW(), 'admin', NOW(), b'0');

-- 床位等级字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS床位等级', 'his_bed_class', 0, 'HIS床位等级字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '普通', '1', 'his_bed_class', 0, 'default', '', '普通床位', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '双人', '2', 'his_bed_class', 0, 'info', '', '双人房间', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '单人', '3', 'his_bed_class', 0, 'primary', '', '单人房间', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, 'VIP', '4', 'his_bed_class', 0, 'warning', '', 'VIP病房', 'admin', NOW(), 'admin', NOW(), b'0');

-- 出院方式字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS出院方式', 'his_discharge_way', 0, 'HIS出院方式字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '医嘱出院', '1', 'his_discharge_way', 0, 'success', '', '医嘱出院', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '自动出院', '2', 'his_discharge_way', 0, 'warning', '', '自动出院', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '转院', '3', 'his_discharge_way', 0, 'info', '', '转院', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '死亡', '4', 'his_discharge_way', 0, 'danger', '', '死亡', 'admin', NOW(), 'admin', NOW(), b'0');

-- 评估类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS评估类型', 'his_assess_type', 0, 'HIS评估类型字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '入院评估', '1', 'his_assess_type', 0, 'primary', '', '入院评估', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '跌倒评估', '2', 'his_assess_type', 0, 'warning', '', '跌倒风险评估', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '压疮评估', '3', 'his_assess_type', 0, 'danger', '', '压疮风险评估', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '疼痛评估', '4', 'his_assess_type', 0, 'info', '', '疼痛评估', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '营养评估', '5', 'his_assess_type', 0, 'success', '', '营养评估', 'admin', NOW(), 'admin', NOW(), b'0');

-- 风险等级字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS风险等级', 'his_risk_level', 0, 'HIS风险等级字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '低', '1', 'his_risk_level', 0, 'success', '', '低风险', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '中', '2', 'his_risk_level', 0, 'warning', '', '中风险', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '高', '3', 'his_risk_level', 0, 'danger', '', '高风险', 'admin', NOW(), 'admin', NOW(), b'0');

-- 预交金状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS预交金状态', 'his_prepayment_status', 0, 'HIS预交金状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '正常', '1', 'his_prepayment_status', 0, 'success', '', '正常', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '已退', '2', 'his_prepayment_status', 0, 'danger', '', '已退', 'admin', NOW(), 'admin', NOW(), b'0');

-- 预住院状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS预住院状态', 'his_pre_admission_status', 0, 'HIS预住院状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '待入院', '1', 'his_pre_admission_status', 0, 'warning', '', '待入院', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '已入院', '2', 'his_pre_admission_status', 0, 'success', '', '已入院', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '已取消', '3', 'his_pre_admission_status', 0, 'danger', '', '已取消', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 预住院表结构
-- ============================================
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

-- ============================================
-- 完成提示
-- ============================================
-- 入院管理模块菜单权限配置完成
-- 包含:
-- 1. 入院登记管理
-- 2. 床位管理
-- 3. 病区管理
-- 4. 预住院管理
-- 5. 入院评估管理
-- 6. 预交金管理
-- 7. 相关字典数据
-- 8. 预住院表结构
-- ============================================