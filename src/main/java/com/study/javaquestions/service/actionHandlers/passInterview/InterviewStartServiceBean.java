package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class InterviewStartServiceBean implements ActionHandlerService, BotSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionValue = "START";
        String requestValueBack = "Емітація співбесіди";
        return request.getSendMessage().getText().toLowerCase().endsWith(requestValueBack.toLowerCase())
                && request.getStep().toLowerCase().startsWith(requestSessionValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "INTERVIEW");
        sessionSteps.put(chatID, "INTERVIEW START");

        showKeyboardButtons(request,
                "Будь-ласка, обери що робити далі: ",
                Arrays.asList("Обрати рівень співбесіди", "🔙 Повернутись до головного меню"));
    }

    @Override
    public void showKeyboardButtons(Request request, String text, List<String> buttonsText) {
        sender.sendMessageWithButtons(
                request,
                text,
                buttons.createKeyboard(buttonsText)
        );
    }
}
