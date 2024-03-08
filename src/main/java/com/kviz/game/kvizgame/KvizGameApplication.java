package com.kviz.game.kvizgame;

import com.kviz.game.kvizgame.entity.Question;
import com.kviz.game.kvizgame.entity.User;
import com.kviz.game.kvizgame.repository.QuestionRepository;
import com.kviz.game.kvizgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KvizGameApplication implements CommandLineRunner {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(KvizGameApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        questionRepository.save(Question.builder()
                .id(null)
                .question("Что?1")
                .answerA("a")
                .answerB("b")
                .answerC("c")
                .answerD("d")
                .currentAnswer("a")
                .price(1)
                .prize("приз1")
                .build());
        questionRepository.save(Question.builder()
                .id(null)
                .question("Что?2")
                .answerA("a")
                .answerB("b")
                .answerC("c")
                .answerD("d")
                .currentAnswer("a")
                .price(1)
                .prize("приз2")
                .build());
        questionRepository.save(Question.builder()
                .id(null)
                .question("Что?3")
                .answerA("a")
                .answerB("b")
                .answerC("c")
                .answerD("d")
                .currentAnswer("a")
                .price(1)
                .prize("приз3")
                .build());
        questionRepository.save(Question.builder()
                .id(null)
                .question("Что?4")
                .answerA("a")
                .answerB("b")
                .answerC("c")
                .answerD("d")
                .currentAnswer("a")
                .price(1)
                .prize("приз4")
                .build());
    }
}
