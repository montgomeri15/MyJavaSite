package com;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Launcher extends Application {

    Label label_user = new Label("Имя:");
    Label label_pass = new Label("Пароль:");
    TextField text_user = new TextField();
    TextField text_pass = new TextField();
    Button start = new Button("Гамать!");

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(label_user, 0, 0);
        pane.add(text_user, 1, 0);
        pane.add(label_pass, 0, 1);
        pane.add(text_pass, 1, 1);

        GridPane pane_button = new GridPane();
        pane_button.setAlignment(Pos.CENTER);
        pane_button.getChildren().addAll(start);

        GridPane pane_all = new GridPane();
        pane_all.setAlignment(Pos.CENTER);

        pane_all.add(pane, 0, 0);
        pane_all.add(pane_button, 0, 1);

        Scene scene = new Scene(pane_all, 300, 180);

        primaryStage.setTitle("WOW Legion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
