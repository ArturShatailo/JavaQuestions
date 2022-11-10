package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.Interview;
import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.InlineKeyboardButtons;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.interview.InterviewServiceBean;
import com.study.javaquestions.service.level.LevelServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
//BotSession can be injected
public class ChooseInterviewLevelServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, KeyboardButtons<String>, InlineKeyboardButtons<Level> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final InterviewServiceBean interviewServiceBean;

    private final LevelServiceBean levelServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "Обрати рівень співбесіди";
        String requestValueBack = "Повернутись до вибору рівня";
        return request.getSendMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase())
                || request.getSendMessage().getText().toLowerCase().endsWith(requestValueBack.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "CHOOSE INTERVIEW LEVEL");

        processInterview(chatID);

        showKeyboardButtons(request,
                "Дякую, " + request.getUser().getFirstName() + " 🙂",
                List.of("🔙 Повернутись до головного меню"));

        showInlineButtons(getLevels(), request);
    }

    private void processInterview(String chatID) {
        Interview interview = new Interview();
        interview.setChatID(chatID);
        interviewServiceBean.createOrUpdateByChatID(interview);
    }

    @Override
    public void showInlineButtons(Collection<Level> levels, Request request) {
        sender.sendMessageWithButtons(
                request,
                "Обери рівень з представлених нижче 📊",
                buttons.createInlineKeyboard(
                        levels.stream()
                                .map(Level::getName)
                                .collect(Collectors.toList())
                ));
    }

    private List<Level> getLevels(){
        return levelServiceBean.getAll();
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
