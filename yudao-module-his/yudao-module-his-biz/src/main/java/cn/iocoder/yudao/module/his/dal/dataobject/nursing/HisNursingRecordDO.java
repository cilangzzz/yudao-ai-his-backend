package cn.iocoder.yudao.module.his.dal.dataobject.nursing;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 护理记录 DO
 *
 * 记录护理人员的护理过程和观察内容
 */
@TableName("his_nursing_record")
@KeySequence("his_nursing_record_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisNursingRecordDO extends TenantBaseDO {

    /**
     * 护理记录ID
     */
    @TableId
    private Long id;

    /**
     * 记录编号
     */
    private String recordNo;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 住院ID
     */
    private Long admissionId;

    /**
     * 护士ID
     */
    private Long nurseId;

    /**
     * 护士姓名
     */
    private String nurseName;

    /**
     * 记录类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.NursingRecordTypeEnum}
     * 1-一般护理记录 2-危重护理记录 3-手术护理记录 4-交接班记录 5-特殊护理记录
     */
    private Integer recordType;

    /**
     * 标题
     */
    private String title;

    /**
     * 护理内容
     */
    private String content;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    // ==================== 签名信息 ====================

    /**
     * 签名状态
     *
     * 0-未签名 1-已签名
     */
    private Integer signatureStatus;

    /**
     * 签名时间
     */
    private LocalDateTime signatureTime;

    /**
     * 电子签名
     */
    private String signature;

    // ==================== 审核信息 ====================

    /**
     * 审核状态
     *
     * 0-未审核 1-已审核
     */
    private Integer auditStatus;

    /**
     * 审核护士ID
     */
    private Long auditNurseId;

    /**
     * 审核护士姓名
     */
    private String auditNurseName;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否已签名
     */
    public boolean isSigned() {
        return signatureStatus != null && signatureStatus == 1;
    }

    /**
     * 是否已审核
     */
    public boolean isAudited() {
        return auditStatus != null && auditStatus == 1;
    }

    /**
     * 是否可以签名
     */
    public boolean canSign() {
        return signatureStatus == null || signatureStatus == 0;
    }

    /**
     * 是否可以审核
     */
    public boolean canAudit() {
        return isSigned() && !isAudited();
    }

    /**
     * 获取记录类型名称
     */
    public String getRecordTypeName() {
        if (recordType == null) {
            return "未知";
        }
        return switch (recordType) {
            case 1 -> "一般护理记录";
            case 2 -> "危重护理记录";
            case 3 -> "手术护理记录";
            case 4 -> "交接班记录";
            case 5 -> "特殊护理记录";
            default -> "未知";
        };
    }
}