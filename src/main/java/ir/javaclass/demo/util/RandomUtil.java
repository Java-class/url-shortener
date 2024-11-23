package ir.javaclass.demo.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtil {

    public static String generateShortCode(int length, String letters) {
        return RandomStringUtils.random(length, letters);
    }
}
