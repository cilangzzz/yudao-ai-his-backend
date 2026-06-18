-- =============================================
-- HIS 医嘱执行模块数据库初始化脚本
-- 包含: 给药记录(eMAR)、生命体征、护理记录、护理评估
-- 版本: V1.0
-- 创建日期: 2026-06-18
-- =============================================

-- =============================================
-- 1. 给药记录表 (his_medication_admin)
-- 对应FHIR资源: MedicationAdministration
-- HIMSS EMRAM Stage 5核心表 - 闭环给药管理
-- =============================================
CREATE TABLE IF NOT EXISTS `his_medication_admin` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '给药记录ID',
    `admin_no` VARCHAR(30) NOT NULL COMMENT '记录编号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) NOT NULL COMMENT '患者姓名',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `admission_no` VARCHAR(30) COMMENT '住院号',
    `order_id` BIGINT NOT NULL COMMENT '医嘱ID',
    `order_no` VARCHAR(30) COMMENT '医嘱编号',
    `drug_id` BIGINT NOT NULL COMMENT '药品ID',
    `drug_code` VARCHAR(50) COMMENT '药品编码',
    `drug_name` VARCHAR(100) NOT NULL COMMENT '药品名称',
    `spec` VARCHAR(50) COMMENT '规格',
    `dosage` DECIMAL(10,2) NOT NULL COMMENT '剂量',
    `dosage_unit` VARCHAR(20) COMMENT '剂量单位',
    `route` VARCHAR(50) COMMENT '给药途径',
    `scheduled_time` DATETIME NOT NULL COMMENT '预定执行时间',
    `actual_time` DATETIME COMMENT '实际执行时间',
    `nurse_id` BIGINT NOT NULL COMMENT '执行护士ID',
    `nurse_name` VARCHAR(50) NOT NULL COMMENT '执行护士姓名',
    -- 闭环给药核对字段
    `wristband_scan_status` TINYINT NOT NULL DEFAULT 0 COMMENT '腕带扫描状态: 0未扫描/1匹配/2不匹配',
    `wristband_scan_time` DATETIME COMMENT '腕带扫描时间',
    `wristband_scan_result` VARCHAR(200) COMMENT '腕带扫描结果',
    `drug_scan_status` TINYINT NOT NULL DEFAULT 0 COMMENT '药品扫描状态: 0未扫描/1匹配/2不匹配',
    `drug_scan_time` DATETIME COMMENT '药品扫描时间',
    `drug_scan_result` VARCHAR(200) COMMENT '药品扫描结果',
    `drug_batch_no` VARCHAR(50) COMMENT '药品批号',
    `drug_expire_date` DATE COMMENT '药品有效期',
    `check_result` TINYINT NOT NULL COMMENT '核对结果: 1通过/2不通过',
    -- 执行状态字段
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 1待执行/2已执行/3未执行/4延迟执行/5患者拒绝',
    `reason` VARCHAR(200) COMMENT '未执行/延迟原因',
    `reason_type` VARCHAR(50) COMMENT '原因类型: 患者拒绝/病情变化/药品问题/其他',
    `notify_doctor` TINYINT DEFAULT 0 COMMENT '是否通知医生: 0否/1是',
    -- 记账字段
    `charge_status` TINYINT DEFAULT 0 COMMENT '记账状态: 0未记账/1已记账',
    `charge_time` DATETIME COMMENT '记账时间',
    `remark` VARCHAR(500) COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_admin_no` (`admin_no`),
    KEY `idx_med_admin_patient` (`patient_id`),
    KEY `idx_med_admin_admission` (`admission_id`),
    KEY `idx_med_admin_order` (`order_id`),
    KEY `idx_med_admin_nurse` (`nurse_id`),
    KEY `idx_med_admin_status` (`status`),
    KEY `idx_med_admin_scheduled` (`scheduled_time`),
    KEY `idx_med_admin_actual` (`actual_time`),
    KEY `idx_med_admin_drug` (`drug_id`),
    KEY `idx_med_admin_check` (`check_result`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='给药记录表(eMAR)';

-- =============================================
-- 2. 生命体征表 (his_vital_sign)
-- 对应FHIR资源: Observation
-- =============================================
CREATE TABLE IF NOT EXISTS `his_vital_sign` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '生命体征ID',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) NOT NULL COMMENT '患者姓名',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    -- 生命体征数据
    `temperature` DECIMAL(4,1) COMMENT '体温(°C)',
    `pulse` INT COMMENT '脉搏(次/分)',
    `respiration` INT COMMENT '呼吸(次/分)',
    `systolic_bp` INT COMMENT '收缩压(mmHg)',
    `diastolic_bp` INT COMMENT '舒张压(mmHg)',
    `oxygen_saturation` DECIMAL(5,2) COMMENT '血氧饱和度(%)',
    `weight` DECIMAL(5,2) COMMENT '体重(kg)',
    `height` DECIMAL(5,2) COMMENT '身高(cm)',
    `bmi` DECIMAL(5,2) COMMENT 'BMI指数',
    `pain_score` INT COMMENT '疼痛评分(0-10)',
    `consciousness` VARCHAR(20) COMMENT '意识状态: 清醒/嗜睡/昏迷',
    -- 测量信息
    `measure_time` DATETIME NOT NULL COMMENT '测量时间',
    `nurse_id` BIGINT NOT NULL COMMENT '测量护士ID',
    `nurse_name` VARCHAR(50) COMMENT '测量护士姓名',
    -- 异常标识
    `abnormal_flag` TINYINT DEFAULT 0 COMMENT '异常标识: 0正常/1异常',
    `abnormal_items` VARCHAR(200) COMMENT '异常项目',
    `remark` VARCHAR(500) COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY `idx_vital_sign_patient` (`patient_id`),
    KEY `idx_vital_sign_admission` (`admission_id`),
    KEY `idx_vital_sign_measure_time` (`measure_time`),
    KEY `idx_vital_sign_abnormal` (`abnormal_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='生命体征表';

