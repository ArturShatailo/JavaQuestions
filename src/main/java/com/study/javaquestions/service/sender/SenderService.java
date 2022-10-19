package com.study.javaquestions.service.sender;

import com.study.javaquestions.bot.componenents.BotFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
public class SenderService extends DefaultAbsSender implements BotFactory {

    private final String botToken = bot.getToken();

    public SenderService() {
        super(new DefaultBotOptions());
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}