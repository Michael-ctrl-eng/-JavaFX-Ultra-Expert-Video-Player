package com.example.videoplayer.adapter.impl;

import com.example.videoplayer.adapter.presenter.MainPresenter;
import com.example.videoplayer.core.model.MediaMetadata;
import com.example.videoplayer.core.model.PlayerState;
import com.example.videoplayer.core.model.Subtitle;
import com.example.videoplayer.core.usecase.LoadMediaUseCase;
import com.example.videoplayer.core.usecase.PlaybackUseCase;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.io.File;
import java.util.List;

/**
 * Implementation of the MainPresenter that coordinates use cases.
 */
public class MainPresenterImpl implements MainPresenter {
    private final LoadMediaUseCase loadMediaUseCase;
    private final PlaybackUseCase playbackUseCase;

    /**
     * Constructor of MainPresenterImpl
     * @param loadMediaUseCase  loadMediaUseCase implementation
     * @param playbackUseCase  playbackUseCase implementation
     */
    public MainPresenterImpl(LoadMediaUseCase loadMediaUseCase, PlaybackUseCase playbackUseCase) {
        this.loadMediaUseCase = loadMediaUseCase;
        this.playbackUseCase = playbackUseCase;
    }
    /**
     *  Method to load the media
     * @param file File containing the media.
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> loadMedia(File file) {
        return loadMediaUseCase.loadMedia(file);
    }
    /**
     * Method to load the subtitle file
     * @param file File containing the subtitle.
     * @return Single list of Subtitle instance.
     */
    @Override
    public Single<List<Subtitle>> loadSubtitle(File file) {
        return loadMediaUseCase.loadSubtitle(file);
    }
    /**
     * Method to load the media metadata
     * @param file File containing the metadata.
     * @return Single MediaMetadata instance.
     */
    @Override
    public Single<MediaMetadata> loadMetadata(File file) {
        return loadMediaUseCase.loadMetadata(file);
    }
    /**
     * Method to play the media
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> play() {
        return playbackUseCase.play();
    }
    /**
     * Method to pause the media
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> pause() {
        return playbackUseCase.pause();
    }
    /**
     * Method to seek the media
     * @param value Double value to seek the media.
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> seek(double value) {
        return playbackUseCase.seek(value);
    }
    /**
     * Method to seek forward in the media
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> seekForward() {
        return playbackUseCase.seekForward();
    }
    /**
     * Method to seek backward in the media
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> seekBackward() {
        return playbackUseCase.seekBackward();
    }
    /**
     * Method to set the playback rate of the media
     * @param rate Double value of the rate.
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> setRate(double rate) {
        return playbackUseCase.setRate(rate);
    }
    /**
     * Method to set the volume of the media
     * @param volume Double value of the volume.
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> setVolume(double volume) {
        return playbackUseCase.setVolume(volume);
    }
    /**
     * Method to stop the media
     * @return Single PlayerState instance.
     */
    @Override
    public Single<PlayerState> stop() {
        return playbackUseCase.stop();
    }
     /**
     * Observe the player state for changes
      * @return Observable PlayerState instance.
      */
    @Override
    public Observable<PlayerState> observePlayerState() {
        return playbackUseCase.observePlayerState();
    }
}
