package com.study.javaquestions.service.question;

import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.repository.QuestionRepository;
import com.study.javaquestions.repository.QuestionSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class QuestionServiceBean {

    private final QuestionRepository questionRepository;

    private final QuestionSessionRepository questionSessionRepository;

    @Transactional
    public List<Question> getQuestionsListByLevelAndTopic(String chatID){
        QuestionSession questionSession = questionSessionRepository.getByChatID(chatID);
        return questionRepository.findQuestionsByLevelAndTopic(
                questionSession.getLevel(), questionSession.getTopic());
    }

    public Question getById (Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find Question with id = " + id));
    }

    public Question createFromQuestionSession(QuestionSession questionSession) {
        Question question = new Question();
        question.setTopic(questionSession.getTopic());
        question.setLevel(questionSession.getLevel());
        question.setTitle(questionSession.getTitle());
        question.setAnswer(questionSession.getAnswer());
        question.setHint(questionSession.getHint());
        return questionRepository.save(question);
    }
}
