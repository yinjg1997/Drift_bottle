package cn.edu.zust.common.config;



import cn.edu.zust.common.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // /**
    //  * 开启跨域
    //  * 解决跨域问题的方法
    //  * 第一种:在 Controller类上加 @CrossOrigin 解决跨域
    //  * 第二种:使用 springCloud 网关
    //  * 第三种:下面的mvc配置
    //  */
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**")
    //             .allowedOriginPatterns("*")
    //             .allowCredentials(true)
    //             .allowedHeaders("*")
    //             .allowedMethods("*")
    //             .maxAge(3600 * 24);
    // }

    // 拦截器>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger**/**",
                        "/webjars/**",  // swagger icons 使用拦截器就可以看到这些路径
                        "/v2/**", //
                        "/v3/**",
                        "/doc.html");

    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }
    // 拦截器<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}
