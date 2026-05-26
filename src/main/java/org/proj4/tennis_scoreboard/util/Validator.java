package org.proj4.tennis_scoreboard.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Validator {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 32;
    private static final int UUID_LENGTH = 36;

    private static final String ENGLISH_PROFANITY_WORDS = "en_profanity_words.txt";
    private static final String RUSSIAN_PROFANITY_WORDS = "ru_profanity_words.txt";

    private static final Set<String> PROFANITY_WORDS;

    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZА-Яа-яЁё0-9_.` -]+$");

    static {
        List<String> profanityRu = ResourceReader.readLines(RUSSIAN_PROFANITY_WORDS);
        List<String> profanityEng = ResourceReader.readLines(ENGLISH_PROFANITY_WORDS);

        Stream<String> russian = profanityRu.stream()
                                .filter(Objects::nonNull)
                                .map(s -> s.trim().toLowerCase());

        Stream<String> english = profanityEng.stream()
                                .filter(Objects::nonNull)
                                .map(s -> s.trim().toLowerCase());

        PROFANITY_WORDS = Stream.concat(russian, english).collect(Collectors.toSet());

    }

    public static boolean isValidUuid(String uuid) {
        if (uuid == null || uuid.length() != UUID_LENGTH) {
            return false;
        }
        return UUID_PATTERN.matcher(uuid).matches();
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        name = name.trim();

        if (!NAME_PATTERN.matcher(name).matches()) {
            return false;
        }

        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            return false;
        }

        return !isProfanityWord(name);
    }

    private static boolean isProfanityWord(String word) {
        Objects.requireNonNull(word);

        word = word.toLowerCase();

        return PROFANITY_WORDS.contains(word);
    }
}
