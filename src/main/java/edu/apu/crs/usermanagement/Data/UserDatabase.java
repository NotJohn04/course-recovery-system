package edu.apu.crs.usermanagement.Data;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
     // username → password
    public static Map<String, String> users = new HashMap<>();

    // username → role
    public static Map<String, String> roles = new HashMap<>();

    static {
        // Admin
        users.put("admin", "admin123");
        roles.put("admin", "ADMIN");

        // Academic Officers
        users.put("officer1", "pass1");
        roles.put("officer1", "OFFICER");

        users.put("officer2", "pass2");
        roles.put("officer2", "OFFICER");

        // Students
        users.put("student1", "111");
        roles.put("student1", "STUDENT");

        users.put("student2", "222");
        roles.put("student2", "STUDENT");
    }

    public static boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public static String getRole(String username) {
        return roles.get(username);
    }


}
