package com.mycompany.cyberjocx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    //  3asep 

    public boolean login(
            String username,
            String password
    ) {

        String query =
                "SELECT id, username, password FROM users WHERE username = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(query)
        ) {

            if (conn == null) {
                return false;
            }

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                int userId =
                        rs.getInt("id");

                String dbUsername =
                        rs.getString("username");

                String storedPassword =
                        rs.getString("password");

                // ================= HASH CHECK =================

                String hashedInputPassword =
                        PasswordUtil.hashPassword(password);

                boolean valid =
                        hashedInputPassword.equals(storedPassword);

                if (valid) {

                    // ================= SAVE SESSION =================

                    Session.setSession(
                            userId,
                            dbUsername
                    );

                    System.out.println(
                            "✅ Logged in as: " + dbUsername
                    );

                    System.out.println(
                            "SESSION USER: " +
                            Session.getUsername()
                    );

                    return true;
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Login Error: " + e.getMessage()
            );
        }

        return false;
    }

    // ================= REGISTER =================

    public boolean register(
            String user,
            String email,
            String pass
    ) {

        String query =
                "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(query)
        ) {

            if (conn == null) {
                return false;
            }

            stmt.setString(1, user);

            stmt.setString(2, email);

            // ================= HASH PASSWORD =================

            stmt.setString(
                    3,
                    PasswordUtil.hashPassword(pass)
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "❌ Register Error: " + e.getMessage()
            );
        }

        return false;
    }
}
