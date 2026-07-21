package com.mycompany.cyberjocx;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.util.List;

public class HomeDashboard {

    private final PostService postService = new PostService();
    private final ProfileService profileService = new ProfileService();
    private final CourseController courseController = new CourseController(); 

    public Scene getScene() {
        StackPane rootStack = new StackPane();
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #05070B, #0B0F17, #070A12);");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.96;
        double height = screenBounds.getHeight() * 0.95;

        // =========================================================
        // MAIN GLASS CONTAINER
        // =========================================================
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

        // =========================================================
        // SIDEBAR
        // =========================================================
        VBox sidebar = new VBox(15); 
        sidebar.setPadding(new Insets(28, 20, 25, 20));
        sidebar.setPrefWidth(255);
        sidebar.setStyle(
                "-fx-background-color: rgba(10,15,23,0.82);" +
                "-fx-background-radius: 24 0 0 24;" +
                "-fx-border-color: rgba(255,255,255,0.05);" +
                "-fx-border-width: 0 1 0 0;"
        );

        VBox logoBox = new VBox(8);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(0,0,18,0));

        Label shield = new Label("🛡");
        shield.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        shield.setTextFill(Color.web("#00FFFF"));
        shield.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.8), 18, 0, 0, 0);");

        javafx.scene.text.Text textCyber = new javafx.scene.text.Text("CYBER\n");
        textCyber.setFill(Color.WHITE);
        textCyber.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 25));

        javafx.scene.text.Text textJocx = new javafx.scene.text.Text("JOCX");
        textJocx.setFill(Color.web("#00DFFF"));
        textJocx.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 25));

        javafx.scene.text.TextFlow logo = new javafx.scene.text.TextFlow(textCyber, textJocx);
        logo.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.5), 15, 0, 0, 0);");
        logoBox.getChildren().addAll(shield, logo);

        Button dashboardBtn = createSidebarButton("⌂  Dashboard");
        Button profileBtn = createSidebarButton("👤  Hologram Profile");
        Button CVEBtn = createSidebarButton("◎  CVE Radar");
        Button marketBtn = createSidebarButton("?  Book Market");
        Button academyBtn = createSidebarButton("🎓  Academy"); 
        Button roadmapBtn = createSidebarButton("🚀  Career Roadmaps"); 
        Button aboutBtn = createSidebarButton("ℹ  About");
        Button logoutBtn = createSidebarButton("⎋  Log out");

        dashboardBtn.setStyle(
                "-fx-background-color: rgba(120,140,170,0.18);" +
                "-fx-text-fill: #EAFBFF;" +
                "-fx-background-radius: 12;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-alignment: center-left;" +
                "-fx-padding: 0 0 0 18;" +
                "-fx-border-color: rgba(0,255,255,0.35);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 12;" +
                "-fx-cursor: hand;"
        );

        dashboardBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));
        profileBtn.setOnAction(e -> Navigation.go(new ProfileView().getScene(Session.getUserId())));
        CVEBtn.setOnAction(e -> Navigation.go(new CVEView().getScene()));
        marketBtn.setOnAction(e -> Navigation.go(new MarketView().getScene()));
        academyBtn.setOnAction(e -> Navigation.go(new CourseCollectionView().getScene())); 
        roadmapBtn.setOnAction(e -> Navigation.go(new RoadmapCollectionWindow().getViewAsScene())); 
        logoutBtn.setOnAction(e -> Navigation.go(new LoginView().getScene()));
        aboutBtn.setOnAction(e -> Navigation.go(new AboutPage().getScene()));

        sidebar.getChildren().addAll(logoBox, dashboardBtn, profileBtn, CVEBtn, marketBtn, academyBtn, roadmapBtn, aboutBtn);
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        logoutBtn.setStyle("-fx-background-color: rgba(255,255,255,0.08); -fx-text-fill: white; -fx-background-radius: 12; -fx-font-size: 14px; -fx-alignment: center; -fx-cursor: hand;");
        sidebar.getChildren().addAll(spacer, logoutBtn);

        // =========================================================
        // MAIN CONTENT
        // =========================================================
        VBox main = new VBox(25);
        main.setPadding(new Insets(28, 35, 30, 35));
        main.setAlignment(Pos.TOP_CENTER);

        ScrollPane mainScroll = new ScrollPane(main);
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        // =========================================================
        // FIXED HEADER
        // =========================================================
        int totalCVEs = AnalyticsService.getTotalCVEs();
        RiskLevel risk = AnalyticsService.calculateRisk(totalCVEs);
        int totalCoursesCount = courseController.getCoursesCount(); 

        GridPane headerGrid = new GridPane();
        headerGrid.setAlignment(Pos.CENTER_LEFT);
        headerGrid.setHgap(40);
        headerGrid.setMaxWidth(980);
        headerGrid.setStyle("-fx-padding: 10 0 20 0; -fx-border-color: rgba(255,255,255,0.05); -fx-border-width: 0 0 1 0;");

        VBox hero = new VBox(2);
        Label title = new Label("Cyber JocX");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        Label sub = new Label("Control Center");
        sub.setTextFill(Color.web("#8B9DB5"));
        sub.setFont(Font.font("Segoe UI", 14));
        hero.getChildren().addAll(title, sub);
        headerGrid.add(hero, 0, 0);

        headerGrid.add(createHeaderInfoBlock("Risk Level:", risk.toString(), "#FF5B45"), 1, 0);
        headerGrid.add(createHeaderInfoBlock("Total CVEs:", String.valueOf(totalCVEs), "#7EDBFF"), 2, 0);

        // DYNAMIC POINTS BLOCK IN HEADER
        VBox pointsBlock = new VBox(2);
        pointsBlock.setAlignment(Pos.CENTER_LEFT);
        Label pointsLbl = new Label("Points:"); 
        pointsLbl.setTextFill(Color.web("#8B9DB5")); 
        pointsLbl.setFont(Font.font("Segoe UI", 12));

        Label pointsVal = new Label(); 
        pointsVal.setTextFill(Color.web("#7BFF8C")); 
        pointsVal.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        pointsVal.textProperty().bind(PointService.getInstance().pointsProperty().asString());

        pointsBlock.getChildren().addAll(pointsLbl, pointsVal);
        headerGrid.add(pointsBlock, 3, 0);

        headerGrid.add(createHeaderInfoBlock("Courses:", String.valueOf(totalCoursesCount), "#FFD86A"), 4, 0);

        ColumnConstraints col0 = new ColumnConstraints(); col0.setHgrow(Priority.ALWAYS);
        headerGrid.getColumnConstraints().addAll(col0);

        HBox metricHeader = new HBox();
        metricHeader.setAlignment(Pos.CENTER_LEFT);
        metricHeader.setMaxWidth(980);
        Label metricTitle = new Label("Key Metrics");
        metricTitle.setTextFill(Color.WHITE);
        metricTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        metricHeader.getChildren().add(metricTitle);

        // =========================================================
        // STATS CARDS WITH CYBERPUNK HUD GRAPHICS
        // =========================================================
        HBox stats = new HBox(15);
        stats.setAlignment(Pos.CENTER);
        stats.setMaxWidth(980);

        VBox riskCard = createImageStatCard("Risk Level:", risk.toString(), "CVE hot spot", createVectorGlobeHUD(), "#FF5B45");
        VBox cveCard = createImageStatCard("Total CVEs:", String.valueOf(totalCVEs), "Attack vector", createAttackVectorHUD(), "#7EDBFF");

        // DYNAMIC POINTS CARD WITH QUANTUM CRYSTAL HUD
        VBox pointsCard = new VBox(5);
        pointsCard.setAlignment(Pos.TOP_LEFT);
        pointsCard.setPadding(new Insets(16));
        pointsCard.setPrefSize(225, 145);
        pointsCard.setStyle("-fx-background-color: rgba(20,28,40,0.82); -fx-background-radius: 18; -fx-border-color: rgba(255,255,255,0.07); -fx-border-width: 1; -fx-border-radius: 18;");

        Label cardTitle = new Label("Points:"); 
        cardTitle.setTextFill(Color.WHITE); 
        cardTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));

        Label cardValue = new Label(); 
        cardValue.setTextFill(Color.web("#7BFF8C")); 
        cardValue.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 28));
        cardValue.setStyle("-fx-effect: dropshadow(three-pass-box, #7BFF8C, 15, 0, 0, 0);");
        cardValue.textProperty().bind(PointService.getInstance().pointsProperty().asString());

        Label cardSub = new Label("Dynamic balance"); 
        cardSub.setTextFill(Color.web("#7A8A9E")); 
        cardSub.setFont(Font.font("Segoe UI", 11));

        HBox crystalGraphicBox = new HBox(createQuantumCrystalHUD());
        crystalGraphicBox.setAlignment(Pos.BOTTOM_RIGHT);
        VBox.setVgrow(crystalGraphicBox, Priority.ALWAYS);

        pointsCard.getChildren().addAll(cardTitle, cardValue, cardSub, crystalGraphicBox);
        pointsCard.setOnMouseEntered(e -> pointsCard.setStyle("-fx-background-color: rgba(24,34,50,0.92); -fx-background-radius: 18; -fx-border-color: rgba(0,255,255,0.28); -fx-border-width: 1; -fx-border-radius: 18; -fx-scale-x: 1.02; -fx-scale-y: 1.02;"));
        pointsCard.setOnMouseExited(e -> pointsCard.setStyle("-fx-background-color: rgba(20,28,40,0.82); -fx-background-radius: 18; -fx-border-color: rgba(255,255,255,0.07); -fx-border-width: 1; -fx-border-radius: 18; -fx-scale-x: 1; -fx-scale-y: 1;"));

        VBox coursesCard = createImageStatCard("Courses:", String.valueOf(totalCoursesCount), "Skill tree", createNeuralShockwaveHUD(), "#FFD86A");

        stats.getChildren().addAll(riskCard, cveCard, pointsCard, coursesCard);

        // =========================================================
        // MODULES CARDS
        // =========================================================
        FlowPane modules = new FlowPane();
        modules.setHgap(15);
        modules.setVgap(15);
        modules.setAlignment(Pos.CENTER);
        modules.setMaxWidth(980);

        modules.getChildren().addAll(
                createModuleCard("AI Controller", createAttackVectorHUD()),
                createModuleCard("CVE Search", createVectorGlobeHUD()),
                createModuleCard("Courses", createAcademyGridHUD()),       
                createModuleCard("Tools", createHardwareTerminalHUD()),     
                createModuleCard("Book Market", createDigitalLibraryHUD())  
        );

        // Recent Activity Section
        VBox activity = new VBox(12);
        activity.setPadding(new Insets(20));
        activity.setMaxWidth(980);
        activity.setStyle("-fx-background-color: rgba(16,22,34,0.72); -fx-background-radius: 18; -fx-border-color: rgba(255,255,255,0.06); -fx-border-width: 1; -fx-border-radius: 18;");
        Label actTitle = new Label("Recent Activity");
        actTitle.setTextFill(Color.WHITE);
        actTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        activity.getChildren().addAll(actTitle, createActivityLabel("• Vulnerability Synced"), createActivityLabel("• CVE Database Synced"), createActivityLabel("• Market Loaded Successfully"));

        // Latest Posts Section (تم إلغاء الـ loop هنا وعرض آخر بوست منفرد فقط)
        VBox postsSection = new VBox(15);
        postsSection.setMaxWidth(980);
        Label postsTitle = new Label("Latest Posts");
        postsTitle.setTextFill(Color.WHITE);
        postsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        postsSection.getChildren().add(postsTitle);

        int currentUserId = Session.getUserId();
        List<Post> userPosts = postService.getPostsByUserId(currentUserId);
        
        // التحقق من وجود بوستات بدون عمل loop تكراري
        if (userPosts == null || userPosts.isEmpty()) {
            Label empty = new Label("No posts yet."); 
            empty.setTextFill(Color.web("#64748B")); 
            postsSection.getChildren().add(empty);
        } else { 
            // (أحدث بوست)
            Post latestPost = userPosts.get(0); 
            postsSection.getChildren().add(createEnhancedPostCard(latestPost)); 
        }

        main.getChildren().addAll(headerGrid, metricHeader, stats, modules, activity, postsSection);
        internalLayout.setLeft(sidebar);
        internalLayout.setCenter(mainScroll);
        glassContainer.getChildren().add(internalLayout);
        root.setCenter(glassContainer);
        BorderPane.setMargin(glassContainer, new Insets(20));
        rootStack.getChildren().add(root);

        // Floating AI Trigger Button
        Button aiBotBtn = new Button("🤖");
        aiBotBtn.setPrefSize(65, 65);
        aiBotBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #00FFFF, #0099AA); -fx-text-fill: #071018; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 50; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.8), 20, 0, 0, 0);");
        aiBotBtn.setOnAction(e -> Navigation.go(new AIView().getScene()));
        StackPane.setAlignment(aiBotBtn, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(aiBotBtn, new Insets(0, 40, 40, 0));
        rootStack.getChildren().add(aiBotBtn);

        return new Scene(rootStack, width, height);
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(46);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #AAB7C8; -fx-background-radius: 12; -fx-font-size: 14px; -fx-alignment: center-left; -fx-padding: 0 0 0 18; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: rgba(255,255,255,0.04); -fx-text-fill: white; -fx-background-radius: 12; -fx-font-size: 14px; -fx-alignment: center-left; -fx-padding: 0 0 0 18; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> { 
            if (!btn.getText().contains("Dashboard")) {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #AAB7C8; -fx-background-radius: 12; -fx-font-size: 14px; -fx-alignment: center-left; -fx-padding: 0 0 0 18; -fx-cursor: hand;"); 
            }
        });
        return btn;
    }

    private VBox createHeaderInfoBlock(String labelText, String valueText, String hexColor) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER_LEFT);
        Label lbl = new Label(labelText); lbl.setTextFill(Color.web("#8B9DB5")); lbl.setFont(Font.font("Segoe UI", 12));
        Label val = new Label(valueText); val.setTextFill(Color.web(hexColor)); val.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        box.getChildren().addAll(lbl, val);
        return box;
    }

    private Node createAcademyGridHUD() {
        StackPane container = new StackPane();
        container.setPrefSize(50, 50);
        
        Polygon cap = new Polygon(25.0, 12.0, 40.0, 20.0, 25.0, 28.0, 10.0, 20.0);
        cap.setFill(Color.TRANSPARENT);
        cap.setStroke(Color.web("#FFD86A"));
        cap.setStrokeWidth(1.5);
        cap.setStyle("-fx-effect: dropshadow(three-pass-box, #FFD86A, 10, 0, 0, 0);");

        Line thread = new Line(40, 20, 40, 32);
        thread.setStroke(Color.web("#FFD86A"));
        thread.setStrokeWidth(1.2);

        Circle node1 = new Circle(25, 36, 4, Color.TRANSPARENT);
        node1.setStroke(Color.web("#FFC107"));
        node1.setStrokeWidth(1);
        
        ScaleTransition scale = new ScaleTransition(Duration.millis(1200), cap);
        scale.setFromX(0.95); scale.setFromY(0.95); scale.setToX(1.05); scale.setToY(1.05);
        scale.setAutoReverse(true); scale.setCycleCount(Animation.INDEFINITE); scale.play();

        container.getChildren().addAll(cap, thread, node1);
        return container;
    }

    private Node createHardwareTerminalHUD() {
        StackPane container = new StackPane();
        container.setPrefSize(50, 50);

        Circle gearBody = new Circle(16, Color.TRANSPARENT);
        gearBody.setStroke(Color.web("#7BFF8C"));
        gearBody.setStrokeWidth(2.5);
        gearBody.getStrokeDashArray().addAll(6.0, 6.0); 
        gearBody.setStyle("-fx-effect: dropshadow(three-pass-box, #7BFF8C, 12, 0, 0, 0);");

        Rectangle coreKey = new Rectangle(6, 12, Color.web("#FFFFFF"));
        coreKey.setArcWidth(4); coreKey.setArcHeight(4);

        Group dynamicGear = new Group(gearBody);
        RotateTransition spin = new RotateTransition(Duration.millis(3500), dynamicGear);
        spin.setByAngle(360); spin.setCycleCount(Animation.INDEFINITE); spin.setInterpolator(Interpolator.LINEAR); spin.play();

        container.getChildren().addAll(dynamicGear, coreKey);
        return container;
    }

    private Node createDigitalLibraryHUD() {
        StackPane container = new StackPane();
        container.setPrefSize(50, 50);

        Circle borderRing = new Circle(22, Color.TRANSPARENT);
        borderRing.setStroke(Color.web("#00FFFF", 0.4));
        borderRing.setStrokeWidth(1);
        borderRing.getStrokeDashArray().addAll(4.0, 8.0);

        Path bookLeft = new Path(
                new MoveTo(25, 38),
                new LineTo(12, 34),
                new LineTo(12, 18),
                new LineTo(25, 22),
                new ClosePath()
        );
        bookLeft.setFill(new LinearGradient(0,0,1,1,true,CycleMethod.NO_CYCLE, new Stop(0, Color.web("#00FFFF", 0.6)), new Stop(1, Color.TRANSPARENT)));
        bookLeft.setStroke(Color.web("#66FFF2")); bookLeft.setStrokeWidth(1.2);

        Path bookRight = new Path(
                new MoveTo(25, 38),
                new LineTo(38, 34),
                new LineTo(38, 18),
                new LineTo(25, 22),
                new ClosePath()
        );
        bookRight.setFill(new LinearGradient(0,0,1,1,true,CycleMethod.NO_CYCLE, new Stop(0, Color.TRANSPARENT), new Stop(1, Color.web("#00FFFF", 0.6))));
        bookRight.setStroke(Color.web("#66FFF2")); bookRight.setStrokeWidth(1.2);

        Group hologramBook = new Group(bookLeft, bookRight);
        TranslateTransition floatAnim = new TranslateTransition(Duration.millis(1000), hologramBook);
        floatAnim.setFromY(-2); floatAnim.setToY(2); floatAnim.setAutoReverse(true); floatAnim.setCycleCount(Animation.INDEFINITE); floatAnim.play();

        container.getChildren().addAll(borderRing, hologramBook);
        return container;
    }

    private Node createVectorGlobeHUD() {
        StackPane canvas = new StackPane();
        canvas.setPrefSize(50, 50);
        Circle baseGlobe = new Circle(22, Color.TRANSPARENT);
        baseGlobe.setStroke(new LinearGradient(0,0,1,1,true,CycleMethod.NO_CYCLE, new Stop(0, Color.web("#FF5B45")), new Stop(1, Color.web("#881100"))));
        baseGlobe.setStrokeWidth(1.5);
        baseGlobe.setStyle("-fx-effect: dropshadow(three-pass-box, #FF5B45, 12, 0, 0, 0);");
        Ellipse latLines = new Ellipse(22, 7); latLines.setFill(Color.TRANSPARENT); latLines.setStroke(Color.web("#FF8B7B", 0.6)); latLines.setStrokeWidth(1);
        Ellipse lonLines = new Ellipse(7, 22); lonLines.setFill(Color.TRANSPARENT); lonLines.setStroke(Color.web("#FF8B7B", 0.6)); lonLines.setStrokeWidth(1);
        Group dynamicGroup = new Group(latLines, lonLines);
        RotateTransition rotate = new RotateTransition(Duration.millis(2500), dynamicGroup); rotate.setByAngle(360); rotate.setCycleCount(Animation.INDEFINITE); rotate.setInterpolator(Interpolator.LINEAR); rotate.play();
        Circle activeCore = new Circle(4, Color.web("#FFFFFF")); activeCore.setStyle("-fx-effect: dropshadow(three-pass-box, #FF0000, 15, 0, 0, 0);");
        ScaleTransition pulse = new ScaleTransition(Duration.millis(800), activeCore); pulse.setFromX(0.7); pulse.setFromY(0.7); pulse.setToX(1.4); pulse.setToY(1.4); pulse.setCycleCount(Animation.INDEFINITE); pulse.setAutoReverse(true); pulse.play();
        canvas.getChildren().addAll(baseGlobe, dynamicGroup, activeCore);
        return canvas;
    }

    private Node createAttackVectorHUD() {
        AnchorPane netPane = new AnchorPane(); netPane.setPrefSize(55, 50);
        Line rootLine1 = new Line(10, 25, 28, 12); Line rootLine2 = new Line(10, 25, 28, 38);
        rootLine1.setStroke(Color.web("#7EDBFF", 0.5)); rootLine2.setStroke(Color.web("#7EDBFF", 0.5));
        Circle masterNode = new Circle(10, 25, 5, Color.web("#00FFFF")); Circle subNode1 = new Circle(28, 12, 3, Color.web("#7EDBFF")); Circle subNode2 = new Circle(28, 38, 3, Color.web("#7EDBFF"));
        masterNode.setStyle("-fx-effect: dropshadow(three-pass-box, #00FFFF, 10, 0, 0, 0);");
        Timeline liveTraffic = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(subNode1.fillProperty(), Color.web("#7EDBFF")), new KeyValue(subNode2.fillProperty(), Color.web("#004466"))),
                new KeyFrame(Duration.millis(500), new KeyValue(subNode1.fillProperty(), Color.web("#004466")), new KeyValue(subNode2.fillProperty(), Color.web("#7EDBFF"))),
                new KeyFrame(Duration.millis(1000),  new KeyValue(subNode1.fillProperty(), Color.web("#7EDBFF")), new KeyValue(subNode2.fillProperty(), Color.web("#004466")))
        );
        liveTraffic.setCycleCount(Animation.INDEFINITE); liveTraffic.play();
        netPane.getChildren().addAll(rootLine1, rootLine2, masterNode, subNode1, subNode2);
        return netPane;
    }

    private Node createQuantumCrystalHUD() {
        StackPane container = new StackPane(); container.setPrefSize(50, 50);
        Polygon outerPoly = new Polygon(25.0, 5.0, 42.0, 18.0, 42.0, 37.0, 25.0, 48.0, 7.0, 37.0, 7.0, 18.0);
        outerPoly.setFill(Color.TRANSPARENT); outerPoly.setStroke(Color.web("#7BFF8C")); outerPoly.setStrokeWidth(1.2);
        Polygon innerPoly = new Polygon(25.0, 12.0, 36.0, 21.0, 36.0, 33.0, 25.0, 40.0, 14.0, 33.0, 14.0, 21.0);
        innerPoly.setFill(new LinearGradient(0,0,1,1,true,CycleMethod.NO_CYCLE, new Stop(0, Color.web("#7BFF8C", 0.5)), new Stop(1, Color.TRANSPARENT))); innerPoly.setStroke(Color.web("#00FF22"));
        Group crystalStructure = new Group(outerPoly, innerPoly);
        RotateTransition spin = new RotateTransition(Duration.millis(4000), crystalStructure); spin.setByAngle(360); spin.setCycleCount(Animation.INDEFINITE); spin.setInterpolator(Interpolator.LINEAR); spin.play();
        ScaleTransition scale = new ScaleTransition(Duration.millis(1500), crystalStructure); scale.setFromX(0.92); scale.setFromY(0.92); scale.setToX(1.05); scale.setToY(1.05); scale.setAutoReverse(true); scale.setCycleCount(Animation.INDEFINITE); scale.play();
        container.getChildren().add(crystalStructure);
        return container;
    }

    private Node createNeuralShockwaveHUD() {
        StackPane ringContainer = new StackPane(); ringContainer.setPrefSize(50, 50);
        Circle coreCenter = new Circle(6, Color.web("#FFD86A")); coreCenter.setStyle("-fx-effect: dropshadow(three-pass-box, #FFD86A, 15, 0, 0, 0);");
        Circle wave1 = new Circle(8, Color.TRANSPARENT); wave1.setStroke(Color.web("#FFD86A", 0.8)); wave1.setStrokeWidth(1.5);
        Circle wave2 = new Circle(8, Color.TRANSPARENT); wave2.setStroke(Color.web("#FFC107", 0.4)); wave2.setStrokeWidth(1);
        Timeline waveAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(wave1.radiusProperty(), 6), new KeyValue(wave1.opacityProperty(), 1.0), new KeyValue(wave2.radiusProperty(), 6), new KeyValue(wave2.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(1200), new KeyValue(wave1.radiusProperty(), 24), new KeyValue(wave1.opacityProperty(), 0.0), new KeyValue(wave2.radiusProperty(), 16), new KeyValue(wave2.opacityProperty(), 0.3)),
                new KeyFrame(Duration.millis(2000), new KeyValue(wave2.radiusProperty(), 25), new KeyValue(wave2.opacityProperty(), 0.0))
        );
        waveAnimation.setCycleCount(Animation.INDEFINITE); waveAnimation.play();
        ringContainer.getChildren().addAll(wave1, wave2, coreCenter);
        return ringContainer;
    }

    private VBox createImageStatCard(String title, String value, String sub, Node graphicIcon, String glow) {
        VBox card = new VBox(5); card.setAlignment(Pos.TOP_LEFT); card.setPadding(new Insets(16)); card.setPrefSize(225, 145);
        card.setStyle("-fx-background-color: rgba(20,28,40,0.82); -fx-background-radius: 18; -fx-border-color: rgba(255,255,255,0.07); -fx-border-width: 1; -fx-border-radius: 18;");
        Label titleLabel = new Label(title); titleLabel.setTextFill(Color.WHITE); titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        Label valueLabel = new Label(value); valueLabel.setTextFill(Color.web(glow)); valueLabel.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 28));
        valueLabel.setStyle("-fx-effect: dropshadow(three-pass-box, " + glow + ", 15, 0, 0, 0);");
        Label subLabel = new Label(sub); subLabel.setTextFill(Color.web("#7A8A9E")); subLabel.setFont(Font.font("Segoe UI", 11));
        HBox graphicBox = new HBox(graphicIcon); graphicBox.setAlignment(Pos.BOTTOM_RIGHT); VBox.setVgrow(graphicBox, Priority.ALWAYS);
        card.getChildren().addAll(titleLabel, valueLabel, subLabel, graphicBox);
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: rgba(24,34,50,0.92); -fx-background-radius: 18; -fx-border-color: rgba(0,255,255,0.28); -fx-border-width: 1; -fx-border-radius: 18; -fx-scale-x: 1.02; -fx-scale-y: 1.02;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: rgba(20,28,40,0.82); -fx-background-radius: 18; -fx-border-color: rgba(255,255,255,0.07); -fx-border-width: 1; -fx-border-radius: 18; -fx-scale-x: 1; -fx-scale-y: 1;"));
        return card;
    }

    private VBox createModuleCard(String name, Node graphicIcon) {
        VBox card = new VBox(14); card.setAlignment(Pos.CENTER); card.setPrefSize(175, 130);
        card.setStyle("-fx-background-color: rgba(18,25,38,0.55); -fx-background-radius: 16; -fx-border-color: rgba(255,255,255,0.05); -fx-border-width: 1; -fx-border-radius: 16; -fx-cursor: hand;");
        Label title = new Label(name); title.setTextFill(Color.WHITE); title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        card.getChildren().addAll(graphicIcon, title);
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: rgba(25,36,54,0.75); -fx-background-radius: 16; -fx-border-color: rgba(0,255,255,0.28); -fx-border-width: 1; -fx-border-radius: 16; -fx-scale-x: 1.03; -fx-scale-y: 1.03; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: rgba(18,25,38,0.55); -fx-background-radius: 16; -fx-border-color: rgba(255,255,255,0.05); -fx-border-width: 1; -fx-border-radius: 16; -fx-scale-x: 1; -fx-scale-y: 1; -fx-cursor: hand;"));
        card.setOnMouseClicked(e -> {
            switch (name) {
                case "Book Market" -> Navigation.go(new MarketView().getScene());
                case "CVE Search" -> Navigation.go(new CVEView().getScene());
                case "AI Controller" -> Navigation.go(new AIView().getScene());
                case "Tools" -> Navigation.go(new ToolsView().getScene());
                case "Courses" -> Navigation.go(new CourseCollectionView().getScene()); 
            }
        });
        return card;
    }

    private Label createActivityLabel(String text) { Label l = new Label(text); l.setTextFill(Color.web("#A5B3C5")); l.setFont(Font.font("Consolas", 13)); return l; }

    private VBox createEnhancedPostCard(Post post) {
        VBox card = new VBox(14); card.setPadding(new Insets(18)); card.setMaxWidth(980);
        card.setStyle("-fx-background-color: rgba(18,25,38,0.55); -fx-background-radius: 16; -fx-border-color: rgba(255,255,255,0.05); -fx-border-width: 1; -fx-border-radius: 16;");
        Label content = new Label(post.getContent()); content.setWrapText(true); content.setTextFill(Color.web("#E5EEF8")); content.setFont(Font.font("Segoe UI", 14));
        card.getChildren().add(content);

        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            try {
                ImageView iv = new ImageView(new Image(post.getImageUrl(), true)); iv.setFitWidth(880); iv.setPreserveRatio(true);
                Rectangle clip = new Rectangle(); clip.setArcWidth(18); clip.setArcHeight(18);
                iv.layoutBoundsProperty().addListener((o, old, b) -> { clip.setWidth(b.getWidth()); clip.setHeight(b.getHeight()); });
                iv.setClip(clip); card.getChildren().add(iv);
            } catch (Exception e) { System.out.println("Image missing"); }
        }
        HBox footer = new HBox(15); footer.setAlignment(Pos.CENTER_LEFT);
        Label likes = new Label("❤ " + post.getLikesCount()); likes.setTextFill(Color.web("#66FFF2")); likes.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        
        // DYNAMIC POINTS TRIGGER ON LIKE CLICK
        likes.setOnMouseClicked(e -> { 
            postService.likePost(post.getId(), Session.getUserId()); 
            likes.setText("❤ " + (post.getLikesCount() + 1)); 
            PointService.getInstance().addPoints(5); 
        });
        
        Label time = new Label(post.getTimestamp()); time.setTextFill(Color.web("#64748B")); time.setFont(Font.font("Segoe UI", 12));
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS); footer.getChildren().addAll(likes, sp, time); card.getChildren().add(footer);
        return card;
    }
}
