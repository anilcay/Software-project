// src/main/java/com/quizgame/repository/UserRepository.java
package com.quizgame.repository;

import com.quizgame.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA, temel CRUD operasyonlarını otomatik sağlar (kaydetme, bulma, silme)
    User findByEmail(String email); // E-posta ile kullanıcı bulmak için özel metod
    boolean existsByEmail(String email); // E-posta'nın var olup olmadığını kontrol etmek için
}
