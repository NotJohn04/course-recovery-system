package edu.apu.crs.usermanagement.Data;

import java.util.*;
    
public class UserDatabase {

    // username → password
    private static Map<String, String> users = new HashMap<>();

    // username → role
    private static Map<String, String> roles = new HashMap<>();

    // username → active/inactive as boolean
    private static Map<String, Boolean> activeStatus = new HashMap<>();

    static {
        // Default Admin
        users.put("admin", "admin123");
        roles.put("admin", "ADMIN");
        activeStatus.put("admin", true);

        // Academic Officers
        users.put("officer1", "pass1");
        roles.put("officer1", "OFFICER");
        activeStatus.put("officer1", true);

        users.put("officer2", "pass2");
        roles.put("officer2", "OFFICER");
        activeStatus.put("officer2", false);

        // Students
        users.put("student1", "111");
        roles.put("student1", "STUDENT");
        activeStatus.put("student1", true);

        users.put("student2", "222");
        roles.put("student2", "STUDENT");
        activeStatus.put("student2", false);
    }

    // ===== AUTHENTICATION =====
    public static boolean authenticate(String username, String password) {
        return users.containsKey(username) &&
               users.get(username).equals(password);
    }

    public static String getRole(String username) {
        return roles.get(username);
    }

    public static boolean isActive(String username) {
        return activeStatus.getOrDefault(username, false);
    }

    // ===== ADD USER =====
    public static boolean addUser(String username, String password, String role, boolean active) {
        if (users.containsKey(username)) return false;

        users.put(username, password);
        roles.put(username, role);
        activeStatus.put(username, active);

        return true;
    }

    // ===== UPDATE USER =====
    public static boolean updateUser(String username, String password, String role, boolean active) {
        if (!users.containsKey(username)) return false;

        users.put(username, password);
        roles.put(username, role);
        activeStatus.put(username, active);

        return true;
    }

    // ===== DEACTIVATE USER =====
    public static boolean deactivateUser(String username) {
        if (!activeStatus.containsKey(username)) return false;

        activeStatus.put(username, false);
        return true;
    }

    // ===== GET ALL USERS =====
    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        for (String username : users.keySet()) {
            list.add(new User(
                    username,
                    users.get(username),
                    roles.get(username),
                    activeStatus.get(username)
            ));
        }

        return list;
    }
}
