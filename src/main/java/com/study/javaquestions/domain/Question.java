package com.study.javaquestions.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "questions")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String answer;

    private String hint;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Topic topic;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Level level;

    @Override
    public String toString() {
        return "*" + title + "*\n\n" +
                "Тема: " + topic.getName() + "\n" +
                "Рівень: " + level.getName() + "\n\n" +
                "Підказка: " + hint + "\n" +
                "Відповідь: " + answer;
    }
}
