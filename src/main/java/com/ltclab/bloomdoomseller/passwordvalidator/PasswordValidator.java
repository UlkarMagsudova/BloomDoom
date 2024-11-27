package com.ltclab.bloomdoomseller.passwordvalidator;

import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PasswordValidator {

    private static final String LENGTH_ERROR = "Password must be at least %d characters long.";
    private static final String UPPERCASE_ERROR = "Password must contain at least one uppercase letter.";
    private static final String LOWERCASE_ERROR = "Password must contain at least one lowercase letter.";
    private static final String DIGIT_ERROR = "Password must contain at least one digit.";
    private static final String SPECIAL_CHAR_ERROR = "Password must contain at least one special character (e.g., !@#$%^&*()-+=).";
    private static final String NULL_OR_EMPTY_ERROR = "Password cannot be null or empty.";

    private final int minLength;
    private final boolean requireUppercase;
    private final boolean requireLowercase;
    private final boolean requireDigit;
    private final boolean requireSpecialChar;


    public List<String> validate(String password) {
        List<String> rejections = new ArrayList<>();

        if (password == null || password.isEmpty()) {
            rejections.add(NULL_OR_EMPTY_ERROR);
            return rejections;
        }
        if (password.length() < minLength) {
            rejections.add(String.format(LENGTH_ERROR, minLength));
        }
        if (requireUppercase && !password.matches(".*[A-Z].*")) {
            rejections.add(UPPERCASE_ERROR);
        }
        if (requireLowercase && !password.matches(".*[a-z].*")) {
            rejections.add(LOWERCASE_ERROR);
        }
        if (requireDigit && !password.matches(".*\\d.*")) {
            rejections.add(DIGIT_ERROR);
        }
        if (requireSpecialChar && !password.matches(".*[!@#$%^&*()\\-+=].*")) {
            rejections.add(SPECIAL_CHAR_ERROR);
        }

        return rejections;
    }

    public void validateOrThrow(String password) {
        List<String> rejections = validate(password);
        if (!rejections.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", rejections));
        }
    }

    public static PasswordValidator defaultValidator() {
        return new PasswordValidator(8, true, true, true, true);
    }
}
