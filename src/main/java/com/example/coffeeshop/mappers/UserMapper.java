package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.UserDTO;
import com.example.coffeeshop.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getFirstName() + " " + user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }

}
