package com.study.javaquestions.repository;

import com.study.javaquestions.domain.QuestionSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionSessionRepository extends JpaRepository<QuestionSession, Long> {
}
