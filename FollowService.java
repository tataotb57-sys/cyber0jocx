package com.mycompany.cyberjocx;

import java.sql.*;

public class FollowService {
    public int getFollowersCount(int userId) {
        String query = "SELECT COUNT(*) FROM follows WHERE following_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
