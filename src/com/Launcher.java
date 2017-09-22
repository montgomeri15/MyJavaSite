package com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Launcher extends Application {

    Label labelUser = new Label("Имя:");
    Label labelPass = new Label("Пароль:");
    Label labelSpace = new Label("     ");
    TextField textUser = new TextField();
    TextField textPass = new TextField();
    Button buttonStart = new Button("Start");
    Button buttonRegistration = new Button("Sign Up");

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Запуск Tomcat
        Process p =  Runtime.getRuntime().exec("cmd /c C:\\Users\\Admin\\Desktop\\MyJavaSite\\apache-tomcat-9.0.0.M26\\bin\\startup.bat", null, new File("C:\\Users\\Admin\\Desktop\\MyJavaSite\\apache-tomcat-9.0.0.M26"));
        p.isAlive();

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> Platform.exit());  //Аналог setDefaultCloseOperation() в Swing
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(20, 20, 20, 20));

        pane.add(labelUser, 0, 0);
        pane.add(textUser, 1, 0);
        pane.add(labelPass, 0, 1);
        pane.add(textPass, 1, 1);

        GridPane paneButton = new GridPane();
        paneButton.setAlignment(Pos.CENTER);
        paneButton.add(buttonStart,0, 0);
        paneButton.add(labelSpace, 1, 0);
        paneButton.add(buttonRegistration, 2, 0);


        GridPane paneAll = new GridPane();
        paneAll.setAlignment(Pos.CENTER);

        paneAll.add(pane, 0, 0);
        paneAll.add(paneButton, 0, 1);

        Scene scene = new Scene(paneAll, 290, 170);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonRegistration.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().browse(new URI("http://localhost:8080/reg.html"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
