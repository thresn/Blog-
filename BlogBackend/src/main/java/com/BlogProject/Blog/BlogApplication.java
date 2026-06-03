package com.BlogProject.Blog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.repositories.UserRepository;

@SpringBootApplication(scanBasePackages = "com.BlogProject")
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "tahir@test.com";
            
            // Eğer veri tabanında bu email yoksa kesin oluştur diyoruz
            if (!userRepository.existsByEmail(email)) { 
                User newUser = User.builder()
                    .name("Tahir Esin")
                    .email(email)
                    .password(passwordEncoder.encode("1234"))
                    .build();
                userRepository.save(newUser);
                System.out.println(">>> KONSOL KONTROL: Test kullanıcısı başarıyla DB'ye yazıldı! <<<");
            } else {
                System.out.println(">>> KONSOL KONTROL: Kullanıcı zaten DB'de mevcut. <<<");
            }
        };
    }

}
