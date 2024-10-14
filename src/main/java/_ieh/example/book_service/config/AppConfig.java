package _ieh.example.book_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import _ieh.example.book_service.model.User;
import _ieh.example.book_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppConfig {
    @Autowired
    private UserRepository userRepository;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner() {
        log.info("Init data");
        return args -> {
            if (!userRepository.existsByUserName("admin")) {
                User admin = User.builder()
                        .fullName("admin")
                        .userName("admin")
                        .password("admin")
                        .build();
                userRepository.save(admin);
                log.info("Create Admin: username & password: admin, please change password");
            }
        };
    }
}
