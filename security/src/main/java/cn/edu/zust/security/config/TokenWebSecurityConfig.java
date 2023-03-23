package cn.edu.zust.security.config;

import cn.edu.zust.security.filter.TokenAuthenticationFilter;
import cn.edu.zust.security.filter.TokenLoginFilter;
import cn.edu.zust.security.security.DefaultPasswordEncoder;
import cn.edu.zust.security.security.TokenLogoutHandler;
import cn.edu.zust.security.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * SpringSecurity 核心配置类
 * </p>
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
     * <p>
     *  自定义实现 SpringSecurity的 UserDetailsService
     *  userDetailsService - 认证用户的详情
     * </p>
     */
    private UserDetailsService userDetailsService;

    private DefaultPasswordEncoder defaultPasswordEncoder;
    private RedisTemplate redisTemplate;

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.redisTemplate = redisTemplate;
    }

    /**
     * SpringSecurity 核心配置设置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())

                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                // 设置退出地址/映射
                .and().logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLogoutHandler(redisTemplate)).and()
                .addFilter(new TokenLoginFilter(authenticationManager(), redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), redisTemplate)).httpBasic();
    }

    /**
     * 密码处理
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {


        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     * 哪些请求不需要权限配置
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
       web.ignoring().antMatchers("/api/**",
               "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
              );

//         web.ignoring().antMatchers("/*/**"
//         );
    }
}
