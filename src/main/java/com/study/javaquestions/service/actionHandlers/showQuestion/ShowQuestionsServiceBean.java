package com.study.javaquestions.service.actionHandlers.showQuestion;

import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.*;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.actionHandlers.Showable;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.question.QuestionServiceBean;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.service.topic.TopicServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class ShowQuestionsServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, Showable<List<Question>>, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final TopicServiceBean topicServiceBean;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    private final QuestionServiceBean questionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionStep = "CHOOSE TOPIC";
        String requestSessionValue = "SHOW_QUESTIONS";
        String requestValueBack = "Повернутись до списку питань";
        return (request.getSessionStep().toLowerCase().endsWith(requestSessionStep.toLowerCase())
                || request.getSendMessage().getText().toLowerCase().endsWith(requestValueBack.toLowerCase()))
                && request.getStep().toLowerCase().endsWith(requestSessionValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "QUESTIONS LIST");

        Topic topic = getChosen(request, chatID);
        processQuestionSession(topic, chatID);
        showKeyboardButtons(request,
                    "СПИСОК ПИТАНЬ З ТЕМИ " + topic.getName(),
                    List.of("🔙 Повернутись до вибору теми"));
        defineRequest(request);
    }

    private Topic getChosen(Request request, String chatID) {
        return questionsSessions.containsKey(chatID)
                && questionsSessions.get(chatID).getTopic() != null
                ? questionsSessions.get(chatID).getTopic()
                : defineTopic(request.getSendMessage().getText());
    }

    private void processQuestionSession(Topic topic, String chatID) {
        questionsSessions.get(chatID).setTopic(topic);
        questionSessionServiceBean.updateTopicByChatId(chatID, topic);
    }

    private Topic defineTopic(String topicName) {
        return topicServiceBean.getByName(topicName);
    }

    @Override
    public void showKeyboardButtons(Request request, String text, List<String> buttonsText) {
        sender.sendMessageWithButtons(
                request,
                text,
                buttons.createKeyboard(buttonsText)
        );
    }

    @Override
    public void defineRequest(Request request) {
        try {
            List<Question> questions = questionServiceBean
                    .getQuestionsListByLevelAndTopicFromQuestionSession(request.getSendMessage().getChatId());
            if (questions.size() == 0) nothingToShow(request);
            else show(questions, request);
        } catch (Exception e) {
            sender.sendMessage(request, "Не можу завантажити питання \uD83E\uDD37");
            e.printStackTrace();
        }
    }

    @Override
    public void nothingToShow(Request request) {
        sender.sendMessage(request, "В цій категорії ще немає питань \uD83E\uDD37");
    }

    @Override
    public void show(List<Question> questions, Request request) {
        questions.forEach(q -> sender.sendMessageWithButtons(
                request,
                "❓ " + q.getTitle() + "\n\n" +
                     "Підказка: <span class=\"tg-spoiler\">" + q.getHint() + "</span>",
                buttons.createInlineKeyboard(buttons.getKeyboardMap(
                                Arrays.asList(
                                        "Відповідь", "Відкрити відповідь на питання " + "#" + q.getId())
                )))
        );
    }
}
