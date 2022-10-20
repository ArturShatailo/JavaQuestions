package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.domain.Topic;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.service.topic.TopicServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
//BotSession can be injected
//Buttonable can be injected
public class StartBotServiceBean implements ActionHandlerService, Buttonable, BotSession {

    private final SenderServiceBean sender;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "/start";
        String requestValue1 = "Повернутись до головного меню";
        return request.getSendMessage().getText().toLowerCase()
                .endsWith(requestValue.toLowerCase()) ||
                request.getSendMessage().getText().toLowerCase()
                        .endsWith(requestValue1.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "START");

        createKeyboard(request, defineKeyboard());

        sender.sendMessage(request, "Вітаю, радий тебе бачити \uD83D\uDE4B"+
                "\nЧого бажаєш, розробнику?");
    }
    public List<String> defineKeyboard() {
        return Arrays.asList("📋 Список питань", "💾 Збережені питання",
                "➕ Додати питання", "🗳 Емітація співбесіди");
    }
}
