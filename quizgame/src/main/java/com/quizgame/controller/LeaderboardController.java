package com.quizgame.controller;

import com.quizgame.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Basit lider tablosu: en yüksek puana göre ilk 10 kullanıcı.
 * Endpoint:  GET /api/leaderboard
 */
@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final UserRepository userRepo;

    public LeaderboardController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /** İlk 10 kaydı (yüksekten düşüğe) JSON olarak döndürür. */
    @GetMapping
    public List<UserScore> top10() {
        return userRepo.findTop10ByOrderByHighScoreDesc()
                       .stream()
                       .map(u -> new UserScore(u.getUsername(), u.getHighScore()))
                       .toList();
    }

    /* Basit DTO */
    public record UserScore(String username, int score) { }
}

