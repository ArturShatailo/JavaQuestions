package com.study.javaquestions.service.actionHandlers;

import com.study.javaquestions.bot.componenents.BotSession;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.question.QuestionServiceBean;
import com.study.javaquestions.service.sender.SenderServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ShowAnswerServiceBean implements ActionHandlerService, BotSession, Showable<Question> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionServiceBean questionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "Відкрити відповідь на питання";
        String requestSessionValue = "QUESTIONS LIST";
        return request.getSendMessage().getText().toLowerCase().startsWith(requestValue.toLowerCase())
                && request.getStep().startsWith(requestSessionValue);
    }

    @Override
    public void sendRequest(Request request) {
        String chatID = request.getSendMessage().getChatId();

        sessions.put(chatID, "QUESTIONS ANSWER");

        buttons.createKeyboard(request, defineKeyboard());
        defineRequest(request);
    }

    @Override
    public void defineRequest(Request request) {

        String textID = substringDataFromMessage(request);

        try {
            Long id = Long.parseLong(textID);
            Question question = questionServiceBean.getById(id);
            if (question == null) nothingToShow(request);
            else show(question, request);
        } catch (NumberFormatException nfe) {
            sender.sendMessage(request, "Не можу завантажити питання з id = " + textID + "\uD83E\uDD37");
            nfe.printStackTrace();
        } catch (Exception e) {
            sender.sendMessage(request, "Не можу завантажити питання \uD83E\uDD37");
            e.printStackTrace();
        }
    }

    @Override
    public void nothingToShow(Request request) {
        sender.sendMessage(request, "Вибач, але я не знаю відповіді на це питання \uD83E\uDD37");
    }

    @Override
    public void show(Question question, Request request) {
        sender.sendMessage(request, question.getAnswer());
    }

    private String substringDataFromMessage(Request request) {
        String message = request.getSendMessage().getText();
        return message.substring(message.indexOf("#") + 1);
    }

    public List<String> defineKeyboard() {
        return List.of("🔙 Повернутись до списку питань");
    }

}
