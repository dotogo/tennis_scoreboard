package org.proj4.tennis_scoreboard.util;

import lombok.experimental.UtilityClass;
import tools.jackson.databind.ObjectMapper;

@UtilityClass
public final class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

//    private JsonUtil() {
//        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
//    }

    public static String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }
}
