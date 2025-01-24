package com.example.videoplayer.core.exception;

 public class SubtitleParseException extends RuntimeException {
       public SubtitleParseException(String message) {
            super(message);
       }
       public SubtitleParseException(String message, Throwable cause) {
            super(message, cause);
       }
}
