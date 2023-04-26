package com.bafia.inquizi.security.email;

import com.bafia.inquizi.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="confirmation_codes")
public class ConfirmationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
