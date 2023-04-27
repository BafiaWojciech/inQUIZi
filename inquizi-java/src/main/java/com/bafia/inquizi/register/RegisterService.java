package com.bafia.inquizi.register;

import com.bafia.inquizi.register.dto.RegisterRequestDTO;
import com.bafia.inquizi.register.confirmation.ConfirmationCodeService;
import com.bafia.inquizi.user.Role;
import com.bafia.inquizi.user.User;
import com.bafia.inquizi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Void> signup(RegisterRequestDTO registerRequestDTO) {
        if (userService.findUserByEmail(registerRequestDTO.getEmail()).isEmpty()) {
            User u = userService.save(User.builder()
                    .username(registerRequestDTO.getUsername())
                    .email(registerRequestDTO.getEmail())
                    .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                    .role(Set.of(Role.ROLE_USER))
                    .build());

            confirmationCodeService.generateRandomCode(u);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
