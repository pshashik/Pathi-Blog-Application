package com.pathi.blog.controller;

import com.pathi.blog.payload.JwtResponseDto;
import com.pathi.blog.payload.LoginDto;
import com.pathi.blog.payload.RegisterDto;
import com.pathi.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "REST API's for Login And Register")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(
            summary = "Get JWT Token By REST API",
            description = "Get the JWT Token by passing valid credentials"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto){
        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setToken(authService.login(loginDto));
        return new ResponseEntity<>(jwtResponseDto, HttpStatus.OK);
    }

    @Operation(
            summary="Create a user by REST API",
            description = "Create a user by REST API is used to save a new user to the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.register(registerDto),HttpStatus.OK);
    }
}
