package com.example.videoplayer.framework.utils;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
/**
    *  A simple class for a media player resource pool.
   */
public class MediaPlayerResourcePool {

      private final Map<String, MediaPlayer> mediaPlayerPool = new ConcurrentHashMap<>();
        /**
    * Get a media player or create a new one.
    * @param source The source of the media to be loaded.
    * @return MediaPlayer Instance
     */
    public MediaPlayer getMediaPlayer(String source) {
       if (Objects.nonNull(mediaPlayerPool.get(source))) {
           return mediaPlayerPool.get(source);
      }
        Media media = new Media(source);
      MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayerPool.put(source, mediaPlayer);
         return mediaPlayer;
   }
      /**
     * Release media player instance
      * @param mediaPlayer media player to release.
     */
     public void releaseMediaPlayer(MediaPlayer mediaPlayer){
         mediaPlayerPool.entrySet().removeIf(entry -> entry.getValue() == mediaPlayer);
     }

}
