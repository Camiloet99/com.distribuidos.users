package com.distribuidos.users.exceptions;

import static com.distribuidos.users.exceptions.ErrorCodes.CENTRALIZER_USER_NOT_FOUND;

public class UserNotFoundCentralizerException extends RuntimeException {
    public UserNotFoundCentralizerException(String message) {
        super(CENTRALIZER_USER_NOT_FOUND + " " + message);
    }
}
