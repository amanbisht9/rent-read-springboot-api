package crio.bookrentalsystem.rentread.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF for testing purposes
            .authorizeHttpRequests()
                .requestMatchers("/user/register","/user/login").permitAll()  // Allow unauthenticated access to /user/register
                .anyRequest().authenticated()  // Require authentication for all other endpoints
            .and()
            .httpBasic();  // Use basic authentication or form login as needed

        return http.build();
    }

}
