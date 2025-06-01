// src/main/java/com/quizgame/repository/UserRepository.java
package com.quizgame.repository;

import com.quizgame.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // @Repository anotasyonu için ekledim

@Repository // Genellikle repository arayüzlerine bu anotasyonu eklemek iyi bir pratik
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);         // E-posta ile kullanıcı bulmak için
    boolean existsByEmail(String email);    // E-posta var mı?
    
    // Hatanın kaynağı burasıydı. 'existsByName' yerine 'existsByUsername' olmalı.
    // Çünkü User modelinizde 'username' adında bir alan var, 'name' değil.
    boolean existsByUsername(String username); // Kullanıcı adı var mı? <-- DÜZELTİLDİ
}