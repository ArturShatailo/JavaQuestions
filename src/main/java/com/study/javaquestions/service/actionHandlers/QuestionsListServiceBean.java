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
import java.util.Set;

@AllArgsConstructor
@Service
//BotSession can be injected
//Buttonable can be injected
public class QuestionsListServiceBean implements ActionHandlerService, Buttonable, BotSession {

    private final SenderServiceBean sender;

    private final TopicServiceBean topicServiceBean;

    private final QuestionSessionServiceBean questionSessionServiceBean;

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
        QuestionSession q = questionsSessions.get(chatID);
        q.setTopic(topic);

        //questionSessionServiceBean.create(q);
        //questionSessionServiceBean.updateTopicByChatId(chatID, request.getSendMessage().getText());

        createKeyboard(request, defineKeyboard());

        sender.sendMessage(request, "Список питань 👇");
        defineQuestions(q, request);
    }

    private void defineQuestions(QuestionSession q, Request request) {
        try {
            Set<Question> questions = q.getTopic().getQuestions();
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

    private void showQuestions(Set<Question> questions, Request request) {

        long time5 = System.currentTimeMillis();

        questions.forEach(q -> sender.sendMessageWithButtons(
                request,
                q.getTitle(),
                createInlineKeyboard(
                        getKeyboardMap(Arrays.asList("\uD83D\uDD2E Відкрити відповідь", "Відкрити відповідь від питання " + "#" + q.getId()))
                )));

        System.out.println("SHOW QUESTIONS | TIME IS SPENT: " + (System.currentTimeMillis() - time5));
        System.out.println("\n----------------------------------------\n");
    }

    private Topic defineTopic(String topicName) {
        return topicServiceBean.getByName(topicName);
    }

    public List<String> defineKeyboard() {
        return Arrays.asList("🔙 Повернутись до головного меню");
    }
}
