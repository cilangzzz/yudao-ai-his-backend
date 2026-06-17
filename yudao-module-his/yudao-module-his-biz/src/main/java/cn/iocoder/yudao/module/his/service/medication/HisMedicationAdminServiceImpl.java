package cn.iocoder.yudao.module.his.service.medication;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.medication.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.medication.HisMedicationAdminDO;
import cn.iocoder.yudao.module.his.dal.dataobject.order.HisOrderDO;
import cn.iocoder.yudao.module.his.dal.mysql.medication.HisMedicationAdminMapper;
import cn.iocoder.yudao.module.his.enums.CheckResultEnum;
import cn.iocoder.yudao.module.his.enums.MedicationAdminStatusEnum;
import cn.iocoder.yudao.module.his.enums.ScanStatusEnum;
import cn.iocoder.yudao.module.his.service.admission.HisAdmissionService;
import cn.iocoder.yudao.module.his.service.order.HisOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 给药记录 Service 实现类
 *
 * 实现闭环给药管理（HIMSS EMRAM Stage 5核心功能）
 */
@Service
@Validated
public class HisMedicationAdminServiceImpl implements HisMedicationAdminService {

    @Resource
    private HisMedicationAdminMapper medicationAdminMapper;

    @Resource
    private HisAdmissionService admissionService;

    @Resource
    private HisOrderService orderService;

    @Override
    public Long createMedicationAdmin(HisMedicationAdminSaveReqVO createReqVO) {
        // 校验住院记录
        HisAdmissionDO admission = admissionService.validateAdmissionExists(createReqVO.getAdmissionId());

        // 校验医嘱
        HisOrderDO order = orderService.validateOrderExists(createReqVO.getOrderId());

        // 创建给药记录
        HisMedicationAdminDO admin = BeanUtils.toBean(createReqVO, HisMedicationAdminDO.class);
        admin.setPatientId(admission.getPatientId());
        admin.setPatientName(admission.getPatientName());
        admin.setAdmissionNo(admission.getAdmissionNo());
        admin.setOrderNo(order.getOrderNo());
        admin.setStatus(MedicationAdminStatusEnum.PENDING.getStatus());
        admin.setWristbandScanStatus(ScanStatusEnum.NOT_SCANNED.getStatus());
        admin.setDrugScanStatus(ScanStatusEnum.NOT_SCANNED.getStatus());
        admin.setChargeStatus(0); // 未记账

        medicationAdminMapper.insert(admin);
        return admin.getId();
    }

    @Override
    public void updateMedicationAdmin(HisMedicationAdminSaveReqVO updateReqVO) {
        validateMedicationAdminExists(updateReqVO.getId());
        HisMedicationAdminDO updateObj = BeanUtils.toBean(updateReqVO, HisMedicationAdminDO.class);
        medicationAdminMapper.updateById(updateObj);
    }

    @Override
    public void deleteMedicationAdmin(Long id) {
        validateMedicationAdminExists(id);
        medicationAdminMapper.deleteById(id);
    }

    @Override
    public HisMedicationAdminDO getMedicationAdmin(Long id) {
        return medicationAdminMapper.selectById(id);
    }

