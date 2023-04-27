package com.bafia.inquizi.register.confirmation;

import com.bafia.inquizi.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {
    Optional<ConfirmationCode> findConfirmationCodeByUser(User user);
    @Transactional
    void deleteByUser(User user);
}
