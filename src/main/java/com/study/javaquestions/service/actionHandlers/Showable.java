package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.Request;

public interface Showable<T> {

    void defineRequest(Request request);

    void nothingToShow(Request request);

    void show(T T, Request request);

}
