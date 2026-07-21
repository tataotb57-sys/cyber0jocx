/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cyberjocx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class ChatController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField inputField;

    @FXML
    private Button sendBtn;

    private AIController ai = new AIController();

    @FXML
    public void initialize() {
        sendBtn.setOnAction(e -> sendMessage());
        inputField.setOnAction(e -> sendMessage()); // Enter يبعث
    }

    private void sendMessage() {
        String userMsg = inputField.getText();

        if (userMsg.isEmpty()) return;

        chatArea.appendText("You: " + userMsg + "\n");

        inputField.clear();

        // ⚠️ أهم نقطة: متعملش request على الـ UI Thread
        new Thread(() -> {
            String response = ai.askAI(userMsg);

            // لازم ترجع للـ UI Thread
            javafx.application.Platform.runLater(() -> {
                chatArea.appendText(response + "\n\n");
            });
        }).start();
    }
}
