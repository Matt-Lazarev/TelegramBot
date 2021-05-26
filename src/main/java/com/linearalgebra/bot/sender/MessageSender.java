package com.linearalgebra.bot.sender;

import com.linearalgebra.bot.config.BotContext;
import org.springframework.stereotype.Service;
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

@Service
public class MessageSender {

    public MessageSender(){}

    public static void sendMessage(BotContext context, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(String.valueOf(context.getUser().getChatId()));

        try {
            context.getBot().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageWithKeyboard(BotContext context, String text){
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

    public static void sendMessageWithTheoryKeyboard(BotContext context, String text){
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

    public static void sendMessageWithCalculatorKeyboard(BotContext context, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(String.valueOf(context.getUser().getChatId()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonDeterminant = new InlineKeyboardButton();
        buttonDeterminant.setText("1.Определитель матрицы");

        InlineKeyboardButton buttonAddition = new InlineKeyboardButton();
        buttonAddition.setText("2.Сложение матриц");

        InlineKeyboardButton buttonSubtraction = new InlineKeyboardButton();
        buttonSubtraction.setText("3.Вычитание матриц");

        InlineKeyboardButton buttonMultiplication = new InlineKeyboardButton();
        buttonMultiplication.setText("4.Умножение матриц");

        //Every button must have callBackData, or else not work !
        buttonDeterminant.setCallbackData("Calculator_det");
        buttonAddition.setCallbackData("Calculator_add");
        buttonSubtraction.setCallbackData("Calculator_sub");
        buttonMultiplication.setCallbackData("Calculator_mul");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();

        keyboardButtonsRow1.add(buttonDeterminant);
        keyboardButtonsRow2.add(buttonAddition);
        keyboardButtonsRow3.add(buttonSubtraction);
        keyboardButtonsRow4.add(buttonMultiplication);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(keyboardButtonsRow1, keyboardButtonsRow2,
                keyboardButtonsRow3, keyboardButtonsRow4));

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            context.getBot().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageWithTrainKeyboard(BotContext context, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(String.valueOf(context.getUser().getChatId()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonDeterminant = new InlineKeyboardButton();
        buttonDeterminant.setText("1.Определитель матрицы");

        InlineKeyboardButton buttonAddition = new InlineKeyboardButton();
        buttonAddition.setText("2.Сложение матриц");

        InlineKeyboardButton buttonSubtraction = new InlineKeyboardButton();
        buttonSubtraction.setText("3.Вычитание матриц");

        InlineKeyboardButton buttonMultiplication = new InlineKeyboardButton();
        buttonMultiplication.setText("4.Умножение матриц");

        //Every button must have callBackData, or else not work !
        buttonDeterminant.setCallbackData("Generator_det");
        buttonAddition.setCallbackData("Generator_add");
        buttonSubtraction.setCallbackData("Generator_sub");
        buttonMultiplication.setCallbackData("Generator_mul");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();

        keyboardButtonsRow1.add(buttonDeterminant);
        keyboardButtonsRow2.add(buttonAddition);
        keyboardButtonsRow3.add(buttonSubtraction);
        keyboardButtonsRow4.add(buttonMultiplication);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(keyboardButtonsRow1, keyboardButtonsRow2,
                keyboardButtonsRow3, keyboardButtonsRow4));

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            context.getBot().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendPhoto(BotContext context, List<String> path){
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
}
