package com.example.online_shop.service;

import com.example.online_shop.config.jwt.JwtService;
import com.example.online_shop.dto.AuthenticationRequest;
import com.example.online_shop.dto.AuthenticationResponse;
import com.example.online_shop.dto.SignUpRequest;
import com.example.online_shop.entities.User;
import com.example.online_shop.enums.Role;
import com.example.online_shop.exceptions.AlreadyExistException;
import com.example.online_shop.exceptions.BadCredentialException;
import com.example.online_shop.exceptions.NotFoundException;
import com.example.online_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistException("Sorry, this email is already registered. Please try a different email or login to your existing account");
        }

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.CLIENT)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse signIn(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(
                () -> new NotFoundException("User with this email: " + authenticationRequest.getEmail() + " not found!")
        );

        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialException("incorrect password!");
        }

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .name(user.getName())
                .build();
    }
}
