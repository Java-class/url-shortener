package ir.javaclass.demo.service;

import java.util.Random;

public class RandomUtil {
    public static String generateShortCode(int length) {
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortCode.toString();
    }
}
