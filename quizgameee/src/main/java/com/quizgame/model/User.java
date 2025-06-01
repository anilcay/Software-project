// src/main/java/com/quizgame/model/User.java
package com.quizgame.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column; // @Column için import
import jakarta.persistence.Transient; // @Transient için import
import jakarta.persistence.Table; // @Table için import

@Entity // Bu sınıfın bir veritabanı tablosunu temsil ettiğini belirtir
@Table(name = "users") // Tablo adını 'user' yerine 'users' olarak belirlemek daha geneldir
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Kullanıcı adı boş olamaz ve benzersiz olmalı
    private String username;

    @Column(nullable = false, unique = true) // E-posta boş olamaz ve benzersiz olmalı
    private String email;

    // Bu alan, kullanıcının girdiği ham şifreyi geçici olarak tutar.
    // Veritabanına kaydedilmez.
    @Transient
    private String password;

    // Şifreler hash'lenmiş olarak saklanmalı.
    // BCrypt hash'leri uzun olduğu için 'length' değerini artırdım.
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "email_verified", nullable = false) // Kolon adını belirtmek daha açık olabilir
    private boolean emailVerified;

    // Constructor, getters ve setters
    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password; // Ham şifreyi @Transient alana ata
        // passwordHash burada doğrudan ayarlanmaz, UserService'te hashlenir
    }

    // Getter ve Setter'lar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Ham şifre için getter/setter (UserService'te hashlenmeden önce kullanılır)
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
}