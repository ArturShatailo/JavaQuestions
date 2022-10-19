package com.study.javaquestions.domain;

import lombok.Data;
import javax.persistence.*;

@Table(name = "question_sessions")
@Data
@Entity
public class QuestionSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;

    private String topic;

}
