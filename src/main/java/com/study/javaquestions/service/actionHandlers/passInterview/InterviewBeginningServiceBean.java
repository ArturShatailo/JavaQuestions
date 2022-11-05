package com.study.javaquestions.service.actionHandlers.passInterview;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.*;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.interview.InterviewServiceBean;
import com.study.javaquestions.service.level.LevelServiceBean;
import com.study.javaquestions.service.question.QuestionServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InterviewBeginningServiceBean implements ActionHandlerService, BotSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final LevelServiceBean levelServiceBean;

    private final InterviewServiceBean interviewServiceBean;

    private final QuestionServiceBean questionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestStepValue = "CHOOSE INTERVIEW LEVEL";
        String requestSessionValue = "INTERVIEW";
        //String requestValue = "Noooo God! No! God, please, no!";
        return request.getStep().toLowerCase().startsWith(requestSessionValue.toLowerCase())
                && request.getSessionStep().toLowerCase().startsWith(requestStepValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "NEW INTERVIEW STARTED");

        defineRequest(request);
        //define all the questions (random 1-4 from each topic)
        //this list of questions should be saved in database as an Interview
        //the answers list should be saved as a map where key is question id and value is String
        //inputted as an answer.

        showKeyboardButtons(request,
                "Зараз я буду тобі надсилати питання, а ти маєш відпривляти мені відповіді у повідомленні. " +
                        "В кінці співбесіди, ти зможеш самостійно оцінити наскільки ти впорався, я надам тобі список" +
                        "всіх пройдених питань та відповіді на них, різом із твоїм варіантом для порівняння.",
                List.of("Показати перше питання", "🔙 Повернутись до головного меню"));
    }

    private void defineRequest(Request request) {

        List<Question> questions = new ArrayList<>();
        Interview interview = interviewServiceBean.getByChatID(request.getSendMessage().getChatId());
        Level level = getChosen(request);
        Set<Topic> topics = level.getTopics();
        topics.forEach(q ->
                questions.addAll(questionServiceBean.getQuestionsListByLevelAndTopic(level, q))
        );

        interview.setQuestions(
                questions.stream()
                        .map(question -> {
                            QuestionInterview q = new QuestionInterview();
                            q.setQuestion(question);
                            return q;
                        })
                        .collect(Collectors.toList())
        );

        interviewServiceBean.updateById(interview.getId(), interview);

    }

    private Level getChosen(Request request) {
        return defineLevel(request.getSendMessage().getText());
    }

    private Level defineLevel(String levelName) {
        return levelServiceBean.getByName(levelName);
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
