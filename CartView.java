package com.mycompany.cyberjocx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CartView {

    private CartService cartService;

    public CartView(CartService cartService) {
        this.cartService = cartService;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1a1d21;");

        // الحاوية الرئيسية للمحتوى (عشان نلم الحجم)
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(40));
        // حددنا هنا عرض المحتوى بـ 800 بكسل بس عشان ميبقاش مفرود بزيادة
        mainContainer.setMaxWidth(800); 

        // ================= HEADER =================
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button btnBack = new Button("← Back");
        btnBack.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-cursor: hand;");
        btnBack.setOnAction(e -> Navigation.go(new MarketView().getScene()));

        Label title = new Label("Shopping Cart");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        header.getChildren().addAll(btnBack, title);

        // ================= ITEMS LIST =================
        VBox itemsList = new VBox(15);
        itemsList.setPadding(new Insets(10));

        if (cartService.getCart().getItems().isEmpty()) {
            Label empty = new Label("No items in your cart yet.");
            empty.setTextFill(Color.GRAY);
            itemsList.getChildren().add(empty);
        } else {
            for (Book b : cartService.getCart().getItems()) {
                HBox row = new HBox(20);
                row.setPadding(new Insets(15));
                row.setAlignment(Pos.CENTER_LEFT);
                // تصميم الكارت الصغير لكل كتاب
                row.setStyle("-fx-background-color: #24272b; -fx-background-radius: 8; -fx-border-color: #3f444c; -fx-border-radius: 8;");

                Label name = new Label(b.getTitle());
                name.setTextFill(Color.WHITE);
                name.setFont(Font.font(16));
                name.setPrefWidth(400);

                Label price = new Label(b.getPricePoints() + " pts");
                price.setTextFill(Color.web("#f1c40f"));
                price.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                row.getChildren().addAll(name, price);
                itemsList.getChildren().add(row);
            }
        }

        ScrollPane scroll = new ScrollPane(itemsList);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(450); // طول محدد للسكرول عشان الصفحة متبقاش سايحة
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        // ================= SUMMARY =================
        VBox summary = new VBox(10);
        summary.setPadding(new Insets(20));
        summary.setAlignment(Pos.CENTER_RIGHT);
        summary.setStyle("-fx-background-color: #24272b; -fx-background-radius: 10;");

        Label total = new Label("Total Points: " + cartService.getCart().getTotal());
        total.setTextFill(Color.WHITE);
        total.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Button btnCheckout = new Button("Confirm Purchase");
        btnCheckout.setPrefWidth(200);
        btnCheckout.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-cursor: hand;");

        summary.getChildren().addAll(total, btnCheckout);

        // تجميع كل حاجة في الـ mainContainer
        mainContainer.getChildren().addAll(header, scroll, summary);

        // وضع الـ mainContainer في نص الـ BorderPane
        root.setCenter(mainContainer);

        return new Scene(root, 1400, 850);
    }
}
