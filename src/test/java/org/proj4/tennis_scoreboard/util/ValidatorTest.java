package org.proj4.tennis_scoreboard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    @Nested
    @DisplayName("UUID validation")
    class UUIDValidation {

        @Test
        @DisplayName("True if UUID is correct")
        void trueIfUuidIsCorrect() {
            String uuid = UUID.randomUUID().toString();

            assertTrue(Validator.isValidUuid(uuid));
        }

        @Test
        @DisplayName("False if UUID is not correct")
        void falseIfUuidIsNotCorrect() {
            String wrongUuid = "018124c1-text-4bb4-b328-aa4094bd7390";

            assertFalse(Validator.isValidUuid(wrongUuid));
        }

        @Test
        @DisplayName("False if UUID is null")
        void falseIfUuidIsNull() {

            assertFalse(Validator.isValidUuid(null));
        }

        @Test
        @DisplayName("False if UUID length != 36")
        void falseIfUuidLengthNotCorrect() {
            String wrongUuid = "eee20c29-0ce-445a-b648-49bbce329dbf";

            assertFalse(Validator.isValidUuid(wrongUuid));
        }

    }
    @Nested
    @DisplayName("Name validation")
    class NameValidation {
        private static final String SAMPLE_PLAYER_NAMES = "samplePlayerNames.txt";

        @Test
        @DisplayName("Blank name is not valid")
        void blankNameIsNotValid() {
            assertFalse(Validator.isValidName(""));
        }

        @ParameterizedTest
        @ValueSource(strings = {"N/\\me", "N@me", "Johnn;y", "Mary~Ann", "Vict()r", "Lucky&", "No n*me"})
        @DisplayName("Name with extra symbols is not valid")
        void nameWithExtraSymbolsIsNotValid(String input) {
            assertFalse(Validator.isValidName(input));
        }

        @Test
        @DisplayName("Too short name is not valid")
        void tooShortNameIsNotValid() {
            assertFalse(Validator.isValidName("a"));
        }

        @Test
        @DisplayName("Too long name is not valid")
        void tooLongNameIsNotValid() {
            assertFalse(Validator.isValidName("Very-very-very big high long name with many letters"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"гОвно", "Жопа", "какашка", "   shit  ", "sex", "GAY"})
        void profanityNameIsNotValid(String input) {
            assertFalse(Validator.isValidName(input));
        }

        @ParameterizedTest
        @MethodSource("getArgumentsForParametersNormalNameIsValidTest")
        @DisplayName("Normal name is valid")
        void normalNameIsValid(String input) {
            assertTrue(Validator.isValidName(input));
        }

        static Stream<String> getArgumentsForParametersNormalNameIsValidTest() {
            List<String> enNames = ResourceReader.readLines(SAMPLE_PLAYER_NAMES);
            List<String> ruNames = List.of("Серена Уильямс", "Ига Швёнтек",
                    "Арина Соболенко", "Коко Гауфф",
                    "Мария Шарапова", "Елена Рыбакина",
                    "Наоми Осака", "Винус Уильямс",
                    "Штеффи Граф", "Симона Халеп");

            return Stream.concat(enNames.stream(), ruNames.stream());
        }
    }

}
