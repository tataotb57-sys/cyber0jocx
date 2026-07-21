package com.mycompany.cyberjocx;
import java.time.LocalDateTime;

// الـ Enum زي ما هو، عاش
enum RelationshipStatus {
    PENDING, ACCEPTED, BLOCKED;
}

public class Friendship { // غيرنا الاسم لـ Friendship عشان المنطق
    private String userId;
    private String friendId;
    private RelationshipStatus status;
    private LocalDateTime createdAt;

    // Constructor بسيط
    public Friendship(String userId, String friendId, RelationshipStatus status) {
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
        this.createdAt = LocalDateTime.now(); // بياخد وقت اللحظة تلقائياً
    }

    // الـ Getters اللي الجافا اف اكس والداتا بيز هيحتاجوها
    public String getUserId() { return userId; }
    public String getFriendId() { return friendId; }
    public String getStatusString() { return status.name(); } // عشان تخزنها في الداتا بيز كـ Text
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(RelationshipStatus status) { this.status = status; }

    // الميثود دي هتحتاجها لما تسحب من الداتا بيز عشان تطبع البيانات بسرعة
    public void printDetails() {
        System.out.println(String.format("[%s] %s -> %s (%s)", createdAt, userId, friendId, status));
    }
}
