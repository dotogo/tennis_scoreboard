package org.proj4.tennis_scoreboard.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class Validator {
    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    
    public static boolean isValidUuid(String uuid) {
        if (uuid == null || uuid.length() != 36) {
            return false;
        }
        return UUID_PATTERN.matcher(uuid).matches();
    }
}
