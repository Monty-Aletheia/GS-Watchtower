package br.com.fiap.watchtower.integration;

import br.com.fiap.watchtower.controller.DashboardController;
import br.com.fiap.watchtower.controller.RegisterController;
import br.com.fiap.watchtower.dto.UserRequestDTO;
import br.com.fiap.watchtower.integration.config.IntegrationTestConfig;
import br.com.fiap.watchtower.integration.config.TestSecurityConfig;
import br.com.fiap.watchtower.model.User;
import br.com.fiap.watchtower.repository.UserRepository;
import br.com.fiap.watchtower.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {RegisterController.class, DashboardController.class})
@Import({TestSecurityConfig.class, IntegrationTestConfig.class})
@ActiveProfiles("test")
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserRequestDTO(
                "Test User",
                "test@example.com",
                "password123"
        );

        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    void registerUser_Success() throws Exception {

        ResultActions result = mockMvc.perform(post("/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", userDTO.name())
                .param("email", userDTO.email())
                .param("password", userDTO.password()))
                .andDo(print());

        result.andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrl("/login"));
    }

    @Test
    void registerUser_ValidationError() throws Exception {
  
        ResultActions result = mockMvc.perform(post("/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "")
                .param("email", "invalid-email")
                .param("password", "123"))
                .andDo(print());

        result.andExpect(status().isOk())
              .andExpect(model().hasErrors())
              .andExpect(view().name("auth/register"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void accessDashboard_Authenticated() throws Exception {
        when(userRepository.findByEmailIgnoreCase("test@example.com"))
                .thenReturn(Optional.of(user));

        List<Map<String, Object>> pontos = new ArrayList<>();
        Map<String, Object> p1 = Map.of(
            "latitude", -23.5505,
            "longitude", -46.6333,
            "description", "São Paulo",
            "riskLevel", "MEDIUM"
        );
        pontos.add(p1);

        ResultActions result = mockMvc.perform(get("/dashboard")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print());
       
        result.andExpect(status().isOk())
              .andExpect(view().name("dashboard"))
              .andExpect(model().attributeExists("name"))
              .andExpect(model().attributeExists("pontos"));
    }

    @Test
    void accessDashboard_Unauthenticated_RedirectToLogin() throws Exception {
    
        ResultActions result = mockMvc.perform(get("/dashboard"))
                .andDo(print());

        result.andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrl("http://localhost/login"));
    }
} 