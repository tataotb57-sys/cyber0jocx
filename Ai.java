 package com.mycompany.cyberjocx;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class AIController {

    public String askAI(String userMsg) {
        // 1. المفتاح (شغال تمام ومتجرب)
        String apiKey = "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"; 
        
        // 2. رابط Groq السريع
        String url = "https://api.groq.com/openai/v1/chat/completions";

        // 3. تنظيف النص عشان الـ JSON ميبوظش (Escaping)
        String cleanQuery = userMsg.replace("\\", "\\\\")
                                 .replace("\"", "\\\"")
                                 .replace("\n", "\\n")
                                 .replace("\r", "\\r");

       // التعديل في السطر ده بس (استخدمنا llama-3.1-8b-instant لأنه سريع ومجاني)
String jsonInput = "{"
        + "\"model\": \"llama-3.1-8b-instant\","
        + "\"messages\": [{\"role\": \"user\", \"content\": \"" + cleanQuery + "\"}]"
        + "}";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInput, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return "[Hussein]: " + parseGroqResponse(response.body());
            } else {
                // لو حصل مشكلة هيطبعلك التفاصيل في الـ Output عشان نعرف السبب
                System.out.println("Full Error: " + response.body());
                return "[Hussein]: Error " + response.statusCode();
            }
        } catch (Exception e) {
            return "[Hussein]: مشكلة في الاتصال: " + e.getMessage();
        }
    }

    // ميثود استخراج النص من الـ JSON اللي راجع من Groq
    private String parseGroqResponse(String body) {
        try {
            String searchKey = "\"content\":\"";
            int startIndex = body.indexOf(searchKey);
            if (startIndex != -1) {
                startIndex += searchKey.length();
                int endIndex = body.indexOf("\"", startIndex);
                if (endIndex != -1) {
                    return body.substring(startIndex, endIndex)
                               .replace("\\n", "\n")
                               .replace("\\\"", "\"");
                }
            }
            return "لم أستطع فهم الرد.";
        } catch (Exception e) {
            return "خطأ في فك البيانات.";
        }
    }
}
