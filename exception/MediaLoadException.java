package com.example.videoplayer.core.exception;

public class MediaLoadException extends RuntimeException {
    public MediaLoadException(String message) {
        super(message);
    }

    public MediaLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
