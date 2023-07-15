package com.pathi.blog.service;

import com.pathi.blog.payload.LoginDto;
import com.pathi.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
