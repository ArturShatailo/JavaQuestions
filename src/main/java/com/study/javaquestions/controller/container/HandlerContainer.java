package com.study.javaquestions.controller.container;

import com.study.javaquestions.controller.ActionHandler;
import com.study.javaquestions.domain.Request;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HandlerContainer{

    private final List<ActionHandler> handlers;

    public HandlerContainer(List<ActionHandler> handlers) {
        this.handlers = handlers
                .stream()
                .sorted(Comparator
                        .comparing(ActionHandler::isGlobal)
                        .reversed())
                .collect(Collectors.toList());
    }

    public boolean chooseHandler(Request request) {
        for (ActionHandler a : handlers) {

            if(a.isMine(request)){
                a.handleRequest(request);
                return true;
            }
        }
        return false;
    }
}
