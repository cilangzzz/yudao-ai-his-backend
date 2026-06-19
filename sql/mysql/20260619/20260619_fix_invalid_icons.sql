-- ============================================
-- HIS 医院信息系统 - 修复无效菜单图标
-- 使用层级路径定位菜单，不使用硬编码 ID
-- 执行时间: 2026-06-19
-- ============================================

USE `yudao-ai-his`;

-- 说明:
-- 本 SQL 修复使用了不存在图标标识的菜单
-- 将无效的 Element Plus 图标替换为有效图标或使用其他图标集

-- ============================================
-- 修复记录
-- ============================================

-- 药品管理: ep:medicine-box (无效) -> mdi:pill
-- 路径: HIS医院信息系统/药品管理
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理' AND m2.deleted = 0
SET m2.icon = 'mdi:pill'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
  AND m2.icon = 'ep:medicine-box';

-- 护理记录: ep:heart (无效) -> lucide:heart
-- 路径: HIS医院信息系统/住院管理/护理记录
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '护理记录' AND m3.deleted = 0
SET m3.icon = 'lucide:heart'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
  AND m3.icon = 'ep:heart';

-- 生命体征: ep:heart (无效) -> lucide:activity
-- 路径: HIS医院信息系统/医嘱执行/生命体征
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '生命体征' AND m3.deleted = 0
SET m3.icon = 'lucide:activity'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
  AND m3.icon = 'ep:heart';

-- 退药管理: ep:return (无效) -> lucide:undo-2
-- 路径: HIS医院信息系统/药品管理/退药管理
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '退药管理' AND m3.deleted = 0
SET m3.icon = 'lucide:undo-2'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
  AND m3.icon = 'ep:return';

-- ============================================
-- 验证修复结果
-- ============================================

-- 查看修复后的图标配置
SELECT m2.id, m2.name, m2.icon
FROM system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0
  AND m2.name IN ('药品管理', '退药管理')

UNION ALL

SELECT m3.id, m3.name, m3.icon
FROM system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '护理记录';

-- ============================================
-- END
-- ============================================