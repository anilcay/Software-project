package com.quizgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // Constructor Injection tercih edin, @Autowired field injection yerine.
    // Ancak mevcut yapınızda @Autowired kullandığınız için şimdilik öyle bırakıyorum.
    // İdealde:
    // private final JavaMailSender mailSender;
    // public EmailService(JavaMailSender mailSender) { this.mailSender = mailSender; }
    @Autowired
    private JavaMailSender mailSender;

    // Metot adı sendVerificationEmail olarak değiştirildi ve 3 parametre alacak şekilde güncellendi.
    public void sendVerificationEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("millionare.noreply@gmail.com"); // Buradaki e-posta adresinin, application.properties'deki ile eşleştiğinden emin olun.
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body); // Gönderilecek metin (body), doğrulama linkini içerecek

        try {
            mailSender.send(message);
        } catch (Exception e) {
            // E-posta gönderme hatalarını loglamak ve duruma göre fırlatmak daha iyidir.
            // Kullanıcıya doğrudan teknik hata mesajı göstermek yerine daha genel bir mesaj verilebilir.
            System.err.println("E-posta gönderilemedi: " + e.getMessage());
            // throw new RuntimeException("Doğrulama e-postası gönderilirken bir hata oluştu.");
            // Hata fırlatma tercihine göre yorum satırı yapabilir veya aktif edebilirsiniz.
            // Bu, kaydın başarısız olmasına neden olur, bu nedenle dikkatli olun.
            // Şimdilik sadece logluyorum, kaydın devam etmesini sağlayacak şekilde.
        }
    }

    // Eski sendVerificationCode metodu artık kullanılmayacak, isterseniz kaldırabilirsiniz.
    // Ancak uyumluluk için burada bırakılabilir veya tamamen kaldırılabilir.
    // public void sendVerificationCode(String to, String code) { ... }
}