package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.bot.componenents.QuestionMenuSession;
import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.Topic;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.InlineKeyboardButtons;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.level.LevelServiceBean;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
//BotSession can be injected
public class ChooseTopicServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, KeyboardButtons<String>, InlineKeyboardButtons<Topic> {

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
        String requestValueBack = "Повернутись до вибору теми";
        return request.getStep().toLowerCase().startsWith(requestSession.toLowerCase())
                || request.getSendMessage().getText().toLowerCase().endsWith(requestValueBack.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "CHOOSE TOPIC AND SHOW LIST");

        Level level = getChosen(request, chatID);
        processQuestionSession(level, chatID);

        showKeyboardButtons(request,
                "Ти обрав *" + level.getName() + "*",
                List.of("🔙 Повернутись до вибору рівня"));

        showInlineButtons(level.getTopics(), request);
    }

    private Level getChosen(Request request, String chatID) {
        return questionsSessions.containsKey(chatID)
                && questionsSessions.get(chatID).getLevel() != null
                ? questionsSessions.get(chatID).getLevel()
                : defineLevel(request.getSendMessage().getText());
    }

    private Level defineLevel(String levelName) {
        return levelServiceBean.getByName(levelName);
    }

    private void processQuestionSession(Level level, String chatID) {
        questionsSessions.get(chatID).setTopic(null);
        questionsSessions.get(chatID).setLevel(level);
        questionSessionServiceBean.updateLevelByChatId(chatID, level);
    }

    @Override
    public void showKeyboardButtons(Request request, String text, List<String> buttonsText) {
        sender.sendMessageWithButtons(
                request,
                text,
                buttons.createKeyboard(buttonsText)
        );
    }

    @Override
    public void showInlineButtons(Collection<Topic> topics, Request request) {
        sender.sendMessageWithButtons(
                request,
                "Будь-ласка, тепер обери топік 🎓",
                buttons.createInlineKeyboard(
                        topics.stream()
                                .map(Topic::getName)
                                .collect(Collectors.toList())
                ));
    }
}
