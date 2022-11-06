package com.study.javaquestions.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    List<Question> questions;

    @ManyToMany(cascade = CascadeType.MERGE)
    List<Answer> answers;
}
