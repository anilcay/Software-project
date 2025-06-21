package com.quizgame.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.quizgame.model.Question;
import com.quizgame.repository.QuestionRepository;
import com.quizgame.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {

    /* ---------- Session anahtarları ---------- */
    private static final String Q_LIST  = "qList";
    private static final String INDEX   = "qIdx";
    private static final String SCORE   = "score";
    private static final String SENT_AT = "sentAt";

    private final QuestionRepository qRepo;
    private final UserRepository     uRepo;

    public GameService(QuestionRepository qRepo, UserRepository uRepo) {
        this.qRepo = qRepo;
        this.uRepo = uRepo;
    }

    /* ---------- DTO’lar ---------- */
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public record QuestionDto(
            Long id,
            String text,
            @JsonProperty("optionA") String optionA,
            @JsonProperty("optionB") String optionB,
            @JsonProperty("optionC") String optionC,
            @JsonProperty("optionD") String optionD) { }

    public record AnswerDto(int score, QuestionDto nextQuestion) { }

    /* ---------- Oyun başlat ---------- */
    public QuestionDto startGame(HttpSession session) {
        // 1) Tüm soruları çek (data.sql’de tanımlı 100 soru)
        List<Question> all = qRepo.findAll();

        // 2) Karıştır
        Collections.shuffle(all);

        // 3) İlk 10 tanesini al
        List<Question> quiz = all.stream()
                                 .limit(10)
                                 .toList();

        // 4) Oturuma yerleştir
        session.setAttribute(Q_LIST, quiz);
        session.setAttribute(INDEX,  0);
        session.setAttribute(SCORE,  0);

        // 5) İlk soruyu döndür
        return toDto(quiz.get(0), session);
    }

    /**
     * Cevabı değerlendirir, skoru günceller, sıradaki soruyu döndürür.
     * Oyun bittiğinde highScore güncellemesi yapar.
     */
    public AnswerDto answer(HttpSession session,
                            Long qId,
                            String opt,
                            String email) {

        @SuppressWarnings("unchecked")
        List<Question> quiz = (List<Question>) session.getAttribute(Q_LIST);
        Integer idx   = (Integer) session.getAttribute(INDEX);
        Integer score = (Integer) session.getAttribute(SCORE);
        Instant sent  = (Instant) session.getAttribute(SENT_AT);

        if (quiz == null) throw new RuntimeException("Aktif oyun yok");

        // 10 sn süre kontrolü
        boolean inTime = sent != null && Instant.now().minusSeconds(10).isBefore(sent);

        Question current = quiz.get(idx);
        if (inTime &&
            current.getId().equals(qId) &&
            current.getCorrectOption().equalsIgnoreCase(opt)) {
            score += current.getPoints();
        }

        // sıradaki soruya geç
        idx++;
        session.setAttribute(SCORE, score);
        session.setAttribute(INDEX, idx);

        QuestionDto next = (idx < quiz.size())
                         ? toDto(quiz.get(idx), session)
                         : null;

        // Oyun bitti ise highScore güncelle
        if (next == null && email != null) {
            final int finalScore = score;
            uRepo.findByEmail(email).ifPresent(u -> {
                if (finalScore > u.getHighScore()) {
                    u.setHighScore(finalScore);
                    uRepo.save(u);
                }
            });
        }

        return new AnswerDto(score, next);
    }

    /* ---------- Sayaç başlangıcı + dönüşüm ---------- */
    private QuestionDto toDto(Question q, HttpSession s) {
        s.setAttribute(SENT_AT, Instant.now());
        return new QuestionDto(
            q.getId(),
            q.getText(),
            q.getOptionA(),
            q.getOptionB(),
            q.getOptionC(),
            q.getOptionD()
        );
    }
}
