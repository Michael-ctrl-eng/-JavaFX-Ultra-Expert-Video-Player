package com.example.videoplayer.core.model;

import javafx.scene.media.MediaPlayer;

/**
 * Represents the state of the media player. Immutable.
 */
public record PlayerState(MediaPlayer.Status status, double currentTime, double volume, double rate, String subtitleText) {
}
