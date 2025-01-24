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
    Single<PlayerState> loadMedia(File file);
    Single<List<Subtitle>> loadSubtitle(File file);
    Single<MediaMetadata> loadMetadata(File file);
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
