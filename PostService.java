package com.mycompany.cyberjocx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostService {

    // =========================================================
    // GET POSTS BY USER ID
    // =========================================================
    public List<Post> getPostsByUserId(int userId) {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Posts WHERE user_id = ? ORDER BY timestamp DESC";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // جلب رابط الصورة الأساسي الموجود في الجدول
                String imgUrl = rs.getString("image_url");

                posts.add(
                        new Post(
                                rs.getInt("id"),
                                rs.getInt("user_id"),
                                rs.getString("content"),
                                imgUrl, // مبعوت لـ imageUrl
                                rs.getInt("likes_count"),
                                rs.getString("timestamp"),
                                imgUrl  // مبعوت لـ ImagePath لحل مشكلة العمود المفقود
                        )
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error while loading posts!");
            e.printStackTrace();
        }
        return posts;
    }

    // =========================================================
    // CREATE POST
    // =========================================================
    public boolean createPost(int userId, String content, String imageUrl) {
        String query = "INSERT INTO Posts (user_id, content, image_url, likes_count) VALUES (?, ?, ?, 0)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, userId);
            stmt.setString(2, content);
            stmt.setString(3, imageUrl != null ? imageUrl : "");

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error while creating post!");
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // GET LIKES COUNT
    // =========================================================
    public int getLikesCount(int postId) {
        String query = "SELECT likes_count FROM Posts WHERE id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("likes_count");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error while getting likes count!");
            e.printStackTrace();
        }
        return 0;
    }

    // =========================================================
    // CHECK IF USER LIKED POST
    // =========================================================
    public boolean hasUserLikedPost(int postId, int userId) {
        String query = "SELECT 1 FROM PostLikes WHERE post_id = ? AND user_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("❌ Error while checking like state!");
            e.printStackTrace();
        }
        return false;
    }

    // =========================================================
    // LIKE / UNLIKE SYSTEM
    // =========================================================
    public boolean likePost(int postId, int userId) {
        String checkQuery = "SELECT * FROM PostLikes WHERE post_id = ? AND user_id = ?";
        String insertLikeQuery = "INSERT INTO PostLikes (post_id, user_id) VALUES (?, ?)";
        String deleteLikeQuery = "DELETE FROM PostLikes WHERE post_id = ? AND user_id = ?";
        String incrementQuery = "UPDATE Posts SET likes_count = likes_count + 1 WHERE id = ?";
        String decrementQuery = "UPDATE Posts SET likes_count = likes_count - 1 WHERE id = ? AND likes_count > 0";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, postId);
                checkStmt.setInt(2, userId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    try (
                            PreparedStatement deleteStmt = conn.prepareStatement(deleteLikeQuery);
                            PreparedStatement decStmt = conn.prepareStatement(decrementQuery)
                    ) {
                        deleteStmt.setInt(1, postId);
                        deleteStmt.setInt(2, userId);
                        deleteStmt.executeUpdate();

                        decStmt.setInt(1, postId);
                        decStmt.executeUpdate();
                    }
                    conn.commit();
                    return false;
                } else {
                    try (
                            PreparedStatement insertStmt = conn.prepareStatement(insertLikeQuery);
                            PreparedStatement incStmt = conn.prepareStatement(incrementQuery)
                    ) {
                        insertStmt.setInt(1, postId);
                        insertStmt.setInt(2, userId);
                        insertStmt.executeUpdate();

                        incStmt.setInt(1, postId);
                        incStmt.executeUpdate();
                    }
                    conn.commit();
                    return true;
                }
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("❌ Transaction Failed!");
                ex.printStackTrace();
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Database Error during Like/Unlike!");
            e.printStackTrace();
        }
        return false;
    }
}
