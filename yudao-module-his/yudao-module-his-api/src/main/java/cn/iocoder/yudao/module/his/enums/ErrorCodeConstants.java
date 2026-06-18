package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * HIS 医院信息系统错误码枚举类
 *
 * HIS 系统，使用 1_0XX_XXX_XXX 段
 * 按照 sub-module 分配:
 * - M01 门诊管理: 1_015_000_XXX
 * - M02 住院管理: 1_016_000_XXX
 * - M06 药品管理: 1_017_000_XXX
 */
public interface ErrorCodeConstants {

    // ========== M01 门诊管理 1_015_000_XXX ==========

    // 患者管理
    ErrorCode PATIENT_NOT_EXISTS = new ErrorCode(1_015_000_000, "患者不存在");
    ErrorCode PATIENT_CARD_NOT_EXISTS = new ErrorCode(1_015_000_001, "患者就诊卡不存在");
    ErrorCode PATIENT_ID_CARD_DUPLICATE = new ErrorCode(1_015_000_002, "身份证号已存在");
    ErrorCode PATIENT_NAME_EMPTY = new ErrorCode(1_015_000_003, "患者姓名不能为空");

    // 挂号管理
    ErrorCode REGISTER_NOT_EXISTS = new ErrorCode(1_015_001_000, "挂号记录不存在");
    ErrorCode REGISTER_ALREADY_CANCELLED = new ErrorCode(1_015_001_001, "挂号已取消，无法操作");
    ErrorCode REGISTER_ALREADY_COMPLETED = new ErrorCode(1_015_001_002, "挂号已完成就诊，无法退号");
    ErrorCode REGISTER_SCHEDULE_NOT_EXISTS = new ErrorCode(1_015_001_003, "排班不存在");
    ErrorCode REGISTER_SCHEDULE_NO_SLOT = new ErrorCode(1_015_001_004, "该时段号源已满");
    ErrorCode REGISTER_DEPT_NOT_EXISTS = new ErrorCode(1_015_001_005, "科室不存在");
    ErrorCode REGISTER_DOCTOR_NOT_EXISTS = new ErrorCode(1_015_001_006, "医生不存在");

    // 预约管理
    ErrorCode APPOINTMENT_NOT_EXISTS = new ErrorCode(1_015_002_000, "预约记录不存在");
    ErrorCode APPOINTMENT_ALREADY_CANCELLED = new ErrorCode(1_015_002_001, "预约已取消");
    ErrorCode APPOINTMENT_TIME_INVALID = new ErrorCode(1_015_002_002, "预约时间无效");
    ErrorCode APPOINTMENT_EXPIRED = new ErrorCode(1_015_002_003, "预约已过期");

    // 处方管理
    ErrorCode PRESCRIPTION_NOT_EXISTS = new ErrorCode(1_015_003_000, "处方不存在");
    ErrorCode PRESCRIPTION_ALREADY_AUDITED = new ErrorCode(1_015_003_001, "处方已审核，无法修改");
    ErrorCode PRESCRIPTION_ALREADY_DISPENSED = new ErrorCode(1_015_003_002, "处方已发药，无法修改");
    ErrorCode PRESCRIPTION_ITEM_NOT_EXISTS = new ErrorCode(1_015_003_003, "处方明细不存在");
    ErrorCode PRESCRIPTION_DRUG_NOT_EXISTS = new ErrorCode(1_015_003_004, "药品不存在");
    ErrorCode PRESCRIPTION_DRUG_OUT_OF_STOCK = new ErrorCode(1_015_003_005, "药品库存不足");
    ErrorCode PRESCRIPTION_SKIN_TEST_REQUIRED = new ErrorCode(1_015_003_006, "该药品需要皮试");
    ErrorCode PRESCRIPTION_SKIN_TEST_FAILED = new ErrorCode(1_015_003_007, "皮试结果阳性，禁止使用");

