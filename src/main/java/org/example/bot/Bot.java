package org.example.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * WeatherBot by @senior_javist (Telegram Channel)
 */
public class Bot extends TelegramLongPollingBot {
    private final Weather weather = new Weather();

    // Точка входа (метод main, в котором происходит создание и инициализация бота
    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Создать и получить токен бота нужно в @BotFather в Telegram.
     */
    @Override
    public String getBotUsername() {
        return "mybottJavamybot_bot"; // Метод, который возвращает username бота.
    }

    @Override
    public String getBotToken() {
        return "6650591421:AAHrT3OMJqM5cgxOxZ3nQ9-GKQUXPuPAMtY"; // Метод, который возвращает token бота.
    }

    // Метод, отвечающий за обработку полученного сообщения.
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText(); // Получаем текст сообщения пользователя
            Long chatId = update.getMessage().getChatId(); // Получаем chatId пользователя
            switch (messageText) {
                case "/start" ->
                        sendMessage(
                                chatId,
                                "Привет! Я помогу узнать погоду, просто напиши название города."
                        );
                case "/help" ->
                        sendMessage(
                                chatId,
                                "Чтобы узнать погоду, отправь название города. Если такого города нет, я промолчу!"
                        );
                default ->
                        sendMessage(
                                chatId,
                                weather.getWeather(messageText)
                        );
            }
        }
    }

    // Метод для отправки сообщения пользователю
    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messageText);
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
