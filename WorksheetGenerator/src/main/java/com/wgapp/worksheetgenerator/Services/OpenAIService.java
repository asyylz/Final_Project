package com.wgapp.worksheetgenerator.Services;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

public class OpenAIService {

    private static final String API_URL;
    private static final String API_KEY;  // Replace with your key


    static {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("API_KEY");  // Retrieve the API_KEY from .env file
        API_URL = dotenv.get("API_URL"); // Retrieve the API_URL from .env file
    }

    public String generateWorksheet(String prompt) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setDoOutput(true);

        // Create the JSON payload
        JSONObject payload = new JSONObject();
        payload.put("model", "gpt-3.5-turbo");

        // Add the messages array
        JSONArray messages = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.put(userMessage);

        payload.put("messages", messages);
        System.out.println(messages.toString());


        // Send the request
        try (OutputStream os = connection.getOutputStream()) {
            // getBytes("utf-8") converts the JSON string (payload.toString()) into a byte array using the UTF-8 encoding.
            os.write(payload.toString().getBytes("utf-8"));
        }

        // Read the response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();  // Return the JSON response as a string
            }
        } else {
            throw new RuntimeException("Failed to generate worksheet: HTTP " + responseCode);
        }
    }
}

