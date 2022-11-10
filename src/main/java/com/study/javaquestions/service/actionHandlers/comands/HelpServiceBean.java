package com.study.javaquestions.service.actionHandlers.comands;

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

@AllArgsConstructor
@Service
public class HelpServiceBean implements ActionHandlerService, BotSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    @Override
    public int globalCheck() {
        return 1;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "/help";
        return request.getSendMessage().getText().toLowerCase()
                        .endsWith(requestValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        showKeyboardButtons(request,
                "Цей бот стартує відразу як ти тицнеш /start\n" +
                        "Також, ти можеш обрати в меню нижче команди щодо: \n" +
                        "\n" +
                        "📋 переглянути всі питання (за обраною тобою темою і рівнем підготовки)\n" +
                        "➕ стати автором питання і додати запит на розгляд нового питання у список\n" +
                        "🗳 пройти емітацію співбесіди, де тобі буде запропоновано відповісти на декілька питань з кожної теми твого рівня подготовки.  " +
                        "\n\nЧого бажаєш, розробнику?",
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
