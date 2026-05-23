package org.proj4.tennis_scoreboard.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class Validator {
    private static final String ENGLISH_PROFANITY_WORDS = "en_profanity_words.txt";
    private static final String RUSSIAN_PROFANITY_WORDS = "ru_profanity_words.txt";

    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZА-Яа-яЁё0-9_`-]+$");

    public static boolean isValidUuid(String uuid) {
        if (uuid == null || uuid.length() != 36) {
            return false;
        }
        return UUID_PATTERN.matcher(uuid).matches();
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        if (name.length() < 2 || name.length() > 32) {
            return false;
        }

        return NAME_PATTERN.matcher(name).matches();
    }

}
