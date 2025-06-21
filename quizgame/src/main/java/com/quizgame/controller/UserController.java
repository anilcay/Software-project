package com.quizgame.controller;

import com.quizgame.dto.LoginRequestDto;
import com.quizgame.dto.UserRegistrationDto;
import com.quizgame.dto.ChangePasswordRequestDto;
import com.quizgame.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ---------- KAYIT ---------- */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDto dto,
                                               BindingResult br) {
        if (br.hasErrors()) {
            String msg = br.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(msg);
        }

        userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("Kayıt başarılı! Lütfen e-postanızı doğrulayın.");
    }

    /* ---------- (Opsiyonel) API tabanlı login ---------- */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDto dto,
                                            HttpSession session) {
        try {
            String result = userService.loginUser(dto.getEmail(), dto.getPassword(), session);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /* ---------- E-posta doğrulama ---------- */
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email,
                                              @RequestParam String code) {
        try {
            userService.verifyUser(email, code);
            return ResponseEntity.ok("Hesabınız başarıyla doğrulandı!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* ---------- Şifre değiştirme ---------- */
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String email,
                                                 @Valid @RequestBody ChangePasswordRequestDto dto) {
        try {
            userService.changePassword(email, dto);
            return ResponseEntity.ok("Şifreniz başarıyla değiştirildi.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* ---------- Çıkış ---------- */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Çıkış başarılı");
    }

    // *** checkAuth metodu kaldırıldı: AuthCheckController tek yetkili ***
}
