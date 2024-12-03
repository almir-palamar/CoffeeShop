package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.user.UserDTO;
import com.example.coffeeshop.mappers.UserMapper;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).get();
    }

    public Page<UserDTO> findAll(Integer page, Integer pageSize, String sortBy) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, pageSize, Sort.by(sortBy)));
        return userPage.map(userMapper::toUserDTO);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

}
