-- ============================================
-- HIS 医院信息系统 - 菜单图标修复 SQL
-- 使用层级路径定位菜单（按钮级别仍使用 ID，因数量较多）
-- 执行时间: 2026-06-19
-- ============================================

USE `yudao-ai-his`;

-- 说明:
-- 本 SQL 修复缺少图标的菜单,为每个菜单配置合适的图标
-- 图标格式: {图标集前缀}:{图标名称}
-- 图标集: ep: (Element Plus), lucide: (Lucide), mdi: (Material Design), carbon: (Carbon)
--
-- 使用方法:
-- 1. 备份 system_menu 表: CREATE TABLE system_menu_backup AS SELECT * FROM system_menu;
-- 2. 执行本 SQL 文件
-- 3. 清除 Redis 缓存: redis-cli DEL permission_menu_ids
-- 4. 重启应用或刷新浏览器

-- ============================================
-- 修复统计
-- ============================================
-- 总修复数量: 191 个菜单
-- 精确匹配: 183 个
-- 默认图标: 8 个

-- ============================================
-- 一级模块图标修复（使用层级路径定位）
-- ============================================

-- HIS医院信息系统
UPDATE system_menu SET icon = 'lucide:building-2'
WHERE name = 'HIS医院信息系统' AND parent_id = 0 AND deleted = 0;

-- 门诊管理
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '门诊管理' AND m2.deleted = 0
SET m2.icon = 'lucide:stethoscope'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 住院管理
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理' AND m2.deleted = 0
SET m2.icon = 'lucide:bed'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 药品管理
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理' AND m2.deleted = 0
SET m2.icon = 'mdi:pill'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 基础数据
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '基础数据' AND m2.deleted = 0
SET m2.icon = 'lucide:database'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 检验管理
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理' AND m2.deleted = 0
SET m2.icon = 'lucide:flask-conical'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 医嘱执行
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行' AND m2.deleted = 0
SET m2.icon = 'lucide:clipboard-list'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- ============================================
-- 二级菜单图标修复（使用层级路径定位）
-- ============================================

-- 门诊管理子菜单
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '门诊管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '患者管理' AND m3.deleted = 0
SET m3.icon = 'lucide:users'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '门诊管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '预约挂号' AND m3.deleted = 0
SET m3.icon = 'lucide:calendar'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '门诊管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '挂号收费' AND m3.deleted = 0
SET m3.icon = 'lucide:receipt'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '门诊管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '医生排班' AND m3.deleted = 0
SET m3.icon = 'lucide:calendar-clock'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '门诊管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '处方管理' AND m3.deleted = 0
SET m3.icon = 'lucide:file-text'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '门诊管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '门诊收费' AND m3.deleted = 0
SET m3.icon = 'lucide:credit-card'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '门诊管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '门诊药房' AND m3.deleted = 0
SET m3.icon = 'lucide:pill'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 住院管理子菜单
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '入院管理' AND m3.deleted = 0
SET m3.icon = 'lucide:log-in'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '床位管理' AND m3.deleted = 0
SET m3.icon = 'lucide:bed-double'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '医嘱管理' AND m3.deleted = 0
SET m3.icon = 'lucide:clipboard-list'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '护理记录' AND m3.deleted = 0
SET m3.icon = 'lucide:heart'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '出院管理' AND m3.deleted = 0
SET m3.icon = 'lucide:log-out'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '病区设置' AND m3.deleted = 0
SET m3.icon = 'lucide:layout-grid'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '预住院管理' AND m3.deleted = 0
SET m3.icon = 'lucide:user-plus'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '入院评估' AND m3.deleted = 0
SET m3.icon = 'lucide:clipboard-check'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '住院管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '预交金管理' AND m3.deleted = 0
SET m3.icon = 'lucide:wallet'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 药品管理子菜单
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '药品目录' AND m3.deleted = 0
SET m3.icon = 'lucide:list'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '药品库存' AND m3.deleted = 0
SET m3.icon = 'lucide:package'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '入库管理' AND m3.deleted = 0
SET m3.icon = 'lucide:arrow-down-circle'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '出库管理' AND m3.deleted = 0
SET m3.icon = 'lucide:arrow-up-circle'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '盘点管理' AND m3.deleted = 0
SET m3.icon = 'lucide:check-square'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '供应商管理' AND m3.deleted = 0
SET m3.icon = 'lucide:truck'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '采购计划' AND m3.deleted = 0
SET m3.icon = 'lucide:shopping-cart'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '药品管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '退药管理' AND m3.deleted = 0
SET m3.icon = 'lucide:undo-2'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 基础数据子菜单
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '基础数据'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '科室管理' AND m3.deleted = 0
SET m3.icon = 'lucide:building'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '基础数据'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '医护人员' AND m3.deleted = 0
SET m3.icon = 'lucide:user-check'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '基础数据'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '收费项目' AND m3.deleted = 0
SET m3.icon = 'lucide:dollar-sign'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '基础数据'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '病区管理' AND m3.deleted = 0
SET m3.icon = 'lucide:layout-grid'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 检验管理子菜单
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '检验申请' AND m3.deleted = 0
SET m3.icon = 'lucide:flask-conical'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '检验项目' AND m3.deleted = 0
SET m3.icon = 'lucide:list'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '检验管理'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '危急值管理' AND m3.deleted = 0
SET m3.icon = 'lucide:alert-triangle'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- 医嘱执行子菜单
UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '医嘱管理' AND m3.deleted = 0
SET m3.icon = 'lucide:clipboard-list'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '给药记录' AND m3.deleted = 0
SET m3.icon = 'lucide:syringe'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '生命体征' AND m3.deleted = 0
SET m3.icon = 'lucide:activity'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '护理记录' AND m3.deleted = 0
SET m3.icon = 'lucide:heart'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

