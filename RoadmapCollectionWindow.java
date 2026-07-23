package com.mycompany.cyberjocx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.awt.Desktop;
import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoadmapCollectionWindow {

    // الـ Model البسيط جوه الكلاس عشان ميعملش زحمة ملفات
    public static class SimpleRoadmap {
        int id; String name; String category; String link; String desc; String phases;
        public SimpleRoadmap(int id, String name, String category, String link, String desc, String phases) {
            this.id = id; this.name = name; this.category = category; this.link = link; this.desc = desc; this.phases = phases;
        }
    }

    public VBox getView() {
        VBox root = new VBox(20); // قللنا الـ spacing لـ 20 عشان استيعاب زرار الرجوع
        root.setPadding(new Insets(20, 30, 30, 30)); // تعديل البادينج عشان التناسق
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: transparent;"); 

        // =========================================================
        // زرار الرجوع للداشبورد (Navigation Row)
        // =========================================================
        HBox navigationRow = new HBox();
        navigationRow.setAlignment(Pos.CENTER_LEFT);
        navigationRow.setPadding(new Insets(0, 0, 10, 0));

        Button backBtn = new Button("⬅ System Dashboard");
        backBtn.setStyle(
            "-fx-background-color: rgba(0, 255, 255, 0.05);" +
            "-fx-text-fill: #00FFFF;" +
            "-fx-border-color: rgba(0, 255, 255, 0.3);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 15;"
        );

        // حدث الانتقال للـ Home Dashboard
        backBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));
        
        // تأثيرات الماوس (Hover Effects) لزرار الرجوع
        backBtn.setOnMouseEntered(e -> backBtn.setStyle(
            "-fx-background-color: rgba(0, 255, 255, 0.2);" +
            "-fx-text-fill: white;" +
            "-fx-border-color: #00FFFF;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 15;"
        ));
        backBtn.setOnMouseExited(e -> backBtn.setStyle(
            "-fx-background-color: rgba(0, 255, 255, 0.05);" +
            "-fx-text-fill: #00FFFF;" +
            "-fx-border-color: rgba(0, 255, 255, 0.3);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 15;"
        ));

        navigationRow.getChildren().add(backBtn);

        // العنوان الرئيسي للـ Section
        Label headerTitle = new Label("⚡ Verified Cyber & Data Roadmaps ⚡");
        headerTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        headerTitle.setTextFill(Color.web("#00FFFF"));
        headerTitle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.3), 10, 0, 0, 0);");

        Label subTitle = new Label("Curated from elite industry repositories to guide your learning journey");
        subTitle.setFont(Font.font("Segoe UI", 14));
        subTitle.setTextFill(Color.LIGHTGRAY);
        
        // إضافة شريط الرجوع مع العناوين للـ root
        root.getChildren().addAll(navigationRow, headerTitle, subTitle);

        // Container لرص الكروت بشكل طولي نضيف
        VBox cardsContainer = new VBox(20);
        cardsContainer.setAlignment(Pos.TOP_CENTER);

        // جلب البيانات من الجدول المستقل
        List<SimpleRoadmap> roadmaps = getRoadmapsFromDB();

        for (SimpleRoadmap rm : roadmaps) {
            cardsContainer.getChildren().add(createRoadmapCard(rm));
        }

        ScrollPane scrollPane = new ScrollPane(cardsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        root.getChildren().add(scrollPane);
        return root;
    }

    public javafx.scene.Scene getViewAsScene() {
        javafx.scene.layout.VBox mainView = getView();
        
        // عمل حاوية سوداء رئيسية عشان الـ ScrollPane ميبقاش شفاف أو يضرب الـ Background
        javafx.scene.layout.StackPane bgContainer = new javafx.scene.layout.StackPane(mainView);
        bgContainer.setStyle("-fx-background-color: #05070B;");
        
        // جلب أبعاد الشاشة المتوافقة مع أبعاد الـ Dashboard عندك
        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.96;
        double height = screenBounds.getHeight() * 0.95;
        
        return new javafx.scene.Scene(bgContainer, width, height);
    }

    private VBox createRoadmapCard(SimpleRoadmap rm) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(22));
        card.setMaxWidth(900);
        card.setStyle("-fx-background-color: rgba(20, 30, 48, 0.5); " +
                     "-fx-background-radius: 15; " +
                     "-fx-border-color: rgba(0, 255, 255, 0.1); " +
                     "-fx-border-width: 1.5; " +
                     "-fx-border-radius: 15;");

        // الصف العلوي: الاسم + التراك
        HBox topRow = new HBox(15);
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox nameBox = new VBox(4);
        Label titleLabel = new Label(rm.name);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.WHITE);

        Label catLabel = new Label("🏷️ Category: " + rm.category);
        catLabel.setFont(Font.font("Segoe UI", 12));
        catLabel.setTextFill(Color.web("#8A9DB5"));
        nameBox.getChildren().addAll(titleLabel, catLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // زرار يفتح جيت هاب علطول
        Button openGitBtn = new Button("Open GitHub Source 🛠️");
        openGitBtn.setStyle("-fx-background-color: linear-gradient(to right, #00FFFF, #008899); -fx-text-fill: #05070B; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
        openGitBtn.setOnAction(e -> openWebpage(rm.link));

        topRow.getChildren().addAll(nameBox, spacer, openGitBtn);

        // الوصف
        Label descLabel = new Label(rm.desc);
        descLabel.setTextFill(Color.LIGHTGRAY);
        descLabel.setWrapText(true);
        descLabel.setFont(Font.font("Segoe UI", 13));

        // شريط المراحل المكتوب (The Flow Line)
        HBox flowLine = new HBox(10);
        flowLine.setPadding(new Insets(10, 8, 10, 8));
        flowLine.setStyle("-fx-background-color: rgba(10, 15, 26, 0.6); -fx-background-radius: 8; -fx-border-color: rgba(0,255,255,0.05);");
        
        Label phasesLabel = new Label("🗺️ Path Layout:  " + rm.phases);
        phasesLabel.setTextFill(Color.web("#00FFFF"));
        phasesLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));        phasesLabel.setWrapText(true);
        flowLine.getChildren().add(phasesLabel);

        card.getChildren().addAll(topRow, descLabel, flowLine);

        // Hover Effect
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: rgba(25, 40, 65, 0.6); -fx-background-radius: 15; -fx-border-color: #00FFFF; -fx-border-width: 1.5; -fx-border-radius: 15;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: rgba(20, 30, 48, 0.5); -fx-background-radius: 15; -fx-border-color: rgba(0, 255, 255, 0.1); -fx-border-width: 1.5; -fx-border-radius: 15;"));

        return card;
    }

    private List<SimpleRoadmap> getRoadmapsFromDB() {
        List<SimpleRoadmap> list = new ArrayList<>();
        
        String createTableQuery = "CREATE TABLE IF NOT EXISTS github_roadmaps (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "roadmap_name VARCHAR(255) NOT NULL, " +
                "category VARCHAR(100) NOT NULL, " +
                "github_link VARCHAR(550), " +
                "roadmap_description TEXT, " +
                "phases_summary TEXT" +
                ");";
                
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(createTableQuery);
            
            String checkEmpty = "SELECT COUNT(*) FROM github_roadmaps";
            try (ResultSet rs = stmt.executeQuery(checkEmpty)) {
                if (rs.next() && rs.getInt(1) == 0) {
                    String insertData = "INSERT INTO github_roadmaps (roadmap_name, category, github_link, roadmap_description, phases_summary) VALUES " +
                            "('Ultimate Cybersecurity Roadmap', 'Cybersecurity', 'https://github.com/minhaj-313/-Ultimate-Cybersecurity-Roadmap', 'Comprehensive guide from fundamentals to advanced hacking and security operations.', 'Phase 1: Networking & OS Fundamentals -> Phase 2: Security Basics -> Phase 3: Pen Testing & SOC')," +
                            "('Reverse Engineering & Malware Analysis', 'Cybersecurity', 'https://github.com/x86byte/RE-MA-Roadmap', 'Deep dive into Assembly, Reverse Engineering, and analyzing malicious binaries.', 'Phase 1: C/Assembly & Architecture -> Phase 2: Static Analysis (Ghidra) -> Phase 3: Dynamic Analysis')," +
                            "('Data Analyst Roadmap', 'Data Analysis', 'https://github.com/hemansnation/Data-Analyst-Roadmap', 'Complete path to mastering data extraction, analysis, and visualization.', 'Phase 1: Excel & SQL -> Phase 2: Python & Pandas -> Phase 3: BI Tools (PowerBI/Tableau)');";
                    stmt.executeUpdate(insertData);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error initializing roadmap table: " + e.getMessage());
        }

        String query = "SELECT * FROM github_roadmaps";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                list.add(new SimpleRoadmap(
                    rs.getInt("id"),
                    rs.getString("roadmap_name"),
                    rs.getString("category"),
                    rs.getString("github_link"),
                    rs.getString("roadmap_description"),
                    rs.getString("phases_summary")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void openWebpage(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
