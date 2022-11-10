package com.study.javaquestions.service.questionSession;

import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.domain.Topic;
import com.study.javaquestions.repository.QuestionSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class QuestionSessionServiceBean {

    private final QuestionSessionRepository questionSessionRepository;

    public QuestionSession create(QuestionSession questionSession) {
        QuestionSession q = questionSessionRepository.getByChatID(questionSession.getChatID());
        if (q != null) {
            return updateByChatId(questionSession);
        } else {
            return questionSessionRepository.save(questionSession);
        }
    }

    public QuestionSession getByChatId(String chatId){
        return questionSessionRepository.findQuestionSessionByChatID(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Question Session not found with chatId = " + chatId));
    }

    public QuestionSession updateById(Long id, QuestionSession q) {
        return questionSessionRepository.findById(id)
                .map(qs -> {
                    qs.setTopic(q.getTopic());
                    qs.setLevel(q.getLevel());
                    qs.setChatID(q.getChatID());
                    return questionSessionRepository.save(qs);
                })
                .orElseThrow(() -> new EntityNotFoundException("Question Session not found with id = " + id));
    }

    public QuestionSession updateByChatId(QuestionSession q) {
        int status = questionSessionRepository.updateByChatId(q);
        if (status < 1) {
            throw new EntityNotFoundException("Question Session not found with chatID = " + q.getChatID());
        }
        return q;
    }

    public void updateLevelByChatId(String chatID, Level level) {
        questionSessionRepository.updateLevelByChatId(chatID, level);
    }

    public void updateTopicByChatId(String chatID, Topic topic) {
        questionSessionRepository.updateTopicByChatId(chatID, topic);
    }

    public void updateTitleByChatId(String chatID, String title) {
        questionSessionRepository.updateTitleByChatId(chatID, title);
    }

    public void updateHintByChatId(String chatID, String title) {
        questionSessionRepository.updateHintByChatId(chatID, title);
    }

    public void updateAnswerByChatId(String chatID, String title) {
        questionSessionRepository.updateAnswerByChatId(chatID, title);
    }
}
