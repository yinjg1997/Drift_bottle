package cn.edu.zust.cms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
/**
 * ComponentScan
 * 不仅仅在当前模块下扫描，还在其依赖的模块下扫描，注册bean,
 * 如果当前模块要调用 common 下的 bean, 就要在其启动类上加该注解
 *
 */
@ComponentScan(basePackages = {"cn.edu.zust"})
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
