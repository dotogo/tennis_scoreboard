package org.proj4.tennis_scoreboard.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

//    private JsonUtil() {
//        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
//    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
