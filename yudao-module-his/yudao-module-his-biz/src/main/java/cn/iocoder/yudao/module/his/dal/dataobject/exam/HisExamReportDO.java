package cn.iocoder.yudao.module.his.dal.dataobject.exam;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 检查报告 DO
 *
 * 检查完成后的报告记录，包括影像报告、检验报告、病理报告
 *
 * @author yudao
 */
@TableName(value = "his_exam_report", autoResultMap = true)
@KeySequence("his_exam_report_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisExamReportDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 报告编号
     */
    private String reportNo;

    /**
     * 申请ID
     */
    private Long requestId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 检查类型
     *
     * 1-影像检查 2-检验检查 3-病理检查 4-功能检查
     */
    private Integer examType;

    /**
     * 检查类别
     */
    private String examCategory;

    /**
     * 检查部位
     */
    private String examPart;

    /**
     * 检查方法
     */
    private String examMethod;

    /**
     * 检查时间
     */
    private LocalDateTime examTime;

    /**
     * 报告医生ID
     */
    private Long reportDoctorId;

    /**
     * 报告医生姓名
     */
    private String reportDoctorName;

    /**
     * 报告时间
     */
    private LocalDateTime reportTime;

    /**
     * 审核医生ID
     */
    private Long auditDoctorId;

    /**
     * 审核医生姓名
     */
    private String auditDoctorName;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 报告类型
     *
     * 1-初步报告 2-最终报告
     */
    private Integer reportType;

    /**
     * 状态
     *
     * 1-待审核 2-已审核 3-已发布 4-已撤回
     */
    private Integer reportStatus;

    /**
     * 报告内容/检查所见
     */
    private String reportContent;

    /**
     * 诊断结论/检查结论
     */
    private String diagnosisResult;

    /**
     * 印象/分析意见
     */
    private String impression;

    /**
     * 建议
     */
    private String recommendation;

    /**
     * 对比历史检查ID
     */
    private Long comparisonWith;

    /**
     * 对比结果
     */
    private String comparisonResult;

    /**
     * 影像数量
     */
    private Integer imageCount;

    /**
     * 影像URL列表(JSON数组)
     */
    private String imageUrls;

    /**
     * 附件URL列表
     */
    private String attachmentUrls;

    /**
     * 阳性发现
     */
    private String positiveFindings;

    /**
     * 阴性发现
     */
    private String negativeFindings;

    /**
     * 危急值标志
     *
     * 0-无 1-有
     */
    private Integer criticalFlag;

    /**
     * 危急值内容
     */
    private String criticalValue;

    /**
     * 危急值报告时间
     */
    private LocalDateTime criticalTime;

    /**
     * 危急值处理人
     */
    private Long criticalHandler;

    /**
     * 危急值处理时间
     */
    private LocalDateTime criticalHandleTime;

    /**
     * 打印次数
     */
    private Integer printCount;

    /**
     * 最后打印时间
     */
    private LocalDateTime lastPrintTime;

    /**
     * 备注
     */
    private String remark;

}