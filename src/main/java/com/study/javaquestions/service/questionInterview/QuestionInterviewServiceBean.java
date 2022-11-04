package com.study.javaquestions.service.questionInterview;

import com.study.javaquestions.repository.QuestionInterviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionInterviewServiceBean {

    private final QuestionInterviewRepository questionInterviewRepository;

}
