package com.study.javaquestions.repository;

import com.study.javaquestions.domain.Answer;
import com.study.javaquestions.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findAnswerByChatIDAndQuestion(String ChatID, Question question);

}
