package com.study.javaquestions.service.interview;

import com.study.javaquestions.domain.Answer;
import com.study.javaquestions.domain.Interview;
import com.study.javaquestions.repository.InterviewRepository;
import com.study.javaquestions.util.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InterviewServiceBean {

    private final InterviewRepository interviewRepository;

    public Interview create(Interview interview) {
        return interviewRepository.save(interview);
    }

    public void createOrUpdateByChatID(Interview interview) {
        String chatID = interview.getChatID();
        if (getByChatID(chatID) == null) create(interview);
        else updateByChatID(chatID, interview);
    }

    public void updateById(Long id, Interview interview) {
        interviewRepository.findById(id).map(
                i -> {
                    i.setChatID(interview.getChatID());
                    i.setQuestions(interview.getQuestions());
                    i.setMaxQuestion(interview.getMaxQuestion());
                    i.setCurrentQuestion(interview.getCurrentQuestion());
                    return interviewRepository.save(i);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Can't find an interview with id = " + id));
    }

    public Interview getByChatID(String chatID) {
        return interviewRepository.findInterviewByChatID(chatID)
                .orElse(null);
    }

    public void updateByChatID(String chatID, Interview interview) {
        updateById(getByChatID(chatID).getId(), interview);
    }

    @Transactional
    public void addNewAnswerByChatID(String chatID, Answer answer) {
        Interview interview = getByChatID(chatID);
        interview.getAnswers().add(answer);
        updateByChatID(chatID, interview);
    }
}
