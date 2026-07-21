package com.mycompany.cyberjocx;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class LoginView {

    private final LoginService loginService =
            new LoginService();

    public Scene getScene() {

        VBox root = new VBox(20);

        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #0f172a, #020617);" +
                "-fx-padding: 40;"
        );

        root.setAlignment(Pos.CENTER);

        // ================= LOGO =================

        Text cChar = new Text("C");
        cChar.setFill(Color.web("#00d2ff"));

        Text yberJo = new Text("YBER J");
        yberJo.setFill(Color.WHITE);

        Text cxChars = new Text("OCX");
        cxChars.setFill(Color.web("#00d2ff"));

        Font logoFont = Font.font("Arial", FontWeight.BOLD, 38);

        cChar.setFont(logoFont);
        yberJo.setFont(logoFont);
        cxChars.setFont(logoFont);

        TextFlow logo = new TextFlow(cChar, yberJo, cxChars);

        logo.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Glow Effect
        Glow glow = new Glow(0.8);

        logo.setEffect(glow);

        FadeTransition glowAnim =
                new FadeTransition(Duration.seconds(1.5), logo);

        glowAnim.setFromValue(0.6);
        glowAnim.setToValue(1);

        glowAnim.setCycleCount(Animation.INDEFINITE);

        glowAnim.setAutoReverse(true);

        glowAnim.play();

        // ================= LOGIN BOX =================

        VBox loginBox = new VBox(15);

        loginBox.setAlignment(Pos.CENTER);

        loginBox.setMaxWidth(350);

        loginBox.setPadding(new Insets(35));

        loginBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.06);" +
                "-fx-background-radius: 25;" +
                "-fx-border-color: rgba(0,210,255,0.25);" +
                "-fx-border-radius: 25;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,210,255,0.2), 25, 0.2, 0, 0);"
        );

        // Animation
        ScaleTransition scale =
                new ScaleTransition(Duration.seconds(2), loginBox);

        scale.setFromX(1);
        scale.setFromY(1);

        scale.setToX(1.02);
        scale.setToY(1.02);

        scale.setAutoReverse(true);

        scale.setCycleCount(Animation.INDEFINITE);

        scale.play();

        // ================= FIELDS =================

        TextField userField = new TextField();

        userField.setPromptText("username");

        PasswordField passField =
                new PasswordField();

        passField.setPromptText("password");

        String fieldStyle =
                "-fx-background-color: rgba(255,255,255,0.08);" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: #7c8594;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-border-color: rgba(0,210,255,0.15);" +
                "-fx-padding: 12;" +
                "-fx-font-size: 14px;";

        userField.setStyle(fieldStyle);
        passField.setStyle(fieldStyle);

        userField.setPrefWidth(300);
        passField.setPrefWidth(300);

        // ================= BUTTONS =================

        Button loginBtn = new Button("LOGIN");

        loginBtn.setPrefWidth(300);

        loginBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #00d2ff, #3a7bd5);" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 15;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 14px;"
        );

        Button signUpBtn = new Button("CREATE ACCOUNT");

        signUpBtn.setPrefWidth(300);

        signUpBtn.setStyle(
                "-fx-background-color: rgba(255,255,255,0.08);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: rgba(255,255,255,0.08);" +
                "-fx-border-radius: 15;" +
                "-fx-cursor: hand;"
        );

        // ================= STATUS =================

        Label statusLabel = new Label("READY");

        statusLabel.setTextFill(
                Color.web("#00d2ff")
        );

        statusLabel.setFont(
                Font.font("Consolas", FontWeight.BOLD, 13)
        );

        // ================= ACTIONS =================

        loginBtn.setOnAction(e -> {

            String u =
                    userField.getText().trim();

            String p =
                    passField.getText().trim();

            if (u.isEmpty() || p.isEmpty()) {

                statusLabel.setText(
                        "Please fill all fields"
                );

                statusLabel.setTextFill(
                        Color.ORANGE
                );

                return;
            }

            if (loginService.login(u, p)) {

                statusLabel.setText(
                        "ACCESS GRANTED"
                );

                statusLabel.setTextFill(
                        Color.LIME
                );

                userField.clear();
                passField.clear();

                HomeDashboard dashboard =
                        new HomeDashboard();

                Navigation.go(
                        dashboard.getScene()
                );

            } else {

                statusLabel.setText(
                        "ACCESS DENIED"
                );

                statusLabel.setTextFill(
                        Color.RED
                );
            }
        });

        signUpBtn.setOnAction(e -> {

            SignUpView signUp =
                    new SignUpView();

            Navigation.go(
                    signUp.getScene()
            );
        });

        loginBox.getChildren().addAll(
                statusLabel,
                userField,
                passField,
                loginBtn,
                signUpBtn
        );

        // ================= CYBER BACKGROUND =================

        Line line1 = new Line(0, 0, 350, 350);

        line1.setStroke(Color.web("#00d2ff"));

        line1.setOpacity(0.15);

        line1.setStrokeWidth(2);

        Line line2 = new Line(350, 0, 0, 350);

        line2.setStroke(Color.web("#00d2ff"));

        line2.setOpacity(0.1);

        line2.setStrokeWidth(2);

        Line line3 = new Line(175, 0, 175, 350);

        line3.setStroke(Color.web("#00d2ff"));

        line3.setOpacity(0.08);

        Line line4 = new Line(0, 175, 350, 175);

        line4.setStroke(Color.web("#00d2ff"));

        line4.setOpacity(0.08);

        StackPane cyberBg =
                new StackPane(line1, line2, line3, line4);

        cyberBg.setMinSize(350, 350);

        // ================= MAIN LAYOUT =================

        HBox mainLayout =
                new HBox(50, cyberBg, loginBox);

        mainLayout.setAlignment(Pos.CENTER);

        // ================= FOOTER =================

        VBox footer = new VBox(15);

        footer.setAlignment(Pos.CENTER);

        footer.setPadding(new Insets(30, 0, 0, 0));

        // Supervisors
        VBox supervisorsBox = new VBox(5);

        supervisorsBox.setAlignment(Pos.CENTER);

        Label supTitle =
                new Label("Under Supervision of:");

        supTitle.setTextFill(Color.GRAY);

        supTitle.setFont(
                Font.font("Arial", FontWeight.LIGHT, 12)
        );

        Label drName =
                new Label("Dr. Ahmed Saleh & Eng. Mohamed Abdelbaset");

        drName.setTextFill(Color.WHITE);

        drName.setFont(
                Font.font("Arial", FontWeight.BOLD, 16)
        );

        drName.setStyle(
                "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.3), 10, 0.5, 0, 0);"
        );

        supervisorsBox.getChildren().addAll(
                supTitle,
                drName
        );

        // Team Box
        VBox teamBox = new VBox(8);

        teamBox.setAlignment(Pos.CENTER);

        teamBox.setStyle(
                "-fx-background-color: rgba(44, 49, 58, 0.5);" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 15;"
        );

        teamBox.setMaxWidth(800);

        Label leaderLabel =
                new Label("◈ FARES GEBRIL - Team LEADER (Back-end | JavaFX | AI) ◈");

        leaderLabel.setTextFill(
                Color.web("#00d2ff")
        );

        leaderLabel.setFont(
                Font.font("Courier New", FontWeight.BOLD, 15)
        );

        Label memberRow1 =
                new Label("Yassen Hamdi (Back-end | JavaFX)  •  Mohamed Qutp (Database | SQL)");

        Label memberRow2 =
                new Label("Omar Abdel Aziz (Architecture | Logic)  •  Omar Ramadan (UML | UI Design)");

        memberRow1.setTextFill(Color.LIGHTGRAY);
        memberRow2.setTextFill(Color.LIGHTGRAY);

        memberRow1.setFont(Font.font("Arial", 13));
        memberRow2.setFont(Font.font("Arial", 13));

        teamBox.getChildren().addAll(
                leaderLabel,
                memberRow1,
                memberRow2
        );

        footer.getChildren().addAll(
                supervisorsBox,
                teamBox
        );

        // ================= ROOT =================

        root.getChildren().addAll(
                logo,
                mainLayout,
                footer
        );

        return new Scene(root, 1100, 750);
    }
}