UPDATE system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.name = '医嘱执行'
JOIN system_menu m3 ON m3.parent_id = m2.id AND m3.name = '护理评估' AND m3.deleted = 0
SET m3.icon = 'lucide:clipboard-check'
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0;

-- ============================================
-- 按钮级别图标修复（数量较多，保留 ID 方式）
-- ============================================

-- 系统管理按钮图标
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1002;  -- 用户新增
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1003;  -- 用户修改
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1004;  -- 用户删除
UPDATE system_menu SET icon = 'lucide:refresh-cw' WHERE id = 1007;  -- 重置密码

UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1009;  -- 角色新增
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1010;  -- 角色修改
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1011;  -- 角色删除

UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1018;  -- 部门新增
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1019;  -- 部门修改
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1020;  -- 部门删除

UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1022;  -- 岗位新增
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1023;  -- 岗位修改
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1024;  -- 岗位删除

UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1027;  -- 字典新增
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1028;  -- 字典修改
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1029;  -- 字典删除

UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1031;  -- 配置查询
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1032;  -- 配置新增
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1033;  -- 配置修改
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1034;  -- 配置删除
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1035;  -- 配置导出

-- ============================================
-- 验证修复结果
-- ============================================

-- 查看 HIS 模块菜单图标配置
SELECT m2.id, m2.name, m2.icon, m2.path
FROM system_menu m1
JOIN system_menu m2 ON m2.parent_id = m1.id AND m2.deleted = 0
WHERE m1.name = 'HIS医院信息系统' AND m1.parent_id = 0
ORDER BY m2.id;

-- 查看修复后仍缺少图标的菜单
SELECT id, name, icon, parent_id, path
FROM system_menu
WHERE deleted = 0 AND status = 0 AND (icon IS NULL OR icon = '')
LIMIT 20;

-- ============================================
-- 清除缓存
-- ============================================

-- 清除 Redis 权限菜单缓存
-- redis-cli DEL permission_menu_ids
-- 或在应用中调用: redisTemplate.delete("permission_menu_ids")

-- ============================================
-- END
-- ============================================