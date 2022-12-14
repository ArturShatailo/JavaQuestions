package com.study.javaquestions.bot;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.util.BotConfig;
import com.study.javaquestions.controller.container.HandlerContainer;
import com.study.javaquestions.bot.sender.SenderServiceBean;
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
public class JavaQuestionsBot extends TelegramLongPollingBot implements BotSession {

    private final SenderServiceBean sender;

    private final HandlerContainer hc;

    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.botBean().getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.botBean().getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText())
            processMessage(update.getMessage());
        else if (update.hasCallbackQuery()) {
            processMessage(
                    update.getCallbackQuery().getMessage(),
                    update.getCallbackQuery().getData());
        } else
            log.warn("Unexpected update from user");
    }

    private void processMessage(Message message, String text) {

        User from = message.getFrom();

        SendMessage sendMessage = createSendMessage(message);
        sendMessage.setText(text);
        Request request = startRequest(message, sendMessage, from);
        defineAction(request);
    }

    private void processMessage(Message message) {

        User from = message.getFrom();

        SendMessage sendMessage = createSendMessage(message);
        Request request = startRequest(message, sendMessage, from);
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
        sendMessage.enableHtml(true);
        //sendMessage.setParseMode("MarkdownV2");

        return sendMessage;
    }

    private Request startRequest(Message message, SendMessage sendMessage, User from) {
        String session = sessions.get(sendMessage.getChatId());
        String sessionStep = sessionSteps.get(sendMessage.getChatId());
        log.info("Get session request: {}", session);
        log.info("Get sessionStep request: {}", sessionStep);
        return new Request(
                sendMessage,
                message,
                sessionStep != null ? sessionStep : "START",
                session != null ? session : "START",
                from);
    }

    private synchronized void defineAction(Request request) {

        if (!hc.chooseHandler(request)){

            sessions.put(request.getSendMessage().getChatId(), "START");
            sessionSteps.put(request.getSendMessage().getChatId(), "START");
            sender.sendMessage(request, "?? ???? ???????? ???????? ??????????????, ?????????? ????");
            sender.sendMessage(request, "/start");

            defineAction(request);
        }

    }


}
