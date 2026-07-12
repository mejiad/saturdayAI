package com.example.rag_telegram.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final AiChatService aiChatService;

    public TelegramBot(AiChatService aiChatService, @Value("${telegram.bot.token}") String botToken) {
        this.aiChatService = aiChatService;
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update){
        System.out.println("Dentro de consume");
        if (update.hasMessage() && update.getMessage().hasText()){

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            System.out.println("Message de consume " + message_text);

            String response = aiChatService.getResponse(chat_id, message_text);
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text("> :" + response)
                    .build();
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}