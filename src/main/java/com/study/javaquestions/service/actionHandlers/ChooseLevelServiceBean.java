package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.level.LevelServiceBean;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
//BotSession can be injected
//Buttonable can be injected
public class ChooseLevelServiceBean implements ActionHandlerService, Buttonable, BotSession {

    private final SenderServiceBean sender;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    private final LevelServiceBean levelServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "Список питань";
        String requestSession = "START";
        return request.getSendMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase()) &&
                request.getStep().toLowerCase().startsWith(requestSession.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "CHOOSE LEVEL");

        questionsSessions.put(chatID, new QuestionSession(chatID));
        //questionSessionServiceBean.create(new QuestionSession(chatID));

        createKeyboard(request, defineKeyboard());
        sender.sendMessage(request, "Обери рівень підготовки 👇");
    }

    public List<String> defineKeyboard() {
        List<String> keyboard = getLevels()
                .stream()
                .map(Level::getName)
                .collect(Collectors.toList());
        keyboard.add("🔙 Повернутись до головного меню");
        return keyboard;
    }

    private List<Level> getLevels(){
        return levelServiceBean.getAll();
    }

}
