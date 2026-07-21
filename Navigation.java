package com.mycompany.cyberjocx;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Centralized Navigation
public class Navigation {

    private static Stage stage;

    public static void init(Stage s) {
        stage = s;
        
        if (stage != null) {
            stage.setWidth(1200);
            stage.setHeight(800);
            stage.setResizable(true); 

            // ) عشان المايفن ميعملش كراش  
            stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                if (stage != null && stage.getScene() != null && stage.getScene().getRoot() != null) {
                    Platform.runLater(() -> stage.getScene().getRoot().requestLayout());
                }
            });
            
            stage.heightProperty().addListener((obs, oldVal, newVal) -> {
                if (stage != null && stage.getScene() != null && stage.getScene().getRoot() != null) {
                    Platform.runLater(() -> stage.getScene().getRoot().requestLayout());
                }
            });
        }
    }

    public static void go(Scene scene) {

        // Protection against null stage
        if (stage == null) {
            throw new IllegalStateException("Stage not initialized!");
        }

        // Responsive Text Smooth
        scene.getRoot().setStyle(
                scene.getRoot().getStyle() +
                "-fx-font-smoothing-type: lcd;"
        );

        // 1. اربط الـ Scene بالـ Stage
        stage.setScene(scene);
        
        // 2. اسمح بالتكبير والتصغير
        stage.setResizable(true);

        // 3. اظهر الفريم لو مش ظاهر
        if (!stage.isShowing()) {
            stage.centerOnScreen();
            stage.show();
        }

        // 4. ريفرش فورى
        Platform.runLater(() -> {
            if (scene.getRoot() != null) {
                scene.getRoot().requestLayout();
            }
        });
    }

    public static Stage getStage() {
        return stage;
    }
}
