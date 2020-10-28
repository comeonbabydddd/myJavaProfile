package com.athjx.profileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.athjx"})
@EnableSwagger2
public class ProfileApplication {

    public static void main(String[] args) {
        String swaggerUrl = "http://localhost:8001/swagger-ui.html#/";
        System.out.println(swaggerUrl);
        SpringApplication.run(ProfileApplication.class, args);

    }
}
