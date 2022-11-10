package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Interview;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.Showable;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.interview.InterviewServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class ShowInterviewResultServiceBean implements BotSession, KeyboardButtons<String>, Showable<Interview> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final InterviewServiceBean interviewServiceBean;

    @Override
    public void defineRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        Interview interview = interviewServiceBean.getByChatID(chatID);

        if (interview == null) nothingToShow(request);
        else show(interview, request);
    }

    @Override
    public void nothingToShow(Request request) {
        showKeyboardButtons(request,
                "Не можу знайти твого інтерв'ю, вибач  \uD83E\uDD37",
                List.of("🔙 Повернутись до головного меню"));
    }

    @Override
    public void show(Interview interview, Request request) {

        new ArrayList<>(interview.getAnswers())
                .stream()
                .sorted(Comparator.comparing(
                        answer -> answer.getQuestion().getId()))
                .forEach(
                        a -> sender.sendMessage(request, a.toString()));

        showKeyboardButtons(request,
                "Дякую за пройдене інтерв'ю 🥳",
                List.of("🔙 Повернутись до головного меню"));
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
