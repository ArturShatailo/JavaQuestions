package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class InterviewGetAnswerQuestionServiceBean implements ActionHandlerService, BotSession{

    private final ShowSingleQuestionServiceBean showSingleQuestionServiceBean;

    private final AnswerSingleQuestionServiceBean answerSingleQuestionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionValue = "INTERVIEW";
        String requestSessionStepValue = "INPUT ANSWER QUESTION";
        return request.getStep().toLowerCase().startsWith(requestSessionValue.toLowerCase())
                && request.getSessionStep().toLowerCase().startsWith(requestSessionStepValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        answerSingleQuestionServiceBean.defineRequest(request);
        showSingleQuestionServiceBean.defineRequest(request);
    }

}
