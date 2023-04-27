package com.bafia.inquizi.register;

import com.bafia.inquizi.register.dto.ConfirmationCodeRequestDTO;
import com.bafia.inquizi.register.dto.RegisterRequestDTO;
import com.bafia.inquizi.register.confirmation.ConfirmationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
public class RegisterController {
    public final RegisterService registerService;
    public final ConfirmationCodeService confirmationCodeService;

    @PostMapping("")
    public ResponseEntity<Void> signup(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return registerService.signup(registerRequestDTO);
    }


    @PostMapping("/code/confirm")
    public ResponseEntity<Void> confirm(@RequestBody ConfirmationCodeRequestDTO confirmationCodeRequestDTO) {
        return confirmationCodeService.confirm(confirmationCodeRequestDTO);
    }

    @PostMapping("/code/resend")
    public ResponseEntity<Void> resendConfirmationCode(@RequestBody ConfirmationCodeRequestDTO confirmationCodeRequestDTO) {
        return confirmationCodeService.resendConfirmationCode(confirmationCodeRequestDTO);
    }
}
