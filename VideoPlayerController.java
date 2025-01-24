import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VideoPlayerController {
    @FXML private MediaView mediaView;
    @FXML private ProgressBar progressBar;
    @FXML private Label currentTimeLabel;
    @FXML private Label totalTimeLabel;
    @FXML private Slider volumeSlider;
    @FXML private Button playButton;
    @FXML private HBox controlsContainer;
    @FXML private StackPane rootPane;

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private boolean controlsVisible = true;

    public void initialize() {
        setupMediaControls();
        setupKeyboardShortcuts();
        setupControlAnimations();
    }

    private void setupMediaControls() {
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null) mediaPlayer.setVolume(newVal.doubleValue() / 100);
        });
    }

    private void setupKeyboardShortcuts() {
        rootPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) togglePlayPause();
            if (event.getCode() == KeyCode.RIGHT) seek(5);
            if (event.getCode() == KeyCode.LEFT) seek(-5);
            if (event.getCode() == KeyCode.F) toggleFullscreen();
        });
    }

    private void setupControlAnimations() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), controlsContainer);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        
        rootPane.setOnMouseMoved(e -> {
            fadeTransition.stop();
            controlsContainer.setOpacity(1.0);
            fadeTransition.playFromStart();
        });
    }

    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv", "*.avi")
        );
        
        Stage stage = (Stage) rootPane.getScene().getWindow();
        java.io.File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            loadMedia(file.toURI().toString());
        }
    }

    private void loadMedia(String mediaPath) {
        try {
            if (mediaPlayer != null) mediaPlayer.dispose();
            
            Media media = new Media(mediaPath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
            mediaPlayer.currentTimeProperty().addListener((obs, oldVal, newVal) -> {
                progressBar.setProgress(newVal.toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
                currentTimeLabel.setText(formatTime(newVal));
            });
            
            mediaPlayer.setOnReady(() -> {
                totalTimeLabel.setText(formatTime(mediaPlayer.getTotalDuration()));
                mediaPlayer.play();
                isPlaying = true;
                updatePlayButton();
            });
            
            mediaPlayer.setOnError(() -> showError("Media Error", mediaPlayer.getError().getMessage()));
            
        } catch (Exception e) {
            showError("Invalid File", "Unsupported media format");
        }
    }

    @FXML
    private void togglePlayPause() {
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
            isPlaying = !isPlaying;
            updatePlayButton();
        }
    }

    @FXML
    private void handleProgressClick(MouseEvent event) {
        if (mediaPlayer != null) {
            double seekTime = (event.getX() / progressBar.getWidth()) * 
                            mediaPlayer.getTotalDuration().toSeconds();
            mediaPlayer.seek(Duration.seconds(seekTime));
        }
    }

    private void seek(int seconds) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(seconds)));
        }
    }

    private void toggleFullscreen() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    private void updatePlayButton() {
        playButton.setText(isPlaying ? "⏸" : "▶");
    }

    private String formatTime(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
