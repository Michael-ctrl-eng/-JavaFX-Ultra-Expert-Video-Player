  package com.example.videoplayer.core.exception;

   public class PlaybackException extends RuntimeException {
        public PlaybackException(String message) {
             super(message);
       }
       public PlaybackException(String message, Throwable cause) {
           super(message, cause);
        }
    }
