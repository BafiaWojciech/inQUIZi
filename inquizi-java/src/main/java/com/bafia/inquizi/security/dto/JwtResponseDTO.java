package com.bafia.inquizi.security.dto;

import com.bafia.inquizi.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponseDTO {
    private String token;
    private String refreshToken;
    private Set<String> roles;

    public static JwtResponseDTO of(String token, String refreshToken, Set<Role> roles) {
        return new JwtResponseDTO(token, refreshToken, roles.stream().map(Role::name).collect(Collectors.toSet()));
    }

}
