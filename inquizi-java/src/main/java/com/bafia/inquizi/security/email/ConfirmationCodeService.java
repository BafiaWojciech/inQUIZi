package com.bafia.inquizi.security.email;

import com.bafia.inquizi.user.User;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {
    private final ConfirmationCodeRepository confirmationCodeRepository;

    public void generateRandomCode(User user) {
        String numbers = "0123456789";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb;

        do {
            List<Character> chars = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                chars.add(numbers.charAt(random.nextInt(numbers.length())));
            for (int i = 0; i < 2; i++)
                chars.add(alphabet.charAt(random.nextInt(alphabet.length())));

            Collections.shuffle(chars, random);

            sb = new StringBuilder();
            for (char c : chars) {
                sb.append(c);
            }
        } while(!confirmationCodeRepository.findConfirmationCodeByCode(sb.toString()).isEmpty());

        confirmationCodeRepository.save(
                ConfirmationCode.builder()
                        .code(sb.toString())
                        .user(user)
                        .build());
    }
}
