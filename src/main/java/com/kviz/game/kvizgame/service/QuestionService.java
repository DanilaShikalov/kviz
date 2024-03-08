package com.kviz.game.kvizgame.service;

import com.kviz.game.kvizgame.entity.Question;
import com.kviz.game.kvizgame.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question getNextQuestion(int state) {
        List<Question> questions = questionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (state >= questions.size()) {
            return null;
        }
        return questions.get(state);
    }
}
