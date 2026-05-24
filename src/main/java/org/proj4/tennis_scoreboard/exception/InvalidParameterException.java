package org.proj4.tennis_scoreboard.exception;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
