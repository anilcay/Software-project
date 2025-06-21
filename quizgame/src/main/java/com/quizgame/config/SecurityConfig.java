package com.quizgame.config;

import com.quizgame.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /* ---------- BCrypt ---------- */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* ---------- AuthProvider ---------- */
    @Bean
    public DaoAuthenticationProvider authProvider(CustomUserDetailsService uds,
                                                  BCryptPasswordEncoder enc) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(enc);
        return p;
    }

    /* ---------- FilterChain ---------- */
    @Bean @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider provider) throws Exception {

        http.authenticationProvider(provider);

        http
          .cors(c -> c.configurationSource(cors()))
          .csrf(AbstractHttpConfigurer::disable)

          /* 1️⃣ Spring’in “request cache” mekanizmasını kapatıyoruz
             (aksi hâlde 4xx durumlarında HTML’e 302 yönlendirme yapıyor) */
          .requestCache(cache -> cache.disable())

          .authorizeHttpRequests(auth -> auth
              /* --- Herkese açık --- */
              .requestMatchers("/api/auth/register").permitAll()
              .requestMatchers("/api/auth/login").permitAll()
              .requestMatchers("/api/auth/verify").permitAll()
              .requestMatchers("/api/auth/check").permitAll()

              .requestMatchers("/", "/index.html",
                               "/css/**", "/js/**", "/images/**").permitAll()

              /* Oyun dosyaları */
              .requestMatchers("/game.html").permitAll()

              /* Lider tablosu */
              .requestMatchers("/api/leaderboard", "/leaderboard.html").permitAll()

              /* Oyun API'si → giriş gerekli */
              .requestMatchers("/api/game/**").authenticated()

              /* Diğer her şey */
              .anyRequest().authenticated()
          )

          .formLogin(f -> f
              .loginPage("/index.html")
              .loginProcessingUrl("/login")
              .usernameParameter("email")
              .passwordParameter("password")
              .defaultSuccessUrl("/game.html", true)

              /* 2️⃣ Hata mesajını ayırt edilebilir hâle getiriyoruz */
              .failureHandler((req, res, ex) -> {
                  String msg = ex.getMessage().toLowerCase();
                  if (msg.contains("disabled")) {
                      res.sendError(403, "Email doğrulanmamış");
                  } else {
                      res.sendError(401, "Parola hatalı");
                  }
              })

              .permitAll()
          )

          .logout(l -> l
              .logoutUrl("/logout")
              .logoutSuccessUrl("/index.html")
          );

        return http.build();
    }

    /* ---------- Basit CORS ---------- */
    @Bean
    public CorsConfigurationSource cors() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        c.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()));
        c.setAllowedHeaders(Arrays.asList("*"));
        c.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", c);
        return src;
    }
}


