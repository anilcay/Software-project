package com.quizgame.model;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    /* ---------- PRIMARY KEY ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- SORU METNİ ---------- */
    @Column(name = "text", nullable = false, length = 300)
    private String text;

    /* ---------- ŞIKLAR (A-D) ---------- */
    @Column(name = "option_a", nullable = false, length = 150)
    private String optionA;

    @Column(name = "option_b", nullable = false, length = 150)
    private String optionB;

    @Column(name = "option_c", nullable = false, length = 150)
    private String optionC;

    @Column(name = "option_d", nullable = false, length = 150)
    private String optionD;

    /** "A" – "D" harfi (doğru şık) */
    @Column(name = "correct_option", nullable = false, length = 1)
    private String correctOption;

    /** Doğru cevaba verilecek puan (varsayılan = 10) */
    @Column(name = "points", nullable = false)
    private Integer points = 10;

    /* ---------- CONSTRUCTORS ---------- */
    protected Question() { /* JPA için */ }

    public Question(String text,
                    String optionA, String optionB,
                    String optionC, String optionD,
                    String correctOption) {
        this.text          = text;
        this.optionA       = optionA;
        this.optionB       = optionB;
        this.optionC       = optionC;
        this.optionD       = optionD;
        this.correctOption = correctOption.toUpperCase();
    }

    /* ---------- GETTERS / SETTERS ---------- */
    public Long    getId()            { return id; }
    public String  getText()          { return text; }
    public String  getOptionA()       { return optionA; }
    public String  getOptionB()       { return optionB; }
    public String  getOptionC()       { return optionC; }
    public String  getOptionD()       { return optionD; }
    public String  getCorrectOption() { return correctOption; }
    public Integer getPoints()        { return points; }

    public void setText(String text)                      { this.text = text; }
    public void setOptionA(String optionA)                { this.optionA = optionA; }
    public void setOptionB(String optionB)                { this.optionB = optionB; }
    public void setOptionC(String optionC)                { this.optionC = optionC; }
    public void setOptionD(String optionD)                { this.optionD = optionD; }
    public void setCorrectOption(String correctOption)    { this.correctOption = correctOption.toUpperCase(); }
    public void setPoints(Integer points)                 { this.points = points; }
}

