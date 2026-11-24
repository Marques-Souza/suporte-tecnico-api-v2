package com.github.marquessouza.suportetecnico.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectRequestWithoutToken() throws Exception {
        mockMvc.perform(get("/clients"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowAttendantToListClients() throws Exception {
        mockMvc.perform(get("/clients")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ATTENDANT"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldForbidAttendantFromListingUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ATTENDANT"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldAllowAdminToListUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk());
    }
}