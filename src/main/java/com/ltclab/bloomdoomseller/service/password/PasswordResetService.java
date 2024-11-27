package com.ltclab.bloomdoomseller.service.password;

import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.exception.InvalidTokenException;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.passwordvalidator.PasswordEncryptionUtil;
import com.ltclab.bloomdoomseller.passwordvalidator.PasswordValidator;
import com.ltclab.bloomdoomseller.repository.PasswordResetTokenRepository;
import com.ltclab.bloomdoomseller.repository.account.AccountRepository;
import com.ltclab.bloomdoomseller.repository.password.PasswordResetToken;
import com.ltclab.bloomdoomseller.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetService {

    private final AccountRepository accountRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncryptionUtil passwordEncryptionUtil;
    private final EmailService emailService;
    private final PasswordValidator passwordValidator = PasswordValidator.defaultValidator();

    /**
     * Sends a password reset link to the given email address.
     */
    public void sendPasswordResetLink(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Account with email '" + email + "' not found."));

        String token = generateResetToken(account);
        log.info(token);
        emailService.sendPasswordResetEmail(account.getEmail(), token);
    }

    /**
     * Resets the password for the user associated with the given token.
     */
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid or expired token."));

        validateTokenExpiry(resetToken);
        passwordValidator.validateOrThrow(newPassword);

        Account account = resetToken.getAccount();
        updatePassword(account, newPassword);

        tokenRepository.delete(resetToken); // Clean up the token after use
    }

    /**
     * Generates a unique token and saves it with an expiration time.
     */
    private String generateResetToken(Account account) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);

        String token = Base64.encodeBase64URLSafeString(tokenBytes);
        PasswordResetToken resetToken = new PasswordResetToken(token, account);

        tokenRepository.save(resetToken);
        return token;
    }

    /**
     * Validates if the token is still valid based on expiration time.
     */
    private void validateTokenExpiry(PasswordResetToken resetToken) {
        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token has expired.");
        }
    }

    /**
     * Encrypts the new password and updates the account.
     */
    private void updatePassword(Account account, String newPassword) {
        String encryptedPassword = passwordEncryptionUtil.encryptPassword(newPassword);
        account.setPassword(encryptedPassword);
        accountRepository.save(account);
    }
}