    @Override
    public PageResult<HisMedicationAdminDO> getMedicationAdminPage(HisMedicationAdminPageReqVO pageReqVO) {
        return medicationAdminMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisMedicationAdminDO> getMedicationAdminListByAdmission(Long admissionId) {
        return medicationAdminMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public List<HisMedicationAdminDO> getMedicationAdminListByOrder(Long orderId) {
        return medicationAdminMapper.selectListByOrderId(orderId);
    }

    @Override
    public List<HisMedicationAdminDO> getPendingMedicationAdminList(Long admissionId) {
        return medicationAdminMapper.selectPendingList(admissionId);
    }

    @Override
    public WristbandVerifyResult verifyWristband(WristbandVerifyReqVO reqVO) {
        // 1. 获取住院记录
        HisAdmissionDO admission = admissionService.validateAdmissionExists(reqVO.getAdmissionId());

        // 2. 验证腕带编码
        // 实际项目中应该通过腕带编码查询患者信息，这里简化处理
        // 腕带编码格式: WB + 住院号
        String expectedWristbandCode = "WB" + admission.getAdmissionNo();
        if (!expectedWristbandCode.equals(reqVO.getWristbandCode())) {
            return WristbandVerifyResult.fail("腕带不匹配，请确认患者身份");
        }

        // 3. 返回患者信息
        // TODO: 查询患者过敏史
        List<String> allergyHistory = new ArrayList<>();

        return WristbandVerifyResult.success(
            admission.getPatientName(),
            admission.getBedNo(),
            allergyHistory
        );
    }

    @Override
    public DrugBarcodeVerifyResult verifyDrugBarcode(DrugBarcodeVerifyReqVO reqVO) {
        // 1. 获取医嘱信息
        HisOrderDO order = orderService.validateOrderExists(reqVO.getOrderId());

        // 2. 验证药品条码
        // 实际项目中应该通过条码查询药品信息，这里简化处理
        // 药品条码格式: DR + 药品编码
        if (!order.isDrugOrder()) {
            return DrugBarcodeVerifyResult.fail("该医嘱不是药品医嘱");
        }

        // 3. 验证是否过期
        // TODO: 查询药品库存信息验证批号和有效期

        // 4. 返回药品信息
        return DrugBarcodeVerifyResult.success(
            order.getItemName(),
            null, // 规格
            null, // 批号
            null, // 有效期
            order.getStartTime() != null ? order.getStartTime().toString() : null
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long confirmMedicationAdmin(MedicationAdminConfirmReqVO reqVO) {
        // 1. 校验给药记录
        HisMedicationAdminDO admin = validateMedicationAdminExists(reqVO.getAdminId());

        // 2. 校验是否可以执行
        if (!admin.canExecute()) {
            throw exception(MEDICATION_ADMIN_ALREADY_EXECUTED);
        }

        // 3. 闭环给药核对验证（HIMSS EMRAM Stage 5核心要求）
        if (!reqVO.getWristbandVerified()) {
            throw exception(MEDICATION_ADMIN_WRISTBAND_MISMATCH);
        }
        if (!reqVO.getDrugVerified()) {
            throw exception(MEDICATION_ADMIN_BARCODE_MISMATCH);
        }

        // 4. 更新给药记录
        HisMedicationAdminDO updateObj = new HisMedicationAdminDO();
        updateObj.setId(reqVO.getAdminId());
        updateObj.setStatus(MedicationAdminStatusEnum.EXECUTED.getStatus());
        updateObj.setActualTime(reqVO.getAdminTime() != null ? reqVO.getAdminTime() : LocalDateTime.now());
        updateObj.setNurseId(reqVO.getNurseId());
        updateObj.setWristbandScanStatus(ScanStatusEnum.MATCHED.getStatus());
        updateObj.setWristbandScanTime(LocalDateTime.now());
        updateObj.setDrugScanStatus(ScanStatusEnum.MATCHED.getStatus());
        updateObj.setDrugScanTime(LocalDateTime.now());
        updateObj.setCheckResult(CheckResultEnum.PASS.getResult());
        updateObj.setDrugBatchNo(reqVO.getDrugBatchNo());
        updateObj.setRemark(reqVO.getRemark());

        medicationAdminMapper.updateById(updateObj);

        // 5. 更新医嘱执行状态
        orderService.updateOrderExecuteStatus(admin.getOrderId());

        return reqVO.getAdminId();
    }

    @Override
    public void recordNotExecutedReason(Long adminId, String reason, String reasonType) {
        HisMedicationAdminDO admin = validateMedicationAdminExists(adminId);

        HisMedicationAdminDO updateObj = new HisMedicationAdminDO();
        updateObj.setId(adminId);
        updateObj.setStatus(MedicationAdminStatusEnum.NOT_EXECUTED.getStatus());
        updateObj.setReason(reason);
        updateObj.setReasonType(reasonType);
        updateObj.setNotifyDoctor(true); // 未执行需通知医生

        medicationAdminMapper.updateById(updateObj);
    }

    @Override
    public HisMedicationAdminDO validateMedicationAdminExists(Long id) {
        if (id == null) {
            return null;
        }
        HisMedicationAdminDO admin = medicationAdminMapper.selectById(id);
        if (admin == null) {
            throw exception(MEDICATION_ADMIN_NOT_EXISTS);
        }
        return admin;
    }
}