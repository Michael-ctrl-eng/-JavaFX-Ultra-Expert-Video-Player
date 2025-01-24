package com.example.videoplayer.ui;

import com.example.videoplayer.Main;
import com.example.videoplayer.adapter.impl.MainPresenterImpl;
import com.example.videoplayer.adapter.presenter.MainPresenter;
import com.example.videoplayer.framework.impl.JavaFXMediaPlayer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.MenuItemMatchers;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;


@ExtendWith(ApplicationExtension.class)
public class MainViewTest {

    private Button playButton;
    private Slider seekSlider;
    private MenuItem openFile;
    private MenuItem openSubtitle;
    private MainPresenter presenter;


    @BeforeAll
    static void setup() {
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
    }
    /**
     * Start the JavaFX application
     * @param stage The main stage of the application.
     */
    @Start
    private void start(Stage stage) {
        presenter = new MainPresenterImpl(new JavaFXMediaPlayer(), new JavaFXMediaPlayer());
         Main main = new Main();
         main.start(stage);
        Scene scene = stage.getScene();
         playButton = (Button) scene.lookup("#playButton");
         seekSlider = (Slider) scene.lookup("#seekSlider");
          openFile = (MenuItem) scene.lookup("#openFile");
          openSubtitle = (MenuItem) scene.lookup("#openSubtitle");
     }


    @BeforeEach
    void setUp(FxRobot robot){
         File file = new File("sample.mp4");
         presenter.loadMedia(file).blockingGet();
     }
    /**
     * Clean up resources and close the test.
     * @param robot The testing Robot
     */
     @AfterEach
    void cleanup(FxRobot robot) {
        robot.release(new int[0]);
          presenter.stop();
    }

    @Test
    void testPlayButton(FxRobot robot){
        verifyThat(playButton, LabeledMatchers.hasText("Play"));
         robot.clickOn(playButton);
        verifyThat(playButton, LabeledMatchers.hasText("Pause"));
        robot.clickOn(playButton);
         verifyThat(playButton, LabeledMatchers.hasText("Play"));
     }
    @Test
    void testSeekSlider(FxRobot robot){
        robot.drag(seekSlider,100,0);
        assertNotEquals(presenter.observePlayerState().blockingFirst().currentTime(), 0);
   }
    @Test
     void testOpenFile(FxRobot robot){
          robot.clickOn("File");
           robot.clickOn(MenuItemMatchers.hasText("Open"));
        //Assert that the dialog opens.
    }
   @Test
     void testOpenSubtitle(FxRobot robot){
         robot.clickOn("File");
         robot.clickOn(MenuItemMatchers.hasText("Open Subtitle"));
          //Assert that the dialog opens.
    }

    @Test
    void testExceptionOnInvalidFile(FxRobot robot){
       File file = new File("sample.txt");
        presenter.loadMedia(file).subscribe(playerState -> {},
                 error -> {
                    assertNotNull(error);
                    assertNotEquals(error.getMessage(), "");
               }
        );
     }
 }
