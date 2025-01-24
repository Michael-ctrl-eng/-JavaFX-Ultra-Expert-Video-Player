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
    /**
     * Loads the media from the given file.
     * @param file The file to load.
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
     * Loads the metadata of the media from the given file.
     * @param file The file to load the metadata from.
     * @return A Single emitting the MediaMetadata.
     */
    Single<MediaMetadata> loadMetadata(File file);
}
