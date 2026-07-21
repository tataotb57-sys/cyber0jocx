package com.mycompany.cyberjocx;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import java.awt.Desktop;
import java.net.URI;

public class CourseCollectionView {

    private final CourseController controller = new CourseController();

    public VBox getView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        // تم جعل الخلفية شفافة هنا لكي يتناسق مع ثيم الـ Glass Container الموحد للـ Dashboard
        root.setStyle("-fx-background-color: transparent;"); 

        // Header Title
        Label headerTitle = new Label("CyberJocx Academy & Playlists");
        headerTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        headerTitle.setTextFill(Color.CYAN);
        root.getChildren().add(headerTitle);

        // Subtitle
        Label subTitle = new Label("Explore top security & development roadmaps");
        subTitle.setFont(Font.font("Segoe UI", 14));
        subTitle.setTextFill(Color.LIGHTGRAY);
        root.getChildren().add(subTitle);

        // GridPane لرص الكروت (3 كروت في كل صف)
        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        gridPane.setAlignment(Pos.CENTER);

        // جلب البيانات من الداتابيز
        ObservableList<Course> courses = controller.getAllCourses();

        int column = 0;
        int row = 0;

        for (Course course : courses) {
            VBox card = createCourseCard(course);
            gridPane.add(card, column, row);

            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }

        // وضع الـ GridPane جوه ScrollPane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        
        root.getChildren().add(scrollPane);
        return root;
    }

    private VBox createCourseCard(Course course) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setPrefSize(280, 220);
        card.setStyle("-fx-background-color: #1F2937; " +
                     "-fx-background-radius: 10; " +
                     "-fx-border-color: #00FFFF; " +
                     "-fx-border-radius: 10; " +
                     "-fx-border-width: 1;");
        card.setAlignment(Pos.TOP_LEFT);

        Label catLabel = new Label(course.getCategory().toUpperCase());
        catLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        catLabel.setTextFill(Color.ORANGE);

        Label titleLabel = new Label(course.getCourseName());
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setWrapText(true);
        titleLabel.setMaxHeight(45);

        Label instructorLabel = new Label("By: " + course.getInstructor());
        instructorLabel.setFont(Font.font("Segoe UI", 12));
        instructorLabel.setTextFill(Color.LIGHTGRAY);

        Label pointsLabel = new Label(course.getRequiredPoints() + " XP");
        pointsLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        pointsLabel.setTextFill(Color.GREENYELLOW);

        Button watchBtn = new Button("▶ Launch Course");
        watchBtn.setStyle("-fx-background-color: #00FFFF; -fx-text-fill: #0D1117; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");
        watchBtn.setMaxWidth(Double.MAX_VALUE);

        // تصليح الـ Threading الخاص بفتح المتصفح لحماية الواجهة من الـ Freezing والـ Blocks
        watchBtn.setOnAction(e -> {
            if (course.getCourseLink() != null && !course.getCourseLink().isEmpty()) {
                new Thread(() -> {
                    try {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            Desktop.getDesktop().browse(new URI(course.getCourseLink()));
                        }
                    } catch (Exception ex) {
                        System.err.println("Could not open link inside thread: " + course.getCourseLink());
                    }
                }).start();
            }
        });

        HBox footer = new HBox(course.isHasCertificate() ? new Label("📜 Cert") {{ setTextFill(Color.CYAN); setFont(Font.font(10)); }} : new Label());
        footer.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(catLabel, titleLabel, instructorLabel, pointsLabel, watchBtn, footer);
        
        // تأثير خفيف عند الـ Hover ليتناسب مع جودة كروت الـ Dashboard بتاعتك
        card.setOnMouseEntered(ev -> card.setStyle("-fx-background-color: #2D3748; -fx-background-radius: 10; -fx-border-color: #00FFFF; -fx-border-radius: 10; -fx-border-width: 1.5; -fx-scale-x: 1.02; -fx-scale-y: 1.02;"));
        card.setOnMouseExited(ev -> card.setStyle("-fx-background-color: #1F2937; -fx-background-radius: 10; -fx-border-color: #00FFFF; -fx-border-radius: 10; -fx-border-width: 1; -fx-scale-x: 1; -fx-scale-y: 1;"));
        
        return card;
    }

    /**
     * تم تعديل بناء الـ Scene بالكامل لكي ترث الـ View نفس الـ Sidebar والـ Glass Container 
     * والـ Gradient خلفية الموجودة في الـ Control Center عشان الثيم والـ Layout ميفصلش من المستخدم.
     */
    public Scene getScene() {
        StackPane rootStack = new StackPane();
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #05070B, #0B0F17, #070A12);");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.96;
        double height = screenBounds.getHeight() * 0.95;

        StackPane glassContainer = new StackPane();
        glassContainer.setPadding(new Insets(10));
        glassContainer.setStyle(
                "-fx-background-color: rgba(12,18,28,0.68);" +
                "-fx-background-radius: 28;" +
                "-fx-border-color: rgba(0,255,255,0.18);" +
                "-fx-border-width: 1.2;" +
                "-fx-border-radius: 28;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.08), 40, 0, 0, 0);"
        );

        BorderPane internalLayout = new BorderPane();

        // تمرير نفس تصميم الـ Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(28, 20, 25, 20));
        sidebar.setPrefWidth(255);
        sidebar.setStyle("-fx-background-color: rgba(10,15,23,0.82); -fx-background-radius: 24 0 0 24; -fx-border-color: rgba(255,255,255,0.05); -fx-border-width: 0 1 0 0;");

        VBox logoBox = new VBox(8);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(0,0,18,0));
        Label shield = new Label("🛡"); shield.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32)); shield.setTextFill(Color.web("#00FFFF")); shield.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.8), 18, 0, 0, 0);");
        Label logo = new Label("CYBER\nJOCX"); logo.setTextFill(Color.web("#66FFF2")); logo.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 25)); logo.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.5), 15, 0, 0, 0);");
        logoBox.getChildren().addAll(shield, logo);

        Button dashboardBtn = new Button("⌂  Dashboard");
        dashboardBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #AAB7C8; -fx-background-radius: 12; -fx-font-size: 14px; -fx-alignment: center-left; -fx-padding: 0 0 0 18; -fx-cursor: hand;");
        dashboardBtn.setMaxWidth(Double.MAX_VALUE); dashboardBtn.setPrefHeight(46);
        dashboardBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));

        sidebar.getChildren().addAll(logoBox, dashboardBtn);
        internalLayout.setLeft(sidebar);
        
        // حقن شبكة الكروت بداخل السكرول الرئيسي في السنتر
        internalLayout.setCenter(getView());
        
        glassContainer.getChildren().add(internalLayout);
        root.setCenter(glassContainer);
        BorderPane.setMargin(glassContainer, new Insets(20));
        rootStack.getChildren().add(root);

        return new Scene(rootStack, width, height);
    }
}
