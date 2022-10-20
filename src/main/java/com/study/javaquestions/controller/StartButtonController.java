package com.study.javaquestions.controller;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.service.actionHandlers.StartBotServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class StartButtonController implements ActionHandler, BotSession, Buttonable {

    private final StartBotServiceBean serviceBean;

    @Override
    public int isGlobal() {
        return 1;
    }

    @Override
    public synchronized boolean isMine(Request request) {
        return serviceBean.mineCheck(request);
    }

    @Override
    public synchronized void handleRequest(Request request) {
        serviceBean.sendRequest(request);
    }
}
