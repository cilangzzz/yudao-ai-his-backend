package cn.iocoder.yudao.framework.operatelog.core.enums;

/**
 * 操作类型枚举
 *
 * @author yudao
 */
public enum OperateTypeEnum {

    /**
     * 其他操作
     */
    OTHER,

    /**
     * 新增操作
     */
    CREATE,

    /**
     * 更新操作
     */
    UPDATE,

    /**
     * 删除操作
     */
    DELETE,

    /**
     * 查询操作
     */
    QUERY,

    /**
     * 导出操作
     */
    EXPORT,

    /**
     * 导入操作
     */
    IMPORT,

    /**
     * 登录操作
     */
    LOGIN,

    /**
     * 登出操作
     */
    LOGOUT;
}