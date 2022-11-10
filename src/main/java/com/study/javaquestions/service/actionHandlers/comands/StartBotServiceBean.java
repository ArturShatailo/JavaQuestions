package com.study.javaquestions.service.actionHandlers.comands;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.service.client.ClientServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
//BotSession can be injected
public class StartBotServiceBean implements ActionHandlerService, BotSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final ClientServiceBean clientServiceBean;

    @Override
    public int globalCheck() {
        return 1;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "/start";
        String requestValueBack = "Повернутись до головного меню";
        String requestValueBack1 = "Noooo God! No! God, please, no!";
        return request.getSendMessage().getText().toLowerCase()
                        .endsWith(requestValue.toLowerCase()) ||
                request.getSendMessage().getText().toLowerCase()
                        .endsWith(requestValueBack.toLowerCase()) ||
                request.getSendMessage().getText().toLowerCase()
                        .endsWith(requestValueBack1.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "START");
        sessionSteps.put(chatID, "START");

        processRequest(request);

        showKeyboardButtons(request,
                "Вітаю, радий тебе бачити \uD83D\uDE4B \nЧого бажаєш, розробнику?",
                Arrays.asList("📋 Список питань", /*"💾 Збережені питання",*/
                        "➕ Додати питання", "🗳 Емітація співбесіди"));
    }

    private void processRequest(Request request) {
        clientServiceBean.createFromUser(
                request.getUser(),
                request.getSendMessage().getChatId()
        );
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
