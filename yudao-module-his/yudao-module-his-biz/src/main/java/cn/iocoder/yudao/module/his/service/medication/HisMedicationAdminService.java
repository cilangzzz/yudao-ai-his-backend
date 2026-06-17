package cn.iocoder.yudao.module.his.service.medication;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.medication.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.medication.HisMedicationAdminDO;

import java.util.List;

/**
 * 给药记录 Service 接口
 *
 * 用于eMAR闭环给药管理
 */
public interface HisMedicationAdminService {

    /**
     * 创建给药记录
     */
    Long createMedicationAdmin(HisMedicationAdminSaveReqVO createReqVO);

    /**
     * 更新给药记录
     */
    void updateMedicationAdmin(HisMedicationAdminSaveReqVO updateReqVO);

    /**
     * 删除给药记录
     */
    void deleteMedicationAdmin(Long id);

    /**
     * 获取给药记录
     */
    HisMedicationAdminDO getMedicationAdmin(Long id);

    /**
     * 分页查询给药记录
     */
    PageResult<HisMedicationAdminDO> getMedicationAdminPage(HisMedicationAdminPageReqVO pageReqVO);

    /**
     * 按住院ID查询给药记录列表
     */
    List<HisMedicationAdminDO> getMedicationAdminListByAdmission(Long admissionId);

    /**
     * 按医嘱ID查询给药记录列表
     */
    List<HisMedicationAdminDO> getMedicationAdminListByOrder(Long orderId);

    /**
     * 查询待执行的给药记录
     */
    List<HisMedicationAdminDO> getPendingMedicationAdminList(Long admissionId);

    /**
     * 腕带扫描验证
     *
     * @param reqVO 腕带验证请求
     * @return 验证结果
     */
    WristbandVerifyResult verifyWristband(WristbandVerifyReqVO reqVO);

    /**
     * 药品条码扫描验证
     *
     * @param reqVO 药品验证请求
     * @return 验证结果
     */
    DrugBarcodeVerifyResult verifyDrugBarcode(DrugBarcodeVerifyReqVO reqVO);

    /**
     * 给药执行确认
     *
     * 实现闭环给药管理(HIMSS EMRAM Stage 5)
     * - 双重核对验证（腕带+药品条码）
     * - 记录执行时间和执行护士
     * - 更新医嘱执行状态
     *
     * @param reqVO 执行确认请求
     * @return 给药记录ID
     */
    Long confirmMedicationAdmin(MedicationAdminConfirmReqVO reqVO);

    /**
     * 记录未执行原因
     */
    void recordNotExecutedReason(Long adminId, String reason, String reasonType);

    /**
     * 校验给药记录是否存在
     */
    HisMedicationAdminDO validateMedicationAdminExists(Long id);

    /**
     * 腕带验证结果
     */
    class WristbandVerifyResult {
        private boolean matched;
        private String patientName;
        private String bedNo;
        private List<String> allergyHistory;
        private String message;

        public static WristbandVerifyResult success(String patientName, String bedNo, List<String> allergyHistory) {
            WristbandVerifyResult result = new WristbandVerifyResult();
            result.matched = true;
            result.patientName = patientName;
            result.bedNo = bedNo;
            result.allergyHistory = allergyHistory;
            result.message = "腕带验证通过";
            return result;
        }

        public static WristbandVerifyResult fail(String message) {
            WristbandVerifyResult result = new WristbandVerifyResult();
            result.matched = false;
            result.message = message;
            return result;
        }

        // Getters
        public boolean isMatched() { return matched; }
        public String getPatientName() { return patientName; }
        public String getBedNo() { return bedNo; }
        public List<String> getAllergyHistory() { return allergyHistory; }
        public String getMessage() { return message; }
    }

    /**
     * 药品条码验证结果
     */
    class DrugBarcodeVerifyResult {
        private boolean matched;
        private String drugName;
        private String spec;
        private String batchNo;
        private String expireDate;
        private String scheduledTime;
        private String message;

        public static DrugBarcodeVerifyResult success(String drugName, String spec, String batchNo, String expireDate, String scheduledTime) {
            DrugBarcodeVerifyResult result = new DrugBarcodeVerifyResult();
            result.matched = true;
            result.drugName = drugName;
            result.spec = spec;
            result.batchNo = batchNo;
            result.expireDate = expireDate;
            result.scheduledTime = scheduledTime;
            result.message = "药品匹配成功";
            return result;
        }

        public static DrugBarcodeVerifyResult fail(String message) {
            DrugBarcodeVerifyResult result = new DrugBarcodeVerifyResult();
            result.matched = false;
            result.message = message;
            return result;
        }

        // Getters
        public boolean isMatched() { return matched; }
        public String getDrugName() { return drugName; }
        public String getSpec() { return spec; }
        public String getBatchNo() { return batchNo; }
        public String getExpireDate() { return expireDate; }
        public String getScheduledTime() { return scheduledTime; }
        public String getMessage() { return message; }
    }
}