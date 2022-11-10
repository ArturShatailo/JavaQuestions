package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InterviewCompleteServiceBean implements ActionHandlerService, BotSession{

    private final ShowInterviewResultServiceBean showInterviewResultServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionValue = "INTERVIEW";
        String requestSessionStepValue = "INTERVIEW COMPLETED";
        String requestValueBack = "Показати результат співбесіди";
        return request.getSendMessage().getText().toLowerCase().endsWith(requestValueBack.toLowerCase())
                && request.getSessionStep().toLowerCase().startsWith(requestSessionStepValue.toLowerCase())
                && request.getStep().toLowerCase().startsWith(requestSessionValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "START");
        sessionSteps.put(chatID, "START");
        showInterviewResultServiceBean.defineRequest(request);
    }
}
