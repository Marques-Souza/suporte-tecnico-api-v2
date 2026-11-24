package com.github.marquessouza.suportetecnico.infrastructure.config;


import com.github.marquessouza.suportetecnico.application.dto.CreateUserRequest;
import com.github.marquessouza.suportetecnico.application.usecase.CreateUserUseCase;
import com.github.marquessouza.suportetecnico.domain.model.Role;
import com.github.marquessouza.suportetecnico.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AdminInitializerConfig {

    private static final Logger log = LoggerFactory.getLogger(AdminInitializerConfig.class);

    @Bean
    public CommandLineRunner createFirstAdmin(
            UserRepository userRepository,
            CreateUserUseCase createUserUseCase,
            @Value("${app.initialization.admin.email}") String email,
            @Value("${app.initialization.admin.password}") String password){

        return args -> {
            if (email.isBlank() || password.isBlank()){
                log.info("ADMIN_EMAIL/ADMIN_PASSWORD not set: skipping first admin creation");
                return;
            }
            if (userRepository.findByEmail(email).isPresent()){
                return;
            }
            createUserUseCase.execute( new CreateUserRequest("Administrator", email, password, Role.ADMIN));
            log.info("First admin created");
        };
    }
}
