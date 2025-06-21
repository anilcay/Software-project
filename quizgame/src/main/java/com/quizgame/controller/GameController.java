package com.quizgame.controller;

import com.quizgame.service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    public GameController(GameService gs) { this.gameService = gs; }

    @GetMapping("/start")
    public GameService.QuestionDto start(HttpSession session) {
        return gameService.startGame(session);
    }

    @PostMapping("/answer")
    public ResponseEntity<GameService.AnswerDto> answer(
            HttpSession session,
            Principal principal,
            @RequestBody AnswerReq req) {

        String email = (principal != null) ? principal.getName() : null;
        GameService.AnswerDto dto =
            gameService.answer(session,
                               req.questionId(),
                               req.option(),
                               email);
        return ResponseEntity.ok(dto);
    }

    public record AnswerReq(Long questionId, String option) { }
}
