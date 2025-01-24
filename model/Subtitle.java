package com.example.videoplayer.core.model;

/**
 * Represents a subtitle. Immutable.
 * @param startTime The start time of the subtitle.
 * @param endTime The end time of the subtitle.
 * @param text The text of the subtitle.
 */
public record Subtitle(double startTime, double endTime, String text) {
    // Immutability by record
}
