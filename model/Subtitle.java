package com.example.videoplayer.core.model;

/**
 * Represents a subtitle. Immutable.
 */
public record Subtitle(double startTime, double endTime, String text) {
}
