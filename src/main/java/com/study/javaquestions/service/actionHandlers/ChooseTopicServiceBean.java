package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.service.button.ButtonService;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.Topic;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.level.LevelServiceBean;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
//BotSession can be injected
//Buttonable can be injected
public class ChooseTopicServiceBean implements ActionHandlerService, BotSession {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    private final LevelServiceBean levelServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSession = "CHOOSE LEVEL";
        return request.getStep().toLowerCase().startsWith(requestSession.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "CHOOSE TOPIC AND SHOW LIST");

        Level level = defineLevel(request.getSendMessage().getText());
        processQuestionSession(level, chatID);
        //questionsSessions.get(chatID).setLevel(level);

        buttons.createKeyboard(request, defineKeyboard(level.getTopics()));
        sender.sendMessage(request, "Обери топік 👇");
    }

    private void processQuestionSession(Level level, String chatID) {
        questionSessionServiceBean.updateLevelByChatId(chatID, level);
    }

    private Level defineLevel(String levelName) {
        return levelServiceBean.getByName(levelName);
    }


    public List<String> defineKeyboard(Set<Topic> topics) {
        List<String> keyboard = topics
                .stream()
                .map(Topic::getName)
                .collect(Collectors.toList());
        keyboard.add("🔙 Повернутись до вибору рівня");
        return keyboard;
    }

}
