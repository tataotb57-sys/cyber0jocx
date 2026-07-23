package com.mycompany.cyberjocx;

public class Session {
    private static int userId;
    private static String username; 

    public static void setSession(int id, String name) {
        userId = id;
        username = name;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void clear() {
        userId = 0;
        username = null;
    }
}
