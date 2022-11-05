package com.study.javaquestions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.MERGE)
    List<QuestionInterview> questions;

}
