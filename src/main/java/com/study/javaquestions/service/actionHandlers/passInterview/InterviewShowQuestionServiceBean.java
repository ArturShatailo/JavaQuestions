package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.*;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class InterviewShowQuestionServiceBean implements ActionHandlerService, BotSession{

    private final ShowSingleQuestionServiceBean showSingleQuestionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionValue = "INTERVIEW";
        String requestValue = "Показати питання";
        String requestValue1 = "Наступне питання";
        return request.getStep().toLowerCase().startsWith(requestSessionValue.toLowerCase())
                && (request.getMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase())
                || request.getMessage().getText().toLowerCase().endsWith(requestValue1.toLowerCase()));
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "NEW INTERVIEW STARTED");
        showSingleQuestionServiceBean.defineRequest(request);
    }
}
