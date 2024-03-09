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
public class KvizGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(KvizGameApplication.class, args);
    }
}
