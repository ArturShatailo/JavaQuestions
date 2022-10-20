package com.study.javaquestions.controller;

import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ChooseTopicServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChooseTopicController implements ActionHandler {

    private final ChooseTopicServiceBean serviceBean;

    @Override
    public int isGlobal() {
        return serviceBean.globalCheck();
    }

    @Override
    public boolean isMine(Request request) {
        return serviceBean.mineCheck(request);
    }

    @Override
    public void handleRequest(Request request) {
        serviceBean.sendRequest(request);
    }

}
