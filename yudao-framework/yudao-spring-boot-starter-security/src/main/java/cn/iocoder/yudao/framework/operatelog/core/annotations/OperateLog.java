package cn.iocoder.yudao.framework.operatelog.core.annotations;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * 用于标记需要记录操作日志的方法
 *
 * @author yudao
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {

    /**
     * 操作模块
     */
    String module() default "";

    /**
     * 操作名
     */
    String name() default "";

    /**
     * 操作类型
     */
    cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum type() default cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.OTHER;

    /**
     * 是否记录业务对象
     */
    boolean enableRecordObject() default true;

    /**
     * 是否记录业务参数
     */
    boolean enableRecordParam() default true;

    /**
     * 是否记录业务结果
     */
    boolean enableRecordResult() default true;
}