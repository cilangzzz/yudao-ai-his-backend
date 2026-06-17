package cn.iocoder.yudao.module.his;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HIS 模块启动类（仅用于独立启动测试）
 * 正常情况下，HIS 模块作为依赖被 yudao-server 引入
 *
 * @author 芋道源码
 */
@SpringBootApplication
public class HisModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HisModuleApplication.class, args);
    }

}
