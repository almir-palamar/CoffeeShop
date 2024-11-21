package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.user.UserDTO;
import com.example.coffeeshop.mappers.UserMapper;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).get();
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper)
                .collect(Collectors.toList());
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

}
