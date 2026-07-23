package com.mycompany.cyberjocx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToolsDAO {

    public static List<Tools> getAllTools() {
        List<Tools> list = new ArrayList<>();
        // إضافة عمود commands للاستعلام
        String sql = "SELECT id, name, category, about, commands, download_link FROM t2ools";

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) {
                System.out.println("[-] DB Connection is NULL");
                return list;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Tools t = new Tools(
                            rs.getInt("id"),
                            safe(rs.getString("name")),
                            safe(rs.getString("category")),
                            safe(rs.getString("about")),
                            safe(rs.getString("commands")), // جلب الأوامر
                            safe(rs.getString("download_link"))
                    );
                    list.add(t);
                }
                System.out.println("[+] Tools Loaded = " + list.size());
            }

        } catch (Exception e) {
            System.out.println("[-] DAO ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
