package com.quizgame.controller;

import com.quizgame.model.User;
import com.quizgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        // Burada örnek olarak passwordHash'e direkt password'u koyuyoruz (şimdilik)
        user.setPasswordHash(user.getPassword());
        user.setEmailVerified(false); // Yeni kayıt olduğunda doğrulanmamış olur
        return userRepository.save(user);
    }
}

