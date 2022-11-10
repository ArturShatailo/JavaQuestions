package com.study.javaquestions.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "answers")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatID;

    @OneToOne(cascade = CascadeType.MERGE)
    private Question question;

    private String answer;

    @Override
    public String toString() {
        return "<b>" + question.getTitle() + "</b>\n\n" +
                "Правильна відповідь: " + question.getAnswer() + "\n" +
                "-----\n"+
                "Твоя відповідь: " + answer;
    }

}
