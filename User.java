/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cyberjocx;


public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private int points;
    private boolean isVip;
    private String role;

    public User(int id, String username, String password, String email,
                int points, boolean isVip, String role) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.points = points;
        this.isVip = isVip;
        this.role = role;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public int getPoints() { return points; }
    public boolean isVip() { return isVip; }
    public String getRole() { return role; }
}
