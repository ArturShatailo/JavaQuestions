package com.study.javaquestions.controller;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.service.actionHandlers.QuestionsListServiceBean;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionsListController implements ActionHandler {

    private final QuestionsListServiceBean serviceBean;

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
