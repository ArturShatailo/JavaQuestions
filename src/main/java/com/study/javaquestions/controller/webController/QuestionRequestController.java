package com.study.javaquestions.controller.webController;

import com.study.javaquestions.domain.QuestionRequest;
import com.study.javaquestions.service.questionRequest.QuestionRequestServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bot")
@AllArgsConstructor
public class QuestionRequestController {

    public final QuestionRequestServiceBean questionRequestServiceBean;

    @GetMapping("/question-requests")
    @ResponseStatus(HttpStatus.FOUND)
    List<QuestionRequest> getAllQuestionRequests(){
        return questionRequestServiceBean.getAll();
    }

    @PatchMapping(value = "/question-requests", params = {"id", "status"})
    @ResponseStatus(HttpStatus.FOUND)
    void updateStatusById(@RequestParam Long id, @RequestParam String status) {
        questionRequestServiceBean.updateStatusById(id, status);
    }

}
