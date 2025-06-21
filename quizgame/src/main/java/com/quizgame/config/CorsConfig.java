package com.quizgame.config; // BURASI ÇOK ÖNEMLİ: Class'ın hangi pakete ait olduğunu belirtir

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // API endpoint'leriniz için
                .allowedOrigins("http://localhost:3000", "http://127.0.0.1:5500") // Frontend uygulamanızın adresi veya *
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İzin verilen HTTP metotları
                .allowedHeaders("*") // İzin verilen HTTP başlıkları
                .allowCredentials(true); // Çerezler veya kimlik doğrulama başlıkları gibi kimlik bilgilerine izin verir
    }
}