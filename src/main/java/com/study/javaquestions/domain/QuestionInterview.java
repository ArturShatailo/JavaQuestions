package com.study.javaquestions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "questions_interview")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    private Question question;

    private String answer;
}
