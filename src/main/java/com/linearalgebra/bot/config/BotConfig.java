package com.linearalgebra.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    private String token;
    private String username;
    private String webhook;

    @Bean()
    public MyTelegramBot botConfiguration(){
        MyTelegramBot bot = new MyTelegramBot(new DefaultBotOptions());
        bot.setWebhook(webhook);
        bot.setUsername(username);
        bot.setToken(token);
        return bot;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }
}
