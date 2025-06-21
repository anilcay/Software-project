package com.quizgame.repository;

import com.quizgame.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /* --------- Temel sorgular --------- */
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    /* --------- Lider Tablosu --------- */
    /** En yüksek skora göre ilk 10 kullanıcıyı getirir. */
    List<User> findTop10ByOrderByHighScoreDesc();
}
