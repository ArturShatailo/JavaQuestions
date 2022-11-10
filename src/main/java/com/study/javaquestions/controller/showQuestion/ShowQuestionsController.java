package com.study.javaquestions.controller.showQuestion;

import com.study.javaquestions.controller.ActionHandler;
import com.study.javaquestions.service.actionHandlers.showQuestion.ShowQuestionsServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShowQuestionsController implements ActionHandler {

    private final ShowQuestionsServiceBean serviceBean;

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
