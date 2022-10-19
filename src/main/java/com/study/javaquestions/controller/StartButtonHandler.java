package com.study.javaquestions.controller;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class StartButtonHandler implements ActionHandler, BotSession, Buttonable {

    private final SenderServiceBean sender;

    @Override
    public int isGlobal() {
        return 1;
    }

    @Override
    public synchronized boolean isMine(Request request) {
        String requestValue = "/start";
        String requestValue1 = "Повернутись до головного меню";
        return request.getSendMessage().getText().toLowerCase()
                .endsWith(requestValue.toLowerCase()) ||
                request.getSendMessage().getText().toLowerCase()
                        .endsWith(requestValue1.toLowerCase());
    }

    @Override
    public synchronized void handleRequest(Request request) {

        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "START");

        createKeyboard(request, Arrays.asList("📋 Список питань", "💾 Збережені питання",
                "➕ Додати питання", "🗳 Пройти тест"));

        sender.sendMessage(request, "Вітаю, радий тебе бачити \uD83D\uDE4B"+
                "\nЧого бажаєш, розробнику?");
    }


}
