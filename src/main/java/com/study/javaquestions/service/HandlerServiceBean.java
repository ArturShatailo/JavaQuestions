package com.study.javaquestions.service;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
//BotSession can be injected
//Buttonable can be injected
public class HandlerServiceBean implements Buttonable, BotSession {

    private final SenderServiceBean sender;

    public void chooseTopicRequest(Request request){

        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "CHOOSE TOPIC AND SHOW LIST");

        //change to UPDATE QUESTION SESSION IN DATABASE
        questionsSessions.get(chatID).setLevel(request.getSendMessage().getText());


        //get topics from database
        createKeyboard(request, List.of("One", "Two", "Three", "🔙 Повернутись до головного меню"));

        sender.sendMessage(request, "Обери топік 👇");
    }

    public void chooseLevelRequest(Request request){

        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "CHOOSE LEVEL");

        QuestionSession questionSession = new QuestionSession();
        //change to SAVE QUESTION SESSION INTO DATABASE
        questionsSessions.put(chatID, questionSession);

        //get levels from database
        createKeyboard(request, List.of("Junior", "Middle", "Senior", "🔙 Повернутись до головного меню"));
        sender.sendMessage(request, "Обери рівень підготовки 👇");
    }


}
