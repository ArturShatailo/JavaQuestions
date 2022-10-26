package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.sender.SenderServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
//BotSession can be injected
public class StartBotServiceBean implements ActionHandlerService, BotSession, KeyboardButtons {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "/start";
        String requestValueBack = "Повернутись до головного меню";
        return request.getSendMessage().getText().toLowerCase()
                .endsWith(requestValue.toLowerCase()) ||
                request.getSendMessage().getText().toLowerCase()
                        .endsWith(requestValueBack.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "START");

        showKeyboardButtons(request,
                "Вітаю, радий тебе бачити \uD83D\uDE4B"+
                "\nЧого бажаєш, розробнику?");
    }

    @Override
    public void showKeyboardButtons(Request request, String text) {
        sender.sendMessageWithButtons(
                request,
                text,
                buttons.createKeyboard(defineKeyboard())
        );
    }

    @Override
    public List<String> defineKeyboard() {
        return Arrays.asList("📋 Список питань", "💾 Збережені питання",
                "➕ Додати питання", "🗳 Емітація співбесіди");
    }
}
