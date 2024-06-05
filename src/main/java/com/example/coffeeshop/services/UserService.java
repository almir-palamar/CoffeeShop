package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.UserDTO;
import com.example.coffeeshop.mappers.UserMapper;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Authentication me() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username).get();
            }
        };
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
