package com.study.javaquestions.controller;

import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.AddQuestionServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddQuestionController implements ActionHandler {

    private final AddQuestionServiceBean serviceBean;

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
