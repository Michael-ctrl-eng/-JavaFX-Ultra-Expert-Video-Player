import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VideoPlayer extends Application {

    private MediaPlayer mediaPlayer;
    private Media media;
    private MediaView mediaView;
    private Slider volumeSlider;
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private Button rewindButton;
    private Button fastForwardButton;
    private Button fullScreenButton;
    private Button closeButton;

    @Override
    public void start(Stage primaryStage) {
        // Create the media player
        media = new Media("path/to/your/video.mp4");
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        // Create the UI components
        VBox root = new VBox();
        root.getChildren().addAll(mediaView, createControlPanel());

        // Set the scene and show the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize the media player
        mediaPlayer.play();
    }

    private VBox createControlPanel() {
        VBox controlPanel = new VBox();

        // Create the play button
        playButton = new Button("Play");
        playButton.setOnAction(event -> {
            mediaPlayer.play();
        });

        // Create the pause button
        pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> {
            mediaPlayer.pause();
        });

        // Create the stop button
        stopButton = new Button("Stop");
        stopButton.setOnAction(event -> {
            mediaPlayer.stop();
        });

        // Create the rewind button
        rewindButton = new Button("Rewind");
        rewindButton.setOnAction(event -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());
        });

        // Create the fast forward button
        fastForwardButton = new Button("Fast Forward");
        fastForwardButton.setOnAction(event -> {
            mediaPlayer.seek(mediaPlayer.getMedia().getDuration() - 10);
        });

        // Create the full screen button
        fullScreenButton = new Button("Full Screen");
        fullScreenButton.setOnAction(event -> {
            // TO DO: Implement full screen functionality
        });

        // Create the close button
        closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            System.exit(0);
        });

        // Add the buttons to the control panel
        controlPanel.getChildren().addAll(playButton, pauseButton, stopButton, rewindButton, fastForwardButton, fullScreenButton, closeButton);

        return controlPanel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
