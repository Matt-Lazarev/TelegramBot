package com.linearalgebra.bot.config;

import com.linearalgebra.bot.User;

public class BotContext {
    private final MyTelegramBot bot;
    private final User user;
    private final String input;

    public static BotContext of(MyTelegramBot bot, User user, String input){
        return new BotContext(bot, user, input);
    }

    public static BotContext of(MyTelegramBot bot, User user){
        return new BotContext(bot, user);
    }

    private BotContext(MyTelegramBot bot, User user, String input) {
        this.bot = bot;
        this.user = user;
        this.input = input;
    }

    private BotContext(MyTelegramBot bot, User user) {
        this.bot = bot;
        this.user = user;
        this.input = null;
    }

    public MyTelegramBot getBot() {
        return bot;
    }

    public User getUser() {
        return user;
    }

    public String getInput() {
        return input;
    }
}
