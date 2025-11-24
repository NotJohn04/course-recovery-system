package edu.apu.crs.UserManager.userservice;

import edu.apu.crs.UserManager.model.User;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private static final String USER_DATA_FILE = "UserData.txt";
    private static final String RESOURCE_PATH = "UserData.txt";
    
    private static File getUserDataFile() throws IOException {
        // Try to use a writable location (project root or user directory)
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        File userFile = new File(dataDir, USER_DATA_FILE);
        
        // If file doesn't exist, copy from resources
        if (!userFile.exists()) {
            URL resource = UserStorage.class.getClassLoader().getResource(RESOURCE_PATH);
            if (resource != null) {
                try (InputStream is = resource.openStream()) {
                    Files.copy(is, userFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                // Fallback: try src/main/resources
                File srcFile = new File("src/main/resources/" + RESOURCE_PATH);
                if (srcFile.exists()) {
                    Files.copy(srcFile.toPath(), userFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    // Create default file
                    createDefaultFile(userFile);
                }
            }
        }
        return userFile;
    }
    
    private static void createDefaultFile(File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("username,password,role,isActive");
            writer.println("admin,admin123,ADMIN,true");
            writer.println("officer1,pass1,OFFICER,true");
            writer.println("officer2,pass2,OFFICER,true");
            writer.println("officer3,pass3,OFFICER,true");
            writer.println("officer4,pass4,OFFICER,true");
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            File userFile = getUserDataFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip header line
                    }
                    if (line.trim().isEmpty()) {
                        continue; // Skip empty lines
                    }
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String username = parts[0].trim();
                        String password = parts[1].trim();
                        String role = parts[2].trim();
                        boolean isActive = Boolean.parseBoolean(parts[3].trim());
                        users.add(new User(username, password, role, isActive));
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading user data: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error accessing user data file: " + e.getMessage());
        }
        return users;
    }

    public static void updateUser(User updatedUser) {
        List<User> users = getAllUsers();
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                found = true;
                break;
            }
        }
        if (found) {
            saveAllUsers(users);
        }
    }

    private static void saveAllUsers(List<User> users) {
        try {
            File userFile = getUserDataFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(userFile))) {
                writer.println("username,password,role,isActive");
                for (User user : users) {
                    writer.println(user.getUsername() + "," + 
                                 user.getPassword() + "," + 
                                 user.getRole() + "," + 
                                 user.isActive());
                }
            } catch (IOException e) {
                System.err.println("Error saving user data: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error accessing user data file: " + e.getMessage());
        }
    }
}

