package com.example.videoplayer.adapter.presenter;

import com.example.videoplayer.core.model.MediaMetadata;
import com.example.videoplayer.core.model.PlayerState;
import com.example.videoplayer.core.model.Subtitle;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.io.File;
import java.util.List;

/**
 * Interface for the presenter that adapts use cases to the UI.
 */
public interface MainPresenter {
    /**
     * Loads the media.
     * @param file The file to load the media from.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> loadMedia(File file);

    /**
     * Loads the subtitles from the given file.
     * @param file The file to load the subtitles from.
     * @return A Single emitting a List of Subtitles.
     */
    Single<List<Subtitle>> loadSubtitle(File file);

    /**
     * Loads the media metadata from the given file.
     * @param file The file to load the metadata from.
     * @return A Single emitting the MediaMetadata.
     */
    Single<MediaMetadata> loadMetadata(File file);

    /**
     * Starts the playback of the media.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> play();

    /**
     * Pauses the playback of the media.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> pause();

    /**
     * Seeks the media to the given value.
     * @param value The double value to seek the media to.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> seek(double value);

    /**
     * Seeks the media to the next frame.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> seekForward();

    /**
     * Seeks the media to the previous frame.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> seekBackward();

    /**
     * Sets the playback rate of the media.
     * @param rate The double value of the playback rate.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> setRate(double rate);

    /**
     * Sets the volume of the media.
     * @param volume The double value of the volume.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> setVolume(double volume);

    /**
     * Stops the playback of the media.
     * @return A Single emitting the PlayerState.
     */
    Single<PlayerState> stop();

    /**
     * Observes the player state of the media player.
     * @return An Observable emitting the PlayerState.
     */
    Observable<PlayerState> observePlayerState();
}
