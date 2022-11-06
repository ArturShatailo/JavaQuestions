package com.study.javaquestions.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    List<Question> questions;

    @ManyToMany(cascade = CascadeType.MERGE)
    List<Answer> answers;

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
