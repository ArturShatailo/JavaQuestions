package com.study.javaquestions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name = "question_requests")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;

    private String title;

    private String answer;

    private String hint;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Topic topic;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Level level;

    private String status = "processing";

    private String created = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date());

    @Override
    public String toString() {
        return "<b>" + title + "</b>\n\n" +
                "Тема: " + topic.getName() + "\n" +
                "Рівень: " + level.getName() + "\n\n" +
                "Підказка: " + hint + "\n" +
                "Відповідь: " + answer + "\n\n" +
                "створено: " + created + " / " + status;
    }
}
