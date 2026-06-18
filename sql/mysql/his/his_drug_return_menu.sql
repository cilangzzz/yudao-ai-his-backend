-- ============================================
-- HIS 退药管理 - 菜单权限初始化脚本
-- 版本: V1.0
-- 日期: 2026-06-18
-- ============================================

-- 获取药品管理菜单ID
SET @pharmacy_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '药品管理' AND `parent_id` = (SELECT id FROM `system_menu` WHERE `name` = 'HIS医院信息系统' AND `deleted` = b'0') AND `deleted` = b'0');

-- 添加退药管理菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('退药管理', '', 2, 8, @pharmacy_menu_id, 'drug-return', 'ep:return', 'his/pharmacy/drug-return/index', 'OpDrugReturn', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- 设置退药管理菜单 ID
SET @drug_return_menu_id = LAST_INSERT_ID();

-- 添加按钮权限
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

-- ============================================
-- 为角色分配权限
-- ============================================

-- 获取药师角色ID
SET @pharmacist_role_id = (SELECT id FROM `system_role` WHERE `code` = 'his_pharmacist' AND `deleted` = b'0');

-- 获取医生角色ID
SET @doctor_role_id = (SELECT id FROM `system_role` WHERE `code` = 'his_doctor' AND `deleted` = b'0');

-- 获取收费员角色ID
SET @cashier_role_id = (SELECT id FROM `system_role` WHERE `code` = 'his_cashier' AND `deleted` = b'0');

-- 为药师角色分配退药管理权限
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(@pharmacist_role_id, @drug_return_menu_id, 'admin', NOW(), 'admin', NOW(), b'0');

-- 为药师角色分配退药管理按钮权限
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT @pharmacist_role_id, id, 'admin', NOW(), 'admin', NOW(), b'0'
FROM `system_menu` WHERE `parent_id` = @drug_return_menu_id AND `deleted` = b'0';

-- 为医生角色分配退药申请查询和创建权限（医生可以发起退药申请）
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(@doctor_role_id, @drug_return_menu_id, 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT @doctor_role_id, id, 'admin', NOW(), 'admin', NOW(), b'0'
FROM `system_menu` WHERE `parent_id` = @drug_return_menu_id AND `permission` IN ('his:drug-return:query', 'his:drug-return:create') AND `deleted` = b'0';

-- 为收费员角色分配退药退款权限
INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(@cashier_role_id, @drug_return_menu_id, 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_role_menu` (`role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT @cashier_role_id, id, 'admin', NOW(), 'admin', NOW(), b'0'
FROM `system_menu` WHERE `parent_id` = @drug_return_menu_id AND `permission` IN ('his:drug-return:query', 'his:drug-return:refund') AND `deleted` = b'0';

-- ============================================
-- 初始化完成
-- ============================================