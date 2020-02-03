package com.glue.club;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.glue.club.web.dao")
public class ClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubApplication.class, args);
    }

}
