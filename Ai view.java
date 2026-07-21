package com.mycompany.cyberjocx;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class AIView {

    private final AIController aiController = new AIController();
    private final VBox chatBox = new VBox(18); // زيادة المسافة بين الرسائل لراحة العين

    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #04060A, #090D16, #06090F);");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.85;
        double height = screenBounds.getHeight() * 0.85;

        // ================= TOP HEADER =================
        Button backBtn = new Button("← Back to Control Center");
        backBtn.setCursor(javafx.scene.Cursor.HAND);
        backBtn.setPadding(new Insets(10, 16, 10, 16));
        backBtn.setStyle(
                "-fx-background-color: rgba(0, 255, 255, 0.08);" +
                "-fx-text-fill: #00FFFF;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(0, 255, 255, 0.3);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;"
        );
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.2); -fx-text-fill: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #00FFFF; -fx-border-width: 1; -fx-border-radius: 8;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.08); -fx-text-fill: #00FFFF; -fx-background-radius: 8; -fx-border-color: rgba(0, 255, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 8;"));
        backBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));

        Label title = new Label("AI CYBER ASSISTANT");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#00FFFF"));
        title.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.15), 8, 0, 0, 0); -fx-letter-spacing: 1.2;");

        HBox topBar = new HBox(25, backBtn, title);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(30, 50, 15, 50));
        root.setTop(topBar);

        // ================= CENTER CHAT AREA =================
        chatBox.setAlignment(Pos.TOP_CENTER);
        chatBox.setPadding(new Insets(10, 5, 10, 5));
        chatBox.setStyle("-fx-background-color: transparent;");
        
        ScrollPane scrollPane = new ScrollPane(chatBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );
        
        chatBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setVvalue(1.0d);
        });

        // ================= INPUT AREA =================
        TextField inputField = new TextField();
        inputField.setPromptText("Ask me about vulnerabilities, exploits, or asset analysis...");
        inputField.setStyle(
                "-fx-background-color: rgba(22, 27, 34, 0.7);" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: rgba(48, 54, 61, 0.5);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10;" +
                "-fx-padding: 12 16 12 16;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 14px;"
        );
        inputField.setPrefHeight(48);
        HBox.setHgrow(inputField, Priority.ALWAYS);

        Button sendBtn = new Button("Deploy Message");
        sendBtn.setCursor(javafx.scene.Cursor.HAND);
        sendBtn.setPrefHeight(48);
        sendBtn.setPrefWidth(140);
        
        String defSendStyle = 
            "-fx-background-color: rgba(0, 255, 204, 0.1);" +
            "-fx-text-fill: #00FFCC;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: rgba(0, 255, 204, 0.3);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 10;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;";

        String hvrSendStyle = 
            "-fx-background-color: #00FFCC;" +
            "-fx-text-fill: #000000;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #00FFCC;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 10;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,255,204,0.2), 8, 0, 0, 0);";

        sendBtn.setStyle(defSendStyle);
        sendBtn.setOnMouseEntered(e -> {
            sendBtn.setStyle(hvrSendStyle);
            sendBtn.setScaleX(1.02);
            sendBtn.setScaleY(1.02);
        });
        sendBtn.setOnMouseExited(e -> {
            sendBtn.setStyle(defSendStyle);
            sendBtn.setScaleX(1.0);
            sendBtn.setScaleY(1.0);
        });

        HBox inputBoxLayout = new HBox(15, inputField, sendBtn);
        inputBoxLayout.setAlignment(Pos.CENTER);
        inputBoxLayout.setPadding(new Insets(20, 0, 0, 0));

        // تجميع عناصر الشات في حاوية مركزية ذات عرض محدد ومريح (Glassmorphic Main Container)
        VBox mainContent = new VBox(10, scrollPane, inputBoxLayout);
        mainContent.setMaxWidth(950);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        StackPane centerStack = new StackPane(mainContent);
        centerStack.setPadding(new Insets(10, 50, 40, 50));
        
        root.setCenter(centerStack);

        // ================= ACTIONS =================
        sendBtn.setOnAction(e -> sendMessage(inputField));
        inputField.setOnAction(e -> sendMessage(inputField));

        return new Scene(root, width, height);
    }

    private void sendMessage(TextField input) {
        String text = input.getText().trim();
        if (text.isEmpty()) return;

        input.clear();
        addMessage(text, true);

        Label loadingLabel = addMessage(" [⚡] Processing threat vectors... Analyzing...", false);

        new Thread(() -> {
            String response = aiController.askAI(text);
            Platform.runLater(() -> {
                // إزالة مؤشر التحميل
                chatBox.getChildren().removeIf(node -> {
                    if (node instanceof HBox) {
                        HBox hb = (HBox) node;
                        return hb.getChildren().contains(loadingLabel);
                    }
                    return false;
                });
                addMessage(response, false);
            });
        }).start();
    }

    private Label addMessage(String text, boolean isUser) {
        Label msg = new Label(text);
        msg.setWrapText(true);
        msg.setMaxWidth(620); 
        msg.setPadding(new Insets(14, 18, 14, 18));
        
        HBox box = new HBox(msg);
        box.setPadding(new Insets(4, 0, 4, 0));

        if (isUser) {
            msg.setFont(Font.font("Segoe UI", 14));
            msg.setStyle(
                    "-fx-background-color: rgba(0, 210, 255, 0.08);" +
                    "-fx-text-fill: #00D2FF;" +
                    "-fx-background-radius: 14 14 2 14;" +
                    "-fx-border-color: rgba(0, 210, 255, 0.2);" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 14 14 2 14;"
            );
            box.setAlignment(Pos.CENTER_RIGHT);
        } else {
            msg.setFont(Font.font("Consolas", 14.5));
            msg.setStyle(
                    "-fx-background-color: rgba(13, 17, 23, 0.75);" +
                    "-fx-text-fill: #00FFCC;" +
                    "-fx-background-radius: 14 14 14 2;" +
                    "-fx-border-color: rgba(0, 255, 204, 0.1);" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 14 14 14 2;"
            );
            box.setAlignment(Pos.CENTER_LEFT);
        }

        chatBox.getChildren().add(box);
        return msg;
    }
}
