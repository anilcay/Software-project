package com.quizgame.dto;

import com.quizgame.validation.PasswordMatches; // Yeni import
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatches // Bu anotasyon sınıf seviyesinde şifre eşleşmesini kontrol edecek
public class UserRegistrationDto {

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır")
    private String username;

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email adresi giriniz")
    private String email;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 8, max = 20, message = "Şifre 8 ile 20 karakter arasında olmalıdır") // Örnek validasyon kuralı
    private String password;

    @NotBlank(message = "Şifre tekrarı boş olamaz")
    private String confirmPassword; // Şifre tekrarı alanı

    // Getter ve Setter metotları
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

    public String getConfirmPassword() { // Yeni getter
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) { // Yeni setter
        this.confirmPassword = confirmPassword;
    }
}