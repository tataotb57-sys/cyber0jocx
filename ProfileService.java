package com.mycompany.cyberjocx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileService {

    public Profile getProfileByUserId(int userId) {

        String sql =
                "SELECT username, email FROM users WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Profile(
                        rs.getString("username"),
                        rs.getString("email"),
                        0,
                        "Member",
                        false
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Profile Error: " + e.getMessage()
            );
        }

        return null;
    }
}
