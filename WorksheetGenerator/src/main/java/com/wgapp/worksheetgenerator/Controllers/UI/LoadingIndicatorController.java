package com.wgapp.worksheetgenerator.Controllers.UI;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.animation.KeyValue;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingIndicatorController implements Initializable {

    public Circle outerCircle;
    public Circle innerCircle;
    public StackPane wrapperPaneForLoadingIndicator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Pulse Animation for Outer Circle
        ScaleTransition pulseAnimation = new ScaleTransition(Duration.seconds(1), outerCircle);
        pulseAnimation.setFromX(1);
        pulseAnimation.setFromY(1);
        pulseAnimation.setToX(1.2);
        pulseAnimation.setToY(1.2);
        pulseAnimation.setAutoReverse(true);
        pulseAnimation.setCycleCount(ScaleTransition.INDEFINITE);

        // Scale-Up Animation for Inner Circle
        Timeline scaleUpAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(innerCircle.scaleXProperty(), 0),
                        new KeyValue(innerCircle.scaleYProperty(), 0)
                ),
                new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(innerCircle.scaleXProperty(), 1),
                        new KeyValue(innerCircle.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(innerCircle.scaleXProperty(), 1),
                        new KeyValue(innerCircle.scaleYProperty(), 1)
                )
        );
        scaleUpAnimation.setCycleCount(Timeline.INDEFINITE);

        // Start Animations
        pulseAnimation.play();
        scaleUpAnimation.play();
    }
}
