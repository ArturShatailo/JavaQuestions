package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.domain.Request;

public interface ActionHandlerService{

    int globalCheck();

    boolean mineCheck(Request request);

    void sendRequest(Request request);

}
