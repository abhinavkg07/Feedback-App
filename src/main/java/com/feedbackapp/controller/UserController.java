package com.feedbackapp.controller;

import com.feedbackapp.model.Feedback;
import com.feedbackapp.model.User;
import com.feedbackapp.service.UserService;
import com.feedbackapp.util.ApiKeyManager;
import com.feedbackapp.util.SessionManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired private UserService userService;

  // User Registration
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody User user) {
    if (userService.register(user)) {
      return ResponseEntity.ok("Registration successful.");
    } else {
      return ResponseEntity.badRequest().body("Username already exists.");
    }
  }

  // Login
  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestParam String username, @RequestParam String password) {
    if (userService.authenticate(username, password)) {
      SessionManager.login(username);
      String apiKey = ApiKeyManager.generateApiKey(username);
      return ResponseEntity.ok("Login successful. Your API Key: " + apiKey);
    } else {
      return ResponseEntity.status(401).body("Invalid credentials.");
    }
  }

  // Logout
  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestParam String username) {
    if (SessionManager.isLoggedIn(username)) {
      SessionManager.logout(username);
      ApiKeyManager.revokeApiKey(username);
      return ResponseEntity.ok("Logout successful. API key revoked.");
    }
    return ResponseEntity.status(401).body("User not logged in.");
  }

  // Submit Feedback
  @PostMapping("/feedback")
  public ResponseEntity<Feedback> submitFeedback(
      @RequestHeader("x-api-key") String apiKey, @RequestBody String content) {
    if (ApiKeyManager.isValidApiKey(apiKey)) {
      String username = userService.getUsernameByApiKey(apiKey);
      Feedback feedback = userService.addFeedback(username, content);
      return ResponseEntity.ok(feedback);
    }
    return ResponseEntity.status(401).build();
  }

  // Feedbacks List
  @GetMapping("/feedback")
  public ResponseEntity<List<Feedback>> getFeedbacks(@RequestHeader("x-api-key") String apiKey) {
    if (ApiKeyManager.isValidApiKey(apiKey)) {
      String username = userService.getUsernameByApiKey(apiKey);
      return ResponseEntity.ok(userService.getFeedbacks(username));
    }
    return ResponseEntity.status(401).build();
  }

  // Edit Feedback
  @PutMapping("/feedback/{feedbackId}")
  public ResponseEntity<String> editFeedback(
      @RequestHeader("x-api-key") String apiKey,
      @PathVariable String feedbackId,
      @RequestBody String newContent) {
    if (ApiKeyManager.isValidApiKey(apiKey)) {
      String username = userService.getUsernameByApiKey(apiKey);
      if (userService.editFeedback(username, feedbackId, newContent)) {
        return ResponseEntity.ok("Feedback updated.");
      }
    }
    return ResponseEntity.status(403).body("Unauthorized or Feedback not found.");
  }

  // Delete Feedback
  @DeleteMapping("/feedback")
  public ResponseEntity<String> deleteFeedback(
      @RequestHeader("x-api-key") String apiKey, @RequestParam String feedbackId) {
    if (ApiKeyManager.isValidApiKey(apiKey)) {
      String username = userService.getUsernameByApiKey(apiKey);
      if (userService.deleteFeedback(username, feedbackId)) {
        return ResponseEntity.ok("Feedback deleted.");
      }
    }
    return ResponseEntity.status(403).body("Unauthorized or Feedback not found.");
  }
}
