package com.study.javaquestions.bot.sender;

import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;

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

    public void changeMessageWithButtons(Request request, String text, InlineKeyboardMarkup replyKeyboard){
        try {
            EditMessageText e = new EditMessageText();
            e.setChatId(request.getSendMessage().getChatId());
            e.setMessageId(request.getMessage().getMessageId());
            e.setReplyMarkup(replyKeyboard);
            e.setText(text);
            senderService.execute(e);
        } catch (TelegramApiException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void sendAudio(String chatId, String link){

        InputFile audioFile = new InputFile(new File(link));
        var sendAudio = new SendAudio(chatId, audioFile);

        try {
            senderService.execute(sendAudio);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
