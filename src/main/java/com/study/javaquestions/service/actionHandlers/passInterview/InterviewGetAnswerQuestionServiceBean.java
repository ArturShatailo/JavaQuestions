package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Answer;
import com.study.javaquestions.domain.Interview;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.answer.AnswerServiceBean;
import com.study.javaquestions.service.interview.InterviewServiceBean;
import com.study.javaquestions.service.question.QuestionServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class InterviewGetAnswerQuestionServiceBean implements ActionHandlerService, BotSession{

    private final ShowSingleQuestionServiceBean showSingleQuestionServiceBean;

    private final QuestionServiceBean questionServiceBean;

    private final AnswerServiceBean answerServiceBean;

    private final InterviewServiceBean interviewServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {

        String requestSessionValue = "INTERVIEW";
        String requestValue = "Не знаю відповідь на питання";
        return request.getStep().toLowerCase().startsWith(requestSessionValue.toLowerCase())
                && request.getSendMessage().getText().toLowerCase().startsWith(requestValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        String questionIdText = substringDataFromMessage(request);
        Long questionID = Long.parseLong(questionIdText);

        Answer answer = new Answer();
        answer.setChatID(chatID);
        Question question = questionServiceBean.getById(questionID);
        answer.setQuestion(question);
        answer.setAnswer("Не знаю відповідь на питання");
        answerServiceBean.create(answer);
        Interview interview = interviewServiceBean.getByChatID(chatID);
        interview.getAnswers().add(answerServiceBean.getAnswerByChatIDAndQuestion(chatID, question));
        interviewServiceBean.updateByChatID(chatID, interview);

        showSingleQuestionServiceBean.defineRequest(request);
    }

    private String substringDataFromMessage(Request request) {
        String message = request.getSendMessage().getText();
        return message.substring(message.indexOf("#") + 1);
    }
}
