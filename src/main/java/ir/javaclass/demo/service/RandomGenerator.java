package ir.javaclass.demo.service;

import ir.javaclass.demo.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RandomGenerator {

    private final AppConfig appConfig;

    public String generateShortCode(int length) {
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            shortCode.append(appConfig.getRandomChars().charAt(random.nextInt(appConfig.getRandomChars().length())));
        }
        return shortCode.toString();
    }
}
