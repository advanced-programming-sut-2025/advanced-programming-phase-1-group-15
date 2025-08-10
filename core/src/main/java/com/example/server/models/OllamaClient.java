package com.example.server.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class OllamaClient {
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    private static final String MODEL = "llama3"; // or your custom fine-tuned model
    private static final HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();
    private static final Gson gson = new Gson();

    public static String generateNPCDialog(String npcName, String npcJob, String context) {
        try {
            String prompt = buildPrompt(npcName, npcJob, context);

            JsonObject requestJson = new JsonObject();
            requestJson.addProperty("model", MODEL);
            requestJson.addProperty("prompt", prompt);
            requestJson.addProperty("stream", false);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_API_URL))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(30))
                .POST(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseOllamaResponse(response.body());
            } else {
                System.err.println("Ollama API error: " + response.statusCode() + " - " + response.body());
                return getFallbackResponse(npcName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getFallbackResponse(npcName);
        }
    }

    private static String buildPrompt(String npcName, String npcJob, String context) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are role playing as ").append(npcName).append(", a ")
            .append(npcJob).append(" in a farming/life simulation game.\n\n");
        prompt.append("CONTEXT:\n").append(context).append("\n\n");
        prompt.append("RESPONSE RULES:\n");
        prompt.append("1. Stay in character as ").append(npcName).append("\n");
        prompt.append("2. Respond naturally based on the given context\n");
        prompt.append("3. Keep responses conversational and engaging\n");
        prompt.append("4. Reference the weather, time, season, or relationship level when appropriate\n");
        prompt.append("5. Show personality and emotion\n");
        prompt.append("6. Keep the response between 10-25 words\n");
        prompt.append("7. Do not break character or mention you are an AI\n");
        prompt.append("8. If receiving a gift, react appropriately (excited for favorites, grateful for others)\n\n");
        prompt.append("Respond as ").append(npcName).append(":");

        return prompt.toString();
    }


    private static String parseOllamaResponse(String json) {
        try {
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            if (jsonObject.has("response")) {
                String response = jsonObject.get("response").getAsString().trim();

                response = response.replaceAll("^[\"']|[\"']$", "");
                response = response.replaceAll("\\n+", " ");
                response = response.replaceAll("\\s+", " ");

                return response.isEmpty() ? getFallbackResponse("NPC") : response;
            }
        } catch (Exception e) {
            System.err.println("Error parsing Ollama response: " + e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        for (String line : json.split("\n")) {
            if (line.trim().isEmpty()) continue;
            try {
                JsonObject lineJson = gson.fromJson(line, JsonObject.class);
                if (lineJson.has("response")) {
                    sb.append(lineJson.get("response").getAsString());
                }
            } catch (Exception ignored) {
            }
        }

        String result = sb.toString().trim();
        return result.isEmpty() ? getFallbackResponse("NPC") : result;
    }

    private static String getFallbackResponse(String npcName) {
        String[] fallbacks = {
            "Hello there! How are you doing today?",
            "Nice weather we're having, isn't it?",
            "Good to see you again!",
            "Hope you're having a great day!",
            "What brings you by today?",
            "Always a pleasure to chat with you!",
            "How's life treating you?",
            "Lovely day for a conversation!"
        };

        int index = Math.abs(npcName.hashCode()) % fallbacks.length;
        return fallbacks[index];
    }

    public static boolean testConnection() {
        try {
            JsonObject requestJson = new JsonObject();
            requestJson.addProperty("model", MODEL);
            requestJson.addProperty("prompt", "Hello");
            requestJson.addProperty("stream", false);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_API_URL))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(10))
                .POST(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            System.err.println("Ollama connection test failed: " + e.getMessage());
            return false;
        }
    }

    public static String[] getAvailableModels() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/tags"))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
                if (jsonObject.has("models")) {
                    return gson.fromJson(jsonObject.get("models"), String[].class);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to get available models: " + e.getMessage());
        }

        return new String[]{MODEL};
    }
}
