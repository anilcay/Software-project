package com.quizgame.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    /* ---------- PRIMARY KEY ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- PROFİL ALANLARI ---------- */
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır")
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email adresi giriniz")
    private String email;

    @Column(nullable = false)
    private String password;                // BCrypt hash

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    /* ---------- DOĞRULAMA ALANLARI ---------- */
    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_code_expiry")
    private LocalDateTime verificationCodeExpiry;

    /* ---------- LİDER TABLOSU ALANI ---------- */
    @Column(name = "high_score", nullable = false)
    private int highScore = 0;              // ✨ yeni sütun

    /* ---------- CONSTRUCTORS ---------- */
    protected User() { }                    // JPA için

    public User(String username, String email, String password) {
        this.username = username;
        this.email    = email;
        this.password = password;
    }

    /* ---------- GETTER / SETTER ---------- */
    public Long getId() { 
        return id; 
    }

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public boolean isEmailVerified() { 
        return emailVerified; 
    }
    public void setEmailVerified(boolean emailVerified) { 
        this.emailVerified = emailVerified; 
    }

    public String getVerificationCode() { 
        return verificationCode; 
    }
    public void setVerificationCode(String verificationCode) { 
        this.verificationCode = verificationCode; 
    }

    public LocalDateTime getVerificationCodeExpiry() { 
        return verificationCodeExpiry; 
    }
    public void setVerificationCodeExpiry(LocalDateTime verificationCodeExpiry) { 
        this.verificationCodeExpiry = verificationCodeExpiry; 
    }

    public int getHighScore() { 
        return highScore; 
    }
    public void setHighScore(int highScore) { 
        this.highScore = highScore; 
    }

    /* ---------- Spring Security yardımcı metot ---------- */
    /** Hesap etkin mi?  (e-posta doğrulanmışsa true) */
    public boolean isEnabled() { 
        return emailVerified; 
    }
}
