/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cyberjocx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    
    /*/*
    VIP controller
    */
      private VIPService vipService = new VIPService();

    public void promoteToVip(int userId) {
        vipService.makeVip(userId);
    }

    public void removeVip(int userId) {
        vipService.removeVip(userId);
    }

    // ===== GET ALL USERS =====
    
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
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

        return users;
    }
    // ===== DELETE USER =====
    public void deleteUser(int userId) {

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===== UPDATE USER ROLE =====
    public void updateUserRole(int userId, String role) {

        String sql = "UPDATE users SET role = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, role);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===== TOGGLE VIP =====
    public void toggleVip(int userId, boolean isVip) {

        String sql = "UPDATE users SET is_vip = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, isVip);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
