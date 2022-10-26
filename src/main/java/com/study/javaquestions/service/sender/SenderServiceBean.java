package com.study.javaquestions.service.sender;

import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
public class SenderServiceBean{

    private final SenderService senderService;

    public /*synchronized*/ void sendMessage(Request request, String text){

        var sendMessage = request.getSendMessage();

        if(!text.equals("")) sendMessage.setText(text);

        try {
            senderService.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public /*synchronized*/ void sendMessageWithButtons(Request request, String text, ReplyKeyboard replyKeyboard) {

        var sendMessage = request.getSendMessage();

        if(!text.equals("")) sendMessage.setText(text);

        try {
            sendMessage.setText(sendMessage.getText());
            sendMessage.setReplyMarkup(replyKeyboard);
            senderService.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
