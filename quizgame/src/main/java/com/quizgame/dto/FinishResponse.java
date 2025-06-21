package com.quizgame.dto;

public record FinishResponse(
    int currentScore,    // bu turdaki puan
    int newHighScore     // veritabanına kaydedilen / güncellenen en yüksek puan
) { }
