package com.chen.infoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.chen.infoapp")
public class InfoappApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfoappApplication.class, args);
    }
}
