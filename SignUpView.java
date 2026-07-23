package com.mycompany.cyberjocx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class SignUpView {

    private final LoginService loginService = new LoginService();

    public Scene getScene() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #04060A, #090D16, #06090F);");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.85;
        double height = screenBounds.getHeight() * 0.85;

        VBox cardContainer = new VBox(22);
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setMaxWidth(420);
        cardContainer.setMaxHeight(580);
        cardContainer.setPadding(new Insets(40, 45, 40, 45));
        
        cardContainer.setStyle(
                "-fx-background-color: rgba(13, 17, 23, 0.65);" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: rgba(0, 255, 204, 0.15);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 16;"
        );
        cardContainer.setEffect(new DropShadow(30, Color.rgb(0, 0, 0, 0.6)));

        // ================= TITLE =================
        Label title = new Label("CREATE IDENTITY");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#FFFFFF"));
        title.setStyle("-fx-letter-spacing: 2; -fx-effect: dropshadow(three-pass-box, rgba(0,255,204,0.15), 10, 0, 0, 0);");

        Label subtitle = new Label("Register your credentials into CyberNexus core");
        subtitle.setTextFill(Color.web("#6E7681"));
        subtitle.setFont(Font.font("Segoe UI", 12));
        subtitle.setPadding(new Insets(-10, 0, 10, 0));

        // ================= INPUT FIELDS =================
        TextField userField = new TextField();
        userField.setPromptText("Username");
        styleInputField(userField);

        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        styleInputField(emailField);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Secure Password");
        styleInputField(passField);

        // ================= STATUS LABEL =================
        Label status = new Label();
        status.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        status.setMinHeight(20); 

        // ================= SIGN UP BUTTON (NEON IMMERSIVE) =================
        Button registerBtn = new Button("INITIALIZE ACCOUNT");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setPrefHeight(45);
        registerBtn.setCursor(javafx.scene.Cursor.HAND);

        String defRegisterStyle = 
            "-fx-background-color: rgba(0, 255, 136, 0.1);" +
            "-fx-text-fill: #00FF88;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: rgba(0, 255, 136, 0.3);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;";

        String hvrRegisterStyle = 
            "-fx-background-color: #00FF88;" +
            "-fx-text-fill: #000000;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #00FF88;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,255,136,0.3), 10, 0, 0, 0);";

        registerBtn.setStyle(defRegisterStyle);
        registerBtn.setOnMouseEntered(e -> {
            registerBtn.setStyle(hvrRegisterStyle);
            registerBtn.setScaleX(1.01);
        });
        registerBtn.setOnMouseExited(e -> {
            registerBtn.setStyle(defRegisterStyle);
            registerBtn.setScaleX(1.0);
        });

        // ================= BACK BUTTON =================
        Button backBtn = new Button("← Back to Authorization Center");
        backBtn.setMaxWidth(Double.MAX_VALUE);
        backBtn.setPrefHeight(42);
        backBtn.setCursor(javafx.scene.Cursor.HAND);
        
        String defBackStyle = 
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #6E7681;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;";

        String hvrBackStyle = 
            "-fx-background-color: rgba(255, 255, 255, 0.03);" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-background-radius: 8;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;";

        backBtn.setStyle(defBackStyle);
        backBtn.setOnMouseEntered(e -> backBtn.setStyle(hvrBackStyle));
        backBtn.setOnMouseExited(e -> backBtn.setStyle(defBackStyle));

        // ================= LOGIC ACTIONS =================
        registerBtn.setOnAction(e -> {
            String u = userField.getText().trim();
            String em = emailField.getText().trim();
            String p = passField.getText().trim();

            if (u.length() < 4) {
                status.setText("⚠ Username must be at least 4 characters");
                status.setTextFill(Color.web("#FF9F43"));
                return;
            }

            if (!em.contains("@") || !em.contains(".")) {
                status.setText("⚠ Please enter a valid email architecture");
                status.setTextFill(Color.web("#FF9F43"));
                return;
            }

            if (p.length() < 6) {
                status.setText("⚠ Password entropy weak (Min 6 characters)");
                status.setTextFill(Color.web("#FF9F43"));
                return;
            }

            if (loginService.register(u, em, p)) {
                status.setText("✓ Identity Registered Successfully. Diverting...");
                status.setTextFill(Color.web("#00FF88"));
                
                Navigation.go(new LoginView().getScene());
            } else {
                status.setText("❌ Identity replication error. Registration failed.");
                status.setTextFill(Color.web("#FF5252"));
            }
        });

        backBtn.setOnAction(e -> Navigation.go(new LoginView().getScene()));

        cardContainer.getChildren().addAll(
                title, subtitle, 
                userField, emailField, passField, 
                status, registerBtn, backBtn
        );

        root.getChildren().add(cardContainer);
        return new Scene(root, width, height);
    }

    private void styleInputField(TextField field) {
        field.setMaxWidth(Double.MAX_VALUE);
        field.setPrefHeight(42);
        
        String defaultStyle = 
            "-fx-background-color: rgba(22, 27, 34, 0.7);" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: rgba(48, 54, 61, 0.6);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 0 12 0 12;" +
            "-fx-font-size: 13.5px;";

        String focusStyle = 
            "-fx-background-color: rgba(22, 27, 34, 0.9);" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #00FFCC;" +
            "-fx-border-width: 1.2;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 0 12 0 12;" +
            "-fx-font-size: 13.5px;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,255,204,0.1), 5, 0, 0, 0);";

        field.setStyle(defaultStyle);
        
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle(focusStyle);
            } else {
                field.setStyle(defaultStyle);
            }
        });
    }
}
