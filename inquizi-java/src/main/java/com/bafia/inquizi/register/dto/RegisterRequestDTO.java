package com.bafia.inquizi.register.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequestDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

}
