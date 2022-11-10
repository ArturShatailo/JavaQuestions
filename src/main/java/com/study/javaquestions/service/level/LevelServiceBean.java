package com.study.javaquestions.service.level;

import com.study.javaquestions.domain.Level;
import com.study.javaquestions.repository.LevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class LevelServiceBean {

    private final LevelRepository levelRepository;

    public Level create(Level level) {
        return levelRepository.save(level);
    }

    public List<Level> getAll() {
        return levelRepository.findAll();
    }

    public Level getByName(String name) {
        return levelRepository.findLevelByName(name);
    }

}
