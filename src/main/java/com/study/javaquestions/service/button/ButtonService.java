package com.study.javaquestions.service.button;

import com.study.javaquestions.domain.Request;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.util.*;

public interface ButtonService {

    //List<String> defineKeyboard();

    void createKeyboard(Request request, List<String> buttonsText);

    InlineKeyboardMarkup createInlineKeyboard(/*Request R,*/ List<String> N, String callbackData);

    InlineKeyboardMarkup createInlineKeyboard(Map<String, String> N);

    Map<String, String> getKeyboardMap(List<String> mapInlined);

}