    // 门诊收费
    ErrorCode OP_FEE_NOT_EXISTS = new ErrorCode(1_015_004_000, "门诊费用不存在");
    ErrorCode OP_FEE_ALREADY_PAID = new ErrorCode(1_015_004_001, "费用已支付，请先办理退费");
    ErrorCode OP_FEE_ALREADY_REFUNDED = new ErrorCode(1_015_004_002, "费用已退费");
    ErrorCode OP_PAYMENT_NOT_EXISTS = new ErrorCode(1_015_004_003, "支付记录不存在");
    ErrorCode OP_REFUND_NOT_EXISTS = new ErrorCode(1_015_004_004, "退费记录不存在");
    ErrorCode OP_REFUND_AMOUNT_INVALID = new ErrorCode(1_015_004_005, "退费金额无效");
    ErrorCode PAYMENT_ITEM_NOT_EXISTS = new ErrorCode(1_015_004_006, "支付明细不存在");
    ErrorCode PAYMENT_ITEM_ALREADY_PAID = new ErrorCode(1_015_004_007, "支付明细已支付");
    ErrorCode PAYMENT_ITEM_ALREADY_REFUNDED = new ErrorCode(1_015_004_008, "支付明细已退费");
    ErrorCode PAYMENT_NOT_EXISTS = new ErrorCode(1_015_004_009, "支付记录不存在");
    ErrorCode REFUND_NOT_EXISTS = new ErrorCode(1_015_004_010, "退费记录不存在");
    ErrorCode REFUND_AMOUNT_EXCEED = new ErrorCode(1_015_004_011, "退费金额超过可退金额");
    ErrorCode REFUND_STATUS_ERROR = new ErrorCode(1_015_004_012, "退费状态不正确，无法操作");

    // 门诊药房
    ErrorCode OP_DISPENSE_NOT_EXISTS = new ErrorCode(1_015_005_000, "发药记录不存在");
    ErrorCode OP_DISPENSE_ALREADY_COMPLETED = new ErrorCode(1_015_005_001, "已发药完成");
    ErrorCode OP_DISPENSE_PRESCRIPTION_NOT_AUDITED = new ErrorCode(1_015_005_002, "处方未审核，无法发药");

    // ========== M02 住院管理 1_016_000_XXX ==========

    // 入院管理
    ErrorCode ADMISSION_NOT_EXISTS = new ErrorCode(1_016_000_000, "入院记录不存在");
    ErrorCode ADMISSION_ALREADY_DISCHARGED = new ErrorCode(1_016_000_001, "患者已出院");
    ErrorCode ADMISSION_BED_NOT_AVAILABLE = new ErrorCode(1_016_000_002, "床位不可用");
    ErrorCode ADMISSION_PREPAYMENT_INSUFFICIENT = new ErrorCode(1_016_000_003, "预交金不足");

    // 床位管理
    ErrorCode BED_NOT_EXISTS = new ErrorCode(1_016_001_000, "床位不存在");
    ErrorCode BED_ALREADY_OCCUPIED = new ErrorCode(1_016_001_001, "床位已被占用");
    ErrorCode BED_ALREADY_RESERVED = new ErrorCode(1_016_001_002, "床位已预约");
    ErrorCode BED_ALREADY_MAINTENANCE = new ErrorCode(1_016_001_003, "床位正在维护");
    ErrorCode WARD_NOT_EXISTS = new ErrorCode(1_016_001_004, "病区不存在");

    // 预交金管理
    ErrorCode PREPAYMENT_NOT_EXISTS = new ErrorCode(1_016_002_000, "预交金记录不存在");
    ErrorCode PREPAYMENT_AMOUNT_INVALID = new ErrorCode(1_016_002_001, "预交金金额无效");
    ErrorCode PREPAYMENT_STATUS_ERROR = new ErrorCode(1_016_002_002, "预交金状态不正确，无法操作");
    ErrorCode PREPAYMENT_CAN_NOT_DELETE = new ErrorCode(1_016_002_003, "预交金已使用，不能删除");
    ErrorCode PREPAYMENT_INSUFFICIENT_BALANCE = new ErrorCode(1_016_002_004, "预交金余额不足");
    ErrorCode PREPAYMENT_REFUND_NOT_EXISTS = new ErrorCode(1_016_002_005, "预交金退还记录不存在");
    ErrorCode PREPAYMENT_REFUND_EXCEED_BALANCE = new ErrorCode(1_016_002_006, "退还金额超过可用余额");
    ErrorCode PREPAYMENT_REFUND_ALREADY_APPLYING = new ErrorCode(1_016_002_007, "该预交金已有退还申请中");
    ErrorCode PREPAYMENT_REFUND_STATUS_ERROR = new ErrorCode(1_016_002_008, "退还状态不正确，无法操作");

