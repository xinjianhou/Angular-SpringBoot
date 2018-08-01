package com.xinjian.wechat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xinjian.wechat.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
                .enableUrlTemplating(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api Documentation")
                .description("Api Documentation")
                .contact(new Contact("Jason", "http://blog.51cto.com/7308310", "houxinjian7@gmail.com"))
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");  // 用于Swagger UI测试时添加Bearer Token
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/.*")) // 注意要与Restful API路径一致
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }
}