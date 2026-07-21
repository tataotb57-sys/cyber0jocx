package com.mycompany.cyberjocx;

public class CVE {

    private int id;
    private String cveNumber;
    private String title;
    private String description;
    private String severity;
    private double score;
    private String publishedDate;
    private String source_link;

    public CVE(
            int id,
            String cveNumber,
            String title,
            String description,
            String severity,
            double score,
            String publishedDate,
            String source_link
    ) {
        this.id = id;
        this.cveNumber = cveNumber;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.score = score;
        this.publishedDate = publishedDate;
        this.source_link= source_link;
    }

    public int getId() {
        return id;
    }

    public String getCveNumber() {
        return cveNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSeverity() {
        return severity;
    }

    public double getScore() {
        return score;
    }

    public String getPublishedDate() {
        return publishedDate;
    }
    public String getSource_link(){
      return source_link ;
    }
}
