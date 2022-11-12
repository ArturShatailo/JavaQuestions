package com.study.javaquestions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Table(name = "clients")
@Data
@Entity
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;

    private String firstname;

    private String lastname;

    private String username;

}
