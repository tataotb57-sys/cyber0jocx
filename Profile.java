
package com.mycompany.cyberjocx;

public class Profile {

    private String username;
    private String email;
    private int points;
    private String role;
    private boolean isVip;

    public Profile(String username, String email, int points, String role, boolean isVip) {
        this.username = username;
        this.email = email;
        this.points = points;
        this.role = role;
        this.isVip = isVip;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return points;
    }

    public String getRole() {
        return role;
    }

    public boolean isVip() {
        return isVip;
    }
}
