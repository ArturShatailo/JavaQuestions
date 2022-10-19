package com.study.javaquestions.controller;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.service.HandlerServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ChooseLevelHandler implements ActionHandler {

    private final HandlerServiceBean handlerServiceBean;

    @Override
    public int isGlobal() {
        return 0;
    }

    @Override
    public boolean isMine(Request request) {
        String requestValue = "Список питань";
        String requestSession = "START";
        return request.getSendMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase()) &&
                request.getStep().toLowerCase().startsWith(requestSession.toLowerCase());
    }

    @Override
    public void handleRequest(Request request) {
        handlerServiceBean.chooseTopicRequest(request);
    }

}
