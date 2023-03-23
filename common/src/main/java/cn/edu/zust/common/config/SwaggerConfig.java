package cn.edu.zust.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelSpecification;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author czg
 * @date 2019/3/7 0007
 */
/*
    swagger传值时, 不填就会默认为 空字符串"", 要想为 null, 就在左边把这个值勾掉

    @ApiOperation(value = "根据 员工角色名/部门名/模糊 匹配员工",httpMethod = "GET",notes = "测试")
    httpMethod = "GET" 必须用全大写 会影响swagger自动生成的测试样例

    Swagger 查看传输过来的图片是乱码问题:
    假设现在控制器方法中设置为 response.setContentType("image/jpeg"),
    则@GetMapping(value= "/url", produce="image/jpeg")
 */
@Configuration
@EnableSwagger2 //http://localhost:8001/swagger-ui/index.html
// @EnableOpenApi  // swagger3
public class SwaggerConfig {
    @Bean
    public Docket mainDocket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("cloud-school-API")
                .description("webapi")
                .version("1.0")
                .contact(new Contact("yjg", "", "861843158@qq.com"))
                .build();

        // /**
        //  *  配置请求头
        //  */
        // RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
        // parameterBuilder.name("token").description("请求头中的token").;

        // swagger3 DocumentationType.OAS_30
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("cloud-service全部接口")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();

    }

    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     *
     */
    private List<ApiKey> securitySchemes()
    {
        List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");

        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];

        authorizationScopes[0] = authorizationScope;

        List<SecurityReference> securityReferences = new ArrayList<>();

        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));

        return securityReferences;
    }

}

