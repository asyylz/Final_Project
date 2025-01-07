package com.asiye.eventhandler;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        Label label = new Label("Click Button");
        Button button = new Button("Click Button");
        //button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> label.setText("Thank you for clicking the button"));
        button.setOnAction(event -> {label.setText("Thank you for clicking button");});

        root.getChildren().addAll(label, button);
        Scene scene = new Scene(root, 550, 500);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();

    }
}
