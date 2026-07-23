
package com.mycompany.cyberjocx;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VIPService {

    // ===== WASE3 llvip  =====
    public void makeVip(int userId) {

        String sql = "UPDATE users SET is_vip = true WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===== PARA  VIP =====
    public void removeVip(int userId) {

        String sql = "UPDATE users SET is_vip = false WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===== KOL ELm3araseen  VIP USERS =====
    public List<User> getVipUsers() {

        List<User> vipUsers = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE is_vip = true";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vipUsers.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("points"),
                        rs.getBoolean("is_vip"),
                        rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return vipUsers;
    }

    // ===== NRAKEP IF VIP =====
    public boolean isVip(int userId) {

        String sql = "SELECT is_vip FROM users WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("is_vip");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }
}
