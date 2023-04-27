package com.bafia.inquizi;

import com.bafia.inquizi.user.Role;
import com.bafia.inquizi.user.User;
import com.bafia.inquizi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitUsers implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userService.findUserByEmail("admin@inquizi.com").isEmpty()) {
            User u = userService.save(User.builder()
                    .username("Admin")
                    .email("admin@inquizi.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Set.of(Role.ROLE_ADMIN, Role.ROLE_TEACHER, Role.ROLE_USER))
                    .build());
            u.setEnabled(true);
            userService.save(u);
        }
    }
}