-- ============================================
-- HIS 医院信息系统 - 完整数据库初始化脚本
-- 版本: V1.0
-- 日期: 2026-06-17
-- 说明: 包含芋道框架基础表 + HIS 核心业务表
-- 数据库: MySQL 8.x
-- ============================================

-- ============================================
-- 重要说明:
-- 1. 本脚本整合了 ruoyi-vue-pro 框架基础表和 HIS 业务表
-- 2. 执行顺序: 框架基础表 -> Quartz定时任务表 -> HIS业务表
-- 3. 请按顺序执行: his-database-init-full.sql (本文件)
--    或分别执行: sql/mysql/ruoyi-vue-pro.sql + sql/mysql/quartz.sql + sql/his-init.sql
-- ============================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================
-- 使用说明
-- ============================================
-- 如需完整初始化，请执行:
--   source sql/his-database-init-full.sql
--
-- 如需单独执行，请按顺序执行:
--   1. source sql/mysql/ruoyi-vue-pro.sql    (框架基础表，含 system/infra/bpm/pay/member/mall/crm/erp/ai/iot/mp/report/im/mes/wms 模块)
--   2. source sql/mysql/quartz.sql           (Quartz 定时任务表)
--   3. source sql/his-init.sql               (HIS 业务表)
--
-- 注意: HIS 模块依赖框架的 system_dict_type 和 system_dict_data 表来存储字典数据
-- ============================================

-- ============================================
-- HIS 扩展菜单数据
-- ============================================

