package com.mycompany.cyberjocx;

public class Post {
    private int id;
    private int userId;
    private String content;
    private String imageUrl;
    private int likesCount;
    private String timestamp;
    private String ImagePath;

    public Post(int id, int userId, String content, String imageUrl, int likesCount, String timestamp, String ImagePath) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.imageUrl = imageUrl;
        this.likesCount = likesCount;
        this.timestamp = timestamp;
        this.ImagePath = ImagePath;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public int getLikesCount() { return likesCount; }
    public String getTimestamp() { return timestamp; }
    public String getImagePath() { return ImagePath; }
}
