package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.level.LevelServiceBean;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.bot.sender.SenderServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ChooseLevelServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, KeyboardButtons<Level>/*, InlineKeyboardButtons<Level>*/ {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    private final LevelServiceBean levelServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "Обрати рівень питання";
        String requestValueBack = "Повернутись до вибору рівня";
        //String requestSession = "QUESTIONS";
        return (request.getSendMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase())
                /*&& request.getStep().toLowerCase().startsWith(requestSession.toLowerCase())*/)
                || request.getSendMessage().getText().toLowerCase().endsWith(requestValueBack.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "CHOOSE LEVEL");

        //questionsSessions.put(chatID, new QuestionSession(chatID));
        processQuestionSession(chatID);

        showKeyboardButtons(request,
                "Дякую, " + request.getUser().getFirstName() + " 🙂",
                getLevels());

//        showKeyboardButtons(request,
//                "Дякую, " + request.getUser().getFirstName() + " 🙂",
//                List.of("🔙 Повернутись до головного меню"));

//        showInlineButtons(getLevels(), request);
    }

    private void processQuestionSession(String chatID) {
        QuestionSession questionSession = new QuestionSession(chatID);
        questionsSessions.put(chatID, questionSession);
        questionSessionServiceBean.create(questionSession);
    }

//    @Override
//    public void showInlineButtons(Collection<Level> levels, Request request) {
//        sender.sendMessageWithButtons(
//                request,
//                "Обери рівень з представлених нижче 📊",
//                buttons.createInlineKeyboard(
//                        levels.stream()
//                                .map(Level::getName)
//                                .collect(Collectors.toList())
//                ));
//    }

    private List<Level> getLevels(){
        return levelServiceBean.getAll();
    }

    @Override
    public void showKeyboardButtons(Request request, String text, List<Level> buttonsList) {

        List<String> buttonsText = buttonsList
                .stream()
                .map(Level::getName)
                .collect(Collectors.toList());
        buttonsText.add("🔙 Повернутись до головного меню");

        sender.sendMessageWithButtons(
                request,
                text,
                buttons.createKeyboard(buttonsText)
        );
    }



}
