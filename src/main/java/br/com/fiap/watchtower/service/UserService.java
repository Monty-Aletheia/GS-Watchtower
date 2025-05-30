package br.com.fiap.watchtower.service;

import br.com.fiap.watchtower.dto.UserRequestDTO;
import br.com.fiap.watchtower.dto.UserResponseDTO;
import br.com.fiap.watchtower.repository.UserRepository;
import br.com.fiap.watchtower.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public void create(UserRequestDTO userDTO){
        repository.save(mapper.toEntity(userDTO));
    }

    public UserResponseDTO getByEmail(String email){
        return mapper.toDTO(repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("ooooh")));
    }
}
