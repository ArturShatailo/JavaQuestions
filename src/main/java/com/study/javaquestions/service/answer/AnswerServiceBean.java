package com.study.javaquestions.service.answer;

import com.study.javaquestions.domain.Answer;
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

}