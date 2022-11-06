package com.study.javaquestions.service.interview;

import com.study.javaquestions.domain.Interview;
import com.study.javaquestions.repository.InterviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class InterviewServiceBean {

    private final InterviewRepository interviewRepository;

    public Interview create(Interview interview) {
        return interviewRepository.save(interview);
    }

    public void updateById(Long id, Interview interview) {
        interviewRepository.findById(id).map(
                i -> {
                    i.setChatID(interview.getChatID());
                    i.setQuestions(interview.getQuestions());
                    return interviewRepository.save(i);
                }
        );
    }

    public List<Interview> getAll() {
        return interviewRepository.findAll();
    }

    public Interview getByChatID(String chatID) {
        return interviewRepository.findInterviewByChatID(chatID)
                .orElse(null);
    }

}
