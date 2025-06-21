package com.quizgame.controller;

import com.quizgame.model.Question;
import com.quizgame.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    private QuestionService questionService;

    // Kullanıcıya 10 rastgele soru gönder
    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return questionService.getRandomQuestions(10);
    }

    // Cevap kontrolü ve puanlama
    @PostMapping("/answer")
    public Map<String, Object> checkAnswer(@RequestBody Map<String, String> payload) {
        Long questionId = Long.parseLong(payload.get("questionId"));
        String userAnswer = payload.get("answer");
        boolean isCorrect = questionService.checkAnswer(questionId, userAnswer);
        int score = isCorrect ? 10 : 0;
        Map<String, Object> response = new HashMap<>();
        response.put("correct", isCorrect);
        response.put("score", score);
        return response;
    }
} 