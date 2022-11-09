package com.study.javaquestions.domain;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "interviews")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatID;

    private Integer currentQuestion = 0;

    private Integer maxQuestion;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<Question> questions;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<Answer> answers = new ArrayList<>();

    public Question defineCurrentQuestion(){
        return questions.get(currentQuestion - 1);
    }

    public boolean checkQuestionsAmount(){
        return currentQuestion > maxQuestion;
    }

    public void upscaleCurrentQuestion() {
        currentQuestion += 1;
    }
}
