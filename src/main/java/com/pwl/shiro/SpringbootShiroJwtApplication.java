package com.pwl.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.pwl.shiro.mapper"})
public class SpringbootShiroJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroJwtApplication.class, args);
    }

}
