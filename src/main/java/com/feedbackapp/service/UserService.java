package com.feedbackapp.service;

import com.feedbackapp.model.Feedback;
import com.feedbackapp.model.User;
import com.feedbackapp.util.ApiKeyManager;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private Map<String, User> users = new HashMap<>(); // Stores registered users
  private Map<String, List<Feedback>> feedbackStore = new HashMap<>(); // User feedbacks
  private int feedbackIdCounter = 100000;

  // User Registration
  public boolean register(User user) {
    if (users.containsKey(user.getUsername())) {
      return false; // Username already exists
    }
    users.put(user.getUsername(), user);
    feedbackStore.put(user.getUsername(), new ArrayList<>());
    return true;
  }

  // User Auth
  public boolean authenticate(String username, String password) {
    return users.containsKey(username) && users.get(username).getPassword().equals(password);
  }

  // Submit Feedback
  public Feedback addFeedback(String username, String content) {
    Integer id = feedbackIdCounter++;
    Feedback feedback = new Feedback(id, username, content);
    feedbackStore.get(username).add(feedback);
    return feedback;
  }

  // Fetch User's Feedback
  public List<Feedback> getFeedbacks(String username) {
    return feedbackStore.getOrDefault(username, new ArrayList<>());
  }

  // Edit Feedback
  public boolean editFeedback(String username, String feedbackId, String newContent) {
    List<Feedback> feedbacks = feedbackStore.get(username);
    for (Feedback feedback : feedbacks) {
      if (feedback.getId().equals(feedbackId)) {
        feedback.setContent(newContent);
        return true;
      }
    }
    return false;
  }

  // Delete Feedback
  public boolean deleteFeedback(String username, String feedbackId) {
    return feedbackStore.get(username).removeIf(fb -> fb.getId().equals(feedbackId));
  }

  // Helper method to retrieve username from API key
  public String getUsernameByApiKey(String apiKey) {
    return ApiKeyManager.userApiKeys.entrySet().stream()
        .filter(entry -> entry.getValue().equals(apiKey))
        .map(entry -> entry.getKey())
        .findFirst()
        .orElse(null);
  }
}
