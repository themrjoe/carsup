package com.el.opu.carsup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CarsupApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarsupApplication.class, args);
    }

}
