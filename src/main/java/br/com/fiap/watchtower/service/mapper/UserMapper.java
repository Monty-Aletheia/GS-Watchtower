package br.com.fiap.watchtower.service.mapper;

import br.com.fiap.watchtower.dto.UserRequestDTO;
import br.com.fiap.watchtower.dto.UserResponseDTO;
import br.com.fiap.watchtower.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toDTO(User user){
        return new UserResponseDTO(
                user.getName(),
                user.getEmail()
        );
    }

    public User toEntity(UserRequestDTO dto){
        return new User(
            dto.name(),
            dto.email(),
            dto.password()
        );
    }

}
