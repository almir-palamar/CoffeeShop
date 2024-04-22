//package com.example.coffeeshop.config;
//
//import com.example.coffeeshop.models.User;
//import com.example.coffeeshop.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//@Configuration
//public class JwtAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public JwtAuthenticationProvider(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        User user = userRepository.findByUsername((String) authentication.getPrincipal());
//
//        System.out.println(user);
//
//        if (user == null) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//
//        if (user.getPassword().equals(authentication.getCredentials().toString())) {
//            throw new BadCredentialsException("Invalid password");
//        }
//
//        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//        return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
