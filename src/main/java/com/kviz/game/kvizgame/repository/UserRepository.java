package com.kviz.game.kvizgame.repository;

import com.kviz.game.kvizgame.entity.Question;
import com.kviz.game.kvizgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUuid(String uuid);
}
