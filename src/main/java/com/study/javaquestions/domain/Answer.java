package com.study.javaquestions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Table(name = "answers")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatID;

    @OneToOne(cascade = CascadeType.MERGE)
    private Question question;

    private String answer;
}
