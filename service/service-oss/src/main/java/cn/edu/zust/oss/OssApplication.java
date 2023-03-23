package cn.edu.zust.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
/**
 * ComponentScan
 * 不仅仅在当前模块下扫描，还在其依赖的模块下扫描，注册bean,
 * 如果当前模块要调用 common 下的 bean, 就要在其启动类上加该注解
 *
 */
@ComponentScan(basePackages = {"cn.edu.zust"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }

}
