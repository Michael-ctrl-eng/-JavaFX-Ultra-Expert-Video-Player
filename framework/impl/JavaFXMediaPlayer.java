package com.example.videoplayer.framework.impl;

import com.example.videoplayer.Main;
import com.example.videoplayer.core.exception.MediaLoadException;
import com.example.videoplayer.core.exception.PlaybackException;
import com.example.videoplayer.core.exception.SubtitleParseException;
import com.example.videoplayer.core.model.MediaMetadata;
import com.example.videoplayer.core.model.PlayerState;
import com.example.videoplayer.core.model.Subtitle;
import com.example.videoplayer.core.usecase.LoadMediaUseCase;
import com.example.videoplayer.core.usecase.PlaybackUseCase;
import com.example.videoplayer.framework.utils.MediaPlayerResourcePool;
import com.example.videoplayer.framework.utils.SubtitleParser;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the frameworks logic of loading and playing media
 */
public class JavaFXMediaPlayer implements LoadMediaUseCase, PlaybackUseCase {
    private static final Logger logger = Logger.getLogger(JavaFXMediaPlayer.class.getName());
    private MediaPlayer mediaPlayer;
    private final MediaPlayerResourcePool mediaPlayerResourcePool = new MediaPlayerResourcePool();
    private String VIDEO_FILE_PATH = "sample.mp4";
    private final BehaviorSubject<PlayerState> playerStateSubject = BehaviorSubject.create();
    private List<Subtitle> subtitles = null;

