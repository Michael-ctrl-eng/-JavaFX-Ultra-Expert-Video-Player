package com.example.videoplayer.ui.view;

 import com.example.videoplayer.adapter.presenter.MainPresenter;
 import javafx.scene.media.MediaView;

/**
  * Component to handle Media View events
   */
  public class MediaViewComponent {
     private final MediaView mediaView;
      /**
       * Constructor for media view class
     * @param presenter Application presenter
      */
      public MediaViewComponent(MainPresenter presenter) {
           this.mediaView = new MediaView();
          presenter.observePlayerState()
               .subscribe(playerState -> {
                      if(playerState.status() != null) {
                          if (playerState.status().ordinal() == 2) {
                             presenter.stop().blockingGet();
                       }
                     }
                    mediaView.setMediaPlayer(presenter.getMediaPlayer());
                 },
                  error -> {
                      // Do nothing for now.
                }
               );
          this.mediaView.setFitWidth(800);
         this.mediaView.setFitHeight(600);
          this.mediaView.setPreserveRatio(true);
         this.mediaView.setSmooth(true);
    }
     /**
      * Returns the MediaView instance
      * @return MediaView instance
     */
     public MediaView getMediaView() {
         return mediaView;
     }
}
