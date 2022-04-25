package com.el.opu.carsup.controller;

import com.el.opu.carsup.domain.User;
import com.el.opu.carsup.domain.dto.AuthDto;
import com.el.opu.carsup.domain.dto.AuthResponseDto;
import com.el.opu.carsup.domain.dto.ResponseDto;
import com.el.opu.carsup.jwt.JwtTokenProvider;
import com.el.opu.carsup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/cars/auth/login")
    public ResponseEntity login(@RequestBody AuthDto authDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
            User user = userService.findByUserName(authDto.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + authDto.getUsername() + " not found");
            }
            String token = jwtTokenProvider.createToken(authDto.getUsername(), user.getRoles());
            AuthResponseDto responseDto = new AuthResponseDto(user.getUserName(), token, user.getEmail());
            return ResponseEntity.ok(responseDto);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .status(400)
                    .cause("Invalid username or password")
                    .success(false)
                    .build());
        }
    }

    @PostMapping("/cars/auth/register")
    @ResponseBody
    public ResponseEntity register(@RequestBody User user) {
        User registered = userService.register(user);
        if (registered == null) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .status(400)
                    .cause("User with username: " + user.getUserName() + " already exists")
                    .success(false)
                    .build());
        }
        return ResponseEntity.ok(ResponseDto.builder()
                .status(200)
                .cause("User" + user.getUserName() + " registered")
                .success(true)
                .build());
    }
}
