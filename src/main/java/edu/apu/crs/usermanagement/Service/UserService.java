package edu.apu.crs.usermanagement.Service;
import edu.apu.crs.usermanagement.Data.User;
import java.util.ArrayList;
import java.util.List;
public class UserService {
    List <User> users = new ArrayList<>();
    public UserService(){

  
  users.add(new User("Admin1", "admin@1", "Admin", true));
  users.add(new User("Admin2", "admin@2", "Admin", true));
  users.add(new User("Admin3", "admin@3", "Admin", true));
  

  users.add(new User("Student1", "Student@1", "Student", true));
  users.add(new User("Student2", "Student@2", "Student", true));
  users.add(new User("Student3", "Student@3", "Student", true));
  
 
  users.add(new User("inst1", "inst@1", "Instructor", true));
  users.add(new User("inst2", "inst@2", "Instructor", true));
  users.add(new User("inst3", "inst@3", "Instructor", true));
  
 
   users.add(new User("Aoff1", "off@1", "Officer", true));
   users.add(new User("Aoff2", "off@2", "Officer", true));
   users.add(new User("Aoff3", "off@3", "Officer", true));


    }

    public User login(String username, String password) {
      for (User u : users) {
          if (u.getUsername().equals(username)
                  && u.getPassword().equals(password)
                  && u.isActive()) {
              return u;
          }
      }
      return null;
  }
  public void addUser(String username, String password, String role, boolean active) {
    users.add(new User(username, password, role, active));
}
public boolean removeUser(String username) {
  return users.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
}

public User findUser(String username) {
  for (User u : users) {
      if (u.getUsername().equalsIgnoreCase(username)) {
          return u;
      }
  }
  return null;
}

public boolean deactivateUser(String username) {
  User u = findUser(username);
  if (u != null) {
      u.setActive(false);
      return true;
  }
  return false;
}


public boolean activateUser(String username) {
  User u = findUser(username);
  if (u != null) {
      u.setActive(true);
      return true;
  }
  return false;
}
public List<User> getAllUsers() {
  return users;
}

public boolean updatePassword(String username, String newPassword) {
  User u = findUser(username);
  if (u != null) {
      u.setPassword(newPassword);
      return true;
  }
  return false;
}
public void printAllUsers() {
  System.out.println("---- All Users ----");
  for (User u : users) {
      System.out.println(u.getUsername() + " | " + u.getRole() + " | Active: " + u.isActive());
  }
  System.out.println("-------------------");
}

}
