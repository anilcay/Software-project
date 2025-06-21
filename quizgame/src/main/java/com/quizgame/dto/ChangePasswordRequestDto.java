package com.quizgame.dto;

import com.quizgame.validation.PasswordMatches; // Şifrelerin eşleşmesi için
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatches // Yeni şifre ve tekrarının eşleşmesini kontrol eder
public class ChangePasswordRequestDto {

    @NotBlank(message = "Mevcut şifre boş olamaz")
    private String oldPassword; // Kullanıcının mevcut şifresi

    @NotBlank(message = "Yeni şifre boş olamaz")
    @Size(min = 8, max = 20, message = "Yeni şifre 8 ile 20 karakter arasında olmalıdır") // Yeni şifre için validasyon
    private String newPassword; // Kullanıcının belirlediği yeni şifre

    @NotBlank(message = "Yeni şifre tekrarı boş olamaz")
    private String confirmNewPassword; // Yeni şifrenin tekrarı

    // Getter ve Setter metotları
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    // @PasswordMatches anotasyonunun doğru çalışması için bu metotlar gerekli olabilir.
    // PasswordMatchesValidator sınıfınızın `isValid` metodunu düzenlememiz gerekecek
    // Aşağıda PasswordMatchesValidator güncellemesini bulabilirsiniz.
}
