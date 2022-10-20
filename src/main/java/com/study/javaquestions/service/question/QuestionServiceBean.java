package com.study.javaquestions.service.question;

import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.repository.LevelRepository;
import com.study.javaquestions.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceBean {

    private final QuestionRepository questionRepository;



}
