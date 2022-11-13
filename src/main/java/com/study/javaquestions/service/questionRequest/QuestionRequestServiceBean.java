package com.study.javaquestions.service.questionRequest;

import com.study.javaquestions.domain.*;
import com.study.javaquestions.repository.QuestionRequestRepository;
import com.study.javaquestions.service.question.QuestionServiceBean;
import com.study.javaquestions.util.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionRequestServiceBean {

    private final QuestionServiceBean questionServiceBean;

    private final QuestionRequestRepository questionRequestRepository;

    public QuestionRequest create(QuestionRequest questionRequest) {
        return questionRequestRepository.save(questionRequest);
    }

    public List<QuestionRequest> getAll() {
        return questionRequestRepository.findAll();
    }

    public QuestionRequest createFromQuestionSession(QuestionSession qs) {
        QuestionRequest qr = new QuestionRequest();
        qr.setTopic(qs.getTopic());
        qr.setLevel(qs.getLevel());
        qr.setTitle(qs.getTitle());
        qr.setAnswer(qs.getAnswer());
        qr.setHint(qs.getHint());
        qr.setChatId(qs.getChatID());
        return questionRequestRepository.save(qr);
    }

    public QuestionRequest getById(Long id){
        return questionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionRequest not found with id = " + id));
    }

    public void updateStatusById(Long id, String status) {
        QuestionRequest questionRequest = getById(id);
        if (status.equals("confirmed")) questionServiceBean.createFromQuestionRequest(questionRequest);
        questionRequestRepository.updateStatusById(id, status);
    }
}
