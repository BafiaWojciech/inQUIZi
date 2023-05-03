package com.bafia.inquizi;

import com.bafia.inquizi.user.Role;
import com.bafia.inquizi.user.User;
import com.bafia.inquizi.user.UserRepository;
import com.bafia.inquizi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitUsers implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userService.findUserByEmail("admin").isEmpty()) {
            User u = userService.save(User.builder()
                    .username("Admin")
                    .email("admin")
                    .password(passwordEncoder.encode("asdf"))
                    .role(Set.of(Role.ROLE_ADMIN, Role.ROLE_TEACHER, Role.ROLE_USER))
                    .build());
            u.setEnabled(true);
            userService.save(u);
        }
        if (userService.findUserByEmail("asdft").isEmpty()) {
            User u = userService.save(User.builder()
                    .username("asdft")
                    .email("asdft")
                    .password(passwordEncoder.encode("asdf"))
                    .role(Set.of(Role.ROLE_USER, Role.ROLE_TEACHER))
                    .build());
            u.setEnabled(true);
            userService.save(u);
        }
        if (userService.findUserByEmail("asdf").isEmpty()) {
            User u = userService.save(User.builder()
                    .username("asdf")
                    .email("asdf")
                    .password(passwordEncoder.encode("asdf"))
                    .role(Set.of(Role.ROLE_USER))
                    .build());
            u.setEnabled(true);
            userService.save(u);
        }
        if (userService.findUserByEmail("asdf1").isEmpty()) {
            User u = userService.save(User.builder()
                    .username("asdf1")
                    .email("asdf1")
                    .password(passwordEncoder.encode("asdf"))
                    .role(Set.of(Role.ROLE_USER))
                    .build());
            u.setEnabled(true);
            userService.save(u);
        }
    }
}