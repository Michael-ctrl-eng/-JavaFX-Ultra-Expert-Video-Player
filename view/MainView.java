  package com.example.videoplayer.ui.view;

   import com.example.videoplayer.adapter.presenter.MainPresenter;
   import com.example.videoplayer.ui.utils.UIHelper;
    import javafx.scene.Scene;
  import javafx.scene.layout.BorderPane;
   import javafx.stage.Stage;

   /**
     * Main view class that represents the user interface
    */
 public class MainView {
    private final MainPresenter presenter;
     private final MediaControls controls;
     private final MediaViewComponent mediaViewComponent;
      private final FileMenu fileMenu;
     private final SubtitleComponent subtitleComponent;

     /**
        * Constructor of class
         * @param presenter Application presenter
      */
      public MainView(MainPresenter presenter) {
           this.presenter = presenter;
          this.mediaViewComponent = new MediaViewComponent(presenter);
          this.controls = new MediaControls(presenter);
         this.fileMenu = new FileMenu(presenter);
           this.subtitleComponent = new SubtitleComponent(presenter);

         // Handle the media load errors
         presenter.observePlayerState().subscribe(playerState -> {

                      },
              error -> UIHelper.showAlert("Error","Error occurred", "Error Loading Media")
         );

       }
    /**
       * Sets up the layout and shows the stage
        * @param stage Main application stage
       */
     public void show(Stage stage) {
        BorderPane root = new BorderPane();
          root.setCenter(mediaViewComponent.getMediaView());
          root.setBottom(controls.getControlPanel());
           root.setTop(fileMenu.getMenu());
          root.getChildren().add(subtitleComponent.getSubtitleView());
           Scene scene = new Scene(root, 800, 600);
          scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
          stage.setTitle("Expert Video Player");
          stage.setScene(scene);
          stage.show();
           // Set actions to close window
          stage.setOnCloseRequest(e -> presenter.stop());
    }
  }
