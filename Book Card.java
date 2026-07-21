 package com.mycompany.cyberjocx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BookCard extends VBox {

    public BookCard(Book book, CartService cartService) {

        setSpacing(12);
        setPadding(new Insets(15));
        setAlignment(Pos.TOP_CENTER);

        setPrefWidth(240);
        setMaxWidth(240);

        setStyle(
                "-fx-background-color: linear-gradient(to bottom,#232526,#1c1c1c);" +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;" +
                "-fx-border-color: #2f3640;" +
                "-fx-border-width: 1.5;"
        );

        setEffect(new DropShadow(20, Color.rgb(0,0,0,0.5)));

        ImageView cover = new ImageView();

        try {

              cover.setImage(new Image("file:/" + book.getImagePath()));
        } catch (Exception e) {
               cover.setImage(
                new Image(
                "file:/C:/Users/elmasria/Downloads/تنزيل.jpg"
                       )
                    );
        }

        cover.setFitWidth(170);
        cover.setFitHeight(240);

        cover.setStyle(
                "-fx-background-radius: 12;"
        );

        Label title = new Label(book.getTitle());

        title.setWrapText(true);

        title.setTextFill(Color.WHITE);

        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        title.setAlignment(Pos.CENTER);

        Label author = new Label(book.getAuthor());

        author.setTextFill(Color.web("#a4b0be"));

        author.setFont(Font.font(13));

        Label category = new Label("CYBER SECURITY");

        category.setStyle(
                "-fx-background-color: #00a8ff;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 20;" +
                "-fx-padding: 4 12 4 12;" +
                "-fx-font-size: 11;" +
                "-fx-font-weight: bold;"
        );

        Label price = new Label(book.getPricePoints() + " POINTS");

        price.setTextFill(Color.web("#2ed573"));

        price.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Button btnBuy = new Button("BUY NOW");

        btnBuy.setPrefWidth(170);

        btnBuy.setStyle(
                "-fx-background-color: linear-gradient(to right,#00c6ff,#0072ff);" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14;" +
                "-fx-background-radius: 12;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 10 0 10 0;"
        );

        btnBuy.setOnMouseEntered(e -> {

            btnBuy.setStyle(
                    "-fx-background-color: linear-gradient(to right,#1dd1a1,#10ac84);" +
                    "-fx-text-fill: white;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 14;" +
                    "-fx-background-radius: 12;" +
                    "-fx-cursor: hand;" +
                    "-fx-padding: 10 0 10 0;"
            );
        });

        btnBuy.setOnMouseExited(e -> {

            if (!btnBuy.isDisabled()) {

                btnBuy.setStyle(
                        "-fx-background-color: linear-gradient(to right,#00c6ff,#0072ff);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 10 0 10 0;"
                );
            }
        });

        btnBuy.setOnAction(e -> {

            cartService.addToCart(book);

            btnBuy.setText("ADDED ✓");

            btnBuy.setDisable(true);

            btnBuy.setStyle(
                    "-fx-background-color: #27ae60;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 14;" +
                    "-fx-background-radius: 12;"
            );
        });

        getChildren().addAll(
                cover,
                category,
                title,
                author,
                price,
                btnBuy
        );
    }
}
