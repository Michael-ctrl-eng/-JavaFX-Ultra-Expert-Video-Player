package com.example.videoplayer.core.exception;

/**
* Custom exception for media loading errors.
 */
public class MediaLoadException extends RuntimeException {
    /**
     * Constructor for MediaLoadException.
     * @param message Error message.
     */
    public MediaLoadException(String message) {
        super(message);
    }

    /**
     * Constructor for MediaLoadException.
     * @param message Error message.
     * @param cause Cause of exception.
     */
    public MediaLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
