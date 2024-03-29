package com.bafia.inquizi.security.refresh_token;

import com.bafia.inquizi.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.UUID;


@EqualsAndHashCode(of = "uuid")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    //TODO nie powinien być przechowywany refresh-token w bazie, doczytać o "jti" w JWT tokenie -> wystarczy jego przechowywać (jako NanoID)
    @Column
    private String token;

    @Column
    private ZonedDateTime expiration;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
