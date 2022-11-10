package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Answer;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.answer.AnswerServiceBean;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.interview.InterviewServiceBean;
import com.study.javaquestions.service.question.QuestionServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class AnswerSingleQuestionServiceBean implements BotSession, KeyboardButtons<String>{

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionServiceBean questionServiceBean;

    private final AnswerServiceBean answerServiceBean;

    private final InterviewServiceBean interviewServiceBean;

    public void defineRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        String questionIdText = substringDataSession(sessionSteps.get(chatID));

        try {
            Long id = Long.parseLong(questionIdText);
            Question question = questionServiceBean.getById(id);

            Answer answer = answerServiceBean.createOrUpdateByChatIDAndQuestion(Answer
                    .builder()
                    .chatID(chatID)
                    .answer(request.getSendMessage().getText())
                    .question(question)
                    .build()
            );

            interviewServiceBean.addNewAnswerByChatID(chatID, answer);

        } catch (EntityNotFoundException enf) {
            sender.sendMessage(request, "Такого питання або відповіді немає");
            enf.printStackTrace();
        } catch (NumberFormatException nfe) {
            sender.sendMessage(request, "Не можу завантажити питання з id = " + questionIdText + "\uD83E\uDD37");
            nfe.printStackTrace();
        } catch (Exception e) {
            sender.sendMessage(request, "Не можу завантажити питання \uD83E\uDD37");
            e.printStackTrace();
        }
    }

    private String substringDataSession(String session) {
        return session.substring(session.indexOf("#") + 1);
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
