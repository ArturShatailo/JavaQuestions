package com.study.javaquestions.service.topic;

import com.study.javaquestions.domain.Topic;
import com.study.javaquestions.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TopicServiceBean {

    private final TopicRepository topicRepository;

    public Topic create(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic getByName(String name) {
        return topicRepository.findTopicByName(name);
    }
}
