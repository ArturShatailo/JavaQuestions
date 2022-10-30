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
public class SetQuestionAnswerServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionStep = "QUESTION ANSWER";
        String requestSessionValue = "ADD_QUESTIONS";
        return request.getSessionStep().toLowerCase().endsWith(requestSessionStep.toLowerCase())
                && request.getStep().toLowerCase().endsWith(requestSessionValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "QUESTION COMPLETE");
        String answer = request.getSendMessage().getText();

        processQuestionSession(chatID, answer);

        showKeyboardButtons(request,
                "Дякую, перевір, чи все правильно?\n\n" +
                        questionsSessions.get(chatID).toString(),
                List.of("Так, можна створювати питання", "🔙 Ні, повернутись до вибору теми"));
    }

    private void processQuestionSession(String chatID, String answer) {
        questionsSessions.get(chatID).setAnswer(answer);
        questionSessionServiceBean.updateAnswerByChatId(chatID, answer);
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
