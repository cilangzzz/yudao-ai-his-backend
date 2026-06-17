-- ============================================
-- HIS 支付管理菜单 SQL
-- 版本: V1.0
-- 日期: 2026-06-18
-- 说明: 支付管理、退费管理、预交金管理菜单
-- ============================================

-- ============================================
-- 门诊收费子菜单（放在门诊管理下）
-- ============================================

-- 获取门诊管理菜单ID
SET @outpatient_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '门诊管理' AND `path` = '/his/outpatient' AND `deleted` = b'0');

-- 支付管理菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('支付管理', '', 2, 8, @outpatient_menu_id, 'payment', 'ep:money', 'his/outpatient/payment/index', 'OpPayment', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

SET @payment_menu_id = LAST_INSERT_ID();

-- 支付管理按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('支付查询', 'his:payment:query', 3, 1, @payment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('支付创建', 'his:payment:create', 3, 2, @payment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('支付更新', 'his:payment:update', 3, 3, @payment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('支付删除', 'his:payment:delete', 3, 4, @payment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- 退费管理菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('退费管理', '', 2, 9, @outpatient_menu_id, 'refund', 'ep:refund', 'his/outpatient/refund/index', 'OpRefund', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

SET @refund_menu_id = LAST_INSERT_ID();

-- 退费管理按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('退费查询', 'his:refund:query', 3, 1, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('退费申请', 'his:refund:create', 3, 2, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('退费审核', 'his:refund:audit', 3, 3, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('退费完成', 'his:refund:complete', 3, 4, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('退费删除', 'his:refund:delete', 3, 5, @refund_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 住院管理子菜单（放在住院管理下）
-- ============================================

-- 获取住院管理菜单ID
SET @inpatient_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '住院管理' AND `path` = '/his/inpatient' AND `deleted` = b'0');

-- 预交金管理菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('预交金管理', '', 2, 6, @inpatient_menu_id, 'prepayment', 'ep:wallet', 'his/inpatient/prepayment/index', 'HisPrepayment', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

SET @prepayment_menu_id = LAST_INSERT_ID();

-- 预交金管理按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('预交金查询', 'his:prepayment:query', 3, 1, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金缴纳', 'his:prepayment:create', 3, 2, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金退款', 'his:prepayment:refund', 3, 3, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('预交金删除', 'his:prepayment:delete', 3, 4, @prepayment_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 初始化字典数据 - 支付方式
-- ============================================

INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS支付方式', 'his_pay_type', 0, 'HIS支付方式字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '现金', '1', 'his_pay_type', 0, 'default', '', '现金支付', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '微信', '2', 'his_pay_type', 0, 'success', '', '微信支付', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '支付宝', '3', 'his_pay_type', 0, 'primary', '', '支付宝支付', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '医保', '4', 'his_pay_type', 0, 'warning', '', '医保支付', 'admin', NOW(), 'admin', NOW(), b'0'),
(5, '银行卡', '5', 'his_pay_type', 0, 'info', '', '银行卡支付', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 初始化字典数据 - 支付状态
-- ============================================

INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS支付状态', 'his_payment_status', 0, 'HIS支付状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '成功', '1', 'his_payment_status', 0, 'success', '', '支付成功', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '失败', '2', 'his_payment_status', 0, 'danger', '', '支付失败', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '已退费', '3', 'his_payment_status', 0, 'warning', '', '已退费', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 初始化字典数据 - 退费状态
-- ============================================

INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES ('HIS退费状态', 'his_refund_status', 0, 'HIS退费状态字典', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
(1, '待审核', '1', 'his_refund_status', 0, 'warning', '', '退费待审核', 'admin', NOW(), 'admin', NOW(), b'0'),
(2, '已通过', '2', 'his_refund_status', 0, 'success', '', '退费已通过', 'admin', NOW(), 'admin', NOW(), b'0'),
(3, '已拒绝', '3', 'his_refund_status', 0, 'danger', '', '退费已拒绝', 'admin', NOW(), 'admin', NOW(), b'0'),
(4, '已完成', '4', 'his_refund_status', 0, 'info', '', '退费已完成', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 初始化完成
-- ============================================