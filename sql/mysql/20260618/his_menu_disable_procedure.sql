-- ====================================================================
-- HIS 系统菜单禁用脚本 (递归版本)
-- 禁用非 HIS 核心业务模块的菜单
-- 执行时间: 2026-06-18
-- 说明: 关闭 CRM、ERP、MES、WMS、IoT、商城系统等模块菜单
-- ====================================================================

-- 使用数据库
USE `yudao-ai-his`;

-- ====================================================================
-- 创建临时存储过程用于递归禁用菜单
-- ====================================================================

DROP PROCEDURE IF EXISTS `sp_disable_menu_tree`;

DELIMITER $$

CREATE PROCEDURE `sp_disable_menu_tree`(IN p_root_id BIGINT)
BEGIN
    -- 声明变量
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_id BIGINT;
    DECLARE v_count INT;

    -- 创建临时表存储需要禁用的菜单ID
    DROP TEMPORARY TABLE IF EXISTS tmp_menu_ids;
    CREATE TEMPORARY TABLE tmp_menu_ids (
        id BIGINT PRIMARY KEY
    );

    -- 插入根菜单ID
    INSERT INTO tmp_menu_ids VALUES (p_root_id);

    -- 循环查找所有子菜单
    SET v_count = 0;
    WHILE v_count < 100 DO  -- 最多递归100层
        INSERT IGNORE INTO tmp_menu_ids
        SELECT id FROM system_menu
        WHERE parent_id IN (SELECT id FROM tmp_menu_ids)
          AND deleted = 0;

        -- 检查是否有新记录
        IF ROW_COUNT() = 0 THEN
            SET v_count = 100;  -- 退出循环
        END IF;

        SET v_count = v_count + 1;
    END WHILE;

    -- 批量禁用菜单
    UPDATE system_menu
    SET status = 1
    WHERE id IN (SELECT id FROM tmp_menu_ids);

    -- 返回影响的行数
    SELECT COUNT(*) AS disabled_count FROM tmp_menu_ids;

    -- 清理临时表
    DROP TEMPORARY TABLE IF EXISTS tmp_menu_ids;
END$$

DELIMITER ;

-- ====================================================================
-- 执行禁用操作
-- ====================================================================

SELECT '========================================' AS '';
SELECT 'HIS 系统菜单禁用脚本' AS '';
SELECT '========================================' AS '';

-- 1. 禁用 CRM 系统 (id=2397)
SELECT '禁用 CRM 系统...' AS '';
CALL sp_disable_menu_tree(2397);

-- 2. 禁用 ERP 系统 (id=2563)
SELECT '禁用 ERP 系统...' AS '';
CALL sp_disable_menu_tree(2563);

-- 3. 禁用 MES 系统 (id=5100)
SELECT '禁用 MES 系统...' AS '';
CALL sp_disable_menu_tree(5100);

-- 4. 禁用 WMS 系统 (id=6400)
SELECT '禁用 WMS 系统...' AS '';
CALL sp_disable_menu_tree(6400);

-- 5. 禁用 IoT 物联网 (id=4000)
SELECT '禁用 IoT 物联网...' AS '';
CALL sp_disable_menu_tree(4000);

-- 6. 禁用商城系统 (id=2362)
SELECT '禁用商城系统...' AS '';
CALL sp_disable_menu_tree(2362);

-- 7. 禁用会员中心 (id=2262)
SELECT '禁用会员中心...' AS '';
CALL sp_disable_menu_tree(2262);

-- 8. 禁用公众号管理 (id=2084)
SELECT '禁用公众号管理...' AS '';
CALL sp_disable_menu_tree(2084);

-- 9. 禁用 AI 大模型 (id=2758)
SELECT '禁用 AI 大模型...' AS '';
CALL sp_disable_menu_tree(2758);

-- 10. 禁用支付管理 (id=1117)
SELECT '禁用支付管理...' AS '';
CALL sp_disable_menu_tree(1117);

-- 11. 禁用工作流程 BPM (id=1185)
SELECT '禁用工作流程...' AS '';
CALL sp_disable_menu_tree(1185);

-- 12. 禁用报表管理 (id=1281)
SELECT '禁用报表管理...' AS '';
CALL sp_disable_menu_tree(1281);

-- 13. 禁用代码生成案例 (id=1070)
SELECT '禁用代码生成案例...' AS '';
CALL sp_disable_menu_tree(1070);

-- 14. 禁用 IM 即时通讯 (id=6500)
SELECT '禁用 IM 即时通讯...' AS '';
CALL sp_disable_menu_tree(6500);

-- 15. 禁用开发者文档链接
SELECT '禁用开发者文档...' AS '';
UPDATE system_menu SET status = 1 WHERE id IN (1254, 2159, 2160);
SELECT ROW_COUNT() AS disabled_count;

-- ====================================================================
-- 清理存储过程
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_disable_menu_tree`;

-- ====================================================================
-- 统计结果
-- ====================================================================
SELECT '========================================' AS '';
SELECT '禁用结果统计' AS '';
SELECT '========================================' AS '';

SELECT
    '已禁用菜单总数' AS '项目',
    COUNT(*) AS '数量'
FROM system_menu
WHERE status = 1 AND deleted = 0
UNION ALL
SELECT
    '剩余激活菜单总数' AS '项目',
    COUNT(*) AS '数量'
FROM system_menu
WHERE status = 0 AND deleted = 0;

-- 显示剩余激活的顶级菜单
SELECT '========================================' AS '';
SELECT '剩余激活的顶级菜单:' AS '';
SELECT '========================================' AS '';

SELECT
    id AS 'ID',
    name AS '菜单名称',
    CASE type
        WHEN 1 THEN '目录'
        WHEN 2 THEN '菜单'
        WHEN 3 THEN '按钮'
        ELSE '未知'
    END AS '类型',
    path AS '路径'
FROM system_menu
WHERE parent_id = 0
  AND deleted = 0
  AND status = 0
ORDER BY id;

-- ====================================================================
-- 完成
-- ====================================================================
SELECT '========================================' AS '';
SELECT '执行完成!' AS '';
SELECT '提示: 需要清除 Redis 缓存: DEL permission_menu_ids' AS '';
SELECT '========================================' AS '';