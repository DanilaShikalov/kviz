package com.kviz.game.kvizgame.repository;

import com.kviz.game.kvizgame.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findQuestionByQuestion(String question);
}
