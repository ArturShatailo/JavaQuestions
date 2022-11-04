package com.study.javaquestions.repository;

import com.study.javaquestions.domain.QuestionInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionInterviewRepository extends JpaRepository<QuestionInterview, Long> {
}
