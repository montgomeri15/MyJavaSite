package com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import test.Constructor;
import test.DbManager;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Launcher extends Application {

    DbManager db = new DbManager();
    public static Connection connection;

    Label labelUser = new Label("Имя:");
    Label labelPass = new Label("Пароль:");
    Label labelSpace = new Label("     ");
    TextField textUser = new TextField();
    PasswordField textPass = new PasswordField();
    Button buttonStart = new Button("Start");
    Button buttonRegistration = new Button("Sign Up");

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Запуск Tomcat
//        Process p =  Runtime.getRuntime().exec("cmd /c C:\\Users\\Admin\\Desktop\\MyJavaSite\\apache-tomcat-9.0.0.M26\\bin\\startup.bat", null, new File("C:\\Users\\Admin\\Desktop\\MyJavaSite\\apache-tomcat-9.0.0.M26"));
        //      p.isAlive();

        String login = textUser.getText();
        String password = textPass.getText();

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

        /***Подключение к БД***/
        connection = db.getConnection();

        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                /***Считывание таблицы***/
                List<Constructor> list = null;
                try {
                    list = db.readTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i<list.size(); i++){

                    String dbNames = list.get(i).getName().toString();
                    String dbPasswords = list.get(i).getPass().toString();

                    if (login.equals(dbNames) && password.equals(dbPasswords)){
                        try {
                            fightFrame();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else{
                        textPass.setText(null);
                        labelSpace.setTextFill(Color.web("#DC143C"));
                        labelSpace.setText("Неверный логин или пароль");
                    }
                }
            }
        });
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

    public void fightFrame() throws SQLException, ClassNotFoundException {

        Stage primaryStageFight = new Stage();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();  //Определяем разрешение экрана
        int screenWidth = (int) primaryScreenBounds.getWidth();  //Присваиваем ширину
        int screenHeight = (int) primaryScreenBounds.getHeight();  //Присваиваем высоту

        Button buttonHit = new Button("Hit");
        Button buttonBlock = new Button("Block");
        Button buttonNext = new Button("Next");

        ImageView imgDuoLonAvatar = new ImageView(getClass().getResource("/images/DuoLonHP.jpg").toExternalForm());
        ImageView imgSaikiAvatar = new ImageView(getClass().getResource("/images/SaikiHP.jpg").toExternalForm());
        ImageView imgDuoLon = new ImageView(getClass().getResource("/images/DuoLon.gif").toExternalForm());
        ImageView imgSpace = new ImageView(getClass().getResource("/images/spaceCharacters.png").toExternalForm());
        ImageView imgSaiki = new ImageView(getClass().getResource("/images/Saiki.gif").toExternalForm());

        GridPane paneAvatars = new GridPane();
        paneAvatars.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
        paneAvatars.setAlignment(Pos.CENTER);

        paneAvatars.add(imgDuoLonAvatar, 0, 0);
        paneAvatars.add(imgSaikiAvatar, 1, 0);

        GridPane paneFight = new GridPane();
        paneFight.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
        paneFight.setAlignment(Pos.CENTER);
        paneFight.setHgap(10);
        paneFight.setVgap(10);
        paneFight.setPadding(new Insets(20, 20, 20, 20));

        paneFight.add(imgDuoLon, 0, 0);
        paneFight.add(imgSpace, 1, 0);
        paneFight.add(imgSaiki, 2, 0);

        GridPane paneFightButtons = new GridPane();
        paneFightButtons.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
        paneFightButtons.setAlignment(Pos.CENTER);

        paneFightButtons.add(buttonHit, 0, 0);
        paneFightButtons.add(buttonBlock, 0, 1);
        paneFightButtons.add(buttonNext, 0, 2);

        GridPane paneAll = new GridPane();
        paneAll.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
        paneAll.setAlignment(Pos.CENTER);

        paneAll.add(paneAvatars, 0, 0);
        paneAll.add(paneFight, 0, 1);
        paneAll.add(paneFightButtons, 0, 2);

        primaryStageFight.setWidth(screenWidth);  //Задаем ширину
        primaryStageFight.setHeight(screenHeight);  //Задаем высоту

        Scene scene = new Scene(paneAll, screenWidth, screenHeight, Color.WHITE);

        primaryStageFight.setTitle("Game");
        primaryStageFight.setScene(scene);
        primaryStageFight.show();

        buttonHit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fightAction();
            }
        });
        buttonBlock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fightAction();
            }
        });
        buttonNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.print("Next Player");
            }
        });
    }

    public void fightAction(){

        Stage primaryStageFightAction = new Stage();

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioHead = new RadioButton("Head");
        RadioButton radioBody = new RadioButton("Body");
        RadioButton radioLegs = new RadioButton("Legs");

        radioHead.setToggleGroup(toggleGroup);
        radioBody.setToggleGroup(toggleGroup);
        radioLegs.setToggleGroup(toggleGroup);

        GridPane paneRadio = new GridPane();
        paneRadio.setAlignment(Pos.CENTER);
        paneRadio.setHgap(10);
        paneRadio.setVgap(10);
        paneRadio.setPadding(new Insets(20, 20, 20, 20));

        paneRadio.add(radioHead, 0, 0);
        paneRadio.add(radioBody, 0, 1);
        paneRadio.add(radioLegs, 0, 2);

        Scene scene = new Scene(paneRadio, 300, 300, Color.WHITE);

        primaryStageFightAction.setTitle("Choice");
        primaryStageFightAction.setScene(scene);
        primaryStageFightAction.show();
    }
}
