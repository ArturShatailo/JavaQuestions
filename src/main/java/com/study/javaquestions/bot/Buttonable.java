package com.study.javaquestions.bot;

import com.study.javaquestions.domain.Request;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

public interface Buttonable {

    //List<String> defineKeyboard();

    default void createKeyboard(Request R, List<String> N){

        SendMessage SM = R.getSendMessage();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        SM.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (String s : N) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(s));
            keyboard.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    default InlineKeyboardMarkup createInlineKeyboard(/*Request R,*/ List<String> N, String callbackData){

        //SendMessage SM = R.getSendMessage();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        //SM.setReplyMarkup(inlineKeyboardMarkup);

        List<InlineKeyboardButton> keyboardButtonList = new ArrayList<>();

        for (String s : N) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(s);
            inlineKeyboardButton.setCallbackData(callbackData);
            keyboardButtonList.add(inlineKeyboardButton);
        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonList);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    default InlineKeyboardMarkup createInlineKeyboard(Map<String, String> N){

        //SendMessage SM = R.getSendMessage();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        //SM.setReplyMarkup(inlineKeyboardMarkup);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (Map.Entry<String, String> s : N.entrySet()) {

            List<InlineKeyboardButton> keyboardButtonList = new ArrayList<>();
            rowList.add(keyboardButtonList);

            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(s.getKey());
            inlineKeyboardButton.setCallbackData(s.getValue());
            keyboardButtonList.add(inlineKeyboardButton);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    default Map<String, String> getKeyboardMap(List<String> mapInlined) {

        if (mapInlined.size() == 0) return Collections.emptyMap();
        else if (mapInlined.size() % 2 != 0) mapInlined.add("null");

        Map<String, String> a = new HashMap<>();

        for (int i = 1; i < mapInlined.size(); i+=2) {
            a.put(mapInlined.get(i-1), mapInlined.get(i));
        }

        return a;
    }

}
