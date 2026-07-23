package com.mycompany.cyberjocx;

import java.sql.*;
import java.util.*;

public class SearchService {

    // ===== USERS =====
    public List<User> searchUsers(String query, String roleFilter) {

        List<User> results = new ArrayList<>();
        if (query == null) query = "";

       StringBuilder sql = new StringBuilder(
    "SELECT * FROM users WHERE LOWER(username) LIKE ?"
       );

        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append(" AND LOWER(role) = ?");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int i = 1;
            stmt.setString(i++, "%" + query.toLowerCase() + "%");

            if (roleFilter != null && !roleFilter.isEmpty()) {
                stmt.setString(i++, roleFilter.toLowerCase());
            }

            ResultSet rs = stmt.executeQuery();

           while (rs.next()) {
           results.add(new User(
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
            System.out.println("Error searching users: " + e.getMessage());
        }

        return results;
    }

    // ===== PRODUCTS =====
   public List<Book> searchProducts(String query, String authorFilter, Integer priceFilter) {

    List<Book> results = new ArrayList<>();

    if (query == null)
        query = "";

    StringBuilder sql = new StringBuilder(
      "SELECT id, title, author, price_points, file_path, image_path " +
      "FROM book WHERE LOWER(title) LIKE ?"
    );

    if (authorFilter != null && !authorFilter.isEmpty()) {

        sql.append(" AND LOWER(author) = ?");
    }

    if (priceFilter != null) {

        sql.append(" AND price_points <= ?");
    }

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString())
    ) {

        int i = 1;

        stmt.setString(
            i++,
            "%" + query.toLowerCase() + "%"
        );

        if (authorFilter != null && !authorFilter.isEmpty()) {

            stmt.setString(
                i++,
                authorFilter.toLowerCase()
            );
        }

        if (priceFilter != null) {

            stmt.setInt(i++, priceFilter);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            results.add(
            new Book(
              rs.getInt("id"),
              rs.getString("title"),
              rs.getString("author"),
              rs.getInt("price_points"),
              rs.getString("file_path"),
              rs.getString("image_path")
    )
      );
        }

    } catch (SQLException e) {

        System.out.println(
            "Error searching books: " + e.getMessage()
        );
    }

    return results;
}
}
