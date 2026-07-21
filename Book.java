package com.mycompany.cyberjocx;

public class Book {

    private int id;
    private String title;
    private String author;
    private int pricePoints;
    private String filePath;
    private String imagePath;

    public Book(int id, String title, String author,
                int pricePoints,
                String filePath,
                String imagePath) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.pricePoints = pricePoints;
        this.filePath = filePath;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPricePoints() {
        return pricePoints;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
