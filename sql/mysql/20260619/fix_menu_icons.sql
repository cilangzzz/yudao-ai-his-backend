-- ============================================
-- HIS 医院信息系统 - 菜单图标修复 SQL
-- 生成时间: 2026-06-19
-- ============================================

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
-- 执行修复
-- ============================================


-- 父菜单 ID: 100
-- 用户新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1002;
-- 用户修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1003;
-- 用户删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1004;
-- 重置密码 (path: N/A)
UPDATE system_menu SET icon = 'lucide:refresh-cw' WHERE id = 1007;

-- 父菜单 ID: 101
-- 角色新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1009;
-- 角色修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1010;
-- 角色删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1011;
-- 设置角色菜单权限 (path: N/A)
UPDATE system_menu SET icon = 'lucide:folder' WHERE id = 1063;
-- 设置角色数据权限 (path: N/A)
UPDATE system_menu SET icon = 'lucide:folder' WHERE id = 1064;
-- 设置用户角色 (path: N/A)
UPDATE system_menu SET icon = 'lucide:folder' WHERE id = 1065;

-- 父菜单 ID: 103
-- 部门新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1018;
-- 部门修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1019;
-- 部门删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1020;

-- 父菜单 ID: 104
-- 岗位新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1022;
-- 岗位修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1023;
-- 岗位删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1024;

-- 父菜单 ID: 105
-- 字典新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1027;
-- 字典修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1028;
-- 字典删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1029;

-- 父菜单 ID: 106
-- 配置查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1031;
-- 配置新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1032;
-- 配置修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1033;
-- 配置删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1034;
-- 配置导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1035;

-- 父菜单 ID: 107
-- 公告新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1037;
-- 公告修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1038;
-- 公告删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1039;

-- 父菜单 ID: 109
-- 令牌列表 (path: N/A)
UPDATE system_menu SET icon = 'lucide:folder' WHERE id = 1046;
-- 令牌删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1048;

-- 父菜单 ID: 110
-- 任务新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1050;
-- 任务修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1051;
-- 任务删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1052;
-- 状态修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1053;
-- 任务导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1054;
-- 任务触发 (path: N/A)
UPDATE system_menu SET icon = 'lucide:zap' WHERE id = 1075;
-- 任务查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1087;

-- 父菜单 ID: 113
-- 获得 Redis 监控信息 (path: N/A)
UPDATE system_menu SET icon = 'lucide:activity' WHERE id = 1066;
-- 获得 Redis Key 列表 (path: N/A)
UPDATE system_menu SET icon = 'lucide:folder' WHERE id = 1067;

-- 父菜单 ID: 115
-- 生成修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1056;
-- 生成删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1057;
-- 导入代码 (path: N/A)
UPDATE system_menu SET icon = 'lucide:upload' WHERE id = 1058;
-- 预览代码 (path: N/A)
UPDATE system_menu SET icon = 'lucide:eye' WHERE id = 1059;
-- 生成代码 (path: N/A)
UPDATE system_menu SET icon = 'lucide:file-plus' WHERE id = 1060;

-- 父菜单 ID: 500
-- 操作查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1040;
-- 日志导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1042;

-- 父菜单 ID: 1078
-- 日志导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1082;
-- 日志查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1088;

-- 父菜单 ID: 1084
-- 日志处理 (path: N/A)
UPDATE system_menu SET icon = 'lucide:folder' WHERE id = 1085;
-- 日志导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1086;
-- 日志查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1089;

-- 父菜单 ID: 1090
-- 文件查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1091;
-- 文件删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1092;

-- 父菜单 ID: 1094
-- 短信渠道查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1095;
-- 短信渠道创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1096;
-- 短信渠道更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1097;
-- 短信渠道删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1098;

-- 父菜单 ID: 1100
-- 短信模板查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1101;
-- 短信模板创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1102;
-- 短信模板更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1103;
-- 短信模板删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1104;
-- 短信模板导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1105;
-- 发送测试短信 (path: N/A)
UPDATE system_menu SET icon = 'lucide:send' WHERE id = 1106;

-- 父菜单 ID: 1107
-- 短信日志查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1108;
-- 短信日志导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1109;

-- 父菜单 ID: 1138
-- 租户查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1139;
-- 租户创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1140;
-- 租户更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1141;
-- 租户删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1142;
-- 租户导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1143;
-- 租户切换 (path: N/A)
UPDATE system_menu SET icon = 'lucide:repeat' WHERE id = 5010;

-- 父菜单 ID: 1225
-- 租户套餐查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1226;
-- 租户套餐创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1227;
-- 租户套餐更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1228;
-- 租户套餐删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1229;

