package com.study.javaquestions.service.actionHandlers.addQuestions;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.actionHandlers.Showable;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.question.QuestionServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateNewQuestionServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, Showable<Question>, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionServiceBean questionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionStep = "QUESTION COMPLETE";
        String requestValue = "Так, можна створювати питання";
        String requestSessionValue = "ADD_QUESTIONS";
        return request.getSessionStep().toLowerCase().endsWith(requestSessionStep.toLowerCase())
                && request.getStep().toLowerCase().endsWith(requestSessionValue.toLowerCase())
                && request.getSendMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "QUESTION CREATION");

        showKeyboardButtons(request,
                "Дякую за відповідь, *" + request.getUser().getFirstName() + "*",
                List.of("🔙  Повернутись до головного менюи"));
        defineRequest(request);
    }

    @Override
    public void defineRequest(Request request) {
        try {
            Question question = questionServiceBean.createFromQuestionSession(
                            questionsSessions.get(
                                    request
                                    .getSendMessage()
                                    .getChatId()
                            ));
            if (question == null) nothingToShow(request);
            else show(question, request);
        } catch (Exception e) {
            sender.sendMessage(request, "Не можу завантажити твоє питання \uD83E\uDD37");
            e.printStackTrace();
        }
    }

    @Override
    public void nothingToShow(Request request) {
        sender.sendMessage(request, "Створити питання не вдалося, або його неможливо знайти, вибач \uD83E\uDD37");
    }

    @Override
    public void show(Question question, Request request) {
        sender.sendMessage(request, "Це створене питання: \n\n" + question.toString());
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
