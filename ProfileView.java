package com.mycompany.cyberjocx;

import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.util.List;

public class ProfileView {

    private final ProfileService profileService = new ProfileService();
    private final PostService postService = new PostService();
    private final FollowService followService = new FollowService();

    public Scene getScene(int userId) {

        // ================= DATA =================
        Profile profile = profileService.getProfileByUserId(userId);
        List<Post> userPosts = postService.getPostsByUserId(userId);
        int followersCount = followService.getFollowersCount(userId);

        String currentUsername = "Member";
        if (profile != null && profile.getUsername() != null) {
            currentUsername = profile.getUsername();
        } else if (Session.getUsername() != null) {
            currentUsername = Session.getUsername();
        }

        BorderPane root = new BorderPane();
        // خلفية سيبرانية عميقة مطابقة للداشبورد الرئيسي
        root.setStyle("-fx-background-color: #05070B;");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.96;
        double height = screenBounds.getHeight() * 0.95;

        // ================= MODERN SIDEBAR =================
        VBox sidebar = new VBox(25);
        sidebar.setPadding(new Insets(40, 25, 25, 25));
        sidebar.setPrefWidth(300);
        sidebar.setStyle(
            "-fx-background-color: rgba(10, 15, 28, 0.85);" +
            "-fx-border-color: rgba(0, 255, 255, 0.1);" +
            "-fx-border-width: 0 1.5 0 0;"
        );

        // هالة نيون مضيئة حول الصورة الشخصية
        Circle profilePic = new Circle(45, Color.web("#00FFFF"));
        profilePic.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.4), 15, 0, 0, 0);");
        
        Label nameLabel = new Label(currentUsername);
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        nameLabel.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.2), 5, 0, 0, 0);");

        VBox userHeader = new VBox(12, profilePic, nameLabel);
        userHeader.setAlignment(Pos.CENTER);

        VBox infoBox = new VBox(18);
        infoBox.setPadding(new Insets(10, 5, 10, 5));

        if (profile != null) {
            infoBox.getChildren().addAll(
                    createSidebarItem("📧 Email Address", profile.getEmail()),
                    createSidebarItem("🛡️ Core Role", profile.getRole()),
                    createSidebarItem("⚡ Operational Status",
                            profile.getPoints() > 100 ? "Active Hacker" : "Apprentice")
            );
        } else {
            infoBox.getChildren().addAll(
                    createSidebarItem("🆔 Account ID", "#" + userId),
                    createSidebarItem("⚠️ Operational Status", "Profile not initialized")
            );
        }

        // فاصل نيون أنيق
        Separator sideSeparator = new Separator();
        sideSeparator.setStyle("-fx-opacity: 0.1; -fx-background-color: #00FFFF;");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // زرار الرجوع بتصميم هولوجرام متناسق
        Button backBtn = new Button("⬅ System Dashboard");
        backBtn.setMaxWidth(Double.MAX_VALUE);
        backBtn.setStyle(
            "-fx-background-color: rgba(0, 255, 255, 0.05);" +
            "-fx-text-fill: #00FFFF;" +
            "-fx-border-color: rgba(0, 255, 255, 0.3);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 10;"
        );
        backBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.2); -fx-text-fill: white; -fx-border-color: #00FFFF; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.05); -fx-text-fill: #00FFFF; -fx-border-color: rgba(0, 255, 255, 0.3); -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10;"));

        sidebar.getChildren().addAll(userHeader, sideSeparator, infoBox, spacer, backBtn);

        // ================= MODERN MAIN CONTENT =================
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(30, 50, 20, 50));
        mainContent.setStyle("-fx-background-color: transparent;");

        // ================= STATS ROW =================
        HBox quickStats = new HBox(25);
        quickStats.setAlignment(Pos.CENTER_LEFT);
        quickStats.getChildren().addAll(
                createMiniStat("Total Intelligence Posts", String.valueOf(userPosts != null ? userPosts.size() : 0)),
                createMiniStat("Network Followers", String.valueOf(followersCount)),
                createMiniStat("Terminal Rank Score", (profile != null ? profile.getPoints() : 0) + " XP")
        );

        Label feedTitle = new Label("⚡ " + currentUsername + "'s Cyber Feed");
        feedTitle.setTextFill(Color.web("#00FFFF"));
        feedTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        feedTitle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.2), 8, 0, 0, 0);");

        // ================= CREATE POST BOX (GLASSMORPHISM) =================
        VBox createPostBox = new VBox(12);
        createPostBox.setPadding(new Insets(22));
        createPostBox.setStyle(
                "-fx-background-color: rgba(20, 30, 48, 0.4);" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: rgba(0, 255, 255, 0.1);" +
                "-fx-border-width: 1.5;"
        );

        Label createTitle = new Label("Broadcast New Log");
        createTitle.setTextFill(Color.WHITE);
        createTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        TextArea postInput = new TextArea();
        postInput.setPromptText("Type your security update or thought into the matrix...");
        postInput.setWrapText(true);
        postInput.setPrefHeight(90);
        postInput.setStyle(
                "-fx-control-inner-background: #0A0F1A;" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: #484f58;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: rgba(0, 255, 255, 0.1);" +
                "-fx-border-radius: 8;" +
                "-fx-focused-border-color: #00FFFF;"
        );

        // شريط سفلي للتحكم جوه الـ Create Post
        HBox controlRow = new HBox(15);
        controlRow.setAlignment(Pos.CENTER_RIGHT);

        Label postStatus = new Label();
        postStatus.setTextFill(Color.web("#00FF66"));
        postStatus.setFont(Font.font("Segoe UI", 13));

        Region postSpacer = new Region();
        HBox.setHgrow(postSpacer, Priority.ALWAYS);

        Button publishBtn = new Button("Publish Log 🛰️");
        publishBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #00FFFF, #008899);" +
                "-fx-text-fill: #05070B;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 20;" +
                "-fx-cursor: hand;"
        );
        
        publishBtn.setOnMouseEntered(e -> publishBtn.setStyle("-fx-background-color: linear-gradient(to right, #00FFFF, #00FFFF); -fx-text-fill: #05070B; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 20; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.4), 10, 0, 0, 0);"));
        publishBtn.setOnMouseExited(e -> publishBtn.setStyle("-fx-background-color: linear-gradient(to right, #00FFFF, #008899); -fx-text-fill: #05070B; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 20; -fx-cursor: hand;"));

        publishBtn.setOnAction(e -> {
            String content = postInput.getText();
            if (content == null || content.isBlank()) {
                postStatus.setTextFill(Color.web("#FF3366"));
                postStatus.setText("❌ Post cannot be empty");
                return;
            }
            if (!ContentFilter.isClean(content)) {
                postStatus.setTextFill(Color.web("#FF3366"));
                postStatus.setText("🛡️ Blocked content detected!");
                return;
            }

            boolean created = postService.createPost(userId, content, "");
            if (created) {
                Navigation.go(new ProfileView().getScene(userId));
            } else {
                postStatus.setTextFill(Color.web("#FF3366"));
                postStatus.setText("❌ Failed to publish");
            }
        });

        controlRow.getChildren().addAll(postStatus, postSpacer, publishBtn);
        createPostBox.getChildren().addAll(createTitle, postInput, controlRow);

        // ================= POSTS CONTAINER =================
        VBox postsList = new VBox(18);
        postsList.setAlignment(Pos.TOP_CENTER);

        if (userPosts == null || userPosts.isEmpty()) {
            Label noPosts = new Label("🚫 No data logs recorded yet for " + currentUsername);
            noPosts.setTextFill(Color.web("#484f58"));
            noPosts.setFont(Font.font("Segoe UI", 14));
            noPosts.setPadding(new Insets(30, 0, 0, 0));
            postsList.getChildren().add(noPosts);
        } else {
            for (Post post : userPosts) {
                postsList.getChildren().add(createModernPostCard(post, currentUsername));
            }
        }

        ScrollPane scroll = new ScrollPane(postsList);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        mainContent.getChildren().addAll(
                quickStats,
                feedTitle,
                createPostBox,
                scroll
        );

        root.setLeft(sidebar);
        root.setCenter(mainContent);

        return new Scene(root, width, height);
    }

    // ================= MODERN POST CARD =================
    private VBox createModernPostCard(Post post, String username) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setMaxWidth(850); // وسعنا العرض عشان المظهر المودرن المتناسق
        card.setStyle(
            "-fx-background-color: rgba(20, 30, 48, 0.4);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(0, 255, 255, 0.08);" +
            "-fx-border-width: 1.5;"
        );

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Circle avatar = new Circle(16, Color.web("#8A2BE2")); // لون بنفسجي سيبراني للأفاتار
        avatar.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(138,43,226,0.4), 8, 0, 0, 0);");
        
        Label userLabel = new Label(username);
        userLabel.setTextFill(Color.WHITE);
        userLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));

        header.getChildren().addAll(avatar, userLabel);

        Label content = new Label(post.getContent());
        content.setTextFill(Color.web("#D1D5DB"));
        content.setWrapText(true);
        content.setFont(Font.font("Segoe UI", 14));
        content.setPadding(new Insets(5, 0, 5, 5));

        card.getChildren().addAll(header, content);

        // Hover Effect نيون خفيف لما يقف على البوست
        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color: rgba(25, 40, 65, 0.5);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #00FFFF;" +
            "-fx-border-width: 1.5;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color: rgba(20, 30, 48, 0.4);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(0, 255, 255, 0.08);" +
            "-fx-border-width: 1.5;"
        ));

        return card;
    }

    // ================= HELPERS =================
    private VBox createSidebarItem(String label, String value) {
        Label l = new Label(label);
        l.setTextFill(Color.web("#6B7280"));
        l.setFont(Font.font("Segoe UI", 12));

        Label v = new Label(value != null ? value : "N/A");
        v.setTextFill(Color.web("#00FFFF")); // خليناها نيون أزرق
        v.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));

        VBox box = new VBox(4, l, v);
        box.setPadding(new Insets(5, 10, 5, 10));
        box.setStyle("-fx-background-color: rgba(255,255,255,0.02); -fx-background-radius: 6;");
        return box;
    }

    private VBox createMiniStat(String title, String value) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 28, 16, 28));
        card.setStyle(
            "-fx-background-color: rgba(28, 33, 40, 0.5);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(255,255,255,0.05);" +
            "-fx-border-width: 1;"
        );

        Label t = new Label(title);
        t.setTextFill(Color.web("#9CA3AF"));
        t.setFont(Font.font("Segoe UI", 13));

        Label v = new Label(value);
        v.setTextFill(Color.WHITE);
        v.setFont(Font.font("Consolas", FontWeight.BOLD, 24)); // ستايل الـ Terminal للرقم
        v.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,255,0.2), 5, 0, 0, 0);");

        card.getChildren().addAll(t, v);

        // Hover Effect لكروت الستاتس
        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color: rgba(35, 45, 60, 0.6);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(0,255,255,0.2);" +
            "-fx-border-width: 1;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color: rgba(28, 33, 40, 0.5);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(255,255,255,0.05);" +
            "-fx-border-width: 1;"
        ));

        return card;
    }
}
