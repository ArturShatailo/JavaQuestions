package com.study.javaquestions.service.button;

import com.study.javaquestions.domain.Request;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.*;

@Service
public class ButtonServiceBean implements ButtonService{

    @Override
    public void createKeyboard(Request request, List<String> buttonsText){

        SendMessage sm = request.getSendMessage();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sm.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // The list of keyboard is created here
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (String s : buttonsText) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(s));
            keyboard.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard(Map<String, String> N){

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


    public InlineKeyboardMarkup createInlineKeyboard(List<String> N){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (String s : N) {

            List<InlineKeyboardButton> keyboardButtonList = new ArrayList<>();
            rowList.add(keyboardButtonList);

            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(s);
            inlineKeyboardButton.setCallbackData(s);
            keyboardButtonList.add(inlineKeyboardButton);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public Map<String, String> getKeyboardMap(List<String> mapInlined) {

        if (mapInlined.size() == 0) return Collections.emptyMap();
        else if (mapInlined.size() % 2 != 0) mapInlined.add("null");

        Map<String, String> a = new HashMap<>();

        for (int i = 1; i < mapInlined.size(); i+=2) {
            a.put(mapInlined.get(i-1), mapInlined.get(i));
        }

        return a;
    }

}
