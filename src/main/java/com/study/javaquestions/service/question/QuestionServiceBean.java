package com.study.javaquestions.service.question;

import com.study.javaquestions.domain.*;
import com.study.javaquestions.repository.QuestionRepository;
import com.study.javaquestions.repository.QuestionSessionRepository;
import com.study.javaquestions.util.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceBean {

    private final QuestionRepository questionRepository;

    private final QuestionSessionRepository questionSessionRepository;

    @Transactional
    public List<Question> getQuestionsListByLevelAndTopicFromQuestionSession(String chatID){
        QuestionSession questionSession = questionSessionRepository.getByChatID(chatID)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find QuestionSession with chatID = " + chatID));
        return questionRepository.findQuestionsByLevelAndTopic(
                questionSession.getLevel(), questionSession.getTopic());
    }

    @Transactional
    public List<Question> getQuestionsListByLevelAndTopic(Level level, Topic topic){
        return questionRepository.findQuestionsByLevelAndTopic(
                level, topic);
    }

    public Question getById (Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find Question with id = " + id));
    }

    public Question createFromQuestionRequest(QuestionRequest questionRequest) {
        Question question = new Question();
        question.setTopic(questionRequest.getTopic());
        question.setLevel(questionRequest.getLevel());
        question.setTitle(questionRequest.getTitle());
        question.setAnswer(questionRequest.getAnswer());
        question.setHint(questionRequest.getHint());
        return questionRepository.save(question);
    }
}
