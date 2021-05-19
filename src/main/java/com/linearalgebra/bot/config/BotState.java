package com.linearalgebra.bot.config;

import com.linearalgebra.bot.User;
import com.linearalgebra.bot.controller.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum BotState {

    Main{
        BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessageWithKeyboard(context, "Привет! Я бот-калькулятор линейной алгебры!\n" +
                    "Нажми \"Обо мне\", если не знаком со мной." +
                    "\nУже работал со мной? Выбирай интересующий тебя раздел!");
        }

        @Override
        public void handleInput(BotContext context) {
            String input = context.getInput();

            switch(input){
                case "Обо мне": next=AboutMe; break;
                case "Калькулятор": next=Calculator; break;
                case "Тренировочные примеры": next=Train; break;
                case "Теория": next=Theory; break;
                case "/start": next=Main; break;
                default: sendMessage(context, "Выбери пункт из списка кнопок!");
                         next=Main;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

   AboutMe{
        BotState next;

       @Override
       public void enter(BotContext context) {
           sendMessageWithKeyboard(context, "Я могу:\n" +
                   "1. Решать примеры из линейной алгебры;\n" +
                   "2. Улучшить твои навыки решения примеров;\n" +
                   "3. Показать теорию по решению примеров.");
       }

       @Override
       public void handleInput(BotContext context) {
           String input = context.getInput();

           switch(input){
               case "Обо мне": next=AboutMe; break;
               case "Калькулятор": next=Calculator; break;
               case "Тренировочные примеры": next=Train; break;
               case "Теория": next=Theory; break;
               case "/start": next=Main; break;
               default: sendMessage(context, "Выбери пункт из списка кнопок!");
                   next=Main;
           }
       }

       @Override
       public BotState nextState() {
           return next;
       }
   },

    Calculator {
        BotState next;

        @Override
        public void enter(BotContext context) {

        }

        @Override
        public void handleInput(BotContext context) {
            super.handleInput(context);
        }

        @Override
        public BotState nextState() {
            return null;
        }
    },

    Train {
        BotState next;

        @Override
        public void enter(BotContext context) {

        }

        @Override
        public void handleInput(BotContext context) {
            super.handleInput(context);
        }

        @Override
        public BotState nextState() {
            return null;
        }
    },

    Theory {
        BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessageWithTheoryKeyboard(context, "Выбери интересующую тебя тему:");
        }

        @Override
        public void handleInput(BotContext context) {
            String input = context.getInput();

            switch(input){
                case "Обо мне": next=AboutMe; break;
                case "Калькулятор": next=Calculator; break;
                case "Тренировочные примеры": next=Train; break;
                case "Теория": next=Theory; break;
                case "/start": next=Main; break;
                default: sendMessage(context, "Выбери пункт из списка кнопок!");
                    next=Main;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    };

    private final boolean inputNeeded;

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    private static BotState[] states;
    BotState(){
        this.inputNeeded = true;

    }

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static BotState getState(UserService userService, long chat_id){
        User user = userService.getUserById((int) chat_id);
        if(user == null){
            user = new User(chat_id, getInitialState().ordinal());
            userService.addUser(user);
            return getInitialState();
        }
        else{
            return getById(user.getStateId());
        }
    }

    public static BotState getInitialState(){
        return getById(0);
    }

    public static BotState getById(int id){
        if(states == null)
            states = BotState.values();
        return states[id];
    }

    public void sendMessage(BotContext context, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(String.valueOf(context.getUser().getChatId()));

        try {
            context.getBot().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithKeyboard(BotContext context, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(String.valueOf(context.getUser().getChatId()));
        sendMessage.enableMarkdown(true);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Калькулятор");
        keyboardFirstRow.add("Тренировочные примеры");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("Теория");
        keyboardSecondRow.add("Обо мне");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        try {
            context.getBot().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithTheoryKeyboard(BotContext context, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(String.valueOf(context.getUser().getChatId()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonDeterminant = new InlineKeyboardButton();
        buttonDeterminant.setText("Определитель матрицы");

        InlineKeyboardButton buttonAddition = new InlineKeyboardButton();
        buttonAddition.setText("Сложение матриц");

        InlineKeyboardButton buttonSubtraction = new InlineKeyboardButton();
        buttonSubtraction.setText("Вычитание матриц");

        InlineKeyboardButton buttonMultiplication = new InlineKeyboardButton();
        buttonMultiplication.setText("Умножение матриц");

        InlineKeyboardButton buttonInverse = new InlineKeyboardButton();
        buttonInverse.setText("Обратная матрица");

        //Every button must have callBackData, or else not work !
        buttonDeterminant.setCallbackData("Theory_det");
        buttonAddition.setCallbackData("Theory_add");
        buttonSubtraction.setCallbackData("Theory_sub");
        buttonMultiplication.setCallbackData("Theory_mul");
        buttonInverse.setCallbackData("Theory_inv");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();

        keyboardButtonsRow1.add(buttonDeterminant);
        keyboardButtonsRow2.add(buttonAddition);
        keyboardButtonsRow3.add(buttonSubtraction);
        keyboardButtonsRow4.add(buttonMultiplication);
        keyboardButtonsRow5.add(buttonInverse);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(keyboardButtonsRow1, keyboardButtonsRow2,
                keyboardButtonsRow3, keyboardButtonsRow4, keyboardButtonsRow5));

        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            context.getBot().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(BotContext context, List<String> path){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(path.get(1));
        sendMessage.setChatId(String.valueOf(context.getUser().getChatId()));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(context.getUser().getChatId().toString());
        try{
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path.get(0)));
            InputFile inputFile = new InputFile(inputStream, path.get(0));

            sendPhoto.setPhoto(inputFile);
            context.getBot().execute(sendMessage);
            context.getBot().execute(sendPhoto);
        }catch (IOException ex){
            sendMessage(context, "Произошла ошибка, повтори запрос позже!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public abstract void enter(BotContext context);
    public abstract BotState nextState();
    public void handleInput(BotContext context) {}

}
