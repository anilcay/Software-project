package com.quizgame.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthCheckController {

    /**
     * Oturum hâlâ geçerliyse 200 OK, değilse 401 Unauthorized döner.
     */
    @GetMapping("/check")
    public ResponseEntity<Void> check(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
