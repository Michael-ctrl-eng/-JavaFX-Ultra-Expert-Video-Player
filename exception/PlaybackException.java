package com.example.videoplayer.core.exception;

/**
* Custom exception for playback errors.
 */
public class PlaybackException extends RuntimeException {
    /**
     * Constructor for PlaybackException.
     * @param message Error message.
    */
    public PlaybackException(String message) {
        super(message);
    }

    /**
    * Constructor for PlaybackException.
     * @param message Error message.
    * @param cause Cause of exception.
    */
    public PlaybackException(String message, Throwable cause) {
        super(message, cause);
     }
}
