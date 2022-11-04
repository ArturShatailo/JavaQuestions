package com.study.javaquestions.service.interview;

import com.study.javaquestions.domain.Interview;
import com.study.javaquestions.repository.InterviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class InterviewServiceBean {

    private final InterviewRepository interviewRepository;

    public Interview create(Interview interview) {
        return interviewRepository.save(interview);
    }

    public List<Interview> getAll() {
        return interviewRepository.findAll();
    }

}
