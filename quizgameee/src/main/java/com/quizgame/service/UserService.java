package com.quizgame.service;

import com.quizgame.model.User;
import com.quizgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Email kontrolü
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Bu email adresi zaten kayıtlı.");
        }

        // Kullanıcı adı kontrolü
        // user.getName() yerine user.getUsername() kullanıldı.
        // UserRepository'de existsByName() yerine existsByUsername() kullanılacak.
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Bu kullanıcı adı zaten alınmış.");
        }

        // Şifreyi hash'le ve veritabanına öyle kaydet
        // ÖNEMLİ: Güçlü şifreleme için BCryptPasswordEncoder kullanmanız şiddetle tavsiye edilir.
        // Aşağıdaki hashPassword metodu sadece basit bir SHA-256 örneğidir.
        String hashedPassword = hashPassword(user.getPassword());
        user.setPasswordHash(hashedPassword);

        // Yeni kayıt olan kullanıcının email doğrulanma durumunu varsayılan olarak false yapalım
        user.setEmailVerified(false);

        return userRepository.save(user);
    }

    // Şifreyi SHA-256 ile hash'leyen yardımcı metod
    // Not: Üretim ortamlarında BCryptPasswordEncoder gibi daha güçlü algoritmalar kullanılmalıdır.
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // NoSuchAlgorithmException'ı RuntimeException olarak sarmalamak yerine
            // daha spesifik bir istisna fırlatabilir veya loglayabilirsiniz.
            throw new RuntimeException("Şifre hashlenirken hata oluştu", e);
        }
    }
    
    public boolean loginUser(String email, String plainPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false; // Kullanıcı bulunamadı
        }

        // Giriş şifresini hash'le ve veritabanındaki hash ile karşılaştır
        String hashedInput = hashPassword(plainPassword);
        return hashedInput.equals(user.getPasswordHash());
    }

    // İsteğe bağlı: Kullanıcıyı email ile bulma metodu
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
