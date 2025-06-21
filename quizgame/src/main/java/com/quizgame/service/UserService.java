package com.quizgame.service;

import com.quizgame.dto.LoginRequestDto;
import com.quizgame.dto.UserRegistrationDto;
import com.quizgame.dto.ChangePasswordRequestDto; // Yeni import
import com.quizgame.model.User;
import com.quizgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // Constructor Injection
    public UserService(UserRepository userRepository, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void registerUser(UserRegistrationDto registrationDto) {
        // ... (Mevcut kodunuz aynen kalıyor)
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Bu email adresi zaten kayıtlı.");
        }

        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("Bu kullanıcı adı zaten alınmış.");
        }

        String hashedPassword = bCryptPasswordEncoder.encode(registrationDto.getPassword());

        User user = new User(registrationDto.getUsername(), registrationDto.getEmail(), hashedPassword);
        user.setEmailVerified(false);

        // 6 haneli doğrulama kodu oluştur
        String verificationCode = generateRandomNumericCode(6);
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpiry(LocalDateTime.now().plusMinutes(15));

        userRepository.save(user);

        String emailSubject = "Quiz Game - Email Doğrulama Kodu";
        String emailBody = String.format(
            "Merhaba %s,\n\n" +
            "Quiz Game hesabınızı aktifleştirmek için aşağıdaki 6 haneli doğrulama kodunu kullanın:\n\n" +
            "Doğrulama Kodu: %s\n\n" +
            "Bu kod 15 dakika süreyle geçerlidir.\n" +
            "Eğer bu işlemi siz yapmadıysanız, lütfen bu emaili dikkate almayın.\n\n" +
            "İyi oyunlar,\n" +
            "Quiz Game Ekibi",
            user.getUsername(),
            verificationCode
        );

        emailService.sendVerificationEmail(user.getEmail(), emailSubject, emailBody);
    }

    // authenticateUser metodu (değişiklik yok)
    public void authenticateUser(LoginRequestDto loginRequestDto) {
        // ... (Mevcut kodunuz aynen kalıyor)
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Geçersiz email veya şifre"));

        if (!user.isEmailVerified()) {
            throw new RuntimeException("Hesabınız aktif değil. Lütfen email adresinizi doğrulayın.");
        }

        if (!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Geçersiz email veya şifre");
        }
    }

    // verifyUser metodu (değişiklik yok)
    @Transactional
    public void verifyUser(String email, String code) {
        // ... (Mevcut kodunuz aynen kalıyor)
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı.");
        }

        User user = optionalUser.get();

        if (user.isEmailVerified()) {
            throw new RuntimeException("Hesap zaten doğrulanmış.");
        }

        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(code)) {
            throw new RuntimeException("Geçersiz doğrulama kodu.");
        }

        if (user.getVerificationCodeExpiry() == null || user.getVerificationCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Doğrulama kodu süresi doldu. Lütfen yeni bir doğrulama kodu isteyin veya tekrar kayıt olun.");
        }

        user.setEmailVerified(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiry(null);
        userRepository.save(user);
    }

    // YENİ METOT: Şifre Değiştirme
    @Transactional
    public void changePassword(String email, ChangePasswordRequestDto changePasswordRequestDto) {
        // Kullanıcıyı email ile bul
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        // Mevcut şifreyi doğrula
        if (!bCryptPasswordEncoder.matches(changePasswordRequestDto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Mevcut şifreniz yanlış.");
        }

        // Yeni şifre eski şifreyle aynı olmamalı
        if (bCryptPasswordEncoder.matches(changePasswordRequestDto.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("Yeni şifreniz mevcut şifrenizle aynı olamaz.");
        }

        // Yeni şifreyi hash'le ve kaydet
        String newHashedPassword = bCryptPasswordEncoder.encode(changePasswordRequestDto.getNewPassword());
        user.setPassword(newHashedPassword);
        userRepository.save(user);
    }

    public String loginUser(String email, String password, HttpSession session) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!user.isEmailVerified()) {
            throw new RuntimeException("Lütfen önce email adresinizi doğrulayın");
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Hatalı şifre");
        }

        // Session'a kullanıcı adını ekle
        session.setAttribute("username", user.getUsername());
        
        return "Giriş başarılı";
    }

    private String generateRandomNumericCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}