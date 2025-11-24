package edu.apu.crs.UserManager.userservice;

import edu.apu.crs.UserManager.model.User;

import java.util.List;

public class UserService {

    // Attempt to login
    public String login(String username, String password) {
        List<User> users = UserStorage.getAllUsers();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (!user.isActive()) {
                    return "User is inactive";
                }
                if (user.getPassword().equals(password)) {
                    return "SUCCESS";
                } else {
                    return "Incorrect password";
                }
            }
        }
        return "User not found";
    }

    // Reset the password for a user
    public boolean resetPassword(String username, String newPassword) {
        List<User> users = UserStorage.getAllUsers();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword);
                UserStorage.updateUser(user);
                return true;
            }
        }
        return false;
    }

    // Optionally, you can add more methods like createUser, deleteUser, etc.
}

