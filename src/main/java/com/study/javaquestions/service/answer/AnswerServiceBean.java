package com.study.javaquestions.service.answer;

import com.study.javaquestions.domain.Answer;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerServiceBean {

    private final AnswerRepository answerRepository;

    public Answer create(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer createOrUpdateByChatIDAndQuestion(Answer answer) {
        String chatID = answer.getChatID();
        Question question = answer.getQuestion();
        if (getByChatIDAndQuestion(chatID, question) == null) return create(answer);
        else return updateByChatIDAndQuestion(chatID, question, answer);
    }

    public Answer getByChatIDAndQuestion(String chatID, Question question) {
        return answerRepository.findAnswerByChatIDAndQuestion(chatID, question)
                .orElse(null);
    }

    public Answer updateByChatIDAndQuestion(String chatID, Question question, Answer answer) {
        answerRepository.updateAnswerByChatIDAndQuestion(chatID, question, answer);
        return getByChatIDAndQuestion(chatID, question);
    }
}
