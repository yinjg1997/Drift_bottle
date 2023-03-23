package cn.edu.zust.cms.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan(basePackageClasses = Mapper.class)
public class MPConfig {

    /**
     * 分页插件
     * 不实现它, MP的分页就无效
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL 执行性能分析插件
     * 开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
     */
    // @Bean
    // @Profile({"dev","test"})// 设置 dev test 环境开启
    // public PerformanceInterceptor performanceInterceptor() {
    //     PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    //     performanceInterceptor.setMaxTime(1000);//ms，超过此处设置的ms则sql不执行
    //     performanceInterceptor.setFormat(true);
    //     return performanceInterceptor;
    // }

    // //在执行insert语句时被拦截操作的
    // @Override
    // public void insertFill(MetaObject metaObject) {
    //
    //     this.setFieldValByName("gmtCreate", LocalDateTime.now(), metaObject);
    //     this.setFieldValByName("gmtModified", LocalDateTime.now(), metaObject);
    //     //自动补充对象属性中的数据，isDeleted：Boolean 所以我们应该放入true，false
    //     this.setFieldValByName("isDeleted", false, metaObject);
    // }
    //
    // //在执行update语句时被拦截操作的
    // @Override
    // public void updateFill(MetaObject metaObject) {
    //     this.setFieldValByName("gmtModified", LocalDateTime.now(), metaObject);
    // }
}
