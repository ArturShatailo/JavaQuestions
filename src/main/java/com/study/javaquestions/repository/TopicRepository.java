package com.study.javaquestions.repository;

import com.study.javaquestions.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic findTopicByName(String name);

}
