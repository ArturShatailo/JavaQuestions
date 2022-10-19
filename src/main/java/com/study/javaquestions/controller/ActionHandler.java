package com.study.javaquestions.controller;

import com.study.javaquestions.domain.Request;
import org.springframework.stereotype.Component;

@Component
public interface ActionHandler {

    boolean isMine(Request request);

    void handleRequest(Request request);

    int isGlobal();

}
