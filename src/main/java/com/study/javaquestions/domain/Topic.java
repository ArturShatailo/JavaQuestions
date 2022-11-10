package com.study.javaquestions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Table(name = "topics")
@Data
@Entity
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
