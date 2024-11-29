package com.example.coffeeshop.contract;

import com.example.coffeeshop.dto.user.UserDTO;
import com.example.coffeeshop.enums.RoleEnum;
import com.example.coffeeshop.mappers.UserMapper;
import com.example.coffeeshop.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void shouldReturnUserDTO() {
        User user = User.builder()
                .id(1L)
                .role(RoleEnum.USER)
                .firstName("John")
                .lastName("Doe")
                .username("johny")
                .email("john@doe.com")
                .password("secret-pass")
                .build();

        UserDTO userDTO = userMapper.toUserDTO(user);

        assertThat(userDTO.fullName()).isEqualTo(user.getFirstName() + " " + user.getLastName());
        assertThat(userDTO.username()).isEqualTo(user.getUsername());
        assertThat(userDTO.email()).isEqualTo(user.getEmail());
        assertThat(userDTO.authorities().size()).isEqualTo(user.getAuthorities().stream().toList().size());
        assertThat(userDTO.authorities()).isEqualTo(user.getAuthorities().stream().map(Object::toString).toList());
    }
}