package com.study.javaquestions.service;

import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.repository.QuestionSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionSessionServiceBean {

    private final QuestionSessionRepository questionSessionRepository;

    public QuestionSession create(QuestionSession questionSession) {
        return questionSessionRepository.save(questionSession);
    }

}
