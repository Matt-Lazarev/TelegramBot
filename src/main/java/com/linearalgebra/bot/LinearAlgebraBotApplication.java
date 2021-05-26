package com.linearalgebra.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LinearAlgebraBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinearAlgebraBotApplication.class, args);
    }

}
