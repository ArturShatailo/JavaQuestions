package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.*;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.actionHandlers.Showable;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.interview.InterviewServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class InterviewShowQuestionServiceBean implements ActionHandlerService, BotSession, KeyboardButtons<String>, Showable<Question> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final InterviewServiceBean interviewServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionValue = "INTERVIEW";
        String requestValue = "Показати перше питання";
        String requestValue1 = "Показати наступне питання";
        return request.getStep().toLowerCase().startsWith(requestSessionValue.toLowerCase())
                && (request.getMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase())
                    || request.getMessage().getText().toLowerCase().endsWith(requestValue1.toLowerCase()));
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "NEW INTERVIEW STARTED");

        showKeyboardButtons(request,
                "Добре, " + request.getUser().getFirstName(),
                List.of("Показати наступне питання", "🔙 Повернутись до головного меню"));
        defineRequest(request);
    }

    @Override
    public void defineRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();

        Interview interview = interviewServiceBean.getByChatID(chatID);
        interview.upscaleCurrentQuestion();

        if (interview.checkQuestionsAmount()) nothingToShow(request);
        else {
            show(interview.defineCurrentQuestion(), request);
        }
    }
    @Override
    public void nothingToShow(Request request) {
        showKeyboardButtons(request,
                "Це було останнє питання  \uD83E\uDD37",
                List.of("Показати результат співбесіди", "🔙 Повернутись до головного меню"));
    }

    @Override
    public void show(Question question, Request request) {
        sender.sendMessageWithButtons(
                request,
                "Напиши відповідь і відправ мені :)\n\n" +
                        "❓ " + question.getTitle(),
                buttons.createInlineKeyboard(
                        buttons.getKeyboardMap(Arrays.asList("\uD83D\uDD2E Показати підказку", "Показати підказку на питання " + "#" + question.getId()))
                ));
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
