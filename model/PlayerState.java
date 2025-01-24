package com.example.videoplayer.core.model;

import javafx.scene.media.MediaPlayer;

/**
 * Represents the state of the media player. Immutable.
 * @param status The status of the media player.
 * @param currentTime The current time of the media player.
 * @param volume The volume of the media player.
 * @param rate The playback rate of the media player.
 * @param subtitleText The text of the current subtitle.
 */
public record PlayerState(MediaPlayer.Status status, double currentTime, double volume, double rate, String subtitleText) {
    // Immutability by record
 }
