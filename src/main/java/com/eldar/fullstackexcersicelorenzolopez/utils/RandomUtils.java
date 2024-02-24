package com.eldar.fullstackexcersicelorenzolopez.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static String generateRandomString(int length) {
        String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Allowed characters pool
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static Integer generateRandomInteger(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
