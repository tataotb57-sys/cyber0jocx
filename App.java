package com.mycompany.cyberjocx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        // ربط الملاح بالنافذة الأساسية
        Navigation.init(stage);

        Button btn = new Button("فتح صفحة About");
        btn.setOnAction(e -> new AboutPage().show());

        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("CyberJocx Main");
        Navigation.go(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
