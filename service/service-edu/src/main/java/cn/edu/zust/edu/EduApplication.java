package cn.edu.zust.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@EnableDiscoveryClient // nacos 注册,启用服务发现
@EnableFeignClients // 开启RPC
@SpringBootApplication//默认只在当前模块下扫描，注册bean
/**
 * ComponentScan
 * 不仅仅在当前模块下扫描，还在其依赖的模块下扫描，注册bean,
 * 如果当前模块要调用 common 下的 bean, 就要在其启动类上加该注解
 *
 */
@ComponentScan(basePackages = {"cn.edu.zust"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

    /**
     * 在启动类App.class文件中配置Bean来设置文件大小
     *
     * 如果不在启动类上配置, 那么需要添加 @Configuration
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize(DataSize.parse("50MB")); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("50MB"));
        return factory.createMultipartConfig();
    }
}