    /**
     * Method to load the media from the given file
     * @param file File containing the media
     * @return Single of PlayerState
     */
    @Override
    public Single<PlayerState> loadMedia(File file) {
        return Single.fromCallable(() -> {
            if(file == null || !file.exists() || !file.isFile()) {
                logger.log(Level.SEVERE, "File is null or invalid" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                 throw new MediaLoadException("Invalid media file");
              }
                this.VIDEO_FILE_PATH = file.getAbsolutePath();
              mediaPlayer = mediaPlayerResourcePool.getMediaPlayer(file.toURI().toString());
                mediaPlayer.setOnPlaying(() -> publishState());
               mediaPlayer.setOnPaused(() -> publishState());
                mediaPlayer.setOnStopped(() -> publishState());
                publishState();
                logger.log(Level.INFO, "Media loaded: " + VIDEO_FILE_PATH + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
               return createPlayerState();
          }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
    }
    /**
     * Method to load the subtitle from the given file
     * @param file File containing the subtitle
      * @return Single list of Subtitle
      */
    @Override
    public Single<List<Subtitle>> loadSubtitle(File file) {
         return  Single.fromCallable(() -> {
             SubtitleParser subtitleParser = new SubtitleParser();
            if(Objects.nonNull(file)){
                try {
                  subtitles = subtitleParser.parseSubtitles(file);
                    logger.log(Level.INFO, "Subtitle loaded: " + file.getAbsolutePath() + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                     return subtitles;
                  } catch (IOException e) {
                     logger.log(Level.SEVERE, "Error parsing subtitle file: " + file.getAbsolutePath() + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    throw new SubtitleParseException("Error parsing subtitle file", e);
                    }
                } else {
                    logger.log(Level.SEVERE, "Subtitle file is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                     throw new SubtitleParseException("Subtitle file is null");
                 }
            }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
     }
     /**
       * Method to load the metadata from the given file
      * @param file File containing the metadata
         * @return Single of MediaMetadata
       */
        @Override
         public Single<MediaMetadata> loadMetadata(File file) {
           return  Single.fromCallable(() -> {
                 if(Objects.isNull(file)){
                   logger.log(Level.SEVERE, "Metadata file is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                     throw new MediaLoadException("Metadata file is null");
                }
                 logger.log(Level.INFO, "Metadata loading is not implemented: " + file.getAbsolutePath() + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                 return new MediaMetadata(null, null, null);
             }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
        }
       /**
        * Method to play the media
        * @return Single of PlayerState
       */
        @Override
         public Single<PlayerState> play() {
              return  Single.fromCallable(() -> {
                if(Objects.nonNull(mediaPlayer)){
                  mediaPlayer.play();
                    logger.log(Level.INFO, "Media Playing" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                   publishState();
                 } else {
                     logger.log(Level.SEVERE, "Media player is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                     throw new PlaybackException("Media Player is null");
                  }
                   return createPlayerState();
              }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
         }
         /**
          * Method to pause the media
        * @return Single of PlayerState
        */
       @Override
      public Single<PlayerState> pause() {
          return Single.fromCallable(() -> {
              if(Objects.nonNull(mediaPlayer)){
                   mediaPlayer.pause();
                 logger.log(Level.INFO, "Media Paused" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                   publishState();
                  } else {
                    logger.log(Level.SEVERE, "Media player is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                      throw new PlaybackException("Media Player is null");
                   }
                 return createPlayerState();
          }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
       }
         /**
         * Method to seek the media
         * @param value double value to seek
          * @return Single of PlayerState
        */
          @Override
        public Single<PlayerState> seek(double value) {
             return Single.fromCallable(() -> {
                 if(Objects.nonNull(mediaPlayer)){
                   mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(value));
                   logger.log(Level.INFO, "Media Seeked " + value + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                     publishState();
                   } else {
                    logger.log(Level.SEVERE, "Media player is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    throw new PlaybackException("Media Player is null");
                 }
                return createPlayerState();
          }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
         }
       /**
        * Method to seek forward in the media
        * @return Single of PlayerState
        */
         @Override
       public Single<PlayerState> seekForward() {
            return Single.fromCallable(() -> {
                  if(Objects.nonNull(mediaPlayer)){
                        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.millis(33.33)));
                     logger.log(Level.INFO, "Media Seeked Forward" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                       publishState();
                    } else {
                        logger.log(Level.SEVERE, "Media player is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                     throw new PlaybackException("Media Player is null");
                    }
                  return createPlayerState();
          }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
        }
        /**
        * Method to seek backward in the media
        * @return Single of PlayerState
        */
       @Override
       public Single<PlayerState> seekBackward() {
           return Single.fromCallable(() -> {
                 if(Objects.nonNull(mediaPlayer)){
                     mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(javafx.util.Duration.millis(33.33)));
                      logger.log(Level.INFO, "Media Seeked Backward" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                      publishState();
                 } else {
                       logger.log(Level.SEVERE, "Media player is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                     throw new PlaybackException("Media Player is null");
                    }
                   return createPlayerState();
            }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
      }
        /**
          * Method to set the playback rate of the media
         * @param rate double value of the playback rate
          * @return Single of PlayerState
         */
         @Override
       public Single<PlayerState> setRate(double rate) {
            return Single.fromCallable(() -> {
                  if(Objects.nonNull(mediaPlayer)){
                      mediaPlayer.setRate(rate);
                        logger.log(Level.INFO, "Media Rate changed to " + rate + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                       publishState();
                     } else {
                        logger.log(Level.SEVERE, "Media player is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                         throw new PlaybackException("Media Player is null");
                   }
                 return createPlayerState();
          }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
         }
         /**
           * Method to set the volume of the media
        * @param volume double value of the volume
          * @return Single of PlayerState
          */
         @Override
      public Single<PlayerState> setVolume(double volume) {
            return Single.fromCallable(() -> {
                   if(Objects.nonNull(mediaPlayer)){
                      mediaPlayer.setVolume(volume);
                      logger.log(Level.INFO, "Media Volume changed to " + volume + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        publishState();
                   } else {
                        logger.log(Level.SEVERE, "Media player is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                         throw new PlaybackException("Media Player is null");
                    }
                    return createPlayerState();
              }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
          }
       /**
           * Method to stop the media
        * @return Single of PlayerState
          */
        @Override
         public Single<PlayerState> stop() {
            return Single.fromCallable(() -> {
                  if(Objects.nonNull(mediaPlayer)){
                     mediaPlayer.stop();
                     mediaPlayerResourcePool.releaseMediaPlayer(mediaPlayer);
                        logger.log(Level.INFO, "MediaPlayer stopped and resources disposed" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                       publishState();
                    } else {
                         logger.log(Level.SEVERE, "Media player is null" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                         throw new PlaybackException("Media Player is null");
                   }
                    return createPlayerState();
              }).subscribeOn(Main.getThreadManager().getBackgroundScheduler());
        }
         /**
           * Observes the player state of the media player
           * @return Observable PlayerState
         */
       @Override
       public Observable<PlayerState> observePlayerState() {
              return playerStateSubject;
          }
        /**
         * Method to publish the current state of the media player
          */
        private void publishState() {
             if(Objects.nonNull(mediaPlayer)) {
                  double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                 double rate = mediaPlayer.getRate();
                  double volume = mediaPlayer.getVolume();
                    String subtitleText = null;
                    if(Objects.nonNull(subtitles)){
                       List<String> currentSubtitles = subtitles.stream()
                                    .filter(subtitle -> currentTime >= subtitle.startTime() && currentTime <= subtitle.endTime())
                                  .map(Subtitle::text)
                                  .toList();
                     subtitleText = String.join("\n", currentSubtitles);
                   }
                     playerStateSubject.onNext(new PlayerState(mediaPlayer.getStatus(), currentTime, volume, rate, subtitleText));
              }
       }
      /**
        * Method to create a PlayerState object
         * @return PlayerState instance
         */
         private PlayerState createPlayerState(){
             if(Objects.nonNull(mediaPlayer)) {
                  double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                   double rate = mediaPlayer.getRate();
                   double volume = mediaPlayer.getVolume();
                    String subtitleText = null;
                     if(Objects.nonNull(subtitles)){
                          List<String> currentSubtitles = subtitles.stream()
                                   .filter(subtitle -> currentTime >= subtitle.startTime() && currentTime <= subtitle.endTime())
                                    .map(Subtitle::text)
                                   .toList();
                        subtitleText = String.join("\n", currentSubtitles);
                   }
                  return new PlayerState(mediaPlayer.getStatus(), currentTime, volume, rate, subtitleText);
             } else {
                return new PlayerState(null, 0, 0, 1.0, null);
             }
         }
     }
    ```
  *  **Explanation**: Updated code to use the `MediaPlayerResourcePool` when fetching a mediaplayer resource and also releasing resources when stopping.

 * **`utils/SubtitleParser.java`**: Use a `try-with-resources` block for `BufferedReader`:
```java
    package com.example.videoplayer.framework.utils;

    import com.example.videoplayer.core.model.Subtitle;
    import java.io.BufferedReader;
    import java.io.File;
   import java.io.FileReader;
    import java.io.IOException;
  import java.util.ArrayList;
    import java.util.List;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;
   import java.util.logging.Level;
    import java.util.logging.Logger;
    /**
       * Class to parse subtitles from files
        */
   public class SubtitleParser {
       private static final Logger logger = Logger.getLogger(SubtitleParser.class.getName());
    private static final Pattern SUBTITLE_LINE_PATTERN = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) --> (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})");

       /**
        * Method to parse subtitles from file
      * @param file File to parse
       * @return List of Subtitle instances
        * @throws IOException IOException if there are any issues during parsing.
        */
       public List<Subtitle> parseSubtitles(File file) throws IOException {
          List<Subtitle> subtitles = new ArrayList<>();
             try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                 String line;
                while ((line = reader.readLine()) != null) {
                   Matcher matcher = SUBTITLE_LINE_PATTERN.matcher(line);
                      if (matcher.find()) {
                       double startTime = parseTime(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
                        double endTime = parseTime(matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8));
                        StringBuilder text = new StringBuilder();
                        String textLine;
                        while ((textLine = reader.readLine()) != null) {
                           if (textLine.trim().isEmpty()) {
                               break; // end of the subtitle block.
                             }
                           text.append(textLine).append("\n");
                       }
                        subtitles.add(new Subtitle(startTime, endTime, text.toString().trim()));
                  }
              }
           } catch (Exception e) {
                logger.log(Level.SEVERE, "Error parsing subtitle file: " + file.getAbsolutePath() + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber(), e);
                throw e;
          }
          return subtitles;
        }
         /**
          * Method to get the time from regex.
         * @param hours hours value
          * @param minutes minutes value
           * @param seconds second values
        * @param milliseconds milliseconds value
         * @return double value of the time.
        */
     private double parseTime(String hours, String minutes, String seconds, String milliseconds) {
             try {
                   int h = Integer.parseInt(hours);
                    int m = Integer.parseInt(minutes);
                 int s = Integer.parseInt(seconds);
                 int ms = Integer.parseInt(milliseconds);
                return h * 3600.0 + m * 60.0 + s + ms / 1000.0;
           } catch (NumberFormatException e){
                 logger.log(Level.SEVERE, "Invalid input to parseTime" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber(), e);
                throw e;
          }
     }
    }
