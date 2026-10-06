package com.github.marquessouza.suportetecnico.infrastructure.config;

import com.github.marquessouza.suportetecnico.application.usecase.CreateClientUseCase;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateClientUseCase createClientUseCase(ClientRepository clientRepository){
        return new CreateClientUseCase(clientRepository);
    }
}
