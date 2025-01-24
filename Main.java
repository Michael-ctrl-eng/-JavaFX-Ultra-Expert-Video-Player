java
package com.example.videoplayer;

import com.example.videoplayer.adapter.impl.MainPresenterImpl;
import com.example.videoplayer.adapter.presenter.MainPresenter;
import com.example.videoplayer.framework.impl.JavaFXMediaPlayer;
import com.example.videoplayer.framework.utils.ThreadManager;
import com.example.videoplayer.ui.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class that starts the JavaFX application
 */
public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static ThreadManager threadManager;
    /**
     *  Starts the javaFX application
     * @param stage Main application stage
     */
    @Override
    public void start(Stage stage) {
        try {
             // Initialize the ThreadManager
            threadManager = new ThreadManager();
            // Create the main view (using dependency injection for the Controller)
            MainPresenter presenter = new MainPresenterImpl(new JavaFXMediaPlayer(), new JavaFXMediaPlayer());
            MainView mainView = new MainView(presenter);
            mainView.show(stage);

            logger.log(Level.INFO,"Video Player application started successfully.");

        } catch (Exception e) {
             logger.log(Level.SEVERE, "Failed to start application.", e);
            System.err.println("Failed to start application: " + e.getMessage());
         }
   }

   /**
     * Main method
     * @param args Arguments
    */
    public static void main(String[] args) {
        launch();
   }

    /**
     *  Handles close event of application
    * @throws Exception Exception
     */
    @Override
    public void stop() throws Exception{
        super.stop();
         threadManager.shutdown();
         logger.log(Level.INFO,"Application stopped");
     }
    /**
      * Get thread manager to be used by the application
     * @return ThreadManager instance
      */
    public static ThreadManager getThreadManager(){
         return threadManager;
   }
