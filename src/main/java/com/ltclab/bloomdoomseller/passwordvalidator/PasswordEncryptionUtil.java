package com.ltclab.bloomdoomseller.passwordvalidator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptionUtil {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encryptPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }
    public boolean matchPassword(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}
