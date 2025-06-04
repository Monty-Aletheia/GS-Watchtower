package br.com.fiap.watchtower.service;

import br.com.fiap.watchtower.dto.UserRequestDTO;
import br.com.fiap.watchtower.model.User;
import br.com.fiap.watchtower.repository.UserRepository;
import br.com.fiap.watchtower.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

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
    void createUser_Success() {
        // Arrange
        when(mapper.toEntity(userDTO)).thenReturn(user);
        when(encoder.encode(any())).thenReturn("encoded_password");
        when(repository.save(any(User.class))).thenReturn(user);

        userService.create(userDTO);

        verify(mapper).toEntity(userDTO);
        verify(encoder).encode("password123");
        verify(repository).save(user);
    }

    @Test
    void loadUserByUsername_Success() {
        when(repository.findByEmailIgnoreCase("test@example.com"))
            .thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        when(repository.findByEmailIgnoreCase("nonexistent@example.com"))
            .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> 
            userService.loadUserByUsername("nonexistent@example.com")
        );
    }
} 