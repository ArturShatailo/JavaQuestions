package com.study.javaquestions.service.actionHandlers.addQuestions;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.bot.sender.SenderServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
//BotSession can be injected
public class CreateQuestionServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionValue = "START";
        String requestValue = "Додати питання";
        return request.getStep().toLowerCase().endsWith(requestSessionValue.toLowerCase())
                && request.getSendMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "ADD_QUESTIONS");

        showKeyboardButtons(request,
                "Будь-ласка, обери що робити далі: ",
                Arrays.asList("Обрати рівень питання", "🔙 Повернутись до головного меню"));
        processQuestionSession(chatID);
    }

    private void processQuestionSession(String chatID) {
        QuestionSession questionSession = new QuestionSession(chatID);
        questionsSessions.put(chatID, questionSession);
        questionSessionServiceBean.create(questionSession);
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
