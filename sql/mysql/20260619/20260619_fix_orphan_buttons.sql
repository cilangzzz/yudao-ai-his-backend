-- ====================================================================
-- HIS 孤立按钮修复脚本
-- 使用层级路径定位菜单，不使用硬编码 ID
-- 执行时间: 2026-06-19
-- ====================================================================

USE `yudao-ai-his`;

-- ====================================================================
-- 问题分析
-- ====================================================================
-- 发现孤立按钮，其 parent_id 指向已删除的菜单：
--
-- 1. 入院登记相关按钮: 父菜单「入院登记」已删除
--    - 这些按钮应该归属到「入院管理」菜单下
--    - 路径: HIS医院信息系统 -> 住院管理 -> 入院管理
--
-- 2. 预交金缴纳按钮: 父菜单「预交金管理(重复)」已删除
--    - 应该归属到「预交金管理」菜单下
--    - 路径: HIS医院信息系统 -> 住院管理 -> 预交金管理

-- ====================================================================
-- 修复操作
-- ====================================================================

-- --------------------------------------------------------------------
-- 1. 将入院登记相关按钮移到「入院管理」菜单下
--    路径: HIS医院信息系统/住院管理/入院管理
-- --------------------------------------------------------------------

UPDATE `system_menu` btn
JOIN system_menu m1 ON m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理' AND m2.deleted = 0
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '入院管理' AND m3.deleted = 0
SET btn.parent_id = m3.id
WHERE btn.name IN ('入院登记查询', '入院登记创建', '入院登记更新', '入院登记删除', '出院办理', '转科办理')
  AND btn.type = 3
  AND btn.deleted = 0;

-- --------------------------------------------------------------------
-- 2. 将预交金缴纳按钮移到「预交金管理」菜单下
--    路径: HIS医院信息系统/住院管理/预交金管理
-- --------------------------------------------------------------------

UPDATE `system_menu` btn
JOIN system_menu m1 ON m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理' AND m2.deleted = 0
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '预交金管理' AND m3.deleted = 0
SET btn.parent_id = m3.id
WHERE btn.name = '预交金缴纳'
  AND btn.type = 3
  AND btn.deleted = 0;

-- ====================================================================
-- 验证结果
-- ====================================================================

-- 检查是否还有孤立按钮
SELECT
    child.id AS '按钮ID',
    child.parent_id AS '父菜单ID',
    child.name AS '按钮名称',
    child.permission AS '权限标识'
FROM system_menu child
LEFT JOIN system_menu parent ON child.parent_id = parent.id AND parent.deleted = 0
WHERE child.type = 3
  AND child.deleted = 0
  AND (parent.id IS NULL)
ORDER BY child.id;

-- 查看修复后的按钮层级
SELECT
    m1.name AS '一级菜单',
    m2.name AS '二级菜单',
    m3.name AS '三级菜单',
    btn.name AS '按钮名称',
    btn.permission AS '权限标识'
FROM system_menu btn
JOIN system_menu m3 ON btn.parent_id = m3.id AND m3.deleted = 0
JOIN system_menu m2 ON m3.parent_id = m2.id AND m2.deleted = 0
JOIN system_menu m1 ON m2.parent_id = m1.id AND m1.deleted = 0
WHERE btn.name IN ('入院登记查询', '入院登记创建', '入院登记更新', '入院登记删除', '出院办理', '转科办理', '预交金缴纳')
  AND btn.deleted = 0;

-- ====================================================================
-- 完成
-- ====================================================================