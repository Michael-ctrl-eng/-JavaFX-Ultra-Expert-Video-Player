package com.example.videoplayer.ui.view;

import com.example.videoplayer.adapter.presenter.MainPresenter;
import com.example.videoplayer.ui.utils.Constants;
import com.example.videoplayer.ui.utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javafx.scene.text.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Class to implement media control logic
 */
public class MediaControls {
    private final MainPresenter presenter;
    private final HBox controlPanel;
    private final Button playButton;
    private final Slider seekSlider;
    private final Slider volumeSlider;
    private final Button frameBackButton;
    private final Button frameForwardButton;
    private final Button speedDownButton;
    private final Button speedUpButton;
    private final Label speedValueLabel;
    private final CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Constructor for MediaControls class
     * @param presenter Application presenter
     */
    public MediaControls(MainPresenter presenter) {
        this.presenter = presenter;
        this.controlPanel = new HBox(10);
        this.controlPanel.setPadding(new Insets(10));
        this.controlPanel.setAlignment(Pos.CENTER);
        this.playButton = new Button("Play");
        this.seekSlider = new Slider();
        this.volumeSlider = new Slider(0,1,1);
        this.frameBackButton = new Button("<<");
        this.frameForwardButton = new Button(">>");
        this.speedDownButton = new Button("-");
        this.speedUpButton = new Button("+");
        this.speedValueLabel = new Label("1.0");
        speedValueLabel.setFont(Font.font(10));
        setupControls();
        styleControls();
        controlPanel.getChildren().addAll(playButton, seekSlider, volumeSlider, frameBackButton, frameForwardButton, speedDownButton, speedValueLabel, speedUpButton);
    }

    /**
     * Method to setup the controls of media player
     */
    private void setupControls() {
        playButton.setOnAction(e ->
             presenter.observePlayerState()
                    .take(1)
                    .subscribe(playerState -> {
                        if (playerState.status() != null && playerState.status().ordinal() == 2) {
                             presenter.pause()
                                        .subscribe(playerState1 ->  playButton.setText("Play"),
                                                    error -> UIHelper.showAlert("Error","Error occurred", "Error pausing video"));
                        } else {
                            presenter.play()
                                      .subscribe(playerState1 ->  playButton.setText("Pause"),
                                                  error -> UIHelper.showAlert("Error","Error occurred", "Error playing video"));
                         }
                     },
                                error -> UIHelper.showAlert("Error", "Error", "Error loading video")
                )
        );
        //seek slider logic
        disposables.add(presenter.observePlayerState()
                .subscribe(playerState -> {
                            if(!seekSlider.isValueChanging())
                                seekSlider.setValue(playerState.currentTime() / presenter.observePlayerState().blockingFirst().currentTime() * 100);
                        },
                        error -> {
                            UIHelper.showAlert("Error","Error occurred", "Error seeking video");
                        }
                )
        );
       seekSlider.valueProperty().addListener(observable -> {
          if(seekSlider.isValueChanging()){
                double value = seekSlider.getValue()/100;
                presenter.seek(value)
                        .subscribe(playerState -> {

                                 },
                            error -> {
                                 UIHelper.showAlert("Error", "Error", "Error seeking video");
                             }
                            );
             }
        });
        //volume slider logic
       volumeSlider.valueProperty().addListener(observable -> presenter.setVolume(volumeSlider.getValue())
              .subscribe(playerState -> {

                        },
                    error -> {
                         UIHelper.showAlert("Error", "Error", "Error setting volume");
                    }
            )
       );

        frameBackButton.setOnAction(e -> presenter.seekBackward()
                    .subscribe(playerState -> {

                             },
                        error -> {
                            UIHelper.showAlert("Error", "Error", "Error seeking backward");
                        }
                    )
         );
        frameForwardButton.setOnAction(e -> presenter.seekForward()
                 .subscribe(playerState -> {

                             },
                    error -> {
                         UIHelper.showAlert("Error", "Error", "Error seeking forward");
                    }
            )
        );
       speedDownButton.setOnAction(e -> {
            presenter.observePlayerState()
                    .take(1)
                    .subscribe(playerState -> {
                        double currentRate = playerState.rate();
                        double newRate = Math.max(Constants.MIN_PLAYBACK_RATE, currentRate - Constants.PLAYBACK_RATE_STEP);
                       presenter.setRate(newRate)
                                .subscribe(playerState1 -> updateSpeedLabel(newRate),
                                error -> UIHelper.showAlert("Error", "Error", "Error setting rate")
                                );
                     });
        });
       speedUpButton.setOnAction(e -> {
            presenter.observePlayerState()
                    .take(1)
                    .subscribe(playerState -> {
                        double currentRate = playerState.rate();
                        double newRate = Math.min(Constants.MAX_PLAYBACK_RATE, currentRate + Constants.PLAYBACK_RATE_STEP);
                        presenter.setRate(newRate)
                                .subscribe(playerState1 -> updateSpeedLabel(newRate),
                                        error -> UIHelper.showAlert("Error", "Error", "Error setting rate")
                                );
                    });
        });
    }

    /**
     * Styles the controls
     */
    private void styleControls() {
         seekSlider.setPrefWidth(400);
        volumeSlider.setPrefWidth(150);
        seekSlider.getStyleClass().add("slider-style");
        volumeSlider.getStyleClass().add("slider-style");
        playButton.getStyleClass().add("button-style");
        frameBackButton.getStyleClass().add("button-style");
        frameForwardButton.getStyleClass().add("button-style");
       speedDownButton.getStyleClass().add("button-style");
        speedUpButton.getStyleClass().add("button-style");
    }

   /**
     * Update the speed label of the media player
     * @param rate Double value of the media speed
     */
    private void updateSpeedLabel(double rate) {
        NumberFormat df = new DecimalFormat("#.##");
        speedValueLabel.setText(df.format(rate));
   }
   /**
     * Gets the control panel of the media player
    * @return HBox containing the controls
   */
    public HBox getControlPanel() {
         return controlPanel;
    }
    /**
      * Disposes the observable subscriptions of the control view.
    */
   public void dispose(){
        disposables.dispose();
   }
}
