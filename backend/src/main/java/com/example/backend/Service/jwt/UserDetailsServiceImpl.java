package com.example.backend.Service.jwt;

import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> firstByEmail = userRepository.findFirstByEmail(email);

        if (firstByEmail.isEmpty()) {
            throw new UsernameNotFoundException("Username nor found", null);
        }

        return new org.springframework.security.core.userdetails.User(firstByEmail.get().getEmail(), firstByEmail.get().getPassword(), new ArrayList<>());
    }
}
