package com.mycompany.cyberjocx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.application.Platform;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PointService {

    private static PointService instance;
    
    // الـ Property السحرية اللي واجهات JavaFX بتراقبها عشان تتحدث لحظياً
    private final IntegerProperty currentPoints = new SimpleIntegerProperty(0);

    private PointService() {
        loadPointsFromDatabase();
    }

    public static PointService getInstance() {
        if (instance == null) {
            instance = new PointService();
        }
        return instance;
    }

    public IntegerProperty pointsProperty() {
        return currentPoints;
    }

    public int getPoints() {
        return currentPoints.get();
    }

    /**
     */
    public void loadPointsFromDatabase() {
        int userId = Session.getUserId();
        String query = "SELECT points FROM users WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int points = rs.getInt("points");
                //لضمان الاستقرار
                Platform.runLater(() -> currentPoints.set(points));
            }
        } catch (Exception e) {
            System.err.println("[PointService] Error loading points from DB:");
            e.printStackTrace();
        }
    }

    /**
     */
    public void addPoints(int amount) {
        int userId = Session.getUserId();
        String query = "UPDATE users SET points = points + ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, amount);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();

            
            Platform.runLater(() -> currentPoints.set(currentPoints.get() + amount));
            System.out.println("[PointService] Points modified by: " + amount + " | New Balance: " + currentPoints.get());

        } catch (Exception e) {
            System.err.println("[PointService] Failed to update points in DB:");
            e.printStackTrace();
        }
    }

    /**
     */
    public boolean spendPoints(int amount) {
        if (getPoints() >= amount) {
            addPoints(-amount); 
            return true; 
        }
        return false; 
    }
}
