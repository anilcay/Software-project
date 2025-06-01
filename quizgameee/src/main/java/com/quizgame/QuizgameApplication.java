package com.quizgame;

import com.quizgame.model.User;
import com.quizgame.service.EmailService;
import com.quizgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class QuizgameApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(QuizgameApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Yarışmaya hoş geldiniz!");
        System.out.println("1 - Giriş Yap\n2 - Kayıt Ol");
        int secim = scanner.nextInt();
        scanner.nextLine(); // dummy

        if (secim == 2) {
            System.out.print("Adınız: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim().toLowerCase();
            System.out.print("Şifre: ");
            String password = scanner.nextLine();

            // 1. Kod üret
            String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            // 2. Mail gönder
            try {
                emailService.sendVerificationCode(email, code);
                System.out.println("E-posta adresinize bir doğrulama kodu gönderildi.");
            } catch (Exception e) {
                System.out.println("Kod gönderilemedi: " + e.getMessage());
                return;
            }

            // 3. Kod kontrol
            System.out.print("Kodunuzu giriniz: ");
            String girilenKod = scanner.nextLine();

            if (girilenKod.equals(code)) {
                try {
                    User user = new User(name, email, password);
                    userService.registerUser(user);
                    System.out.println("Kayıt başarılı!");
                } catch (RuntimeException e) {
                    System.out.println("Kayıt başarısız: " + e.getMessage());
                }
            } else {
                System.out.println("Kod yanlış! Kayıt iptal edildi.");
            }
        }
    }
}
