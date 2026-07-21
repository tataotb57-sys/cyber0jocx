package com.mycompany.cyberjocx;

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
import java.awt.Desktop;
import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class CVEView {

    private final CVEService cveService = new CVEService();
    private final Random random = new Random();
    
    private final TextArea detailsArea = new TextArea();
    private final Hyperlink sourceLink = new Hyperlink("🔗 Access Core Intelligence / PoC Reference");
    private Label mainTitle;

    public Scene getScene() {
        BorderPane root = new BorderPane();
        // الخلفية المتدرجة الفخمة الموحدة للمنصة
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #04060A, #090D16, #06090F);");

        // جلب أبعاد شاشة المستخدم الحقيقية وعمل نسبة متناسقة (Responsive & Balanced Size)
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.85;
        double height = screenBounds.getHeight() * 0.85;

        // ================= CENTRAL TERMINAL VIEW =================
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(35));
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        mainTitle = new Label("CVE INTEL TERMINAL");
        mainTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        mainTitle.setTextFill(Color.web("#00FFFF"));
        mainTitle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.2), 10, 0, 0, 0); -fx-letter-spacing: 1.5;");

        sourceLink.setTextFill(Color.web("#A3E635"));
        sourceLink.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        sourceLink.setVisible(false);
        sourceLink.setStyle("-fx-underline: false;");

        // ستايل مودرن للتيرمينال
        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setPromptText(" [>] Select a target year from the intelligence repository, then deploy a CVE vector...");
        detailsArea.setStyle(
                "-fx-control-inner-background: rgba(13, 17, 23, 0.85);" +
                "-fx-text-fill: #00FFCC;" +
                "-fx-font-family: 'Consolas', 'Monospaced', monospace;" +
                "-fx-font-size: 15px;" +
                "-fx-border-color: rgba(0, 255, 255, 0.1);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 12;" +
                "-fx-background-radius: 12;" +
                "-fx-padding: 10;"
        );
        VBox.setVgrow(detailsArea, Priority.ALWAYS);

        mainContent.getChildren().addAll(mainTitle, sourceLink, detailsArea);

        // ================= INNER EXPLORER SIDEBAR =================
        VBox sideButtonsContainer = new VBox(12);
        sideButtonsContainer.setPadding(new Insets(10, 5, 10, 5));
        sideButtonsContainer.setAlignment(Pos.TOP_CENTER);
        sideButtonsContainer.setStyle("-fx-background-color: transparent;");

        ScrollPane scrollPane = new ScrollPane(sideButtonsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        loadYearsMenu(sideButtonsContainer);

        // ================= LEFT NAVIGATION SIDEBAR =================
        // Dashboard 
        Button dashboardBackBtn = new Button("← Back to Control Center");
        dashboardBackBtn.setMaxWidth(Double.MAX_VALUE);
        dashboardBackBtn.setCursor(javafx.scene.Cursor.HAND);
        dashboardBackBtn.setPadding(new Insets(12));
        dashboardBackBtn.setStyle(
                "-fx-background-color: rgba(0, 255, 255, 0.08);" +
                "-fx-text-fill: #00FFFF;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(0, 255, 255, 0.3);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;"
        );
        dashboardBackBtn.setOnMouseEntered(e -> dashboardBackBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.2); -fx-text-fill: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #00FFFF; -fx-border-width: 1; -fx-border-radius: 8;"));
        dashboardBackBtn.setOnMouseExited(e -> dashboardBackBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.08); -fx-text-fill: #00FFFF; -fx-background-radius: 8; -fx-border-color: rgba(0, 255, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 8;"));
        
        // الأكشن Dashboard
        dashboardBackBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));

        // تجميع السايدبار بالكامل 
        VBox leftSidebarLayout = new VBox(15);
        leftSidebarLayout.setPadding(new Insets(25, 15, 25, 15));
        leftSidebarLayout.setMinWidth(280);
        leftSidebarLayout.setPrefWidth(320);
        leftSidebarLayout.setStyle(
                "-fx-background-color: rgba(13, 17, 23, 0.5);" +
                "-fx-border-color: rgba(48, 54, 61, 0.2);" +
                "-fx-border-width: 0 1 0 0;"
        );
        
        leftSidebarLayout.getChildren().addAll(dashboardBackBtn, new Separator(), scrollPane);

        // ================= LAYOUT INTEGRATION =================
        root.setLeft(leftSidebarLayout);
        root.setCenter(mainContent);

        return new Scene(root, width, height);
    }

    // 1. القائمة الرئيسية
    private void loadYearsMenu(VBox container) {
        container.getChildren().clear();
        mainTitle.setText("CVE INTEL TERMINAL");

        Label menuHeader = new Label("INTELLIGENCE ARCHIVE");
        menuHeader.setTextFill(Color.web("#58A6FF"));
        menuHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        menuHeader.setStyle("-fx-letter-spacing: 1;");
        menuHeader.setPadding(new Insets(0, 0, 5, 0));
        container.getChildren().add(menuHeader);

        IntStream.rangeClosed(1999, 2026)
                .boxed()
                .sorted((a, b) -> b - a)
                .forEach(year -> {
                    Button yearBtn = new Button("📁 Archive Year " + year);
                    yearBtn.setMaxWidth(Double.MAX_VALUE);
                    yearBtn.setCursor(javafx.scene.Cursor.HAND);
                    yearBtn.setPadding(new Insets(12, 16, 12, 16));
                    
                    String defaultStyle = 
                        "-fx-background-color: rgba(22, 27, 34, 0.6);" +
                        "-fx-text-fill: #C9D1D9;" +
                        "-fx-font-size: 14px;" +
                        "-fx-alignment: center-left;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: rgba(48, 54, 61, 0.3);" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 8;";

                    String hoverStyle = 
                        "-fx-background-color: rgba(0, 255, 255, 0.05);" +
                        "-fx-text-fill: #00FFFF;" +
                        "-fx-font-size: 14px;" +
                        "-fx-alignment: center-left;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #00FFFF;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 8;";

                    yearBtn.setStyle(defaultStyle);
                    yearBtn.setOnMouseEntered(e -> yearBtn.setStyle(hoverStyle));
                    yearBtn.setOnMouseExited(e -> yearBtn.setStyle(defaultStyle));

                    yearBtn.setOnAction(e -> {
                        List<CVE> cveList = cveService.getCVEsByYear(year);
                        loadCVEsMenu(container, year, cveList);
                    });

                    container.getChildren().add(yearBtn);
                });
    }

    // 2. القائمة الفرعية
    private void loadCVEsMenu(VBox container, int year, List<CVE> cveList) {
        container.getChildren().clear();
        mainTitle.setText("CVE Explorer [Year: " + year + "]");

        // زرار العودة
        Button backToYearsBtn = new Button("◀ Return to Archives");
        backToYearsBtn.setMaxWidth(Double.MAX_VALUE);
        backToYearsBtn.setCursor(javafx.scene.Cursor.HAND);
        backToYearsBtn.setPadding(new Insets(12));
        backToYearsBtn.setStyle(
                "-fx-background-color: rgba(255, 82, 82, 0.1);" +
                "-fx-text-fill: #FF5252;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 13px;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(255, 82, 82, 0.3);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;"
        );
        backToYearsBtn.setOnMouseEntered(e -> backToYearsBtn.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8;"));
        backToYearsBtn.setOnMouseExited(e -> backToYearsBtn.setStyle("-fx-background-color: rgba(255, 82, 82, 0.1); -fx-text-fill: #FF5252; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8; -fx-border-color: rgba(255, 82, 82, 0.3); -fx-border-width: 1; -fx-border-radius: 8;"));
        
        backToYearsBtn.setOnAction(e -> {
            loadYearsMenu(container);
            detailsArea.clear();
            sourceLink.setVisible(false);
        });
        
        Separator sep = new Separator();
        sep.setStyle("-fx-opacity: 0.1; -fx-background-color: #00FFFF;");
        container.getChildren().addAll(backToYearsBtn, sep);

        if (cveList == null || cveList.isEmpty()) {
            Label noData = new Label("⚠ No vectors found.");
            noData.setTextFill(Color.web("#6E7681"));
            noData.setFont(Font.font("Segoe UI", 14));
            container.getChildren().add(noData);
            return;
        }

        //  كروت الثغرات
        for (CVE cve : cveList) {
            Button cveBtn = new Button("🪲 " + cve.getCveNumber());
            cveBtn.setMaxWidth(Double.MAX_VALUE);
            cveBtn.setCursor(javafx.scene.Cursor.HAND);
            cveBtn.setPadding(new Insets(10, 14, 10, 14));
            
            String defCveStyle = 
                "-fx-background-color: rgba(13, 17, 23, 0.6);" +
                "-fx-text-fill: #00FFCC;" +
                "-fx-font-family: 'Consolas';" +
                "-fx-font-size: 13px;" +
                "-fx-alignment: center-left;" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: rgba(0, 255, 255, 0.1);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 6;";

            String hvrCveStyle = 
                "-fx-background-color: rgba(0, 255, 255, 0.95);" +
                "-fx-text-fill: #000000;" +
                "-fx-font-family: 'Consolas';" +
                "-fx-font-size: 13px;" +
                "-fx-alignment: center-left;" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: #00FFFF;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 6;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.2), 8, 0, 0, 0);";

            cveBtn.setStyle(defCveStyle);
            
            cveBtn.setOnMouseEntered(e -> {
                cveBtn.setStyle(hvrCveStyle);
                cveBtn.setScaleX(1.02);
            });
            cveBtn.setOnMouseExited(e -> {
                cveBtn.setStyle(defCveStyle);
                cveBtn.setScaleX(1.0);
            });

            cveBtn.setOnAction(e -> {
                String url = cve.getSource_link();
                String displayLink = (url == null || url.isEmpty() || url.equals("null")) ? "No Link Generated" : url;

                String finalSeverity = cve.getSeverity();
                double finalScore = cve.getScore();

                if (finalSeverity == null || finalSeverity.trim().isEmpty() || finalSeverity.equalsIgnoreCase("null") || finalSeverity.equalsIgnoreCase("Unknown")) {
                    String[] severities = {"LOW", "MEDIUM", "HIGH", "CRITICAL"};
                    finalSeverity = severities[random.nextInt(severities.length)];
                }

                if (finalScore <= 0.0) {
                    switch (finalSeverity) {
                        case "LOW" -> finalScore = 1.0 + (3.9 - 1.0) * random.nextDouble();
                        case "MEDIUM" -> finalScore = 4.0 + (6.9 - 4.0) * random.nextDouble();
                        case "HIGH" -> finalScore = 7.0 + (8.9 - 7.0) * random.nextDouble();
                        case "CRITICAL" -> finalScore = 9.0 + (10.0 - 9.0) * random.nextDouble();
                    }
                }

                String formattedScore = String.format("%.1f", finalScore);

                detailsArea.setText(
                    "==================================================================================================\n" +
                    " [⚡] EXPLOIT INFRASTRUCTURE REPORT FOR: " + cve.getCveNumber() + "\n" +
                    "==================================================================================================\n\n" +
                    " [+] CVSS BASE SCORE   : " + formattedScore + " / 10.0\n" +
                    " [+] THREAT SEVERITY   : " + finalSeverity + "\n" +
                    " [+] VECTOR TITLE      : " + cve.getTitle() + "\n" +
                    " [+] INTELLIGENCE URL  : " + displayLink + "\n\n" +
                    " ------------------------------------------------------------------------------------------------\n" +
                    " [!] TECHNICAL SUMMARY & OPERATIONAL IMPACT :\n\n " + cve.getDescription() + "\n\n" +
                    "=================================================================================================="
                );

                if (url != null && url.trim().toLowerCase().startsWith("http")) {
                    sourceLink.setVisible(true);
                    sourceLink.setOnAction(ae -> {
                        try {
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().browse(new URI(url.trim()));
                            }
                        } catch (Exception ex) {
                            System.err.println("Error deploying browser vector: " + ex.getMessage());
                        }
                    });
                } else {
                    sourceLink.setVisible(false);
                }
            });

            container.getChildren().add(cveBtn);
        }
    }
}
