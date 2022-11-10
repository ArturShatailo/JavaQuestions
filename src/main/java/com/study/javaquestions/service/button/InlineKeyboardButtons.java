package com.study.javaquestions.service.button;

import com.study.javaquestions.domain.Request;

import java.util.Collection;

public interface InlineKeyboardButtons<T> {

    void showInlineButtons(Collection<T> list, Request request);

}
