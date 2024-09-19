package crio.bookrentalsystem.rentread.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import crio.bookrentalsystem.rentread.model.User;
import crio.bookrentalsystem.rentread.repository.IUserRepository;

@Configuration
public class DataInitializer {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Check if the admin user already exists
            if (userRepository.findById("admin@admin.com").isEmpty()) {
                // Create and save the admin user
                User admin = new User();
                admin.setUsername("admin@admin.com");
                admin.setFirstname("Tester");
                admin.setLastname("Developer");
                admin.setPassword(passwordEncoder.encode("adminpassword")); // BCrypt-encoded password
                admin.setRole("ADMIN");

                userRepository.save(admin);
                System.out.println("Admin user registered.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}

