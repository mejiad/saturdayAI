package com.example.rag_telegram.config;

import com.example.rag_telegram.services.TelegramBot;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@Component
public class TelegramBotConfig {

    private final TelegramBotsLongPollingApplication botsApplication;

    @Autowired
    public TelegramBotConfig(@Value("${telegram.bot.token}") String botToken,
                             TelegramBot telegramBot) throws Exception {
       this.botsApplication = new TelegramBotsLongPollingApplication();
       botsApplication.registerBot(botToken, telegramBot);
       System.out.println("Bot de Telegram registrado y corriendo.");
    }

    @PreDestroy
    public void shutdowm() throws Exception {
        botsApplication.close();
        System.out.println("Sessión de Telegram cerrada correctamente.");
    }
}
