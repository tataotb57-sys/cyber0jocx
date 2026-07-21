package com.mycompany.cyberjocx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseController {

    /**
     */
    public int getCoursesCount() {
        String query = "SELECT COUNT(*) FROM courses";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Database Error inside getCoursesCount()!");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     */
    public boolean saveCourse(Course course) {
        String query = "INSERT INTO courses " +
                "(course_name, category, required_points, course_link, course_description, has_certificate, instructor) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connection = DBConnection.getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCategory());
            preparedStatement.setInt(3, course.getRequiredPoints());
            preparedStatement.setString(4, course.getCourseLink());
            preparedStatement.setString(5, course.getCourseDescription());
            preparedStatement.setBoolean(6, course.isHasCertificate());
            preparedStatement.setString(7, course.getInstructor());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // ترجع true لو عملية الإدخال تمت بنجاح

        } catch (SQLException e) {
            System.err.println("Database Error while saving course using DBConnection!");
            e.printStackTrace();
            return false;
        }
    }

    
    
    public ObservableList<Course> getAllCourses() {
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        String query = "SELECT * FROM courses";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery()
        ) {
            while (rs.next()) {
                // بناء الـ Object بالاعتماد على الـ Constructor الكامل اللي إنت كاتبه
                Course course = new Course(
                    rs.getInt("id"),
                    rs.getString("course_name"),
                    rs.getString("category"),
                    rs.getInt("required_points"),
                    rs.getString("course_link"),
                    rs.getString("course_description"),
                    rs.getBoolean("has_certificate"),
                    rs.getString("instructor")
                );
                courseList.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Database Error inside getAllCourses()!");
            e.printStackTrace();
        }
        return courseList;
    }
}
