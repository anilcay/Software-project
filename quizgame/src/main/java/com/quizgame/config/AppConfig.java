package com.quizgame.config; // Burası doğru olmalı

import org.springframework.context.annotation.Configuration;
// Eğer AppConfig içinde başka Bean'ler veya @Autowired ile enjekte edilecek başka sınıflar
// için import'lar varsa onlar kalabilir.
// Fakat BCryptPasswordEncoder ile ilgili import'a gerek kalmaz.

@Configuration // Spring'e bu sınıfın bir yapılandırma sınıfı olduğunu söyler
public class AppConfig {

    // Buradaki @Bean anotasyonu ve bCryptPasswordEncoder() metodu silinmiştir.
    // Artık BCryptPasswordEncoder bean'i sadece SecurityConfig sınıfında tanımlı olacaktır.

    // Eğer AppConfig içinde başka Bean tanımlamalarınız (örneğin mail sender gibi)
    // veya başka konfigürasyonlarınız varsa, onlar burada kalabilir.
    /*
    @Bean
    public SomeOtherBean someOtherBeanMethod() {
        return new SomeOtherBean();
    }
    */
}