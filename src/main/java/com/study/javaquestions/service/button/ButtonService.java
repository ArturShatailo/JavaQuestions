package com.study.javaquestions.service.button;

import com.study.javaquestions.domain.Request;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import java.util.*;

public interface ButtonService {

    void createKeyboard(Request request, List<String> buttonsText);

    ReplyKeyboardMarkup createKeyboardColumns(List<String> buttonsText);

    ReplyKeyboardMarkup createKeyboard(List<String> buttonsText);

    InlineKeyboardMarkup createInlineKeyboard(Map<String, String> N);

    InlineKeyboardMarkup createInlineKeyboard(List<String> N);

    Map<String, String> getKeyboardMap(List<String> mapInlined);

}
