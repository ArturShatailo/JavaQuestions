package com.study.javaquestions.repository;

import com.study.javaquestions.domain.QuestionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuestionRequestRepository extends JpaRepository<QuestionRequest, Long> {

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE QuestionRequest q " +
                    "SET q.status = :status " +
                    "WHERE q.id = :id")
    void updateStatusById(Long id, String status);

}
