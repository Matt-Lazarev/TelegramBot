package com.linearalgebra.bot.config;

import com.linearalgebra.bot.handler.CallbackHandler;
import com.linearalgebra.bot.sender.MessageSender;
import com.linearalgebra.bot.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.linearalgebra.bot.entity.User;

import java.util.List;

public class MyTelegramBot extends TelegramWebhookBot {

    private String token;
    private String username;
    private String webhook;

    private UserServiceImplementation userService;

    @Autowired
    public void setUserService(UserServiceImplementation userService) {
        this.userService = userService;
    }

    public MyTelegramBot(){ }

    public MyTelegramBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            handleCallbackQuery(callbackQuery);
        }

        if (update.getMessage()!=null && update.getMessage().hasText()) {
            final String text = update.getMessage().getText();
            final long chatId = update.getMessage().getChatId();
            final String name = update.getMessage().getFrom().getUserName();

            BotState state = BotState.getState(userService, chatId, name);
            User user = userService.getUserByChatId(chatId);
            BotContext context = BotContext.of(this, user, text);

            state.handleInput(context);

            do {
                state = state.nextState();
                state.enter(context);
            } while (!state.isInputNeeded());

            user.setStateId(state.ordinal());
            userService.addUser(user);
        }
        return null;
    }

    public void handleCallbackQuery(CallbackQuery query){
        final long chatId = query.getMessage().getChatId();
        final String name = query.getMessage().getFrom().getUserName();
        BotState.getState(userService, chatId, name);
        User user = userService.getUserByChatId(chatId);
        BotContext context = BotContext.of(this, user);
        if(query.getData().startsWith("Theory")){
            List<String> theoryAndPhoto = CallbackHandler.handleTheory(query.getData());
            MessageSender.sendPhoto(context, theoryAndPhoto);
        }
        else if (query.getData().startsWith("Calculator")) {
            user.setStateId(CallbackHandler.handleCalculator(query.getData()).ordinal());
            userService.addUser(user);
            MessageSender.sendMessage(context, "Введи размеры матрицы через пробел:");
        }
        else if (query.getData().startsWith("Generator")) {
            user.setStateId(CallbackHandler.handleGenerator(query.getData()).ordinal());
            userService.addUser(user);
            CallbackHandler.handleGenerator(query.getData()).enter(context);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotPath()
    {
        return webhook;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }
}
