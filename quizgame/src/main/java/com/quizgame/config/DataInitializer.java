package com.quizgame.config;

import com.quizgame.model.Question;
import com.quizgame.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initQuestions(QuestionRepository repo) {
        return args -> {
            if (repo.count() > 0) return;   // tablo zaten dolu

            repo.save(new Question(
                "Türkiye'nin başkenti neresidir?",
                "İzmir", "İstanbul", "Ankara", "Bursa", "C"));

            repo.save(new Question(
                "HTML açılımı nedir?",
                "Hyperlinks and Text Markup Language",
                "Hyper Text Markup Language",
                "High-Level Tool Markup Language",
                "Home Tool Markup Language",
                "B"));

            repo.save(new Question(
                "1 gigabayt kaç megabayttır?",
                "512", "1000", "1024", "2048", "C"));

            repo.save(new Question(
                "Python dosyalarının uzantısı hangisidir?",
                ".java", ".py", ".js", ".c", "B"));

            repo.save(new Question(
                "Cumhuriyet kaç yılında ilan edilmiştir?",
                "1919", "1920", "1923", "1938", "C"));

            repo.save(new Question(
                "Dünyanın en büyük okyanusu hangisidir?",
                "Atlas Okyanusu", "Hint Okyanusu",
                "Büyük Okyanus (Pasifik)", "Arktik Okyanusu", "C"));

            repo.save(new Question(
                "SQL’de tüm kayıtları seçmek için hangi anahtar kelime kullanılır?",
                "JOIN", "INSERT", "SELECT", "UPDATE", "C"));

            repo.save(new Question(
                "Güneş sisteminde en küçük gezegen hangisidir?",
                "Mars", "Venüs", "Merkür", "Plüton", "C"));

            repo.save(new Question(
                "Türkiye’deki en yüksek dağ hangisidir?",
                "Uludağ", "Ağrı Dağı", "Kaçkar Dağı", "Erciyes Dağı", "B"));

            repo.save(new Question(
                "React kütüphanesinin ana geliştiricisi kimdir?",
                "Google", "Facebook (Meta)", "Microsoft", "Amazon", "B"));
        };
    }
}
