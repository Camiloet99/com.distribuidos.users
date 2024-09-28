package com.distribuidos.users.exceptions;

import static com.distribuidos.users.exceptions.ErrorCodes.CENTRALIZER_UPSTREAM_ERROR;

public class UnregisterCitizenException extends RuntimeException {
    public UnregisterCitizenException(String message) {
        super(CENTRALIZER_UPSTREAM_ERROR + " " + message);
    }
}
