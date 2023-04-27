package com.bafia.inquizi.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponseDTO {
    private String token;
    private String refreshToken;

    public static JwtResponseDTO of(String token, String refreshToken) {
        return new JwtResponseDTO(token, refreshToken);
    }

}
