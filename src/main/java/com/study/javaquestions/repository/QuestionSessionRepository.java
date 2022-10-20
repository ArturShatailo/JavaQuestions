package com.study.javaquestions.repository;

import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.QuestionSession;
import com.study.javaquestions.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuestionSessionRepository extends JpaRepository<QuestionSession, Long> {

    QuestionSession getByChatID(String chatID);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE QuestionSession q " +
            "SET q.level = :#{#qs.level}, q.topic = :#{#qs.topic} " +
            "WHERE q.chatID = :#{#qs.chatID}")
    int updateByChatId (QuestionSession qs);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE QuestionSession q " +
                    "SET q.level = :level " +
                    "WHERE q.chatID = :chatID")
    void updateLevelByChatId (String chatID, Level level);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE QuestionSession q " +
                    "SET q.topic = :topic " +
                    "WHERE q.chatID = :chatID")
    void updateTopicByChatId (String chatID, Topic topic);

}
