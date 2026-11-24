package com.github.marquessouza.suportetecnico.infrastructure.security;

import com.github.marquessouza.suportetecnico.application.dto.CreateUserRequest;
import com.github.marquessouza.suportetecnico.application.usecase.CreateUserUseCase;
import com.github.marquessouza.suportetecnico.domain.model.Role;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginFlowTest {

    private static final String PASSWORD = "senha-de-teste-123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    private String createUser(Role role) {
        String email = "user-" + UUID.randomUUID() + "@teste.com";
        createUserUseCase.execute(new CreateUserRequest("Usuario Teste", email, PASSWORD, role));
        return email;
    }

    private String loginBody(String email, String password) {
        return """
                {"email":"%s","password":"%s"}
                """.formatted(email, password);
    }

    private String loginAndGetToken(String email) throws Exception {
        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody(email, PASSWORD)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return JsonPath.read(response, "$.token");
    }

    @Test
    void shouldLoginAndAccessProtectedRoute() throws Exception {
        String token = loginAndGetToken(createUser(Role.ATTENDANT));

        mockMvc.perform(get("/clients").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void shouldForbidAttendantTokenOnUsersRoute() throws Exception {
        String token = loginAndGetToken(createUser(Role.ATTENDANT));

        mockMvc.perform(get("/users").header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldAllowAdminTokenOnUsersRoute() throws Exception {
        String token = loginAndGetToken(createUser(Role.ADMIN));

        mockMvc.perform(get("/users").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectWrongPassword() throws Exception {
        String email = createUser(Role.ATTENDANT);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody(email, "senha-errada-999")))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Invalid email or password"));
    }

    @Test
    void shouldRejectUnknownEmailWithSameMessage() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody("naoexiste@teste.com", PASSWORD)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Invalid email or password"));
    }

    @Test
    void shouldRejectTamperedToken() throws Exception {
        String token = loginAndGetToken(createUser(Role.ADMIN));
        String[] parts = token.split("\\.");
        char first = parts[2].charAt(0);
        char other = first == 'A' ? 'B' : 'A';
        String tampered = parts[0] + "." + parts[1] + "." + other + parts[2].substring(1);

        mockMvc.perform(get("/clients").header("Authorization", "Bearer " + tampered))
                .andExpect(status().isUnauthorized());
    }
}