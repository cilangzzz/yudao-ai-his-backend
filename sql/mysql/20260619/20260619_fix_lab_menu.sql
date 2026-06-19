-- ====================================================================
-- HIS 检验管理模块菜单修复脚本
-- 使用层级路径定位菜单，不使用硬编码 ID
-- 执行时间: 2026-06-19
-- ====================================================================

USE `yudao-ai-his`;

-- ====================================================================
-- 问题分析
-- ====================================================================
-- 检验管理模块的子菜单配置错误：
-- - 检验申请管理: 类型是「目录」但应该是「菜单」，缺少组件路径
-- - 检验项目字典: 类型是「目录」但应该是「菜单」，缺少组件路径
-- - 危急值管理: 类型是「目录」但应该是「菜单」，缺少组件路径
--
-- 前端已创建组件：
-- - views/his/lab/lab-request/index
-- - views/his/lab/lab-item/index
-- - views/his/lab/critical-value/index
--
-- 但后端菜单配置指向空目录，导致路由无法匹配组件，出现 404

-- ====================================================================
-- 修复操作
-- ====================================================================

-- --------------------------------------------------------------------
-- 1. 修复检验申请
--    路径: HIS医院信息系统/检验管理/检验申请
-- --------------------------------------------------------------------

UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '检验申请管理' AND m3.deleted = 0
SET
    m3.type = 2,  -- 2=菜单
    m3.name = '检验申请',
    m3.component = 'his/lab/lab-request/index'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0;

-- --------------------------------------------------------------------
-- 2. 修复检验项目
--    路径: HIS医院信息系统/检验管理/检验项目
-- --------------------------------------------------------------------

UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '检验项目字典' AND m3.deleted = 0
SET
    m3.type = 2,  -- 2=菜单
    m3.name = '检验项目',
    m3.component = 'his/lab/lab-item/index'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0;

-- --------------------------------------------------------------------
-- 3. 修复危急值管理
--    路径: HIS医院信息系统/检验管理/危急值管理
-- --------------------------------------------------------------------

UPDATE `system_menu` m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '危急值管理' AND m3.deleted = 0
SET
    m3.type = 2,  -- 2=菜单
    m3.component = 'his/lab/critical-value/index'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0;

-- ====================================================================
-- 验证结果
-- ====================================================================

-- 查看修复后的检验管理模块
SELECT
    m2.id AS 'ID',
    m2.name AS '名称',
    CASE m2.type WHEN 1 THEN '目录' WHEN 2 THEN '菜单' WHEN 3 THEN '按钮' END AS '类型',
    m2.path AS '路径',
    m2.component AS '组件'
FROM system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理' AND m2.deleted = 0
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0

UNION ALL

SELECT
    m3.id AS 'ID',
    m3.name AS '名称',
    CASE m3.type WHEN 1 THEN '目录' WHEN 2 THEN '菜单' WHEN 3 THEN '按钮' END AS '类型',
    m3.path AS '路径',
    m3.component AS '组件'
FROM system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.deleted = 0 AND m3.type IN (1, 2)
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0 AND m1.deleted = 0;

-- ====================================================================
-- 完成
-- ====================================================================