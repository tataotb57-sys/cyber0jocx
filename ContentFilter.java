/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cyberjocx;
public class ContentFilter {
    // كلمات ممنوعة بسيطة (تقدر تزودها)
    private static final String[] BAD_WORDS = {
            "spam",
            "hack",
            "cheat",
            "fake"
    };
    public static boolean isClean(String content) {
        if (content == null) return false;
        String lower = content.toLowerCase();
        for (String word : BAD_WORDS) {
            if (lower.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
