package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Interview;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.Showable;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.interview.InterviewServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class ShowSingleQuestionServiceBean implements BotSession, KeyboardButtons<String>, Showable<Interview> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final InterviewServiceBean interviewServiceBean;

    @Override
    public void defineRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();

        Interview interview = interviewServiceBean.getByChatID(chatID);
        interview.upscaleCurrentQuestion();

        if (interview.checkQuestionsAmount()) nothingToShow(request);
        else show(interview, request);
    }

    @Override
    public void nothingToShow(Request request) {
        sessionSteps.put(
                request.getSendMessage().getChatId(),
                "INTERVIEW COMPLETED");
        showKeyboardButtons(request,
                "Це було останнє питання  \uD83E\uDD37",
                List.of("Показати результат співбесіди", "🔙 Повернутись до головного меню"));
    }

    @Override
    public void show(Interview interview, Request request) {

        Question question = interview.defineCurrentQuestion();

        sessionSteps.put(
                request.getSendMessage().getChatId(),
                "INPUT ANSWER QUESTION #" + question.getId());

        showKeyboardButtons(request,
                "Питання " + interview.getCurrentQuestion() + " з " + interview.getMaxQuestion(),
                List.of("🔙 Повернутись до головного меню"));

        sender.sendMessageWithButtons(
                request,
                "❓ " + question.getTitle() + "\n\nПідказка: <span class=\"tg-spoiler\">" + question.getHint() + "</span>",
                buttons.createInlineKeyboard(buttons.getKeyboardMap(
                                Arrays.asList(
                                        "Не знаю відповідь, наступне питання", "Немає відповіді")
                )));
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
