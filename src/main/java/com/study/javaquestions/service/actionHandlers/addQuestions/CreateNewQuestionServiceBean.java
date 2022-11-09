package com.study.javaquestions.service.actionHandlers.addQuestions;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.QuestionRequest;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.actionHandlers.Showable;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.questionRequest.QuestionRequestServiceBean;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateNewQuestionServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, Showable<QuestionRequest>, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionRequestServiceBean questionRequestServiceBean;

    private final QuestionSessionServiceBean questionSessionServiceBean;

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
                "Дякую за відповідь, " + request.getUser().getFirstName(),
                List.of("🔙  Повернутись до головного меню"));
        defineRequest(request);
    }

    @Transactional
    @Override
    public void defineRequest(Request request) {
        try {
            QuestionSession questionSession = questionSessionServiceBean.getByChatId(
                    request.getSendMessage().getChatId()
            );
            QuestionRequest questionRequest = questionRequestServiceBean.createFromQuestionSession(questionSession);
            if (questionRequest == null) nothingToShow(request);
            else show(questionRequest, request);
        } catch (Exception e) {
            sender.sendMessage(request, "Не можу завантажити твоє питання \uD83E\uDD37");
            e.printStackTrace();
        }
    }

    @Override
    public void nothingToShow(Request request) {
        sender.sendMessage(request, "Створити запрос не вдалося, або його неможливо знайти, вибач \uD83E\uDD37");
    }

    @Override
    public void show(QuestionRequest questionRequest, Request request) {
        sender.sendMessage(request, "Це створений запрос на питання: \n\n" + questionRequest.toString());
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
