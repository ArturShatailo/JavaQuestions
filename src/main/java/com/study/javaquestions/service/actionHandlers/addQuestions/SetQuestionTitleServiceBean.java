package com.study.javaquestions.service.actionHandlers.addQuestions;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SetQuestionTitleServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionStep = "QUESTION TITLE";
        String requestSessionValue = "ADD_QUESTIONS";
        return request.getSessionStep().toLowerCase().endsWith(requestSessionStep.toLowerCase())
                && request.getStep().toLowerCase().endsWith(requestSessionValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "QUESTION HINT");
        String title = request.getSendMessage().getText();

        processQuestionSession(chatID, title);

        showKeyboardButtons(request,
                "Дякую, тепер напиши текст підказки і відправ мені",
                List.of("🔙 Повернутись до вибору теми"));
    }

    private void processQuestionSession(String chatID, String title) {
        questionsSessions.get(chatID).setTitle(title);
        questionSessionServiceBean.updateTitleByChatId(chatID, title);
    }

    @Override
    public void showKeyboardButtons(Request request, String text, List<String> buttonsText) {
        sender.sendMessageWithButtons(
                request,
                text,
                buttons.createKeyboard(buttonsText)
        );
    }
}
