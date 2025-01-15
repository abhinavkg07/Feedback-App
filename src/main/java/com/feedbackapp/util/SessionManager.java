package com.feedbackapp.util;

import java.util.HashSet;
import java.util.Set;

public class SessionManager {
  private static Set<String> loggedInUsers = new HashSet<>();

  public static void login(String username) {
    loggedInUsers.add(username);
  }

  public static void logout(String username) {
    loggedInUsers.remove(username);
  }

  public static boolean isLoggedIn(String username) {
    return loggedInUsers.contains(username);
  }
}
