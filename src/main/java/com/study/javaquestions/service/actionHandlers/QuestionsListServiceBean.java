package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.Buttonable;
import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.domain.*;
import com.study.javaquestions.service.question.QuestionServiceBean;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import com.study.javaquestions.service.topic.TopicServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
//BotSession can be injected
//Buttonable can be injected
public class QuestionsListServiceBean implements ActionHandlerService, Buttonable, BotSession {

    private final SenderServiceBean sender;

    private final TopicServiceBean topicServiceBean;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    private final QuestionServiceBean questionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "CHOOSE TOPIC AND SHOW LIST";
        return request.getStep().toLowerCase().endsWith(requestValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "QUESTIONS LIST");

        Topic topic = defineTopic(request.getSendMessage().getText());
        processQuestionSession(topic, chatID);
        //QuestionSession q = questionsSessions.get(chatID);
        //q.setTopic(topic);

        createKeyboard(request, defineKeyboard());

        sender.sendMessage(request, "Список питань 👇");
        defineQuestions(request);
    }

    private void processQuestionSession(Topic topic, String chatID) {
        questionSessionServiceBean.updateTopicByChatId(chatID, topic);
    }

    private void defineQuestions(Request request) {
        try {
            List<Question> questions = questionServiceBean.getQuestionsListByLevelAndTopic(request.getSendMessage().getChatId());
            if (questions.size() == 0) noQuestionsToShow(request);
            else showQuestions(questions, request);
        } catch (Exception e) {
            sender.sendMessage(request, "Не можу завантажити питання \uD83E\uDD37");
            e.printStackTrace();
        }
    }

    private synchronized void noQuestionsToShow(Request request) {
        sender.sendMessage(request, "В цій категорії ще немає питань \uD83E\uDD37");
    }

    private void showQuestions(List<Question> questions, Request request) {

        questions.forEach(q -> sender.sendMessageWithButtons(
                request,
                q.getTitle(),
                createInlineKeyboard(
                        getKeyboardMap(Arrays.asList("\uD83D\uDD2E Відкрити відповідь", "Відкрити відповідь від питання " + "#" + q.getId()))
                )));
    }

    private Topic defineTopic(String topicName) {
        return topicServiceBean.getByName(topicName);
    }

    public List<String> defineKeyboard() {
        return List.of("🔙 Повернутись до головного меню");
    }
}
