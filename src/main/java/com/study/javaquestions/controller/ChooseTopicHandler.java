package com.study.javaquestions.controller;

import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.HandlerServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChooseTopicHandler implements ActionHandler {

    private final HandlerServiceBean handlerServiceBean;

    @Override
    public int isGlobal() {
        return 0;
    }

    @Override
    public boolean isMine(Request request) {
        String requestSession = "CHOOSE LEVEL";
        return request.getStep().toLowerCase().startsWith(requestSession.toLowerCase());
    }

    @Override
    public void handleRequest(Request request) {
        handlerServiceBean.chooseTopicRequest(request);
    }

}
