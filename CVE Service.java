package com.mycompany.cyberjocx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CVEService {

    public List<CVE> getCVEsByYear(int year) {
        List<CVE> list = new ArrayList<>();
        // published_date و source_link
        String sql = "SELECT id, cve_number, title, description, severity, score, published_date, source_link FROM cves WHERE cve_number LIKE ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, "CVE-" + year + "-%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //  نمرر 8 متغيرات للـ Constructor الخاص بكلاس CVE بالترتيب الصحيح
                list.add(new CVE(
                    rs.getInt("id"),
                    rs.getString("cve_number"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("severity"),
                    rs.getDouble("score"),
                    rs.getString("published_date"),
                    rs.getString("source_link")    
                ));
            }
            System.out.println("Fetched " + list.size() + " records.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