-- =============================================
-- 3. 护理记录表 (his_nursing_record)
-- =============================================
CREATE TABLE IF NOT EXISTS `his_nursing_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '护理记录ID',
    `record_no` VARCHAR(30) COMMENT '记录编号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) NOT NULL COMMENT '患者姓名',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `nurse_id` BIGINT NOT NULL COMMENT '护士ID',
    `nurse_name` VARCHAR(50) NOT NULL COMMENT '护士姓名',
    `record_type` TINYINT NOT NULL COMMENT '记录类型: 1一般护理记录/2危重护理记录/3手术护理记录/4交接班记录/5特殊护理记录',
    `title` VARCHAR(200) COMMENT '标题',
    `content` TEXT NOT NULL COMMENT '护理内容',
    `record_time` DATETIME NOT NULL COMMENT '记录时间',
    -- 签名信息
    `signature_status` TINYINT DEFAULT 0 COMMENT '签名状态: 0未签名/1已签名',
    `signature_time` DATETIME COMMENT '签名时间',
    `signature` VARCHAR(100) COMMENT '电子签名',
    -- 审核信息
    `audit_status` TINYINT DEFAULT 0 COMMENT '审核状态: 0未审核/1已审核',
    `audit_nurse_id` BIGINT COMMENT '审核护士ID',
    `audit_nurse_name` VARCHAR(50) COMMENT '审核护士姓名',
    `audit_time` DATETIME COMMENT '审核时间',
    `remark` VARCHAR(500) COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY `idx_nursing_record_patient` (`patient_id`),
    KEY `idx_nursing_record_admission` (`admission_id`),
    KEY `idx_nursing_record_nurse` (`nurse_id`),
    KEY `idx_nursing_record_type` (`record_type`),
    KEY `idx_nursing_record_time` (`record_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理记录表';

