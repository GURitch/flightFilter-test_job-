package com.gridnine.testing;

public class IncorrectDateBetweenSegmentsException extends RuntimeException {
    public IncorrectDateBetweenSegmentsException() {
    }

    public IncorrectDateBetweenSegmentsException(String message) {
        super(message);
    }

    public IncorrectDateBetweenSegmentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectDateBetweenSegmentsException(Throwable cause) {
        super(cause);
    }

    public IncorrectDateBetweenSegmentsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
