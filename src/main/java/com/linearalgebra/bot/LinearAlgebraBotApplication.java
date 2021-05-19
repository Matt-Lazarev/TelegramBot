package com.linearalgebra.bot;

import com.linearalgebra.bot.config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class LinearAlgebraBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinearAlgebraBotApplication.class, args);
    }

}