-- =============================================
-- 4. 护理评估表 (his_nursing_assessment)
-- =============================================
CREATE TABLE IF NOT EXISTS `his_nursing_assessment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评估ID',
    `assessment_no` VARCHAR(30) COMMENT '评估编号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) NOT NULL COMMENT '患者姓名',
    `admission_id` BIGINT NOT NULL COMMENT '住院ID',
    `assessment_type` TINYINT NOT NULL COMMENT '评估类型: 1跌倒评估/2压疮评估/3疼痛评估/4自理能力/5营养评估',
    `assessment_name` VARCHAR(50) NOT NULL COMMENT '评估名称',
    `total_score` INT NOT NULL COMMENT '总分',
    `risk_level` VARCHAR(20) NOT NULL COMMENT '风险等级: 无风险/低风险/中风险/高风险',
    `assessment_detail` TEXT COMMENT '评估详情(JSON格式)',
    `items` TEXT COMMENT '评估项目明细(JSON格式)',
    `nurse_id` BIGINT NOT NULL COMMENT '评估护士ID',
    `nurse_name` VARCHAR(50) NOT NULL COMMENT '评估护士姓名',
    `assessment_time` DATETIME NOT NULL COMMENT '评估时间',
    `next_assessment_time` DATETIME COMMENT '下次评估时间',
    `measure_suggestion` TEXT COMMENT '护理措施建议',
    `remark` VARCHAR(500) COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY `idx_nursing_assess_patient` (`patient_id`),
    KEY `idx_nursing_assess_admission` (`admission_id`),
    KEY `idx_nursing_assess_type` (`assessment_type`),
    KEY `idx_nursing_assess_risk` (`risk_level`),
    KEY `idx_nursing_assess_time` (`assessment_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理评估表';

-- =============================================
-- 5. 菜单权限初始化
-- =============================================
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('医嘱执行', '', 2, 3, 0, '/his/order-exec', 'ep:document', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

SET @parentId = LAST_INSERT_ID();

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('医嘱管理', 'his:order:query', 2, 1, @parentId, 'order', 'ep:document-checked', 'his/order/index', 'HisOrder', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药记录', 'his:medication-admin:query', 2, 2, @parentId, 'medication-admin', 'ep:first-aid-kit', 'his/medication-admin/index', 'HisMedicationAdmin', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('生命体征', 'his:vital-sign:query', 2, 3, @parentId, 'vital-sign', 'ep:heart', 'his/vital-sign/index', 'HisVitalSign', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录', 'his:nursing-record:query', 2, 4, @parentId, 'nursing-record', 'ep:notebook', 'his/nursing-record/index', 'HisNursingRecord', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理评估', 'his:nursing-assessment:query', 2, 5, @parentId, 'nursing-assessment', 'ep:data-analysis', 'his/nursing-assessment/index', 'HisNursingAssessment', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 6. 按钮权限初始化
-- =============================================
-- 医嘱管理按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('医嘱查询', 'his:order:query', 3, 1, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱审核', 'his:order:audit', 3, 2, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱执行', 'his:order:execute', 3, 3, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱停止', 'his:order:stop', 3, 4, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('医嘱作废', 'his:order:cancel', 3, 5, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:order:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 给药记录按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('给药记录查询', 'his:medication-admin:query', 3, 1, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药记录新增', 'his:medication-admin:create', 3, 2, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药记录更新', 'his:medication-admin:update', 3, 3, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药记录删除', 'his:medication-admin:delete', 3, 4, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('给药执行', 'his:medication-admin:execute', 3, 5, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:medication-admin:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 生命体征按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('生命体征查询', 'his:vital-sign:query', 3, 1, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:vital-sign:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('生命体征新增', 'his:vital-sign:create', 3, 2, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:vital-sign:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('生命体征更新', 'his:vital-sign:update', 3, 3, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:vital-sign:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('生命体征删除', 'his:vital-sign:delete', 3, 4, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:vital-sign:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 护理记录按钮权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('护理记录查询', 'his:nursing-record:query', 3, 1, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录新增', 'his:nursing-record:create', 3, 2, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录更新', 'his:nursing-record:update', 3, 3, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录删除', 'his:nursing-record:delete', 3, 4, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录签名', 'his:nursing-record:sign', 3, 5, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('护理记录审核', 'his:nursing-record:audit', 3, 6, (SELECT id FROM (SELECT id FROM system_menu WHERE permission = 'his:nursing-record:query' LIMIT 1) t), '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');