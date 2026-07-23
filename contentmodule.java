/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cyberjocx;

public class contentmodule {

    private String title;
    private String content;
    private String status;

    public contentmodule(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = "pending";
    }

    public void approve() {
        this.status = "approved";
    }
 
    public String getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }
}
