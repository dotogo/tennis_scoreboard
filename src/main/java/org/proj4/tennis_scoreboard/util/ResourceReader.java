package org.proj4.tennis_scoreboard.util;

import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@UtilityClass
public class ResourceReader {

    public static List<String> readLines(String fileName) {
        return readLines(fileName, ResourceReader.class.getClassLoader());
    }

    public static List<String> readLines(String fileName, ClassLoader classLoader) {
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) {
                throw new FileNotFoundException("File not found: " + fileName);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return br.lines().toList();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + fileName, e);
        }
    }
}
