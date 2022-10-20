package com.study.javaquestions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Table(name = "question_sessions")
@Data
@Entity
@NoArgsConstructor
public class QuestionSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Level level;

    @OneToOne(cascade = CascadeType.ALL)
    private Topic topic;

    private String chatID;

    public QuestionSession(String chatID) {
        this.chatID = chatID;
    }

}
