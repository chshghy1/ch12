package com.spring.myweb12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.spring.myweb12.biz.board.jpa")
@EntityScan(basePackages = "com.spring.myweb12.biz.board.jpa")
public class Myweb12Application {



    public static void main(String[] args) {
        SpringApplication.run(Myweb12Application.class, args);
    }

}
