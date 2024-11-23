package ir.javaclass.demo.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * RandomUtil class for generate random short code
 *
 * @author Mostafa Anbarmoo
 */
public class RandomUtil {

    public static String generateShortCode(int length, String letters) {
        return RandomStringUtils.random(length, letters);
    }
}
