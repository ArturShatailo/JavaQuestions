package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.domain.*;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
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
public class QuestionsListServiceBean implements ActionHandlerService, BotSession, Showable<List<Question>>, KeyboardButtons {

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
        String requestValue = "CHOOSE TOPIC AND SHOW LIST";
        String requestValueBack = "Повернутись до списку питань";
        return request.getStep().toLowerCase().endsWith(requestValue.toLowerCase())
                || request.getSendMessage().getText().toLowerCase().endsWith(requestValueBack.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessions.put(chatID, "QUESTIONS LIST");

        //checkQuestionSession Map if there is such topic, so download it.
        //If no, try to download from db further and set a Topic in QuestionSession

        Topic topic = defineTopic(request.getSendMessage().getText());
        processQuestionSession(topic, chatID);
        //QuestionSession q = questionsSessions.get(chatID);
        //q.setTopic(topic);

        showKeyboardButtons(request, "Список питань з теми *" + topic.getName() + "*");
        defineRequest(request);
    }

    private void processQuestionSession(Topic topic, String chatID) {
        questionSessionServiceBean.updateTopicByChatId(chatID, topic);
    }

    private Topic defineTopic(String topicName) {
        return topicServiceBean.getByName(topicName);
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
        return List.of("🔙 Повернутись до вибору рівня");
    }

    @Override
    public void defineRequest(Request request) {
        try {
            List<Question> questions = questionServiceBean.getQuestionsListByLevelAndTopic(request.getSendMessage().getChatId());
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
                q.getTitle(),
                buttons.createInlineKeyboard(
                        buttons.getKeyboardMap(Arrays.asList("\uD83D\uDD2E Відкрити відповідь", "Відкрити відповідь на питання " + "#" + q.getId()))
                )));
    }
}
