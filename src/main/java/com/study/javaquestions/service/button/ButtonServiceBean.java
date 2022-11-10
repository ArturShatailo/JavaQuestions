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
import java.util.stream.Collectors;

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
        replyKeyboardMarkup.setKeyboard(defineKeyboardButtons(buttonsText));
    }

    @Override
    public ReplyKeyboardMarkup createKeyboard(List<String> buttonsText){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // The list of keyboard is created here
        replyKeyboardMarkup.setKeyboard(defineKeyboardButtons(buttonsText));
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup createKeyboardColumns(List<String> buttonsText){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // The list of keyboard is created here
        replyKeyboardMarkup.setKeyboard(defineKeyboardColumnsButtons(buttonsText));
        return replyKeyboardMarkup;
    }

    private List<KeyboardRow> defineKeyboardColumnsButtons(List<String> buttonsText) {

        List<KeyboardRow> keyboard = new ArrayList<>();

        if (buttonsText.size() == 1) return defineKeyboardButtons(buttonsText);
        else if (buttonsText.size() % 2 != 0) buttonsText.add("null");

        for (int i = 1; i < buttonsText.size(); i+=2) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(buttonsText.get(i-1)));
            if ((buttonsText.get(i) != null))
                keyboardRow.add(new KeyboardButton(buttonsText.get(i)));
            keyboard.add(keyboardRow);
        }
        return keyboard;
    }

    private List<KeyboardRow> defineKeyboardButtons(List<String> buttonsText) {
        return buttonsText.stream().map(s -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(s));
            return keyboardRow;
        }).collect(Collectors.toList());
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

    @Override
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

        for (int i = 1; i < mapInlined.size(); i+=2)
            a.put(mapInlined.get(i-1), mapInlined.get(i));

        return a;
    }

}
