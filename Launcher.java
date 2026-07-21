package com.mycompany.cyberjocx;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    // ================= APP SIZE =================
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage stage) { 

        Navigation.init(stage);

        stage.setTitle("Cyber JOCX - Security Framework");

        // 🛑 منع تغيير حجم الشاشة من قبل المستخدم
        stage.setResizable(false); 

        // Minimum Size
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);

        // Default Start Size
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        LoginView login = new LoginView();

        Navigation.go(login.getScene());
    }

    public static void main(String[] args) {
        System.setProperty("glass.win.uiScale", "100%");         
        System.setProperty("glass.gtk.uiScale", "1.0");

        launch(args);
    }
}
