package com.study.javaquestions.service.actionHandlers.interview;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InterviewBeginningServiceBean implements ActionHandlerService, BotSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionValue = "INTERVIEW START";
        String requestValue = "Noooo God! No! God, please, no!";
        String requestValue1 = "Not ready, but let's start";
        String requestValue2 = "Yeah!";
        return (request.getSendMessage().getText().toLowerCase().endsWith(requestValue.toLowerCase())
                || request.getSendMessage().getText().toLowerCase().endsWith(requestValue1.toLowerCase())
                || request.getSendMessage().getText().toLowerCase().endsWith(requestValue2.toLowerCase()))
                && request.getSessionStep().toLowerCase().startsWith(requestSessionValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "INTERVIEW");
        sessionSteps.put(chatID, "INTERVIEW STARTED");

        //define all the questions (random 1-4 from each topic)
        //this list of questions should be saved in database as an Interview
        //the answers list should be saved as a map where key is question id and value is String
        //inputted as an answer.


        //Interview
        //Long id
        //List<InterviewQuestion> questions

        //InterviewQuestion
        //Long id
        //Question question
        //String answer


        showKeyboardButtons(request,
                "Зараз я буду тобі надсилати питання, а ти маєш відпривляти мені відповіді у повідомленні. " +
                        "В кінці співбесіди, ти зможеш самостійно оцінити наскільки ти впорався, я надам тобі список" +
                        "всіх пройдених питань та відповіді на них, різом із твоїм варіантом для порівняння.",
                List.of("Показати перше питання", "🔙 Повернутись до головного меню"));
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
