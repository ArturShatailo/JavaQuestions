package com.study.javaquestions.controller;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionsListHandler implements ActionHandler, Buttonable, BotSession {

    private final SenderServiceBean sender;

    @Override
    public int isGlobal() {
        return 0;
    }

    @Override
    public boolean isMine(Request request) {
        String requestValue = "CHOOSE TOPIC AND SHOW LIST";
        return request.getSendMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase());
    }

    @Override
    public void handleRequest(Request request) {

        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "QUESTIONS LIST");

        //change to UPDATE QUESTION SESSION IN DATABASE
        questionsSessions.get(chatID).setTopic(request.getSendMessage().getText());


        createKeyboard(request, List.of("🔙 Повернутись до головного меню"));

        //get list of questions according to QuestionSession
        sender.sendMessage(request, "Список питань 👇");
    }

}
