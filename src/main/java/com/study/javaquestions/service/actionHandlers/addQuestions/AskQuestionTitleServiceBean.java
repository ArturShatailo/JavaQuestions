package com.study.javaquestions.service.actionHandlers.addQuestions;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.bot.session.QuestionMenuSession;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.domain.Topic;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.button.KeyboardButtons;
import com.study.javaquestions.service.questionSession.QuestionSessionServiceBean;
import com.study.javaquestions.service.topic.TopicServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class AskQuestionTitleServiceBean implements ActionHandlerService, BotSession, QuestionMenuSession, KeyboardButtons<String> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final TopicServiceBean topicServiceBean;

    private final QuestionSessionServiceBean questionSessionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestSessionStep = "CHOOSE TOPIC";
        String requestSessionValue = "ADD_QUESTIONS";
        String requestValueBack = "Повернутись до написання питання";
        return (request.getSessionStep().toLowerCase().endsWith(requestSessionStep.toLowerCase())
                || request.getSendMessage().getText().toLowerCase().endsWith(requestValueBack.toLowerCase()))
                && request.getStep().toLowerCase().endsWith(requestSessionValue.toLowerCase());
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();
        sessionSteps.put(chatID, "QUESTION TITLE");

        Topic topic = getChosen(request, chatID);
        processQuestionSession(topic, chatID);
        showKeyboardButtons(request,
                "Напиши будь-ласка, текст питання і відправ мені",
                List.of("🔙 Повернутись до вибору теми"));
    }

    private Topic getChosen(Request request, String chatID) {
        return questionsSessions.containsKey(chatID)
                && questionsSessions.get(chatID).getTopic() != null
                ? questionsSessions.get(chatID).getTopic()
                : defineTopic(request.getSendMessage().getText());
    }

    private Topic defineTopic(String topicName) {
        return topicServiceBean.getByName(topicName);
    }

    private void processQuestionSession(Topic topic, String chatID) {
        questionsSessions.get(chatID).setTopic(topic);
        questionSessionServiceBean.updateTopicByChatId(chatID, topic);
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
