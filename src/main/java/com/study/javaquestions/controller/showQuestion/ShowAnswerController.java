package com.study.javaquestions.controller.showQuestion;

import com.study.javaquestions.controller.ActionHandler;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.showQuestion.ShowAnswerServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShowAnswerController implements ActionHandler {

    private final ShowAnswerServiceBean serviceBean;

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
