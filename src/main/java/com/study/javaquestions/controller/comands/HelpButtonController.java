package com.study.javaquestions.controller.comands;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.controller.ActionHandler;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.comands.HelpServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HelpButtonController implements ActionHandler, BotSession {

    private final HelpServiceBean serviceBean;

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
