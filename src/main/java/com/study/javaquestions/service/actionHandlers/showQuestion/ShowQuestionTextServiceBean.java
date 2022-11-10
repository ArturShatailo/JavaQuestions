package com.study.javaquestions.service.actionHandlers.showQuestion;

import com.study.javaquestions.bot.sender.SenderServiceBean;
import com.study.javaquestions.bot.session.BotSession;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.Request;
import com.study.javaquestions.service.actionHandlers.ActionHandlerService;
import com.study.javaquestions.service.actionHandlers.Showable;
import com.study.javaquestions.service.button.ButtonServiceBean;
import com.study.javaquestions.service.question.QuestionServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ShowQuestionTextServiceBean implements ActionHandlerService, BotSession, Showable<Question> {

    private final SenderServiceBean sender;

    private final ButtonServiceBean buttons;

    private final QuestionServiceBean questionServiceBean;

    @Override
    public int globalCheck() {
        return 0;
    }

    @Override
    public boolean mineCheck(Request request) {
        String requestValue = "Відкрити текст питання";
        String requestSessionStep = "QUESTIONS LIST";
        return request.getSendMessage().getText().toLowerCase().startsWith(requestValue.toLowerCase())
                && request.getSessionStep().startsWith(requestSessionStep);
    }

    @Override
    public void sendRequest(Request request) {
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
        sender.sendMessage(request, "Вибач, але я не знаю що це за питання \uD83E\uDD37");
    }

    @Override
    public void show(Question q, Request request) {

        sender.changeMessageWithButtons(
                request,
                "❓ " + q.getTitle() + "\n\n" +
                     "Підказка: <span class=\"tg-spoiler\">" + q.getHint() + "</span>",
                buttons.createInlineKeyboard(buttons.getKeyboardMap(
                                Arrays.asList(
                                        "Відповідь", "Відкрити відповідь на питання " + "#" + q.getId())
                ))
        );
    }

    private String substringDataFromMessage(Request request) {
        String message = request.getSendMessage().getText();
        return message.substring(message.indexOf("#") + 1);
    }

    public List<String> defineKeyboard() {
        return List.of("🔙 Повернутись до списку питань");
    }

}
