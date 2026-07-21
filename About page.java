package com.mycompany.cyberjocx;

import java.awt.Desktop;
import java.net.URI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AboutPage {

    public Scene getScene() {

        // ================= HEADER TITLE & NAVIGATION =================
        // زرار الرجوع للـ Dashboard مصمم بطابع المنصة النيون
        Button backBtn = new Button("◀ Return to Control Center");
        backBtn.setStyle(
                "-fx-background-color: rgba(0, 255, 255, 0.08);" +
                "-fx-text-fill: #00FFFF;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(0, 255, 255, 0.3);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.2); -fx-text-fill: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #00FFFF; -fx-border-width: 1; -fx-border-radius: 8; -fx-cursor: hand;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.08); -fx-text-fill: #00FFFF; -fx-background-radius: 8; -fx-border-color: rgba(0, 255, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 8; -fx-cursor: hand;"));
        
        // ربط الزرار بالـ Dashboard مباشرة
        backBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));

        Label title = new Label("CYBERJOCX");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));
        title.setTextFill(Color.web("#00FFFF"));
        title.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.3), 15, 0, 0, 0); -fx-letter-spacing: 2;");

        Label subTitle = new Label("Advanced Educational & Analytical Cybersecurity Platform");
        subTitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        subTitle.setTextFill(Color.web("#8B949E"));

        VBox headerBox = new VBox(8, backBtn, title, subTitle);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10, 0, 10, 0));

        // ================= ABOUT PROJECT SECTION =================
        Label aboutTitle = new Label("🛰️ System Architecture Overview");
        aboutTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        aboutTitle.setTextFill(Color.web("#58A6FF"));

        Label description = new Label(
                "CyberJocx is an intelligent security ecosystem designed for next-generation threat analysis and learning. "
                + "The infrastructure seamlessly bridges the gap between massive vulnerability tracking and interactive operational intelligence.\n\n"
                + "⚡ Core Subsystems & Operational Features:"
        );
        description.setFont(Font.font("Segoe UI", 15));
        description.setTextFill(Color.web("#C9D1D9"));
        description.setWrapText(true);

        // قائمة المميزات بتنسيق كتل نيون صغيرة
        GridPane featuresGrid = new GridPane();
        featuresGrid.setHgap(20);
        featuresGrid.setVgap(12);
        featuresGrid.setPadding(new Insets(10, 0, 10, 0));

        String[] features = {
            "📊 Daily CVE Updates (1999 - 2026)", "🧬 Risk Analysis Engine",
            "🛡️ Threat Intelligence Module", "📌 Proof of Concept (PoC) Logs",
            "📝 Interactive Cyber Dashboards", "📖 Cybersecurity Books Library",
            "📈 User-Generated Write-ups System", "🎮 Gamified XP/Points System",
            "🛠️ Core Penetration Tools Analytics", "☣️ Malware Behavior & Sandbox Info"
        };

        for (int i = 0; i < features.length; i++) {
            Label fLabel = new Label(features[i]);
            fLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
            fLabel.setTextFill(Color.web("#E6EDF2"));
            fLabel.setPadding(new Insets(6, 12, 6, 12));
            fLabel.setStyle("-fx-background-color: rgba(0, 255, 255, 0.04); -fx-background-radius: 6; -fx-border-color: rgba(0, 255, 255, 0.1); -fx-border-radius: 6;");
            
            int col = i % 2;
            int row = i / 2;
            featuresGrid.add(fLabel, col, row);
            GridPane.setHgrow(fLabel, Priority.ALWAYS);
        }

        Label comparisonLabel = new Label("💡 Platform Workflow Paradigm: Acts as an optimized decentralized hub (Hybrid GitHub/Twitter framework) built exclusively for elite security researchers, defensive engineers, and prospective operators.");
        comparisonLabel.setFont(Font.font("Segoe UI", 14));
        comparisonLabel.setTextFill(Color.web("#A3E635")); 
        comparisonLabel.setWrapText(true);
        comparisonLabel.setPadding(new Insets(10));
        comparisonLabel.setStyle("-fx-background-color: rgba(163, 230, 53, 0.05); -fx-background-radius: 8; -fx-border-color: rgba(163, 230, 53, 0.15); -fx-border-radius: 8;");

        VBox infoSection = new VBox(15, aboutTitle, description, featuresGrid, comparisonLabel);
        infoSection.setPadding(new Insets(20));
        infoSection.setStyle("-fx-background-color: rgba(22, 27, 34, 0.4); -fx-background-radius: 12; -fx-border-color: rgba(48, 54, 61, 0.5); -fx-border-radius: 12;");

        // ================= TEAM SECTION =================
        Label teamTitle = new Label("👥 Operational Engineering Command");
        teamTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        teamTitle.setTextFill(Color.web("#FF9900"));

        GridPane teamGrid = new GridPane();
        teamGrid.setHgap(15);
        teamGrid.setVgap(15);
        teamGrid.setAlignment(Pos.CENTER);

        VBox card1 = createMemberCard("Fares Ahmed Gepril", "TEAM LEADER , Back-End ,Database, JavaFX ,AI Controller, Lead Operator", "0127 077 9541", "https://www.facebook.com/share/14iurUQwZ1Z/?mibextid=wwXIfr");
        VBox card2 = createMemberCard("Mohamed Mohamed Fouad", "Database & Integration Support", "01092889681", "https://www.facebook.com/share/15sNMAKJRsx/?mibextid=wwXIfr");
        VBox card3 = createMemberCard("Yassen Hamdy", "Back-End ,Threat Intelligence Developer , JavaFX , Animation ", "0127 432 1201", "https://www.facebook.com/share/1GsdeQ87gF/?mibextid=wwXIfr");
        VBox card4 = createMemberCard("Omar Ramadan", " UI Designer ", "0115 319 8002", "https://www.facebook.com/share/1CRq3ANmhj/?mibextid=wwXIfr");
        VBox card5 = createMemberCard("Omar Abd Elaziz", "logic dev , Aracher ", "0114 735 5125", null);
        VBox card6 = createMemberCard("Mohamed Tamer Elshanawy", "Security Testing devolber , UML ", "0101 979 3996", "https://www.facebook.com/share/1EZ7dK4fwh/?mibextid=wwXIfr");

        teamGrid.add(card1, 0, 0);
        teamGrid.add(card2, 1, 0);
        teamGrid.add(card3, 2, 0);
        teamGrid.add(card4, 0, 1);
        teamGrid.add(card5, 1, 1);
        teamGrid.add(card6, 2, 1);

        Separator sep1 = new Separator();
        sep1.setStyle("-fx-opacity: 0.15; -fx-background-color: #00FFFF;");
        Separator sep2 = new Separator();
        sep2.setStyle("-fx-opacity: 0.15; -fx-background-color: #00FFFF;");

        // ================= ROOT LAYOUT =================
        VBox root = new VBox(25);
        root.setPadding(new Insets(35, 40, 35, 40));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(headerBox, sep1, infoSection, sep2, teamTitle, teamGrid);

        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #04060A, #090D16, #06090F);" +
                "-fx-border-color: rgba(0, 255, 255, 0.08);" +
                "-fx-border-width: 1;"
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );

        Scene scene = new Scene(scrollPane, 1040, 780);
        scene.setFill(Color.web("#04060A"));

        return scene;
    }

    // ================= MODERN MEMBER CARD DESIGN =================
    private VBox createMemberCard(String name, String role, String phone, String facebook) {

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        nameLabel.setTextFill(Color.web("#00FFFF"));
        nameLabel.setWrapText(true);

        Label roleLabel = new Label(role);
        roleLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
        roleLabel.setTextFill(Color.web("#A3E635"));
        roleLabel.setWrapText(true);

        Label phoneLabel = new Label("📱 " + phone);
        phoneLabel.setFont(Font.font("Consolas", 12));
        phoneLabel.setTextFill(Color.web("#8B949E"));

        VBox card = new VBox(8);
        card.setPadding(new Insets(16));
        card.setPrefWidth(290);
        card.setMinWidth(290);

        if (facebook != null) {
            Hyperlink fbLink = new Hyperlink("🔗 Secure Profile Link");
            fbLink.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            fbLink.setTextFill(Color.web("#58A6FF"));
            fbLink.setStyle("-fx-underline: false;");
            fbLink.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(facebook));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            card.getChildren().addAll(nameLabel, roleLabel, phoneLabel, fbLink);
        } else {
            Label localLabel = new Label("🔒 Internal Operator");
            localLabel.setFont(Font.font("Segoe UI", 12));
            localLabel.setTextFill(Color.web("#6E7681"));
            card.getChildren().addAll(nameLabel, roleLabel, phoneLabel, localLabel);
        }

        String defaultStyle = 
            "-fx-background-color: rgba(13, 17, 23, 0.75);" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: rgba(0, 255, 255, 0.08);" +
            "-fx-border-width: 1.5;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 3);";

        String hoverStyle = 
            "-fx-background-color: rgba(22, 27, 34, 0.95);" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: #00FFFF;" +
            "-fx-border-width: 1.5;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.18), 15, 0, 0, 0);";

        card.setStyle(defaultStyle);
        
        card.setOnMouseEntered(e -> {
            card.setStyle(hoverStyle);
            card.setScaleX(1.02);
            card.setScaleY(1.02);
        });
        
        card.setOnMouseExited(e -> {
            card.setStyle(defaultStyle);
            card.setScaleX(1.0);
            card.setScaleY(1.0);
        });

        return card;
    }

    void show() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  
}
