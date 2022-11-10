package com.study.javaquestions.service.actionHandlers.comands;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class ResetServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    @Override
    public int globalCheck() {
        return 1;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "/reset";
        return request.getSendMessage().getText().toLowerCase()
                        .endsWith(requestValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "START");
        sessionSteps.put(chatID, "START");
        questionsSessions.remove(chatID);

        showKeyboardButtons(request,
                "Твоя сессія оновлена",
                Arrays.asList("📋 Список питань", /*"💾 Збережені питання",*/
                        "➕ Додати питання", "🗳 Емітація співбесіди"));
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
