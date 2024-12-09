package com.example.coffeeshop.unit.services;

import com.example.coffeeshop.dto.user.UserDTO;
import com.example.coffeeshop.enums.RoleEnum;
import com.example.coffeeshop.mappers.UserMapper;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import com.example.coffeeshop.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    void shouldFindAndReturnUserByUsername() {
        String username = "john";
        User user = User.builder()
                .id(1L)
                .username(username)
                .email("john@gmail.com")
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User foundUser = userService.loadUserByUsername(username);

        assertThat(foundUser.getUsername()).isEqualTo(username);
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldReturnAllUsers() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john")
                .email("john@gmail.com")
                .role(RoleEnum.USER)
                .build();

        List<User> allUsers = List.of(user);

        int page = 0;
        int pageSize = 10;
        String sortBy = "email";

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));

        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(allUsers));
        when(userMapper.toUserDTO(user)).thenReturn(
                new UserDTO(
                            user.getFirstName() + user.getLastName(),
                            user.getUsername(),
                            user.getEmail(),
                            List.of(user.getRole().toString())
                        )
        );

        Page<UserDTO> userPage = userService.findAll(page, pageSize, sortBy);

        assertThat(userPage.getSize()).isEqualTo(allUsers.size());
        assertThat(userPage.getContent().getFirst().username()).isEqualTo(user.getUsername());

        verify(userRepository).findAll(pageable);
    }

    @Test
    void shouldAddAndReturnNewUser() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john")
                .email("john@gmail.com")
                .role(RoleEnum.USER)
                .build();

        when(userRepository.save(user)).thenReturn(user);

        User newUser = userService.save(user);

        assertThat(newUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(newUser.getUsername()).isEqualTo(user.getUsername());

        verify(userRepository).save(user);
    }
}