package com.distribuidos.users.exceptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorCodes {

    private static final String PREFIX = "US-00";

    public static final String CENTRALIZER_UPSTREAM_ERROR = PREFIX + "01";
    public static final String CENTRALIZER_USER_NOT_FOUND = PREFIX + "02";

}
