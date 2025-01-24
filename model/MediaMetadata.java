package com.example.videoplayer.core.model;

/**
 * Represents media metadata. Immutable.
 * @param title The title of the media.
 * @param artist The artist of the media.
 * @param album The album of the media.
 */
public record MediaMetadata(String title, String artist, String album) {
     // Immutability by record
}
