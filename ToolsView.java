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

import java.util.*;
import java.util.stream.Collectors;

public class ToolsView {

    private final TextArea displayArea = new TextArea();
    private Label mainTitle;

    public Scene getScene() {
        BorderPane root = new BorderPane();
        // الخلفية المتدرجة الموحدة للمنصة
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #04060A, #090D16, #06090F);");

        // جلب أبعاد شاشة المستخدم لضمان الـ Responsiveness
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.85;
        double height = screenBounds.getHeight() * 0.85;

        // ================= CENTRAL TERMINAL VIEW =================
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(35));
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        mainTitle = new Label("SECURITY TOOLS REPOSITORY");
        mainTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        mainTitle.setTextFill(Color.web("#00FFFF"));
        mainTitle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.2), 10, 0, 0, 0); -fx-letter-spacing: 1.5;");

        // ستايل مودرن للتيرمينال لعرض التوثيق والأوامر
        displayArea.setEditable(false);
        displayArea.setWrapText(true);
        displayArea.setPromptText(" [>] Select a security framework from the toolkit hierarchy to dump documentation...");
        displayArea.setStyle(
                "-fx-control-inner-background: rgba(13, 17, 23, 0.85);" +
                "-fx-text-fill: #00FFCC;" +
                "-fx-font-family: 'Consolas', 'Monospaced', monospace;" +
                "-fx-font-size: 15px;" +
                "-fx-border-color: rgba(0, 255, 255, 0.1);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 12;" +
                "-fx-background-radius: 12;" +
                "-fx-padding: 15;"
        );
        VBox.setVgrow(displayArea, Priority.ALWAYS);

        mainContent.getChildren().addAll(mainTitle, displayArea);

        // ================= INNER SIDEBAR UTILITIES =================
        VBox toolsContainer = new VBox(12);
        toolsContainer.setPadding(new Insets(10, 5, 10, 5));
        toolsContainer.setAlignment(Pos.TOP_CENTER);
        toolsContainer.setStyle("-fx-background-color: transparent;");

        ScrollPane scrollPane = new ScrollPane(toolsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // ================= LEFT NAVIGATION SIDEBAR =================
        // زرار العودة للـ Dashboard مصمم بطابع النيون الفخم
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
        dashboardBackBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));

        // زر تحديث قاعدة البيانات بتصميم متناسق وثابت
        Button refreshBtn = new Button("🔄 Sync Repository");
        refreshBtn.setMaxWidth(Double.MAX_VALUE);
        refreshBtn.setCursor(javafx.scene.Cursor.HAND);
        refreshBtn.setPadding(new Insets(10));
        refreshBtn.setStyle(
                "-fx-background-color: rgba(49, 56, 77, 0.4);" +
                "-fx-text-fill: #C9D1D9;" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: rgba(255, 255, 255, 0.1);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 6;" +
                "-fx-font-size: 12px;"
        );
        refreshBtn.setOnMouseEntered(e -> refreshBtn.setStyle("-fx-background-color: rgba(49, 56, 77, 0.8); -fx-text-fill: #FFFFFF; -fx-background-radius: 6; -fx-border-color: rgba(255, 255, 255, 0.2); -fx-border-width: 1; -fx-border-radius: 6;"));
        refreshBtn.setOnMouseExited(e -> refreshBtn.setStyle("-fx-background-color: rgba(49, 56, 77, 0.4); -fx-text-fill: #C9D1D9; -fx-background-radius: 6; -fx-border-color: rgba(255, 255, 255, 0.1); -fx-border-width: 1; -fx-border-radius: 6;"));
        refreshBtn.setOnAction(e -> loadTools(toolsContainer, displayArea));

        // هيكلة السايدبار الموحد (Glassmorphic Sidebar)
        VBox leftSidebarLayout = new VBox(15);
        leftSidebarLayout.setPadding(new Insets(25, 15, 25, 15));
        leftSidebarLayout.setMinWidth(280);
        leftSidebarLayout.setPrefWidth(320);
        leftSidebarLayout.setStyle(
                "-fx-background-color: rgba(13, 17, 23, 0.5);" +
                "-fx-border-color: rgba(48, 54, 61, 0.2);" +
                "-fx-border-width: 0 1 0 0;"
        );
        
        leftSidebarLayout.getChildren().addAll(dashboardBackBtn, refreshBtn, new Separator(), scrollPane);

        // تحميل الأدوات وتصنيفها لأول مرة
        loadTools(toolsContainer, displayArea);

        root.setLeft(leftSidebarLayout);
        root.setCenter(mainContent);

        return new Scene(root, width, height);
    }

    private void loadTools(VBox left, TextArea display) {
        left.getChildren().clear();

        List<Tools> toolsList = ToolsDAO.getAllTools();
        if (toolsList == null || toolsList.isEmpty()) {
            Label noData = new Label("⚠ No tools found in DB.");
            noData.setTextFill(Color.web("#6E7681"));
            left.getChildren().add(noData);
            return;
        }

        Map<String, List<Tools>> grouped = toolsList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        t -> (t.getCategory() != null && !t.getCategory().isEmpty())
                                ? t.getCategory()
                                : "General"
                ));

        List<String> sortedCategories = new ArrayList<>(grouped.keySet());
        Collections.sort(sortedCategories);

        for (String category : sortedCategories) {
            // تصميم عنوان القسم (Category Header) بشكل مودرن نيون أزرق
            Label catHeader = new Label("📁 " + category.toUpperCase());
            catHeader.setTextFill(Color.web("#58A6FF"));
            catHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            catHeader.setStyle("-fx-letter-spacing: 1.2;");
            catHeader.setPadding(new Insets(15, 0, 8, 4));

            VBox toolButtonsContainer = new VBox(8);

            List<Tools> toolsInCat = grouped.get(category);
            if (toolsInCat == null) continue;

            for (Tools tool : toolsInCat) {
                Button b = new Button("🛠 " + tool.getName());
                b.setMaxWidth(Double.MAX_VALUE);
                b.setCursor(javafx.scene.Cursor.HAND);
                b.setPadding(new Insets(10, 14, 10, 14));

                String defaultStyle = 
                    "-fx-background-color: rgba(22, 27, 34, 0.6);" +
                    "-fx-text-fill: #C9D1D9;" +
                    "-fx-font-size: 13px;" +
                    "-fx-alignment: center-left;" +
                    "-fx-background-radius: 8;" +
                    "-fx-border-color: rgba(48, 54, 61, 0.3);" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 8;";

                String hoverStyle = 
                    "-fx-background-color: rgba(0, 255, 255, 0.05);" +
                    "-fx-text-fill: #00FFFF;" +
                    "-fx-font-size: 13px;" +
                    "-fx-alignment: center-left;" +
                    "-fx-background-radius: 8;" +
                    "-fx-border-color: #00FFFF;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 8;" +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.15), 6, 0, 0, 0);";

                b.setStyle(defaultStyle);

                // أنيميشن بالجافا لمنع أي Jittering أو اهتزاز أثناء تحريك الماوس
                b.setOnMouseEntered(e -> {
                    b.setStyle(hoverStyle);
                    b.setScaleX(1.02);
                });
                b.setOnMouseExited(e -> {
                    b.setStyle(defaultStyle);
                    b.setScaleX(1.0);
                });

                b.setOnAction(e -> {
                    String rawCommands = tool.getCommands();
                    StringBuilder formattedCommands = new StringBuilder();

                    if (rawCommands != null && !rawCommands.trim().isEmpty()) {
                        String[] cmdArray = rawCommands.split("\\|");
                        for (String cmdEntry : cmdArray) {
                            String cleanEntry = cmdEntry.trim();
                            if (!cleanEntry.isEmpty()) {
                                if (cleanEntry.contains(":")) {
                                    String[] parts = cleanEntry.split(":", 2);
                                    String commandName = parts[0].trim();
                                    String explanation = parts[1].trim();

                                    formattedCommands.append("   > ")
                                            .append(String.format("%-22s", commandName))
                                            .append(" # ")
                                            .append(explanation)
                                            .append("\n");
                                } else {
                                    formattedCommands.append("   > ").append(cleanEntry).append("\n");
                                }
                            }
                        }
                    } else {
                        formattedCommands.append("   No specialized command vectors found for this asset in DB.\n");
                    }

                    // طباعة التقارير داخل الكونسول بشكل احترافي ومنظم
                    display.setText(
                            "==================================================================================================\n" +
                            " [⚡] RECONNAISSANCE TOOL PROFILE: " + tool.getName().toUpperCase() + "\n" +
                            "==================================================================================================\n\n" +
                            " [+] CORE CATEGORY    : " + tool.getCategory() + "\n" +
                            " [+] TARGET BINARY    : " + tool.getName() + "\n" +
                            "==================================================================================================\n\n" +
                            " [!] ARCHITECTURE & OPERATIONAL PURPOSE :\n " + tool.getDescription() + "\n\n" +
                            " [>] EXECUTION SYNTAX & COMMON UTILITIES :\n" + formattedCommands.toString() + "\n" +
                            " [→] SOURCE PIPELINE / DOWNLOAD MATRIX :\n " + tool.getDownload_link() + "\n\n" +
                            "=================================================================================================="
                    );
                });

                toolButtonsContainer.getChildren().add(b);
            }

            left.getChildren().addAll(catHeader, toolButtonsContainer);
        }
    }
}
