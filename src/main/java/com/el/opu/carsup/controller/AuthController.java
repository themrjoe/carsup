package com.el.opu.carsup.controller;

import com.el.opu.carsup.domain.User;
import com.el.opu.carsup.domain.dto.AuthDto;
import com.el.opu.carsup.domain.dto.AuthResponseDto;
import com.el.opu.carsup.jwt.JwtTokenProvider;
import com.el.opu.carsup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/cars/auth/register")
    @ResponseBody
    public void register(@RequestBody User user) {
        userService.register(user);
    }
}
