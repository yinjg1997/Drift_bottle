package cn.edu.zust.acl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
/**
 * SpringSecurity 在另一个模块, 不加会导致加载不了 SpringSecurity 配置,
 * 从而使用默认 SpringSecurity 的默认配置, 这个bug真的难找
 */
@ComponentScan(basePackages = "cn.edu.zust")
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
