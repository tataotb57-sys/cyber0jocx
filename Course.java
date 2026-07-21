package com.mycompany.cyberjocx;

public class Course {
    private int id;
    private String courseName;
    private String category;
    private int requiredPoints;
    private String courseLink;
    private String courseDescription;
    private boolean hasCertificate;
    private String instructor;

    // Constructor للبيانات كاملة (عند القراءة من الـ Database)
    public Course(int id, String courseName, String category, int requiredPoints, 
                  String courseLink, String courseDescription, boolean hasCertificate, String instructor) {
        this.id = id;
        this.courseName = courseName;
        this.category = category;
        this.requiredPoints = requiredPoints;
        this.courseLink = courseLink;
        this.courseDescription = courseDescription;
        this.hasCertificate = hasCertificate;
        this.instructor = instructor;
    }

    // Constructor بدون ID (مفيد لعملية الـ Insert الجديدة)
    public Course(String courseName, String category, int requiredPoints, 
                  String courseLink, String courseDescription, boolean hasCertificate, String instructor) {
        this.courseName = courseName;
        this.category = category;
        this.requiredPoints = requiredPoints;
        this.courseLink = courseLink;
        this.courseDescription = courseDescription;
        this.hasCertificate = hasCertificate;
        this.instructor = instructor;
    }

    // ================= Getters and Setters =================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getRequiredPoints() { return requiredPoints; }
    public void setRequiredPoints(int requiredPoints) { this.requiredPoints = requiredPoints; }

    public String getCourseLink() { return courseLink; }
    public void setCourseLink(String courseLink) { this.courseLink = courseLink; }

    public String getCourseDescription() { return courseDescription; }
    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }

    public boolean isHasCertificate() { return hasCertificate; }
    public void setHasCertificate(boolean hasCertificate) { this.hasCertificate = hasCertificate; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
}
