package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.user.UserDTO;
import com.example.coffeeshop.models.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(@NotNull User user) {
        return new UserDTO(
                user.getFirstName() + " " + user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority
                ).toList()
        );
    }

}
