-- ====================================================================
-- HIS 菜单路径修复脚本
-- 修复一级子菜单的路径格式（从绝对路径改为相对路径）
-- 执行时间: 2026-06-18
-- ====================================================================

USE `yudao-ai-his`;

-- ====================================================================
-- 问题说明
-- ====================================================================
-- HIS 一级子菜单（门诊管理、住院管理等）的路径是绝对路径
-- 例如: /his/outpatient, /his/inpatient
-- 这会导致前端路由拼接错误，变成: /his//his/outpatient/patient
--
-- 正确做法:
-- - HIS 顶级菜单: /his (绝对路径)
-- - 一级子菜单: outpatient, inpatient (相对路径)
-- - 二级子菜单: patient, admission (相对路径)
-- 最终路径: /his/outpatient/patient

-- ====================================================================
-- 修复操作
-- ====================================================================

-- 1. 门诊管理: /his/outpatient -> outpatient
UPDATE `system_menu`
SET `path` = 'outpatient'
WHERE `id` = 6736;

-- 2. 住院管理: /his/inpatient -> inpatient
UPDATE `system_menu`
SET `path` = 'inpatient'
WHERE `id` = 6737;

-- 3. 药品管理: /his/pharmacy -> pharmacy
UPDATE `system_menu`
SET `path` = 'pharmacy'
WHERE `id` = 6738;

-- 4. 基础数据: /his/basic -> basic
UPDATE `system_menu`
SET `path` = 'basic'
WHERE `id` = 6739;

-- 5. 检验管理: /his/lab -> lab
UPDATE `system_menu`
SET `path` = 'lab'
WHERE `id` = 6817;

-- 6. 医嘱执行: /his/order-exec -> order-exec
UPDATE `system_menu`
SET `path` = 'order-exec'
WHERE `id` = 6855;

-- ====================================================================
-- 验证结果
-- ====================================================================

-- 查看 HIS 一级子菜单路径
SELECT
    id AS 'ID',
    name AS '菜单名称',
    path AS '路径'
FROM `system_menu`
WHERE `parent_id` = 6735
  AND `deleted` = 0
ORDER BY `id`;

-- ====================================================================
-- 完成
-- ====================================================================