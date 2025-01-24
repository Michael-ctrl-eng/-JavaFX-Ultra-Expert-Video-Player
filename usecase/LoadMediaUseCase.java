package com.example.videoplayer.core.usecase;

import com.example.videoplayer.core.model.MediaMetadata;
import com.example.videoplayer.core.model.PlayerState;
import com.example.videoplayer.core.model.Subtitle;
import io.reactivex.rxjava3.core.Single;
import java.io.File;
import java.util.List;

/**
 * Interface for loading media, subtitles, and metadata.
 */
public interface LoadMediaUseCase {
    Single<PlayerState> loadMedia(File file);
    Single<List<Subtitle>> loadSubtitle(File file);
    Single<MediaMetadata> loadMetadata(File file);
}
