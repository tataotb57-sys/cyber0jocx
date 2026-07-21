package com.mycompany.cyberjocx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class CourseAddView {

    private final CourseController controller = new CourseController();

    // الدالة دي بترجع الـ View كـ VBox عشان لو حبيت تعرضه جوه الـ Center Panel مباشرة
    public VBox getView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #0D1117;"); // متناسق مع ثيم الـ Dashboard الغامق

        // Title
        Label headerTitle = new Label("Add New Cyber Course");
        headerTitle.setFont(new Font("Arial", 26));
        headerTitle.setTextFill(Color.CYAN);

        // Form Layout using GridPane
        GridPane formGrid = new GridPane();
        formGrid.setVgap(15);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        // Input Fields
        TextField nameField = createStyledTextField("Course Name");
        TextField categoryField = createStyledTextField("Category (e.g., API Exploitation, SQLi)");
        TextField pointsField = createStyledTextField("Required Points");
        TextField linkField = createStyledTextField("Course URL Link");
        TextField instructorField = createStyledTextField("Instructor Name");
        
        TextArea descArea = new TextArea();
        descArea.setPromptText("Enter Course Description...");
        descArea.setPrefRowCount(3);
        descArea.setStyle("-fx-control-inner-background: #1F2937; -fx-text-fill: white; -fx-prompt-text-fill: gray;");

        CheckBox certCheckBox = new CheckBox("Provide CyberJocx Certificate upon completion");
        certCheckBox.setTextFill(Color.LIGHTGRAY);

        // Adding Elements to Grid
        formGrid.add(new Label("Course Title:") {{ setTextFill(Color.WHITE); }}, 0, 0);
        formGrid.add(nameField, 1, 0);

        formGrid.add(new Label("Category:") {{ setTextFill(Color.WHITE); }}, 0, 1);
        formGrid.add(categoryField, 1, 1);

        formGrid.add(new Label("Points Price:") {{ setTextFill(Color.WHITE); }}, 0, 2);
        formGrid.add(pointsField, 1, 2);

        formGrid.add(new Label("Instructor:") {{ setTextFill(Color.WHITE); }}, 0, 3);
        formGrid.add(instructorField, 1, 3);

        formGrid.add(new Label("Course Link:") {{ setTextFill(Color.WHITE); }}, 0, 4);
        formGrid.add(linkField, 1, 4);

        formGrid.add(new Label("Description:") {{ setTextFill(Color.WHITE); }}, 0, 5);
        formGrid.add(descArea, 1, 5);

        formGrid.add(certCheckBox, 1, 6);

        // Submit Button
        Button saveBtn = new Button("Save Course to Database");
        saveBtn.setStyle("-fx-background-color: #00FFFF; -fx-text-fill: #0D1117; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;");
        
        // Status Message Label
        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Arial", 14));

        // Button Action (Connecting UI with Logic and Model)
        saveBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                String pointsText = pointsField.getText().trim();
                String link = linkField.getText();
                String desc = descArea.getText();
                boolean hasCert = certCheckBox.isSelected();
                String instructor = instructorField.getText();

                // Validation بسيط عشان التأكد من الخانات
                if (name.isEmpty() || category.isEmpty() || pointsText.isEmpty() || instructor.isEmpty()) {
                    statusLabel.setText("Error: Please fill all required fields!");
                    statusLabel.setTextFill(Color.RED);
                    return;
                }

                int points = Integer.parseInt(pointsText);

                // 1. إنشاء الـ Model Object
                Course newCourse = new Course(name, category, points, link, desc, hasCert, instructor);

                // 2. إرساله للـ Controller للتعامل مع الـ DB
                boolean success = controller.saveCourse(newCourse);

                if (success) {
                    statusLabel.setText("Success: Course added to CyberJocx platform!");
                    statusLabel.setTextFill(Color.GREEN);
                    // مسح الخانات بعد النجاح
                    nameField.clear(); categoryField.clear(); pointsField.clear(); linkField.clear(); descArea.clear(); instructorField.clear();
                    certCheckBox.setSelected(false);
                } else {
                    statusLabel.setText("Database Error: Could not save course.");
                    statusLabel.setTextFill(Color.RED);
                }

            } catch (NumberFormatException ex) {
                statusLabel.setText("Error: Points must be a valid number!");
                statusLabel.setTextFill(Color.RED);
            }
        });

        root.getChildren().addAll(headerTitle, formGrid, saveBtn, statusLabel);
        return root;
    }

    // Helper method لتنسيق التكست فيلدز بشكل موحد وسريع
    private TextField createStyledTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(300);
        tf.setStyle("-fx-background-color: #1F2937; -fx-text-fill: white; -fx-prompt-text-fill: gray; -fx-background-radius: 5;");
        return tf;
    }

    // =========================================================
    // GET SCENE (تم حل مشكلة الـ UnsupportedOperationException)
    // =========================================================
    public Scene getScene() {
        // إنشاء الـ Root الأساسي كـ BorderPane متوافق مع نظام الـ Navigation بتاعك
        javafx.scene.layout.BorderPane mainLayout = new javafx.scene.layout.BorderPane();
        
        // جلب أبعاد الشاشة الحالية بشكل ديناميكي (90%)
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.9;
        double height = screenBounds.getHeight() * 0.9;

        // وضع الفورم (الـ VBox) في منتصف الشاشة
        mainLayout.setCenter(getView());

        // إرجاع الـ Scene جاهز للـ Navigation
        return new Scene(mainLayout, width, height);
    }
}