-- HIS 模块菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('HIS医院信息系统', '', 1, 100, 0, '/his', 'ep:building', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 设置 HIS 菜单 ID
SET @his_menu_id = LAST_INSERT_ID();

-- HIS 子菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('门诊管理', '', 1, 1, @his_menu_id, '/his/outpatient', 'ep:calendar', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('住院管理', '', 1, 2, @his_menu_id, '/his/inpatient', 'ep:bed', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('药品管理', '', 1, 3, @his_menu_id, '/his/pharmacy', 'ep:medicine-box', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('基础数据', '', 1, 4, @his_menu_id, '/his/basic', 'ep:operation', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 设置门诊管理菜单 ID
SET @outpatient_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '门诊管理' AND `parent_id` = @his_menu_id AND `deleted` = b'0');
-- 设置住院管理菜单 ID
SET @inpatient_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '住院管理' AND `parent_id` = @his_menu_id AND `deleted` = b'0');
-- 设置药品管理菜单 ID
SET @pharmacy_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '药品管理' AND `parent_id` = @his_menu_id AND `deleted` = b'0');
-- 设置基础数据菜单 ID
SET @basic_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '基础数据' AND `parent_id` = @his_menu_id AND `deleted` = b'0');

-- ============================================
-- 门诊管理子菜单 (M01)
-- ============================================
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('患者管理', '', 2, 1, @outpatient_menu_id, 'patient', 'ep:user', 'his/outpatient/patient/index', 'HisPatient', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预约挂号', '', 2, 2, @outpatient_menu_id, 'appointment', 'ep:calendar-check', 'his/outpatient/appointment/index', 'OpAppointment', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('挂号收费', '', 2, 3, @outpatient_menu_id, 'register', 'ep:money', 'his/outpatient/register/index', 'OpRegister', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('医生排班', '', 2, 4, @outpatient_menu_id, 'schedule', 'ep:clock', 'his/outpatient/schedule/index', 'OpSchedule', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('处方管理', '', 2, 5, @outpatient_menu_id, 'prescription', 'ep:document', 'his/outpatient/prescription/index', 'OpPrescription', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('门诊收费', '', 2, 6, @outpatient_menu_id, 'fee', 'ep:wallet', 'his/outpatient/fee/index', 'OpFee', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('门诊药房', '', 2, 7, @outpatient_menu_id, 'dispense', 'ep:medicine', 'his/outpatient/dispense/index', 'OpDispense', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 住院管理子菜单 (M02)
-- ============================================
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('入院管理', '', 2, 1, @inpatient_menu_id, 'admission', 'ep:plus', 'his/inpatient/admission/index', 'HisAdmission', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('床位管理', '', 2, 2, @inpatient_menu_id, 'bed', 'ep:bed', 'his/inpatient/bed/index', 'HisBed', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱管理', '', 2, 3, @inpatient_menu_id, 'order', 'ep:edit-pen', 'his/inpatient/order/index', 'HisOrder', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录', '', 2, 4, @inpatient_menu_id, 'nursing', 'ep:heart', 'his/inpatient/nursing/index', 'HisNursing', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('出院管理', '', 2, 5, @inpatient_menu_id, 'discharge', 'ep:minus', 'his/inpatient/discharge/index', 'HisDischarge', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 药品管理子菜单 (M06)
-- ============================================
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('药品目录', '', 2, 1, @pharmacy_menu_id, 'drug', 'ep:goods', 'his/pharmacy/drug/index', 'HisDrug', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('药品库存', '', 2, 2, @pharmacy_menu_id, 'stock', 'ep:box', 'his/pharmacy/stock/index', 'HisDrugStock', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('入库管理', '', 2, 3, @pharmacy_menu_id, 'inbound', 'ep:download', 'his/pharmacy/inbound/index', 'HisDrugInbound', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('出库管理', '', 2, 4, @pharmacy_menu_id, 'outbound', 'ep:upload', 'his/pharmacy/outbound/index', 'HisDrugOutbound', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('盘点管理', '', 2, 5, @pharmacy_menu_id, 'inventory', 'ep:document-checked', 'his/pharmacy/inventory/index', 'HisDrugInventory', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('供应商管理', '', 2, 6, @pharmacy_menu_id, 'supplier', 'ep:office-building', 'his/pharmacy/supplier/index', 'HisSupplier', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('采购计划', '', 2, 7, @pharmacy_menu_id, 'purchase', 'ep:shopping-cart', 'his/pharmacy/purchase/index', 'HisDrugPurchase', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 基础数据子菜单
-- ============================================
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('科室管理', '', 2, 1, @basic_menu_id, 'department', 'ep:office-building', 'his/basic/department/index', 'HisDepartment', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('医护人员', '', 2, 2, @basic_menu_id, 'staff', 'ep:user-filled', 'his/basic/staff/index', 'HisStaff', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('收费项目', '', 2, 3, @basic_menu_id, 'charge-item', 'ep:price-tag', 'his/basic/chargeItem/index', 'HisChargeItem', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('病区管理', '', 2, 4, @basic_menu_id, 'ward', 'ep:build', 'his/basic/ward/index', 'HisWard', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- HIS 角色初始化
-- ============================================

-- 医生角色
INSERT INTO `system_role` (`name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `deleted_time`) VALUES
('医生', 'his_doctor', 10, 1, '', 0, 2, 'HIS医生角色', 'admin', NOW(), 'admin', NOW(), b'0', NULL);

-- 护士角色
INSERT INTO `system_role` (`name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `deleted_time`) VALUES
('护士', 'his_nurse', 11, 1, '', 0, 2, 'HIS护士角色', 'admin', NOW(), 'admin', NOW(), b'0', NULL);

-- 药师角色
INSERT INTO `system_role` (`name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `deleted_time`) VALUES
('药师', 'his_pharmacist', 12, 1, '', 0, 2, 'HIS药师角色', 'admin', NOW(), 'admin', NOW(), b'0', NULL);

-- 收费员角色
INSERT INTO `system_role` (`name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `deleted_time`) VALUES
('收费员', 'his_cashier', 13, 1, '', 0, 2, 'HIS收费员角色', 'admin', NOW(), 'admin', NOW(), b'0', NULL);

-- ============================================
-- 初始化完成提示
-- ============================================
-- 执行完成后，请继续执行以下脚本（如果尚未执行）:
--
-- 1. 框架基础表: sql/mysql/ruoyi-vue-pro.sql
--    包含: system/infra/bpm/pay/member/mall/crm/erp/ai/iot/mp/report/im/mes/wms 等模块表
--
-- 2. Quartz 定时任务表: sql/mysql/quartz.sql
--    包含: QRTZ_* 系列表（用于定时任务调度）
--
-- 3. HIS 业务表: sql/his-init.sql
--    包含: HIS 核心业务表结构及字典数据
--
-- 完整执行顺序示例:
--   CREATE DATABASE IF NOT EXISTS `ruoyi-vue-pro` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
--   USE `ruoyi-vue-pro`;
--   SOURCE sql/mysql/ruoyi-vue-pro.sql;
--   SOURCE sql/mysql/quartz.sql;
--   SOURCE sql/his-init.sql;
--   SOURCE sql/his-database-init-full.sql;  -- 菜单和角色扩展数据（本文件）
--
-- ============================================

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 初始化完成
-- ============================================