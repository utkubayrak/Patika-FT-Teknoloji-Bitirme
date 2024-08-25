package com.utkubayrak.jetbususerservice.config;

import com.utkubayrak.jetbususerservice.security.jwt.JwtTokenValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration // Bu sınıfın bir yapılandırma sınıfı olduğunu belirtir.
@EnableWebSecurity // Spring Security'nin etkinleştirildiğini belirtir.
public class AppConfig {
    @Bean
        //Spring tarafından yönetilen bir bileşenin tanımlandığını belirtir. SecurityFilterChain’i bir @Bean olarak tanımlayarak Spring Security’nin bu yapıyı kullanmasını sağlarız.
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //güvenlik filtrelerinin bir zincirini temsil eder. Bu filtreler, gelen HTTP isteklerini işler ve yetkilendirme, kimlik doğrulama, oturum yönetimi gibi güvenlik işlemlerini gerçekleştirir.
        http.sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //oturum yönetimini yapılandırır.oturumların durumsuz (stateless) olacağını belirtir. Yani sunucu tarafında oturum bilgisi saklanmaz.
                .authorizeHttpRequests(Authorize -> Authorize
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").authenticated() //yolu, kimlik doğrulanmış kullanıcılara izin verir.
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .anyRequest().permitAll() //Diğer tüm istekler herkese açıktır.
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class) //Bu bölüm, özel bir filtre olan JwtTokenValidator’ü BasicAuthenticationFilter’den önce ekler. Bu filtre, gelen JWT (JSON Web Token) token’larını doğrular.
                .csrf(csrf -> csrf.disable()) //CSRF (Cross-Site Request Forgery) korumasını devre dışı bırakır.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        // CORS (Cross-Origin Resource Sharing) yapılandırmasını sağlayan bir method.
        return new CorsConfigurationSource() {
            // CORS (Cross-Origin Resource Sharing) yapılandırması sağlayan anonim iç sınıf.
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                // CORS (Cross-Origin Resource Sharing) yapılandırmasını almak için bir method.
                CorsConfiguration cfg = new CorsConfiguration();
                // CORS (Cross-Origin Resource Sharing) yapılandırma nesnesi oluşturur.
                cfg.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000" // İzin verilen kaynakları belirler.
                ));
                cfg.setAllowedMethods(Collections.singletonList("*"));// İzin verilen HTTP yöntemlerini belirler.
                cfg.setAllowCredentials(true); // Kimlik bilgilerinin paylaşılmasına izin verip verilmeyeceğini belirler.
                cfg.setAllowedHeaders(Collections.singletonList("*")); // İzin verilen başlıkları belirler.
                cfg.setExposedHeaders(Arrays.asList("Authorization"));  // Dışa açık başlıkları belirler.
                cfg.setMaxAge(3600L); // Önbellekleme süresini belirler.
                return cfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // Parolaları şifrelemek için bir method.
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder sınıfını döndürür.
    }
}
