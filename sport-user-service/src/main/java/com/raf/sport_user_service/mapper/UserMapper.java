package com.raf.sport_user_service.mapper;

import com.raf.sport_user_service.domain.User;
import com.raf.sport_user_service.dto.UserCreateDto;
import com.raf.sport_user_service.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    // Mapiranje ENTITET -> DTO (Za prikaz korisnika)
    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());

        // Izvlačim naziv prve uloge (ako postoji)
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            userDto.setRoleName(user.getRoles().get(0).getName());
        }

        return userDto;
    }

    // Mapiranje DTO -> ENTITET (Za registraciju)
    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();

        // Mapiram osnovne podatke
        user.setUsername(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());
        user.setZvanje(userCreateDto.getRoleName());


        return user;
    }


}
