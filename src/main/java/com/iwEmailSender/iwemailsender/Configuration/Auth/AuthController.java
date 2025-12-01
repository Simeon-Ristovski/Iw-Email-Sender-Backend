package com.iwEmailSender.iwemailsender.Configuration.Auth;

import com.iwEmailSender.iwemailsender.Dto.Input.AccountDtoInset;
import com.iwEmailSender.iwemailsender.Model.Account;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account loginRequest, HttpServletRequest request) {
        return authService.login(loginRequest, request);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authService.logout(request);
    }
    @PostMapping("/register")
    public ResponseEntity<AccountDtoInset> registerAccount(@RequestBody AccountDtoInset accountDto) throws BadRequestException {
        return authService.registerAccount(accountDto);
    }
    @GetMapping("/session")
    public ResponseEntity<?> checkSession(HttpServletRequest request) {
        return authService.checkSession(request);
    }
}
