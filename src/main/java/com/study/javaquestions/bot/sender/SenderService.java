package com.study.javaquestions.bot.sender;

import com.study.javaquestions.bot.util.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
public class SenderService extends DefaultAbsSender{

    private final BotConfig botConfig;

    public SenderService(BotConfig botConfig) {
        super(new DefaultBotOptions());
        this.botConfig = botConfig;
    }

    @Override
    public String getBotToken() {
        return botConfig.botBean().getToken();
    }
}