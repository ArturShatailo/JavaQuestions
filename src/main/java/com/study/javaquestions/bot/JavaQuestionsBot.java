package com.study.javaquestions.bot;

import com.study.javaquestions.bot.componenents.BotFactory;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.controller.container.HandlerContainer;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.domain.Request;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Component
@AllArgsConstructor
public class JavaQuestionsBot extends TelegramLongPollingBot implements BotFactory, BotSession {

    private final SenderServiceBean sender;
    private final HandlerContainer hc;

    @Override
    public String getBotUsername() {
        return bot.getUsername();
    }

    @Override
    public String getBotToken() {
        return bot.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText())
            processMessage(update.getMessage());
        else if (update.hasCallbackQuery()) {
            update.getCallbackQuery().getMessage().setText(update.getCallbackQuery().getData());
            processMessage(update.getCallbackQuery().getMessage());
        }else
            log.warn("Unexpected update from user");
    }

    private void processMessage(Message message) {

        User from = message.getFrom();

        SendMessage sendMessage = createSendMessage(message);
        Request request = startRequest(sendMessage, from);
        defineAction(request);
    }

    private SendMessage createSendMessage(Message message) {

        String inputtedText = message.getText();
        String userFirstName = message.getFrom().getFirstName();
        Long chatId = message.getChatId();

        log.info("SendMessage object will be created with data: [{}, {}] : {}]", chatId, userFirstName, inputtedText);

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(inputtedText)
                .build();
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }

    private Request startRequest(SendMessage sendMessage, User from) {
        String session = sessions.get(sendMessage.getChatId());
        log.info("Get session request: {}", session);
        return new Request(sendMessage, session != null ? session : "START", from);
    }

    private synchronized void defineAction(Request request) {

        if(!hc.chooseHandler(request)){

            sender.sendMessage(request, "Я не знаю цієї команди, вибач 🤷");
            sender.sendMessage(request, "/start");

            defineAction(request);
        }

    }


}
