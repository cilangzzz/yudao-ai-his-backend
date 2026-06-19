-- ====================================================================
-- HIS 菜单重复和路径修复脚本
-- 使用层级路径定位菜单，不使用硬编码 ID
-- 执行时间: 2026-06-19
-- ====================================================================

USE `yudao-ai-his`;

-- ====================================================================
-- 问题分析
-- ====================================================================
-- 1. 重复菜单（完全相同的菜单定义）
--    - 预交金管理: 在「住院管理」下有两条相同记录
--    - 床位管理: 在「住院管理」下有两条相同记录
--    - 入院管理/入院登记: 在「住院管理」下有两条相同记录
--
-- 2. 重复名称但不同模块（需要保留，但区分名称）
--    - 病区管理: 在「基础数据」和「住院管理」各有一个
--
-- 3. 医嘱执行模块问题
--    - 类型是「菜单」但没有组件路径，应该是「目录」
--    - 子菜单组件路径错误

-- ====================================================================
-- 修复操作
-- ====================================================================

-- --------------------------------------------------------------------
-- 1. 删除完全重复的菜单
-- --------------------------------------------------------------------

-- 删除重复的「预交金管理」（保留第一条）
DELETE FROM `system_menu`
WHERE `id` = (
    SELECT id FROM (
        SELECT m2.id
        FROM system_menu m1
        JOIN system_menu m2 ON m2.parent_id = m1.id
            AND m2.name = '预交金管理'
            AND m2.path = 'prepayment'
            AND m2.deleted = 0
        WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
        ORDER BY m2.id DESC
        LIMIT 1
    ) tmp
);

-- 删除重复的「床位管理」（保留第一条）
DELETE FROM `system_menu`
WHERE `id` = (
    SELECT id FROM (
        SELECT m2.id
        FROM system_menu m1
        JOIN system_menu m2 ON m2.parent_id = m1.id
            AND m2.name = '床位管理'
            AND m2.path = 'bed'
            AND m2.deleted = 0
        WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
        ORDER BY m2.id DESC
        LIMIT 1
    ) tmp
);

-- 删除重复的「入院登记」（与入院管理相同，保留入院管理）
DELETE FROM `system_menu`
WHERE `id` = (
    SELECT id FROM (
        SELECT m2.id
        FROM system_menu m1
        JOIN system_menu m2 ON m2.parent_id = m1.id
            AND m2.name = '入院登记'
            AND m2.deleted = 0
        WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0
    ) tmp
);

-- 删除医嘱执行下错误命名的按钮
DELETE FROM `system_menu`
WHERE `name` = '医嘱执行' AND `type` = 3 AND `deleted` = 0;

-- 删除孤立按钮（父菜单已删除）
DELETE FROM `system_menu`
WHERE `name` IN ('预交金查询', '预交金退款', '预交金删除')
  AND `type` = 3
  AND `deleted` = 0
  AND `parent_id` = 6850;  -- 旧的预交金管理ID

-- --------------------------------------------------------------------
-- 2. 修复病区管理重名问题
--    路径: HIS医院信息系统/住院管理/病区设置
-- --------------------------------------------------------------------

UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = 'HIS医院信息系统'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '住院管理'
JOIN system_menu m4 ON m4.parent_id = m3.id AND m4.name = '病区管理' AND m4.deleted = 0
SET m4.name = '病区设置';

-- --------------------------------------------------------------------
-- 3. 修复医嘱执行类型（从菜单改为目录）
--    路径: HIS医院信息系统/医嘱执行
-- --------------------------------------------------------------------

UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行' AND m2.deleted = 0
SET m2.type = 1  -- 1=目录
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0;

-- --------------------------------------------------------------------
-- 4. 修复医嘱执行子菜单的组件路径
--    路径: HIS医院信息系统/医嘱执行/*
-- --------------------------------------------------------------------

-- 医嘱管理
UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '医嘱管理' AND m3.deleted = 0
SET m3.component = 'his/order-exec/order/index'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 给药记录
UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '给药记录' AND m3.deleted = 0
SET m3.component = 'his/order-exec/medication-admin/index'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 生命体征
UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '生命体征' AND m3.deleted = 0
SET m3.component = 'his/order-exec/vital-sign/index'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 护理记录
UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '护理记录' AND m3.deleted = 0
SET m3.component = 'his/order-exec/nursing-record/index'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 护理评估
UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '护理评估' AND m3.deleted = 0
SET m3.component = 'his/order-exec/nursing-assessment/index'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- ====================================================================
-- 验证结果
-- ====================================================================

-- 检查是否还有重复名称
SELECT
    name AS '菜单名称',
    COUNT(*) AS '数量',
    GROUP_CONCAT(id ORDER BY id) AS 'ID列表'
FROM `system_menu`
WHERE `deleted` = 0 AND `status` = 0
GROUP BY `name`
HAVING COUNT(*) > 1;

-- 查看修复后的医嘱执行模块
SELECT
    m2.id AS 'ID',
    m2.name AS '名称',
    CASE m2.type WHEN 1 THEN '目录' WHEN 2 THEN '菜单' WHEN 3 THEN '按钮' END AS '类型',
    m2.path AS '路径',
    m2.component AS '组件'
FROM system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0

UNION ALL

SELECT
    m3.id AS 'ID',
    m3.name AS '名称',
    CASE m3.type WHEN 1 THEN '目录' WHEN 2 THEN '菜单' WHEN 3 THEN '按钮' END AS '类型',
    m3.path AS '路径',
    m3.component AS '组件'
FROM system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.deleted = 0
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0;

-- ====================================================================
-- 完成
-- ====================================================================
