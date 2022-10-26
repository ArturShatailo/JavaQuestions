package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.componenents.BotSession;
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
public class ChooseTopicServiceBean implements ActionHandlerService, BotSession, KeyboardButtons, InlineKeyboardButtons<Topic> {

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

        //checkQuestionSession Map if there is such level, so download it.
        //If no, try to download from db further and set a Level in QuestionSession

        Level level = defineLevel(request.getSendMessage().getText());
        processQuestionSession(level, chatID);
        //questionsSessions.get(chatID).setLevel(level);

        showKeyboardButtons(request, "Ти обрав *" + level.getName() + "*");
        showInlineButtons(level.getTopics(), request);
    }

    private Level defineLevel(String levelName) {
        return levelServiceBean.getByName(levelName);
    }

    private void processQuestionSession(Level level, String chatID) {
        questionSessionServiceBean.updateLevelByChatId(chatID, level);
    }

    @Override
    public void showKeyboardButtons(Request request, String text) {
        sender.sendMessageWithButtons(
                request,
                text,
                buttons.createKeyboard(defineKeyboard())
        );
    }

    @Override
    public List<String> defineKeyboard() {
        return List.of("🔙 Повернутись до вибору рівня");
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
