package com.example.server.models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thin wrapper around a local Ollama HTTP API.
 * Calls are executed off the main thread and return CompletableFuture results.
 */
public class OllamaClient {
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    private static final String MODEL = "llama3"; // replace with your model tag if needed

    private static final HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(60))
        .build();

    private static final Gson gson = new Gson();

    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * Generate an NPC dialog asynchronously. Returns a CompletableFuture that completes
     * with the trimmed NPC response or completes exceptionally on failure.
     */
    public static CompletableFuture<String> generateNPCDialogAsync(String npcName, String npcJob, String context) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Preparing prompt for " + npcName + "...");
                String prompt = buildPrompt(npcName, npcJob, context);
                System.out.println("Prompt length: " + prompt.length());

                JsonObject requestJson = new JsonObject();
                requestJson.addProperty("model", MODEL);
                requestJson.addProperty("prompt", prompt);
                requestJson.addProperty("stream", false);

                requestJson.addProperty("num_predict", 50);
                requestJson.addProperty("temperature", 0.7);
                requestJson.addProperty("top_p", 0.9);
                requestJson.addProperty("top_k", 40);
                requestJson.addProperty("stop", "\n");

                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_API_URL))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(60))
                    .POST(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
                    .build();

                System.out.println("Sending request to Ollama...");
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    System.out.println("Ollama responded successfully.");
                    return parseOllamaResponse(response.body());
                } else {
                    String errorMsg = "Ollama API error: " + response.statusCode() + " - " + response.body();
                    System.err.println(errorMsg);
                    throw new RuntimeException(errorMsg);
                }
            } catch (Exception e) {
                System.err.println("Exception during Ollama call: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to generate dialog", e);
            }
        }, executor);
    }

    /**
     * Build a simple, human-friendly prompt. Kept concise to reduce token usage.
     */
    private static String buildPrompt(String npcName, String npcJob, String context) {

        return "You are " + npcName + ", a " + npcJob + " in a farming game.\n\n" +
            "Context: " + context + "\n\n" +
            "Guidelines:\n" +
            "- Keep responses short (roughly 10-25 words).\n" +
            "- Stay in character as " + npcName + ".\n" +
            "- Be friendly and natural.\n" +
            "- Use the context when it helps the reply.\n\n" +
            npcName + " says:";
    }

    /**
     * Try to extract a response field, otherwise fall back to scanning lines for response objects.
     * The result is normalized to a single-line string and truncated if excessively long.
     */
    private static String parseOllamaResponse(String json) {
        try {
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            if (jsonObject != null && jsonObject.has("response")) {
                String response = jsonObject.get("response").getAsString().trim();
                response = normalizeResponse(response);
                return response.isEmpty() ? getFallbackResponse("NPC") : response;
            }
        } catch (Exception e) {
            System.err.println("Error parsing top-level Ollama response: " + e.getMessage());
            e.printStackTrace();
        }

        // Fallback: try reading line-by-line for JSON objects (streaming or multi-line responses).
        StringBuilder collected = new StringBuilder();
        for (String line : json.split("\n")) {
            if (line.trim().isEmpty()) continue;
            try {
                JsonObject lineJson = gson.fromJson(line, JsonObject.class);
                if (lineJson != null && lineJson.has("response")) {
                    JsonElement el = lineJson.get("response");
                    if (el != null) collected.append(el.getAsString());
                }
            } catch (Exception ignored) {
                // ignore parse errors on individual lines
            }
        }

        String result = normalizeResponse(collected.toString());
        return result.isEmpty() ? getFallbackResponse("NPC") : result;
    }

    private static String normalizeResponse(String response) {
        if (response == null) return "";
        response = response.replaceAll("^[\"']|[\"']$", "");
        response = response.replaceAll("\\n+", " ");
        response = response.replaceAll("\\s+", " ").trim();

        if (response.length() > 150) {
            int lastSpace = response.substring(0, 150).lastIndexOf(' ');
            response = response.substring(0, lastSpace > 0 ? lastSpace : 150) + "...";
        }

        return response;
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

    public static void warmUpModel() {
        System.out.println("Warming up model " + MODEL + "...");
        try {
            generateNPCDialogAsync("Test", "villager", "This is a warmup request.")
                .thenAccept(response -> System.out.println("Model warmup complete with response: " + response))
                .exceptionally(ex -> {
                    System.err.println("Model warmup failed: " + ex.getMessage());
                    return null;
                }).join();
        } catch (Exception e) {
            System.err.println("Model warmup failed unexpectedly: " + e.getMessage());
        }
    }

    @SuppressWarnings("unused")
    private static String generateNPCDialog(String npcName, String npcJob, String context) {
        try {
            System.out.println("Building the prompt for synchronous call...");
            String prompt = buildPrompt(npcName, npcJob, context);

            JsonObject requestJson = new JsonObject();
            requestJson.addProperty("model", MODEL);
            requestJson.addProperty("prompt", prompt);
            requestJson.addProperty("stream", false);

            requestJson.addProperty("num_predict", 50);
            requestJson.addProperty("temperature", 0.7);
            requestJson.addProperty("top_p", 0.9);
            requestJson.addProperty("top_k", 40);
            requestJson.addProperty("stop", "\n");

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_API_URL))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(60))
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
}
