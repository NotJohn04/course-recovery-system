package edu.apu.crs.usermanagement.Service;

import edu.apu.crs.usermanagement.Data.User;
import edu.apu.crs.usermanagement.Data.UserDatabase;

import java.util.List;

public class UserService {

    // ===== ADD USER =====
    public boolean addUser(User user) {
        return UserDatabase.addUser(
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.isActive()   // boolean
        );
    }

    // ===== UPDATE USER =====
    public boolean updateUser(User user) {
        return UserDatabase.updateUser(
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.isActive()   // boolean
        );
    }

    // ===== DEACTIVATE USER =====
    public boolean deactivateUser(String username) {
        return UserDatabase.deactivateUser(username);
    }

    // ===== LOAD ALL USERS =====
    public List<User> getAllUsers() {
        return UserDatabase.getAllUsers();
    }

    // ===== LOGIN AUTHENTICATION =====
    public User authenticate(String username, String password) {
        if (UserDatabase.authenticate(username, password)) {

            String role = UserDatabase.getRole(username);
            boolean active = UserDatabase.isActive(username);

            return new User(username, password, role, active);
        }
        return null; // Login failed
    }
}
