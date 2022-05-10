package com.ahirajustice.retail.common;

import com.ahirajustice.retail.enums.TimeFactor;

import java.security.SecureRandom;
import java.util.Random;

public class CommonHelper {

    public static boolean isStringUpperCase(String str) {
        char[] charArray = str.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            if (!Character.isUpperCase(charArray[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean containsSpecialCharactersAndNumbers(String str) {
        String specialCharactersAndNumbers = "!@#$%&*()'+,-./:;<=>?[]^_`{|}0123456789";

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (specialCharactersAndNumbers.contains(Character.toString(ch))) {
                return true;
            }
        }

        return false;
    }

    public static int convertToMillis(int time, TimeFactor timeFactor) {
        int result = 0;

        switch (timeFactor) {
            case SECONDS:
                result = time * 1000;
                break;
            case MINUTES:
                result = time * 60000;
                break;
            case HOURS:
                result = time * 3600000;
                break;
            default:
                break;
        }

        return result;
    }

    public static String generateRandomString(int length) {
        return generateRandomString(length, "0123456789");
    }

    public static String generateRandomString(int length, String alphabet) {
        Random random = new SecureRandom();
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return new String(returnValue);
    }

}
