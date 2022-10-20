package com.study.javaquestions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@Table(name = "questions")
@Data
@Entity
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String answer;

    private String hint;

//    @ManyToOne(cascade = CascadeType.ALL)
//    private Topic topic;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    private Level level;

}
