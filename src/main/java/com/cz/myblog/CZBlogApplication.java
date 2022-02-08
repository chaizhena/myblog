package com.cz.myblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cz.myblog.mapper")
public class CZBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(CZBlogApplication.class, args);
    }
}
