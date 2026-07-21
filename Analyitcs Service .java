/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cyberjocx;
import java.sql.*;

public class AnalyticsService {

    // ===== CVE RISK 
    public static RiskLevel calculateRisk(int totalCVEs) {
        if (totalCVEs < 500) return RiskLevel.LOW;
        if (totalCVEs < 1000) return RiskLevel.MEDIUM;
        return RiskLevel.HIGH;
    }

    public static int getTotalCVEs() {
        String sql = "SELECT COUNT(*) AS total FROM cves";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getInt("total");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ===== 🔥 USER ENGAGEMENT LEVEL =====
    public static RiskLevel getUserEngagementLevel(int userId) {

        String sql = "SELECT points FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int points = rs.getInt("points");

                if (points < 300) return RiskLevel.LOW;
                if (points < 800) return RiskLevel.MEDIUM;
                return RiskLevel.HIGH;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return RiskLevel.LOW;
    }
}
