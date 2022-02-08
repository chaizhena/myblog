package com.cz.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class Swagger2Config {

    //    Swagger实例Bean是Docket，所以通过配置Docket实例来配置Swaggger。
    @Bean //配置docket以配置Swagger具体参数
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("柴震")
                .apiInfo(apiInfo())
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.cz.myblog.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("柴震", "https://https://blog.csdn.net/weixin_47699002?spm=1001.2014.3001.5343", "517098527@qq.com");
        return new ApiInfo(
                "Vue-SpringBoot-Blog", // 标题
                "Personal Blog @ChaiZhen", // 描述
                "v2.0", // 版本
                "http://123.57.36.1/", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }

}
