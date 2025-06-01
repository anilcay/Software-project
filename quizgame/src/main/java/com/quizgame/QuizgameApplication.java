package com.quizgame;

import com.quizgame.model.User;
import com.quizgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizgameApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(QuizgameApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Yeni yarışmacı ekle
        if (!userRepository.existsByEmail("ali@example.com")) {
            User user = new User("Ali", "ali@example.com", "123456");
            user.setPasswordHash("123456"); // Gerçekte hashlenmeli
            user.setEmailVerified(true);
            userRepository.save(user);
            System.out.println("Ali eklendi.");
        }
    }
}
