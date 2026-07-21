package com.mycompany.cyberjocx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/cyberjoucx112";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("✅ Database Connected");

            return conn;

        } catch (ClassNotFoundException e) {

            System.out.println("❌ MySQL Driver Not Found");
            return null;

        } catch (SQLException e) {

            System.out.println("❌ Database Connection Failed");
            System.out.println(e.getMessage());

            return null;
        }
    }
}
