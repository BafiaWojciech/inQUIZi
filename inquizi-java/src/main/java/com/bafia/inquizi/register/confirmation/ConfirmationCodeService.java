package com.bafia.inquizi.register.confirmation;

import com.bafia.inquizi.register.dto.ConfirmationCodeRequestDTO;
import com.bafia.inquizi.user.User;
import com.bafia.inquizi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserService userService;

    public void generateRandomCode(User user) {
        if (confirmationCodeRepository.findConfirmationCodeByUser(user).isPresent())
            confirmationCodeRepository.deleteByUser(user);

        String numbers = "0123456789";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb;

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

        confirmationCodeRepository.save(
                ConfirmationCode.builder()
                        .code(sb.toString())
                        .user(user)
                        .expiration(ZonedDateTime.now().plusMinutes(15))
                        .build());
    }

    public ResponseEntity<Void> confirm(ConfirmationCodeRequestDTO confirmationCodeRequestDTO) {
        String code = confirmationCodeRequestDTO.getCode();
        try {
            User user = userService.getUserByEmail(confirmationCodeRequestDTO.getEmail());
            ConfirmationCode code_entity = confirmationCodeRepository.findConfirmationCodeByUser(user).get();
            if (!code_entity.getCode().equals(code) || code_entity.getExpiration().isBefore(ZonedDateTime.now()))
                throw new Exception();
            user.setEnabled(true);
            confirmationCodeRepository.deleteByUser(user);
            userService.save(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<Void> resendConfirmationCode(ConfirmationCodeRequestDTO confirmationCodeRequestDTO) {
        try {
            User u = userService.findUserByEmail(confirmationCodeRequestDTO.getEmail()).get();
            if(u.isEnabled()) throw new Exception();
            generateRandomCode(u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
