package com.example.videoplayer.core.usecase;

import com.example.videoplayer.core.model.PlayerState;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

/**
 * Interface for controlling playback.
 */
public interface PlaybackUseCase {
    Single<PlayerState> play();
    Single<PlayerState> pause();
    Single<PlayerState> seek(double value);
    Single<PlayerState> seekForward();
    Single<PlayerState> seekBackward();
    Single<PlayerState> setRate(double rate);
    Single<PlayerState> setVolume(double volume);
    Single<PlayerState> stop();
    Observable<PlayerState> observePlayerState();
}
