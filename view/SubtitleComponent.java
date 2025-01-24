package com.example.videoplayer.ui.view;

import com.example.videoplayer.adapter.presenter.MainPresenter;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Objects;

/**
 * Class to handle subtitles of the media player
 */
public class SubtitleComponent {
    private final MainPresenter presenter;
    private final StackPane subtitleView;
    private final Text subtitleText;

    /**
     * Constructor of the SubtitleComponent class
     * @param presenter Application presenter
     */
    public SubtitleComponent(MainPresenter presenter){
        this.presenter = presenter;
        this.subtitleView = new StackPane();
        subtitleView.setAlignment(Pos.BOTTOM_CENTER);
        subtitleText = new Text();
        subtitleText.setFont(Font.font(20));
        subtitleText.setFill(Color.WHITE);
        subtitleView.getChildren().add(subtitleText);
        setupSubtitles();
    }

    /**
     * Setup subtitles for the component
     */
    private void setupSubtitles(){
          presenter.observePlayerState()
                .subscribe(playerState -> {
                            if(Objects.nonNull(playerState.subtitleText())) {
                                subtitleText.setText(playerState.subtitleText());
                            } else{
                                subtitleText.setText("");
                            }
                        },
                        error -> {
                            //Logging
                        }
                );
    }

    /**
     * Get the subtitle view
     * @return StackPane
     */
    public StackPane getSubtitleView(){
        return subtitleView;
    }
}
