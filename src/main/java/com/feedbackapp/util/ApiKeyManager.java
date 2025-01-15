package com.feedbackapp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApiKeyManager {
  public static final Map<String, String> userApiKeys = new HashMap<>();

  // Generate ApiKey
  public static String generateApiKey(String username) {
    String apiKey = UUID.randomUUID().toString();
    userApiKeys.put(username, apiKey);
    return apiKey;
  }

  // Validate ApiKey
  public static boolean isValidApiKey(String apiKey) {
    return userApiKeys.containsValue(apiKey);
  }

  public static void revokeApiKey(String username) {
    userApiKeys.remove(username);
  }

  public static String getApiKey(String username) {
    return userApiKeys.get(username);
  }
}
