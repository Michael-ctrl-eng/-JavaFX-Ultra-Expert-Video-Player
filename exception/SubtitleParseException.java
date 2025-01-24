package com.example.videoplayer.core.exception;

/**
 * Custom exception for subtitle parsing errors.
 */
public class SubtitleParseException extends RuntimeException {
    /**
     * Constructor for SubtitleParseException.
     * @param message Error message.
     */
    public SubtitleParseException(String message) {
        super(message);
    }

     /**
     * Constructor for SubtitleParseException.
      * @param message Error message.
      * @param cause Cause of exception.
    */
    public SubtitleParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
