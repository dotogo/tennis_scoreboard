package org.proj4.tennis_scoreboard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

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
