package com.quizgame.service;

import com.quizgame.model.Question;
import com.quizgame.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getRandomQuestions(int count) {
        List<Question> allQuestions = questionRepository.findAll();
        Collections.shuffle(allQuestions);
        return allQuestions.stream().limit(count).toList();
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public boolean checkAnswer(Long questionId, String userAnswer) {
        return questionRepository.findById(questionId)
                .map(q -> q.getCorrectOption().equalsIgnoreCase(userAnswer))
                .orElse(false);
    }
} 