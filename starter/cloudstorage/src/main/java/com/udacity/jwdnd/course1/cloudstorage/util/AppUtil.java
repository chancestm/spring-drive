package com.udacity.jwdnd.course1.cloudstorage.util;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AppUtil {

    private static final int SALT_LENGTH = 10;
    private static final int AES_KEY_LENGTH = 16;

    public static String generateSalt() {
        return generateKey(SALT_LENGTH);
    }

    public static String generateAesKey() {
        return generateKey(AES_KEY_LENGTH);
    }

    private static String generateKey(int keyLength) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

        StringBuilder key = new StringBuilder(keyLength);
        Random random = new Random();

        for (int i = 0; i < keyLength; i++) {
            int randomIndex = random.nextInt(chars.length());
            char randomChar = chars.charAt(randomIndex);
            key.append(randomChar);
        }

        return key.toString();
    }

    public static List<String> extractValidationErrors(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return Collections.emptyList();
        }

        List<String> errors = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldError -> errors.add(fieldError.getDefaultMessage()));

        return errors;
    }

}
