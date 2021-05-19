package com.tx.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 扫描common/service_base下的com.tx.base.config包
@ComponentScan(value = {"com.tx"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);
    }
}
