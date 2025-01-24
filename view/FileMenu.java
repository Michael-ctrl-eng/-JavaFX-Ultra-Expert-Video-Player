     package com.example.videoplayer.ui.view;

     import com.example.videoplayer.adapter.presenter.MainPresenter;
         import com.example.videoplayer.ui.utils.UIHelper;
        import javafx.scene.control.Menu;
       import javafx.scene.control.MenuBar;
      import javafx.scene.control.MenuItem;
       import javafx.stage.FileChooser;
      import java.io.File;
       import java.util.Objects;

         /**
          * Class to represent the File Menu of the application
           */
          public class FileMenu {
           private final MenuBar menuBar;
           /**
               * Constructor for file menu
              * @param presenter Application presenter
          */
          public FileMenu(MainPresenter presenter) {
                this.menuBar = new MenuBar();
                //File Menu
                 Menu fileMenu = new Menu("File");
                MenuItem openFile = new MenuItem("Open");
              openFile.setId("openFile");
              openFile.setOnAction(e -> {
                     FileChooser fileChooser = new FileChooser();
                     fileChooser.setTitle("Open Video File");
                    File file = fileChooser.showOpenDialog(null);
                     if(Objects.nonNull(file)){
                       presenter.loadMedia(file)
                                 .subscribe(playerState -> {

                                           },
                                           error -> {
                                               UIHelper.showAlert("Error","Error occurred", "Error loading media");
                                          });
                       }
               });
                MenuItem openSubtitle = new MenuItem("Open Subtitle");
                openSubtitle.setId("openSubtitle");
                 openSubtitle.setOnAction(e -> {
                       FileChooser fileChooser = new FileChooser();
                      fileChooser.setTitle("Open Subtitle File");
                       File file = fileChooser.showOpenDialog(null);
                       if(Objects.nonNull(file)){
                          presenter.loadSubtitle(file)
                                 .subscribe(subtitles -> {

                                     },
                                        error -> {
                                           UIHelper.showAlert("Error","Error occurred", "Error loading subtitle");
                                     }
                              );
                    }
             });
              fileMenu.getItems().addAll(openFile, openSubtitle);
             menuBar.getMenus().add(fileMenu);
       }
        /**
           * Method to get the menu bar
         * @return MenuBar instance
         */
       public MenuBar getMenu(){
             return menuBar;
       }
  }
