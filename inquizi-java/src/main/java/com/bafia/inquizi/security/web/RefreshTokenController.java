package com.bafia.inquizi.security.web;

import com.bafia.inquizi.security.dto.JwtRefreshRequestDTO;
import com.bafia.inquizi.security.dto.JwtResponseDTO;
import com.bafia.inquizi.security.refresh_token.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public JwtResponseDTO refreshJwt(@RequestBody JwtRefreshRequestDTO refreshRequestDto) {
        return refreshTokenService.refreshToken(refreshRequestDto);
    }
}