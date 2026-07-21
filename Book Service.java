package com.mycompany.cyberjocx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM book";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                books.add(new Book(

                        rs.getInt("id"),

                        rs.getString("title"),

                        rs.getString("author"),

                        rs.getInt("price_points"),

                        rs.getString("file_path"),

                        rs.getString("image_path")
                ));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return books;
    }
}
