-- ============================================
-- HIS 检查管理模块菜单和权限初始化
-- ============================================

-- 添加检查管理子菜单到门诊管理下
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('检查申请', '', 2, 8, (SELECT id FROM (SELECT id FROM `system_menu` WHERE `name` = '门诊管理' AND `deleted` = b'0') t), 'exam-request', 'ep:document-checked', 'his/outpatient/exam-request/index', 'HisExamRequest', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查项目', '', 2, 9, (SELECT id FROM (SELECT id FROM `system_menu` WHERE `name` = '门诊管理' AND `deleted` = b'0') t), 'exam-item', 'ep:list', 'his/outpatient/exam-item/index', 'HisExamItem', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- 获取检查申请菜单 ID
SET @exam_request_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '检查申请' AND `deleted` = b'0' ORDER BY id DESC LIMIT 1);
-- 获取检查项目菜单 ID
SET @exam_item_menu_id = (SELECT id FROM `system_menu` WHERE `name` = '检查项目' AND `deleted` = b'0' ORDER BY id DESC LIMIT 1);

-- 检查申请按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('检查申请查询', 'his:exam-request:query', 3, 1, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请新增', 'his:exam-request:create', 3, 2, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请修改', 'his:exam-request:update', 3, 3, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请删除', 'his:exam-request:delete', 3, 4, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请预约', 'his:exam-request:appoint', 3, 5, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请签到', 'his:exam-request:check-in', 3, 6, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请执行', 'his:exam-request:execute', 3, 7, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查申请取消', 'his:exam-request:cancel', 3, 8, @exam_request_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- 检查项目按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('检查项目查询', 'his:exam-item:query', 3, 1, @exam_item_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查项目新增', 'his:exam-item:create', 3, 2, @exam_item_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查项目修改', 'his:exam-item:update', 3, 3, @exam_item_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0'),
('检查项目删除', 'his:exam-item:delete', 3, 4, @exam_item_menu_id, '', '', '', '', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- ============================================
-- 初始化完成
-- ============================================