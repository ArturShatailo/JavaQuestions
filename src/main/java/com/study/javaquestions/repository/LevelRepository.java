package com.study.javaquestions.repository;

import com.study.javaquestions.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

    Level findLevelByName(String name);

}
