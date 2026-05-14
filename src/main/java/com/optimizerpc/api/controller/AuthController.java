package com.optimizerpc.api.controller;

import com.optimizerpc.api.dto.LoginRequest;
import com.optimizerpc.api.dto.LoginResponse;
import com.optimizerpc.api.dto.RegisterRequest;
import com.optimizerpc.api.dto.UserResponse;
import com.optimizerpc.api.entity.AppUser;
import com.optimizerpc.api.repository.AppUserRepository;
import com.optimizerpc.api.service.JwtService;
import io.jsonwebtoken.Claims;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v0")
public class AuthController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El username ya existe");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya existe");
        }

        AppUser user = new AppUser();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        AppUser saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getUsername());
    }

    @PostMapping("/auth")
    public LoginResponse login(@RequestBody LoginRequest request) {
        AppUser user = userRepository.findByUsername(request.username())
                .or(() -> userRepository.findByEmail(request.username()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
        }

        String token = jwtService.createToken(user);
        return new LoginResponse(token, user.getUsername(), user.getId());
    }

    @GetMapping("/auth/check")
    public Map<String, Object> check(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            Claims claims = jwtService.validate(token);

            return Map.of(
                    "authenticated", true,
                    "username", claims.get("username", String.class)
            );
        } catch (Exception error) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
    }
}
