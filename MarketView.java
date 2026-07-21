package com.mycompany.cyberjocx;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class MarketView {

    private final BookService bookService = new BookService();
    private final CartService cartService = new CartService();

    public Scene getScene() {
        // ================= ROOT CONTAINER =================
        BorderPane root = new BorderPane();
        
        // تطبيق الخلفية الموحدة والفخمة للمنصة بدلاً من الصور والخلفيات البيضاء
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #04060A, #090D16, #06090F);");

        // جلب أبعاد شاشة المستخدم لضمان الـ Responsiveness بنسبة 85%
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.85;
        double height = screenBounds.getHeight() * 0.85;

        // ================= TOP NAVIGATION BAR =================
        HBox topBar = new HBox(25);
        topBar.setPadding(new Insets(25, 45, 25, 45));
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        // ستايل الـ TopBar الزجاجي الداكن المتناسق مع باقي المشروع
        topBar.setStyle(
                "-fx-background-color: rgba(13, 17, 23, 0.6);" +
                "-fx-border-color: rgba(48, 54, 61, 0.2);" +
                "-fx-border-width: 0 0 1 0;"
        );
        topBar.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.5)));

        // ================= BACK TO DASHBOARD BUTTON =================
        Button backBtn = new Button("← Back to Control Center");
        backBtn.setCursor(javafx.scene.Cursor.HAND);
        backBtn.setPadding(new Insets(10, 18, 10, 18));
        backBtn.setStyle(
                "-fx-background-color: rgba(0, 255, 255, 0.08);" +
                "-fx-text-fill: #00FFFF;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(0, 255, 255, 0.3);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;"
        );
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.2); -fx-text-fill: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #00FFFF; -fx-border-width: 1; -fx-border-radius: 8;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: rgba(0, 255, 255, 0.08); -fx-text-fill: #00FFFF; -fx-background-radius: 8; -fx-border-color: rgba(0, 255, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 8;"));
        backBtn.setOnAction(e -> Navigation.go(new HomeDashboard().getScene()));

        // ================= REPOSITORY TITLE =================
        VBox titleLayout = new VBox(4);
        Label titleLabel = new Label("CYBERJOCX ARCHIVE MARKET");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        titleLabel.setTextFill(Color.web("#FFFFFF"));
        titleLabel.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.05), 5, 0, 0, 0); -fx-letter-spacing: 1;");

        Label subtitleLabel = new Label("Elite Cyber Security Intelligence & Tactical Library");
        subtitleLabel.setTextFill(Color.web("#58A6FF"));
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        titleLayout.getChildren().addAll(titleLabel, subtitleLabel);

        // سبيسر لدفع العناصر لليمين بالكامل
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // ================= VIEW CART BUTTON (NEON STYLE) =================
        Button btnViewCart = new Button("🛒 VIEW CART MATRIX");
        btnViewCart.setCursor(javafx.scene.Cursor.HAND);
        btnViewCart.setPadding(new Insets(12, 22, 12, 22));
        
        String defCartStyle = 
            "-fx-background-color: rgba(0, 255, 204, 0.1);" +
            "-fx-text-fill: #00FFCC;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: rgba(0, 255, 204, 0.3);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 10;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;";

        String hvrCartStyle = 
            "-fx-background-color: #00FFCC;" +
            "-fx-text-fill: #000000;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #00FFCC;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 10;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,255,204,0.25), 8, 0, 0, 0);";

        btnViewCart.setStyle(defCartStyle);
        btnViewCart.setOnMouseEntered(e -> {
            btnViewCart.setStyle(hvrCartStyle);
            btnViewCart.setScaleX(1.02);
        });
        btnViewCart.setOnMouseExited(e -> {
            btnViewCart.setStyle(defCartStyle);
            btnViewCart.setScaleX(1.0);
        });
        btnViewCart.setOnAction(e -> Navigation.go(new CartView(cartService).getScene()));

        topBar.getChildren().addAll(backBtn, titleLayout, spacer, btnViewCart);

        // ================= PRODUCTS TILE PANE (BOOKS GRID) =================
        TilePane booksPane = new TilePane();
        booksPane.setPadding(new Insets(40, 50, 40, 50));
        booksPane.setHgap(30);
        booksPane.setVgap(35);
        booksPane.setAlignment(Pos.TOP_CENTER);
        booksPane.setTileAlignment(Pos.TOP_CENTER);
        booksPane.setPrefColumns(5);
        booksPane.setStyle("-fx-background-color: transparent;");

        // تحميل كروت الكتب وتوزيعها
        List<Book> books = bookService.getAllBooks();
        System.out.println("BOOKS SIZE = " + books.size());
        for (Book b : books) {
            try {
                booksPane.getChildren().add(new BookCard(b, cartService));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // ================= MODERNISED SCROLL PANE =================
        ScrollPane scrollPane = new ScrollPane(booksPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // التخلص من الحواف البيضاء والرمادية التلقائية تماماً وجعلها شفافة بالكامل
        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // تجميع الـ TopBar مع الـ Grid في الحاوية المركزية
        VBox mainLayoutWrapper = new VBox();
        mainLayoutWrapper.getChildren().addAll(topBar, scrollPane);

        root.setCenter(mainLayoutWrapper);

        return new Scene(root, width, height);
    }
}
