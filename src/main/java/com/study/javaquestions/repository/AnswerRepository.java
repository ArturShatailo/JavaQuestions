package com.study.javaquestions.repository;

import com.study.javaquestions.domain.Answer;
import com.study.javaquestions.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findAnswerByChatIDAndQuestion(String chatID, Question question);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE Answer a " +
                    "SET a.answer = :#{#ans.answer}, a.chatID = :#{#ans.chatID} " +
                    "WHERE a.chatID = :chatID AND a.question = :#{#question}")
    void updateAnswerByChatIDAndQuestion(String chatID, Question question, Answer ans);
}
