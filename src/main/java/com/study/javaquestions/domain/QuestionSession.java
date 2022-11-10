package com.study.javaquestions.domain;

import lombok.*;
import javax.persistence.*;

@Table(name = "question_sessions")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Level level;

    @OneToOne(cascade = CascadeType.ALL)
    private Topic topic;

    private String title;

    private String hint;

    private String answer;

    private String chatID;

    public QuestionSession(String chatID) {
        this.chatID = chatID;
    }

    @Override
    public String toString() {
        return "<b>" + title + "</b>\n\n" +
                "Тема: " + topic.getName() + "\n" +
                "Рівень: " + level.getName() + "\n\n" +
                "Підказка: " + hint + "\n" +
                "Відповідь: " + answer;
    }
}
