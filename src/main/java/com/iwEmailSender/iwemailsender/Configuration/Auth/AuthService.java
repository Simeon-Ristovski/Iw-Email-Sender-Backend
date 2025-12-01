package com.iwEmailSender.iwemailsender.Configuration.Auth;

import com.iwEmailSender.iwemailsender.Dto.Input.AccountDtoInset;
import com.iwEmailSender.iwemailsender.Dto.Output.AccountDto;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.Role;
import com.iwEmailSender.iwemailsender.Repository.AccountRepository;
import com.iwEmailSender.iwemailsender.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    public AuthService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }
    public ResponseEntity<?> login(Account loginRequest, HttpServletRequest request) {
        logger.info("Login attempt for email='{}'", loginRequest.getEmail());
        Optional<Account> userOpt = accountRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent()) {
            Account user = userOpt.get();
            boolean matches = passwordEncoder.matches(
                    loginRequest.getPassword() == null ? "" : loginRequest.getPassword().trim(),
                    user.getPassword()
            );
            if (matches) {
                HttpSession session = request.getSession(true);
                AccountDto accountDto = new AccountDto();
                accountDto.setFirstName(user.getFirstName());
                accountDto.setLastName(user.getLastName());
                accountDto.setUuid(user.getUuid());
                accountDto.setEmail(user.getEmail());
                accountDto.setRoles(user.getRoles().stream().map(Role::getRoleName).toList());
                session.setAttribute("user", accountDto);
                return ResponseEntity.ok(accountDto);
            } else {
                logger.warn("Invalid password for email={}", loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            logger.warn("No user found with email={}", loginRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logged out successfully");
    }
    public ResponseEntity<AccountDtoInset> registerAccount(AccountDtoInset accountDto) throws BadRequestException {
        accountService.addAccountToBase(accountDto);
        return new ResponseEntity<>(accountDto, HttpStatus.CREATED);
    }
    public ResponseEntity<?> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return ResponseEntity.ok(session.getAttribute("user"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session");
    }
}