-- 父菜单 ID: 1237
-- 文件配置查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1238;
-- 文件配置创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1239;
-- 文件配置更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1240;
-- 文件配置删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1241;
-- 文件配置导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1242;

-- 父菜单 ID: 1255
-- 数据源配置查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1256;
-- 数据源配置创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1257;
-- 数据源配置更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1258;
-- 数据源配置删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1259;
-- 数据源配置导出 (path: N/A)
UPDATE system_menu SET icon = 'lucide:download' WHERE id = 1260;

-- 父菜单 ID: 1263
-- 客户端查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 1264;
-- 客户端创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 1265;
-- 客户端更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 1266;
-- 客户端删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 1267;

-- 父菜单 ID: 2131
-- 账号查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 2132;
-- 账号创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 2133;
-- 账号更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 2134;
-- 账号删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 2135;

-- 父菜单 ID: 2136
-- 模版查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 2137;
-- 模版创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 2138;
-- 模版更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 2139;
-- 模版删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 2140;
-- 发送测试邮件 (path: N/A)
UPDATE system_menu SET icon = 'lucide:send' WHERE id = 2143;

-- 父菜单 ID: 2141
-- 日志查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 2142;

-- 父菜单 ID: 2145
-- 站内信模板查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 2146;
-- 站内信模板创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 2147;
-- 站内信模板更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 2148;
-- 站内信模板删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 2149;
-- 发送测试站内信 (path: N/A)
UPDATE system_menu SET icon = 'lucide:send' WHERE id = 2150;

-- 父菜单 ID: 2151
-- 站内信消息查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 2152;

-- 父菜单 ID: 2448
-- 三方应用查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 2449;
-- 三方应用创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 2450;
-- 三方应用更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 2451;
-- 三方应用删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 2452;

-- 父菜单 ID: 6763
-- 入院登记查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6769;
-- 入院登记创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 6770;
-- 入院登记更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 6771;
-- 入院登记删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 6772;
-- 出院办理 (path: N/A)
UPDATE system_menu SET icon = 'lucide:clipboard-check' WHERE id = 6773;
-- 转科办理 (path: N/A)
UPDATE system_menu SET icon = 'lucide:clipboard-check' WHERE id = 6774;

-- 父菜单 ID: 6765
-- 病区查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6775;
-- 病区创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 6776;
-- 病区更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 6777;
-- 病区删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 6778;

-- 父菜单 ID: 6766
-- 预住院查询 (path: N/A)
UPDATE system_menu SET icon = 'mdi:bed' WHERE id = 6779;
-- 预住院创建 (path: N/A)
UPDATE system_menu SET icon = 'mdi:bed' WHERE id = 6780;
-- 预住院更新 (path: N/A)
UPDATE system_menu SET icon = 'mdi:bed' WHERE id = 6781;
-- 预住院删除 (path: N/A)
UPDATE system_menu SET icon = 'mdi:bed' WHERE id = 6782;
-- 预住院取消 (path: N/A)
UPDATE system_menu SET icon = 'mdi:bed' WHERE id = 6783;
-- 预住院入院 (path: N/A)
UPDATE system_menu SET icon = 'mdi:bed' WHERE id = 6784;

-- 父菜单 ID: 6767
-- 评估查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6785;
-- 评估创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 6786;
-- 评估更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 6787;
-- 评估删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 6788;

-- 父菜单 ID: 6768
-- 预交金查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6789;
-- 预交金创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 6790;
-- 预交金删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 6791;
-- 预交金退款 (path: N/A)
UPDATE system_menu SET icon = 'lucide:undo-2' WHERE id = 6792;

-- 父菜单 ID: 6803
-- 检查申请查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6805;
-- 检查申请新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6806;
-- 检查申请修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6807;
-- 检查申请删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6808;
-- 检查申请预约 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6809;
-- 检查申请签到 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6810;
-- 检查申请执行 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6811;
-- 检查申请取消 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6812;

-- 父菜单 ID: 6804
-- 检查项目查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6813;
-- 检查项目新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6814;
-- 检查项目修改 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6815;
-- 检查项目删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6816;

