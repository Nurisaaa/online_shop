package com.example.online_shop.api;

import com.example.online_shop.dto.AuthenticationRequest;
import com.example.online_shop.dto.AuthenticationResponse;
import com.example.online_shop.dto.SignUpRequest;
import com.example.online_shop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    AuthenticationResponse signUp(@RequestParam SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    AuthenticationResponse signIn(@RequestParam AuthenticationRequest authenticationRequest) {
        return authenticationService.signIn(authenticationRequest);
    }

}
