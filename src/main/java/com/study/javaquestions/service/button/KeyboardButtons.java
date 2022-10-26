package com.study.javaquestions.service.button;

import com.study.javaquestions.domain.Request;

import java.util.List;

public interface KeyboardButtons {

    void showKeyboardButtons(Request request, String text);

    List<String> defineKeyboard();

}
