package com.ltclab.bloomdoomseller.controller.password;


import com.ltclab.bloomdoomseller.service.password.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    /**
     * Endpoint to request a password reset link.
     * @param email the email address of the account
     * @return a success message
     */
    @PostMapping("/reset-link")
    public ResponseEntity<String> sendPasswordResetLink(@RequestParam String email) {
        passwordResetService.sendPasswordResetLink(email);
        return ResponseEntity.ok("Password reset link sent successfully.");
    }

    /**
     * Endpoint to reset the password using the token.
     * @param token the password reset token
     * @param newPassword the new password for the account
     * @return a success message
     */
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully.");
    }
}

