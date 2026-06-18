-- =============================================
-- HIS 护理模块数据库建表脚本
-- 包含: 护理记录、护理评估、护理措施
-- =============================================

-- ==================== 1. 护理记录表 ====================
-- 如果表已存在则先删除
-- DROP TABLE IF EXISTS his_nursing_record;

CREATE TABLE IF NOT EXISTS his_nursing_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '护理记录ID',
    record_no VARCHAR(32) NOT NULL COMMENT '记录编号',
    patient_id BIGINT NOT NULL COMMENT '患者ID',
    patient_name VARCHAR(64) NOT NULL COMMENT '患者姓名',
    admission_id BIGINT NOT NULL COMMENT '住院ID',
    nurse_id BIGINT COMMENT '护士ID',
    nurse_name VARCHAR(64) COMMENT '护士姓名',
    record_type TINYINT NOT NULL COMMENT '记录类型: 1-一般护理记录 2-危重护理记录 3-手术护理记录 4-交接班记录 5-特殊护理记录',
    title VARCHAR(200) COMMENT '标题',
    content TEXT NOT NULL COMMENT '护理内容',
    record_time DATETIME NOT NULL COMMENT '记录时间',
    -- 签名信息
    signature_status TINYINT DEFAULT 0 COMMENT '签名状态: 0-未签名 1-已签名',
    signature_time DATETIME COMMENT '签名时间',
    signature VARCHAR(512) COMMENT '电子签名',
    -- 审核信息
    audit_status TINYINT DEFAULT 0 COMMENT '审核状态: 0-未审核 1-已审核',
    audit_nurse_id BIGINT COMMENT '审核护士ID',
    audit_nurse_name VARCHAR(64) COMMENT '审核护士姓名',
    audit_time DATETIME COMMENT '审核时间',
    remark VARCHAR(500) COMMENT '备注',
    -- 系统字段
    tenant_id BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    creator VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted BIT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    KEY idx_admission_id (admission_id),
    KEY idx_patient_id (patient_id),
    KEY idx_record_time (record_time),
    KEY idx_nurse_id (nurse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理记录表';

-- ==================== 2. 护理评估表 ====================
-- DROP TABLE IF EXISTS his_nursing_assessment;

CREATE TABLE IF NOT EXISTS his_nursing_assessment (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '评估ID',
    assessment_no VARCHAR(32) NOT NULL COMMENT '评估编号',
    patient_id BIGINT NOT NULL COMMENT '患者ID',
    patient_name VARCHAR(64) NOT NULL COMMENT '患者姓名',
    admission_id BIGINT NOT NULL COMMENT '住院ID',
    assessment_type TINYINT NOT NULL COMMENT '评估类型: 1-跌倒评估 2-压疮评估 3-疼痛评估 4-自理能力 5-营养评估',
    assessment_name VARCHAR(100) COMMENT '评估名称',
    total_score INT COMMENT '总分',
    risk_level VARCHAR(20) COMMENT '风险等级: 无风险/低风险/中风险/高风险',
    assessment_detail TEXT COMMENT '评估详情(JSON格式)',
    items TEXT COMMENT '评估项目明细(JSON格式)',
    nurse_id BIGINT COMMENT '评估护士ID',
    nurse_name VARCHAR(64) COMMENT '评估护士姓名',
    assessment_time DATETIME NOT NULL COMMENT '评估时间',
    next_assessment_time DATETIME COMMENT '下次评估时间',
    measure_suggestion TEXT COMMENT '护理措施建议',
    remark VARCHAR(500) COMMENT '备注',
    -- 系统字段
    tenant_id BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    creator VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted BIT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    KEY idx_admission_id (admission_id),
    KEY idx_patient_id (patient_id),
    KEY idx_assessment_type (assessment_type),
    KEY idx_assessment_time (assessment_time),
    KEY idx_risk_level (risk_level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理评估表';

-- ==================== 3. 护理措施表 ====================
-- DROP TABLE IF EXISTS his_nursing_measure;

CREATE TABLE IF NOT EXISTS his_nursing_measure (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '措施ID',
    measure_no VARCHAR(32) NOT NULL COMMENT '措施编号',
    patient_id BIGINT NOT NULL COMMENT '患者ID',
    patient_name VARCHAR(64) NOT NULL COMMENT '患者姓名',
    admission_id BIGINT NOT NULL COMMENT '住院ID',
    assessment_id BIGINT COMMENT '关联的评估ID',
    measure_type TINYINT NOT NULL COMMENT '措施类型: 1-跌倒预防 2-压疮预防 3-疼痛管理 4-营养支持 5-安全护理 6-其他',
    measure_name VARCHAR(200) NOT NULL COMMENT '措施名称',
    measure_content TEXT NOT NULL COMMENT '措施内容',
    frequency VARCHAR(50) COMMENT '执行频次',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    status TINYINT DEFAULT 0 COMMENT '执行状态: 0-未开始 1-执行中 2-已完成 3-已停止',
    nurse_id BIGINT COMMENT '执行护士ID',
    nurse_name VARCHAR(64) COMMENT '执行护士姓名',
    effect_evaluation TINYINT COMMENT '效果评价: 1-有效 2-部分有效 3-无效',
    effect_description VARCHAR(500) COMMENT '效果评价说明',
    evaluation_time DATETIME COMMENT '评价时间',
    evaluation_nurse_id BIGINT COMMENT '评价护士ID',
    evaluation_nurse_name VARCHAR(64) COMMENT '评价护士姓名',
    remark VARCHAR(500) COMMENT '备注',
    -- 系统字段
    tenant_id BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    creator VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted BIT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    KEY idx_admission_id (admission_id),
    KEY idx_patient_id (patient_id),
    KEY idx_assessment_id (assessment_id),
    KEY idx_measure_type (measure_type),
    KEY idx_status (status),
    KEY idx_start_time (start_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理措施表';

-- ==================== 4. 生命体征表 ====================
-- DROP TABLE IF EXISTS his_vital_sign;

CREATE TABLE IF NOT EXISTS his_vital_sign (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '生命体征ID',
    patient_id BIGINT NOT NULL COMMENT '患者ID',
    patient_name VARCHAR(64) NOT NULL COMMENT '患者姓名',
    admission_id BIGINT NOT NULL COMMENT '住院ID',
    -- 生命体征数据
    temperature DECIMAL(4,1) COMMENT '体温(°C)',
    pulse INT COMMENT '脉搏(次/分)',
    respiration INT COMMENT '呼吸(次/分)',
    systolic_bp INT COMMENT '收缩压(mmHg)',
    diastolic_bp INT COMMENT '舒张压(mmHg)',
    oxygen_saturation DECIMAL(4,1) COMMENT '血氧饱和度(%)',
    weight DECIMAL(5,2) COMMENT '体重(kg)',
    height DECIMAL(5,2) COMMENT '身高(cm)',
    bmi DECIMAL(4,2) COMMENT 'BMI指数',
    pain_score INT COMMENT '疼痛评分(0-10)',
    consciousness VARCHAR(20) COMMENT '意识状态: 清醒/嗜睡/昏迷',
    -- 测量信息
    measure_time DATETIME NOT NULL COMMENT '测量时间',
    nurse_id BIGINT COMMENT '测量护士ID',
    nurse_name VARCHAR(64) COMMENT '测量护士姓名',
    -- 异常标识
    abnormal_flag TINYINT DEFAULT 0 COMMENT '异常标识: 0-正常 1-异常',
    abnormal_items VARCHAR(200) COMMENT '异常项目',
    remark VARCHAR(500) COMMENT '备注',
    -- 系统字段
    tenant_id BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    creator VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted BIT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    KEY idx_admission_id (admission_id),
    KEY idx_patient_id (patient_id),
    KEY idx_measure_time (measure_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='生命体征表';

-- ==================== 5. 给药记录表 ====================
-- DROP TABLE IF EXISTS his_medication_admin;

CREATE TABLE IF NOT EXISTS his_medication_admin (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '给药记录ID',
    admin_no VARCHAR(32) NOT NULL COMMENT '记录编号',
    patient_id BIGINT NOT NULL COMMENT '患者ID',
    patient_name VARCHAR(64) NOT NULL COMMENT '患者姓名',
    admission_id BIGINT NOT NULL COMMENT '住院ID',
    admission_no VARCHAR(32) COMMENT '住院号',
    order_id BIGINT COMMENT '医嘱ID',
    order_no VARCHAR(32) COMMENT '医嘱编号',
    drug_id BIGINT COMMENT '药品ID',
    drug_code VARCHAR(32) COMMENT '药品编码',
    drug_name VARCHAR(200) COMMENT '药品名称',
    spec VARCHAR(100) COMMENT '规格',
    dosage DECIMAL(10,3) COMMENT '剂量',
    dosage_unit VARCHAR(20) COMMENT '剂量单位',
    route VARCHAR(50) COMMENT '给药途径',
    scheduled_time DATETIME COMMENT '预定执行时间',
    actual_time DATETIME COMMENT '实际执行时间',
    nurse_id BIGINT COMMENT '执行护士ID',
    nurse_name VARCHAR(64) COMMENT '执行护士姓名',
    -- 闭环给药核对字段
    wristband_scan_status TINYINT DEFAULT 0 COMMENT '腕带扫描状态: 0-未扫描 1-匹配 2-不匹配',
    wristband_scan_time DATETIME COMMENT '腕带扫描时间',
    wristband_scan_result VARCHAR(200) COMMENT '腕带扫描结果',
    drug_scan_status TINYINT DEFAULT 0 COMMENT '药品扫描状态: 0-未扫描 1-匹配 2-不匹配',
    drug_scan_time DATETIME COMMENT '药品扫描时间',
    drug_scan_result VARCHAR(200) COMMENT '药品扫描结果',
    drug_batch_no VARCHAR(50) COMMENT '药品批号',
    drug_expire_date DATE COMMENT '药品有效期',
    check_result TINYINT COMMENT '核对结果: 1-通过 2-不通过',
    -- 执行状态字段
    status TINYINT DEFAULT 1 COMMENT '状态: 1-待执行 2-已执行 3-未执行 4-延迟执行 5-患者拒绝',
    reason VARCHAR(500) COMMENT '未执行/延迟原因',
    reason_type VARCHAR(50) COMMENT '原因类型: 患者拒绝/病情变化/药品问题/其他',
    notify_doctor BIT DEFAULT 0 COMMENT '是否通知医生',
    -- 记账字段
    charge_status TINYINT DEFAULT 0 COMMENT '记账状态: 0-未记账 1-已记账',
    charge_time DATETIME COMMENT '记账时间',
    remark VARCHAR(500) COMMENT '备注',
    -- 系统字段
    tenant_id BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    creator VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted BIT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    KEY idx_admission_id (admission_id),
    KEY idx_patient_id (patient_id),
    KEY idx_order_id (order_id),
    KEY idx_scheduled_time (scheduled_time),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='给药记录表(eMAR)';
