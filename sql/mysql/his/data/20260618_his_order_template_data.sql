INSERT INTO `his_order_template` (`template_code`, `template_name`, `template_category`, `order_type`, `dept_id`, `dept_name`, `diagnosis_code`, `diagnosis_name`, `content_summary`, `status`, `tenant_id`, `creator`)
VALUES ('TPL_PNEUMONIA_001', '肺炎入院常规医嘱', 3, 1, NULL, NULL, 'J18.9', '肺炎', '包含抗感染、止咳化痰等医嘱', 1, 1, '1');

-- 示例：肺炎医嘱模板明细
INSERT INTO `his_order_template_item` (`template_id`, `order_content`, `item_code`, `item_name`, `order_type`, `order_category`, `dosage`, `dosage_unit`, `frequency_code`, `frequency_name`, `route_code`, `route_name`, `duration`, `urgency`, `skin_test_flag`, `tenant_id`, `creator`)
VALUES
(1, '注射用头孢曲松钠 2g qd', 'DRUG001', '注射用头孢曲松钠', 1, 1, '2', 'g', 'FREQ001', '每日一次', 'ROUTE001', '静脉滴注', 7, 0, 1, 1, '1'),
(1, '氨溴索口服液 10ml tid', 'DRUG002', '氨溴索口服液', 1, 1, '10', 'ml', 'FREQ002', '每日三次', 'ROUTE002', '口服', 7, 0, 0, 1, '1'),
(1, '血常规', 'LAB001', '血常规', 2, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 1, '1'),
(1, '胸部CT', 'EXAM001', '胸部CT', 3, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 1, '1');