    // 医嘱管理
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(1_016_003_000, "医嘱不存在");
    ErrorCode ORDER_ALREADY_STOPPED = new ErrorCode(1_016_003_001, "医嘱已停止");
    ErrorCode ORDER_ALREADY_COMPLETED = new ErrorCode(1_016_003_002, "医嘱已完成");
    ErrorCode ORDER_ALREADY_CANCELLED = new ErrorCode(1_016_003_003, "医嘱已作废");
    ErrorCode ORDER_ITEM_NOT_EXISTS = new ErrorCode(1_016_003_004, "医嘱明细不存在");
    ErrorCode ORDER_CANNOT_MODIFY = new ErrorCode(1_016_003_005, "医嘱已执行，无法修改");

    // 医嘱模板
    ErrorCode ORDER_TEMPLATE_NOT_EXISTS = new ErrorCode(1_016_003_010, "医嘱模板不存在");
    ErrorCode ORDER_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_016_003_011, "医嘱模板编码已存在");
    ErrorCode ORDER_TEMPLATE_ITEM_EMPTY = new ErrorCode(1_016_003_012, "医嘱模板明细项目编码或内容不能为空");

    // 诊断管理
    ErrorCode DIAGNOSIS_NOT_EXISTS = new ErrorCode(1_016_004_000, "诊断记录不存在");
    ErrorCode DIAGNOSIS_CODE_INVALID = new ErrorCode(1_016_004_001, "ICD-10诊断编码无效");
    ErrorCode DIAGNOSIS_ALREADY_DISCHARGE = new ErrorCode(1_016_004_002, "出院诊断已确定，无法修改");

    // 护理管理
    ErrorCode NURSING_RECORD_NOT_EXISTS = new ErrorCode(1_016_005_000, "护理记录不存在");
    ErrorCode MEDICATION_ADMIN_NOT_EXISTS = new ErrorCode(1_016_005_001, "给药记录不存在");
    ErrorCode MEDICATION_ADMIN_ALREADY_EXECUTED = new ErrorCode(1_016_005_002, "给药已执行");
    ErrorCode MEDICATION_ADMIN_BARCODE_MISMATCH = new ErrorCode(1_016_005_003, "药品条码不匹配");
    ErrorCode MEDICATION_ADMIN_WRISTBAND_MISMATCH = new ErrorCode(1_016_005_004, "患者腕带不匹配");
    ErrorCode NURSING_SHIFT_NOT_EXISTS = new ErrorCode(1_016_005_005, "交接班记录不存在");
    ErrorCode VITAL_SIGN_NOT_EXISTS = new ErrorCode(1_016_005_006, "生命体征记录不存在");
    ErrorCode NURSING_ASSESSMENT_NOT_EXISTS = new ErrorCode(1_016_005_007, "护理评估不存在");
    ErrorCode NURSING_MEASURE_NOT_EXISTS = new ErrorCode(1_016_005_008, "护理措施不存在");

    // 护理交接班
    ErrorCode NURSING_HANDOVER_NOT_EXISTS = new ErrorCode(1_016_005_010, "交接班记录不存在");
    ErrorCode NURSING_HANDOVER_STATUS_ERROR = new ErrorCode(1_016_005_011, "交接班状态不正确：{}");
    ErrorCode NURSING_HANDOVER_ALREADY_SIGNED = new ErrorCode(1_016_005_012, "交接班签名失败：{}");
    ErrorCode NURSING_HANDOVER_CANNOT_TAKEOVER = new ErrorCode(1_016_005_013, "接班失败：{}");
    ErrorCode NURSING_HANDOVER_CANNOT_CANCEL = new ErrorCode(1_016_005_014, "作废失败：{}");

    // 入院评估
    ErrorCode ASSESS_NOT_EXISTS = new ErrorCode(1_016_006_000, "入院评估不存在");
    ErrorCode ASSESS_ADMISSION_NOT_EXISTS = new ErrorCode(1_016_006_001, "住院记录不存在");
    ErrorCode ASSESS_TYPE_INVALID = new ErrorCode(1_016_006_002, "评估类型无效");
    ErrorCode ASSESS_SCORE_INVALID = new ErrorCode(1_016_006_003, "评估得分无效");
    ErrorCode ASSESS_RISK_LEVEL_INVALID = new ErrorCode(1_016_006_004, "风险等级无效");

    // 出院管理
    ErrorCode DISCHARGE_NOT_EXISTS = new ErrorCode(1_016_007_000, "出院记录不存在");
    ErrorCode DISCHARGE_FEE_NOT_SETTLED = new ErrorCode(1_016_007_001, "出院费用未结算");
    ErrorCode DISCHARGE_APPLY_NOT_EXISTS = new ErrorCode(1_016_007_002, "出院申请不存在");
    ErrorCode DISCHARGE_APPLY_ALREADY_EXISTS = new ErrorCode(1_016_007_003, "出院申请已存在");
    ErrorCode DISCHARGE_APPLY_STATUS_ERROR = new ErrorCode(1_016_007_004, "出院申请状态不正确，无法操作");
    ErrorCode DISCHARGE_APPLY_NOT_APPROVED = new ErrorCode(1_016_007_005, "出院申请未审批，无法确认出院");

    // 出院小结
    ErrorCode DISCHARGE_SUMMARY_NOT_EXISTS = new ErrorCode(1_016_007_010, "出院小结不存在");
    ErrorCode DISCHARGE_SUMMARY_ALREADY_EXISTS = new ErrorCode(1_016_007_011, "出院小结已存在，不能重复创建");
    ErrorCode DISCHARGE_SUMMARY_CANNOT_MODIFY = new ErrorCode(1_016_007_012, "出院小结已审核，不能修改");
    ErrorCode DISCHARGE_SUMMARY_CANNOT_REVIEW = new ErrorCode(1_016_007_013, "出院小结已审核，不能重复审核");
    ErrorCode DISCHARGE_SUMMARY_NOT_REVIEWED = new ErrorCode(1_016_007_014, "出院小结未审核，不能撤回");

    // ========== M06 药品管理 1_017_000_XXX ==========

    // 药品目录
    ErrorCode DRUG_NOT_EXISTS = new ErrorCode(1_017_000_000, "药品不存在");
    ErrorCode DRUG_CODE_DUPLICATE = new ErrorCode(1_017_000_001, "药品编码已存在");
    ErrorCode DRUG_ALREADY_STOPPED = new ErrorCode(1_017_000_002, "药品已停用");
    ErrorCode DRUG_EXPIRED = new ErrorCode(1_017_000_003, "药品已过期");

    // 药品库存
    ErrorCode DRUG_STOCK_NOT_EXISTS = new ErrorCode(1_017_001_000, "药品库存不存在");
    ErrorCode DRUG_STOCK_INSUFFICIENT = new ErrorCode(1_017_001_001, "药品库存不足");
    ErrorCode DRUG_STOCK_BATCH_NOT_EXISTS = new ErrorCode(1_017_001_002, "药品批次不存在");

    // 入库管理
    ErrorCode DRUG_INBOUND_NOT_EXISTS = new ErrorCode(1_017_002_000, "入库记录不存在");
    ErrorCode DRUG_INBOUND_ALREADY_AUDITED = new ErrorCode(1_017_002_001, "入库已审核");
    ErrorCode DRUG_INBOUND_BATCH_DUPLICATE = new ErrorCode(1_017_002_002, "批号已存在");

    // 出库管理
    ErrorCode DRUG_OUTBOUND_NOT_EXISTS = new ErrorCode(1_017_003_000, "出库记录不存在");
    ErrorCode DRUG_OUTBOUND_ALREADY_AUDITED = new ErrorCode(1_017_003_001, "出库已审核");
    ErrorCode DRUG_OUTBOUND_TARGET_NOT_EXISTS = new ErrorCode(1_017_003_002, "出库目标不存在");

    // 盘点管理
    ErrorCode DRUG_INVENTORY_NOT_EXISTS = new ErrorCode(1_017_004_000, "盘点记录不存在");
    ErrorCode DRUG_INVENTORY_ALREADY_COMPLETED = new ErrorCode(1_017_004_001, "盘点已完成");
    ErrorCode DRUG_INVENTORY_ITEM_NOT_EXISTS = new ErrorCode(1_017_004_002, "盘点明细不存在");

    // 采购管理
    ErrorCode DRUG_PURCHASE_NOT_EXISTS = new ErrorCode(1_017_005_000, "采购计划不存在");
    ErrorCode DRUG_PURCHASE_ALREADY_SUBMITTED = new ErrorCode(1_017_005_001, "采购计划已提交");
    ErrorCode DRUG_PURCHASE_ALREADY_APPROVED = new ErrorCode(1_017_005_002, "采购计划已审批");
    ErrorCode DRUG_PURCHASE_ITEM_NOT_EXISTS = new ErrorCode(1_017_005_003, "采购明细不存在");
    ErrorCode SUPPLIER_NOT_EXISTS = new ErrorCode(1_017_005_004, "供应商不存在");

    // 处方审核与合理用药
    ErrorCode DRUG_INTERACTION_EXISTS = new ErrorCode(1_017_006_000, "存在药物相互作用风险");
    ErrorCode DRUG_CONTRAINDICATION_EXISTS = new ErrorCode(1_017_006_001, "存在配伍禁忌");
    ErrorCode DRUG_ALLERGY_EXISTS = new ErrorCode(1_017_006_002, "患者对该药品过敏");
    ErrorCode DRUG_DOSAGE_INVALID = new ErrorCode(1_017_006_003, "药品剂量超出安全范围");
    ErrorCode DRUG_FREQUENCY_INVALID = new ErrorCode(1_017_006_004, "用药频次无效");

    // 特殊药品管理
    ErrorCode SPECIAL_DRUG_NOT_EXISTS = new ErrorCode(1_017_007_000, "特殊药品不存在");
    ErrorCode SPECIAL_DRUG_RECORD_NOT_EXISTS = new ErrorCode(1_017_007_001, "特殊药品记录不存在");
    ErrorCode NARCOTIC_PRESCRIPTION_NOT_EXISTS = new ErrorCode(1_017_007_002, "麻精处方不存在");
    ErrorCode SPECIAL_DRUG_QUANTITY_EXCEED = new ErrorCode(1_017_007_003, "特殊药品数量超出限制");
    ErrorCode SPECIAL_DRUG_NOT_AUTHORIZED = new ErrorCode(1_017_007_004, "无特殊药品处方权");

    // 退药管理
    ErrorCode DRUG_RETURN_NOT_EXISTS = new ErrorCode(1_017_008_000, "退药申请不存在");
    ErrorCode DRUG_RETURN_ITEM_NOT_EXISTS = new ErrorCode(1_017_008_001, "退药明细不存在");
    ErrorCode DRUG_RETURN_STATUS_ERROR = new ErrorCode(1_017_008_002, "退药状态不正确，无法操作：{}");
    ErrorCode DRUG_RETURN_ALREADY_AUDITED = new ErrorCode(1_017_008_003, "退药申请已审核");
    ErrorCode DRUG_RETURN_ALREADY_CANCELLED = new ErrorCode(1_017_008_004, "退药申请已取消");
    ErrorCode DRUG_RETURN_ALREADY_INBOUNDED = new ErrorCode(1_017_008_005, "退药已入库");
    ErrorCode DRUG_RETURN_ALREADY_REFUNDED = new ErrorCode(1_017_008_006, "退药已退款");
    ErrorCode DRUG_RETURN_QUANTITY_EXCEED = new ErrorCode(1_017_008_007, "退药数量超过原发药数量");
    ErrorCode DRUG_RETURN_INBOUND_NOT_EXISTS = new ErrorCode(1_017_008_008, "退药入库记录不存在");
    ErrorCode DRUG_RETURN_INBOUND_ITEM_NOT_EXISTS = new ErrorCode(1_017_008_009, "退药入库明细不存在");

    // ========== M04 检验管理 1_018_000_XXX ==========

    // 检验申请
    ErrorCode LAB_REQUEST_NOT_EXISTS = new ErrorCode(1_018_000_000, "检验申请不存在");
    ErrorCode LAB_REQUEST_ALREADY_CANCELLED = new ErrorCode(1_018_000_001, "检验申请已取消");
    ErrorCode LAB_REQUEST_ALREADY_COMPLETED = new ErrorCode(1_018_000_002, "检验申请已完成");
    ErrorCode LAB_REQUEST_ALREADY_COLLECTED = new ErrorCode(1_018_000_003, "标本已采集，无法修改");
    ErrorCode LAB_REQUEST_NO_ITEMS = new ErrorCode(1_018_000_004, "检验申请无项目");
    ErrorCode LAB_REQUEST_DUPLICATE = new ErrorCode(1_018_000_005, "检验申请单号已存在");
    ErrorCode LAB_REQUEST_PATIENT_NOT_MATCH = new ErrorCode(1_018_000_006, "患者信息不匹配");
    ErrorCode LAB_REQUEST_STATUS_ERROR = new ErrorCode(1_018_000_007, "检验申请状态不正确，无法操作");

    // 检验标本
    ErrorCode LAB_SPECIMEN_NOT_EXISTS = new ErrorCode(1_018_001_000, "标本不存在");
    ErrorCode LAB_SPECIMEN_ALREADY_REJECTED = new ErrorCode(1_018_001_001, "标本已拒收");
    ErrorCode LAB_SPECIMEN_ALREADY_RECEIVED = new ErrorCode(1_018_001_002, "标本已接收");
    ErrorCode LAB_SPECIMEN_BARCODE_DUPLICATE = new ErrorCode(1_018_001_003, "标本条码已存在");
    ErrorCode LAB_SPECIMEN_NOT_COLLECTED = new ErrorCode(1_018_001_004, "标本未采集");
    ErrorCode LAB_SPECIMEN_EXPIRED = new ErrorCode(1_018_001_005, "标本已过期");

    // 检验项目
    ErrorCode LAB_ITEM_NOT_EXISTS = new ErrorCode(1_018_002_000, "检验项目不存在");
    ErrorCode LAB_ITEM_ALREADY_STOPPED = new ErrorCode(1_018_002_001, "检验项目已停用");

    // 检验结果
    ErrorCode LAB_RESULT_NOT_EXISTS = new ErrorCode(1_018_003_000, "检验结果不存在");
    ErrorCode LAB_RESULT_NOT_AUDITED = new ErrorCode(1_018_003_001, "检验结果未审核");
    ErrorCode LAB_RESULT_ALREADY_PUBLISHED = new ErrorCode(1_018_003_002, "检验结果已发布");

    // 检验报告
    ErrorCode LAB_REPORT_NOT_EXISTS = new ErrorCode(1_018_004_000, "检验报告不存在");
    ErrorCode LAB_REPORT_NOT_AUDITED = new ErrorCode(1_018_004_001, "检验报告未审核");
    ErrorCode LAB_REPORT_ALREADY_PUBLISHED = new ErrorCode(1_018_004_002, "检验报告已发布");

    // 危急值管理
    ErrorCode CRITICAL_VALUE_NOT_EXISTS = new ErrorCode(1_018_005_000, "危急值记录不存在");
    ErrorCode CRITICAL_VALUE_ALREADY_CONFIRMED = new ErrorCode(1_018_005_001, "危急值已确认");
    ErrorCode CRITICAL_VALUE_NOT_CONFIRMED = new ErrorCode(1_018_005_002, "危急值未确认，请先确认");

    // ========== M03 检查管理 1_019_000_XXX ==========

    // 检查申请
    ErrorCode EXAM_REQUEST_NOT_EXISTS = new ErrorCode(1_019_000_000, "检查申请不存在");
    ErrorCode EXAM_REQUEST_STATUS_ERROR = new ErrorCode(1_019_000_001, "检查申请状态不正确，无法操作");
    ErrorCode EXAM_REQUEST_CANNOT_DELETE = new ErrorCode(1_019_000_002, "检查申请不能删除，只有已申请或已取消状态可删除");
    ErrorCode EXAM_REQUEST_CANNOT_CANCEL = new ErrorCode(1_019_000_003, "检查申请不能取消，只有已申请或已预约状态可取消");
    ErrorCode EXAM_REQUEST_ALREADY_CANCELLED = new ErrorCode(1_019_000_004, "检查申请已取消");
    ErrorCode EXAM_REQUEST_ALREADY_COMPLETED = new ErrorCode(1_019_000_005, "检查申请已完成");
    ErrorCode EXAM_REQUEST_NO_ITEMS = new ErrorCode(1_019_000_006, "检查申请无项目");
    ErrorCode EXAM_REQUEST_DUPLICATE = new ErrorCode(1_019_000_007, "检查申请单号已存在");

    // 检查项目
    ErrorCode EXAM_ITEM_NOT_EXISTS = new ErrorCode(1_019_001_000, "检查项目不存在");
    ErrorCode EXAM_ITEM_CODE_DUPLICATE = new ErrorCode(1_019_001_001, "检查项目编码已存在");
    ErrorCode EXAM_ITEM_ALREADY_STOPPED = new ErrorCode(1_019_001_002, "检查项目已停用");

    // 检查报告
    ErrorCode EXAM_REPORT_NOT_EXISTS = new ErrorCode(1_019_002_000, "检查报告不存在");
    ErrorCode EXAM_REPORT_NOT_AUDITED = new ErrorCode(1_019_002_001, "检查报告未审核");
    ErrorCode EXAM_REPORT_ALREADY_PUBLISHED = new ErrorCode(1_019_002_002, "检查报告已发布");
    ErrorCode EXAM_REPORT_ALREADY_WITHDRAWN = new ErrorCode(1_019_002_003, "检查报告已撤回");

    // 检查设备
    ErrorCode EXAM_EQUIPMENT_NOT_EXISTS = new ErrorCode(1_019_003_000, "检查设备不存在");
    ErrorCode EXAM_EQUIPMENT_MAINTENANCE = new ErrorCode(1_019_003_001, "检查设备正在维护");
    ErrorCode EXAM_EQUIPMENT_STOPPED = new ErrorCode(1_019_003_002, "检查设备已停用");

    // ========== M02-05 住院结算 1_016_008_XXX ==========

    // 住院结算
    ErrorCode SETTLEMENT_NOT_EXISTS = new ErrorCode(1_016_008_000, "住院结算单不存在");
    ErrorCode SETTLEMENT_STATUS_ERROR = new ErrorCode(1_016_008_001, "结算状态不正确，无法操作");
    ErrorCode SETTLEMENT_ALREADY_CONFIRMED = new ErrorCode(1_016_008_002, "结算单已确认，无法修改");
    ErrorCode SETTLEMENT_ALREADY_REFUNDED = new ErrorCode(1_016_008_003, "结算单已退费");
    ErrorCode SETTLEMENT_ALREADY_CANCELLED = new ErrorCode(1_016_008_004, "结算单已作废");

    // 住院费用明细
    ErrorCode FEE_NOT_EXISTS = new ErrorCode(1_016_008_010, "住院费用明细不存在");
    ErrorCode FEE_ALREADY_SETTLED = new ErrorCode(1_016_008_011, "费用明细已结算，无法删除");
    ErrorCode FEE_ALREADY_REFUNDED = new ErrorCode(1_016_008_012, "费用明细已退费");

}