-- 父菜单 ID: 6818
-- 检验申请查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6819;
-- 检验申请创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6820;
-- 检验申请更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6821;
-- 检验申请删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6822;
-- 标本采集 (path: N/A)
UPDATE system_menu SET icon = 'lucide:test-tube' WHERE id = 6823;
-- 标本接收 (path: N/A)
UPDATE system_menu SET icon = 'lucide:inbox' WHERE id = 6824;
-- 检验执行 (path: N/A)
UPDATE system_menu SET icon = 'lucide:test-tube' WHERE id = 6825;
-- 报告生成 (path: N/A)
UPDATE system_menu SET icon = 'lucide:file-plus' WHERE id = 6826;
-- 报告审核 (path: N/A)
UPDATE system_menu SET icon = 'lucide:file-check' WHERE id = 6827;
-- 报告发布 (path: N/A)
UPDATE system_menu SET icon = 'lucide:folder' WHERE id = 6828;
-- 危急值确认 (path: N/A)
UPDATE system_menu SET icon = 'lucide:check' WHERE id = 6829;
-- 检验申请取消 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6830;

-- 父菜单 ID: 6831
-- 检验项目查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:test-tube' WHERE id = 6832;
-- 检验项目创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:test-tube' WHERE id = 6833;
-- 检验项目更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:test-tube' WHERE id = 6834;
-- 检验项目删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:test-tube' WHERE id = 6835;

-- 父菜单 ID: 6836
-- 危急值查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6837;
-- 危急值处理 (path: N/A)
UPDATE system_menu SET icon = 'lucide:folder' WHERE id = 6838;

-- 父菜单 ID: 6839
-- 支付查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6840;
-- 支付创建 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 6841;
-- 支付更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 6842;
-- 支付删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 6843;

-- 父菜单 ID: 6844
-- 退费查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6845;
-- 退费申请 (path: N/A)
UPDATE system_menu SET icon = 'lucide:undo-2' WHERE id = 6846;
-- 退费审核 (path: N/A)
UPDATE system_menu SET icon = 'lucide:file-check' WHERE id = 6847;
-- 退费完成 (path: N/A)
UPDATE system_menu SET icon = 'lucide:undo-2' WHERE id = 6848;
-- 退费删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 6849;

-- 父菜单 ID: 6850
-- 预交金缴纳 (path: N/A)
UPDATE system_menu SET icon = 'lucide:credit-card' WHERE id = 6852;

-- 父菜单 ID: 6856
-- 医嘱查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6861;
-- 医嘱审核 (path: N/A)
UPDATE system_menu SET icon = 'lucide:file-check' WHERE id = 6862;
-- 医嘱停止 (path: N/A)
UPDATE system_menu SET icon = 'lucide:square' WHERE id = 6864;
-- 医嘱作废 (path: N/A)
UPDATE system_menu SET icon = 'lucide:file-x' WHERE id = 6865;

-- 父菜单 ID: 6857
-- 给药记录查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6866;
-- 给药记录新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 6867;
-- 给药记录更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 6868;
-- 给药记录删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 6869;
-- 给药执行 (path: N/A)
UPDATE system_menu SET icon = 'lucide:play' WHERE id = 6870;

-- 父菜单 ID: 6858
-- 生命体征查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:search' WHERE id = 6871;
-- 生命体征新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:plus' WHERE id = 6872;
-- 生命体征更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:edit' WHERE id = 6873;
-- 生命体征删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:trash-2' WHERE id = 6874;

-- 父菜单 ID: 6859
-- 护理记录查询 (path: N/A)
UPDATE system_menu SET icon = 'lucide:heart' WHERE id = 6875;
-- 护理记录新增 (path: N/A)
UPDATE system_menu SET icon = 'lucide:heart' WHERE id = 6876;
-- 护理记录更新 (path: N/A)
UPDATE system_menu SET icon = 'lucide:heart' WHERE id = 6877;
-- 护理记录删除 (path: N/A)
UPDATE system_menu SET icon = 'lucide:heart' WHERE id = 6878;
-- 护理记录签名 (path: N/A)
UPDATE system_menu SET icon = 'lucide:heart' WHERE id = 6879;
-- 护理记录审核 (path: N/A)
UPDATE system_menu SET icon = 'lucide:heart' WHERE id = 6880;

-- ============================================
-- 验证修复结果
-- ============================================

-- 查看修复后仍缺少图标的菜单
SELECT id, name, icon, parent_id, path FROM system_menu WHERE deleted = 0 AND status = 0 AND (icon IS NULL OR icon = '');

-- 查看 HIS 模块菜单图标配置
SELECT id, name, icon, path FROM system_menu WHERE id = 6735 OR parent_id IN (SELECT id FROM system_menu WHERE parent_id = 6735) OR parent_id = 6735 AND deleted = 0 ORDER BY parent_id, id;

-- ============================================
-- 清除缓存
-- ============================================

-- 清除 Redis 权限菜单缓存
-- redis-cli DEL permission_menu_ids
-- 或在应用中调用: redisTemplate.delete("permission_menu_ids")

-- ============================================
-- END
-- ============================================
