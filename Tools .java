package com.mycompany.cyberjocx;

public class Tools {
    private int id;
    private String name;
    private String category;
    private String description; // يستقبل عمود about
    private String commands;    // العمود الجديد للأوامر
    private String download_link;

    public Tools(int id, String name, String category, String description, String commands, String download_link) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.commands = commands;
        this.download_link = download_link;
    }

    // الـ Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getCommands() { return commands; }
    public String getDownload_link() { return download_link; }
}
