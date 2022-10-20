package com.study.javaquestions.repository;

import com.study.javaquestions.domain.Level;
import com.study.javaquestions.domain.Question;
import com.study.javaquestions.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Transactional
    List<Question> findQuestionsByLevelAndTopic(Level level, Topic topic);

}
