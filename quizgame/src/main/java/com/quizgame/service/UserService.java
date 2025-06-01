package com.quizgame.service;

import com.quizgame.model.User;
import com.quizgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Örnek: Email varsa kayıt olmasın
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Bu email adresi zaten kayıtlı.");
        }

        // Şifre hash'lemek istiyorsan burada yapabilirsin (opsiyonel)
        user.setPasswordHash(user.getPassword()); // Örnek: gerçek hayatta hash fonksiyonu gerekir

        return userRepository.save(user);
    }
}


