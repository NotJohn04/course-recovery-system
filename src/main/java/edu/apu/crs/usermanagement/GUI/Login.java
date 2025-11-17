package edu.apu.crs.usermanagement.GUI;
import javax.swing.*;

import edu.apu.crs.usermanagement.Data.UserDatabase;

import java.awt.*;

public class Login {
        private JFrame frame;



private void openFormForRole(String role) {
    switch (role) {
        case "ADMIN":
            new UserAccountForm().show();   // your User Manager form
            break;

       //case "OFFICER":
        //   new UserAccountForm().show();   // your User Manager form
          //  break; 

       // case "STUDENT":
           
        default:
           JOptionPane.showMessageDialog(null, "Unknown role: " + role);
           break; }
    }
public Login() {
        // Create the main frame
        frame = new JFrame("SRC Technology Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null); // turn off layout manager
        frame.setLocationRelativeTo(null);

        // ===== LEFT PANEL =====
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBounds(0, 0, 300, 500); // x, y, width, height
        leftPanel.setLayout(null); // center the text

        JLabel srcLabel = new JLabel("SRC");
        srcLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        srcLabel.setBounds(110, 180, 100, 40);

        JLabel techLabel = new JLabel("Technology");
        techLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        techLabel.setBounds(100, 220, 150, 25);

        leftPanel.add(srcLabel);
        leftPanel.add(techLabel);

              // ===== RIGHT PANEL =====
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(180, 180, 180)); // gray
        rightPanel.setBounds(300, 0, 600, 500);
        rightPanel.setLayout(null); // disable layout

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        welcomeLabel.setBounds(220, 80, 200, 40);
        rightPanel.add(welcomeLabel);

        // Username label + field
        JLabel userLabel = new JLabel("Enter your username:");
        userLabel.setBounds(150, 150, 200, 20);
        rightPanel.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 175, 300, 35);
        rightPanel.add(usernameField);

        // Password label + field
        JLabel passLabel = new JLabel("Enter your password:");
        passLabel.setBounds(150, 225, 200, 20);
        rightPanel.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 250, 300, 35);
        rightPanel.add(passwordField);

        // Buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(180, 320, 100, 35);
        rightPanel.add(loginButton);

        JButton forgotButton = new JButton("Forgot password");
        forgotButton.setBounds(310, 320, 150, 35);
        rightPanel.add(forgotButton);

        // ===== ADD PANELS TO FRAME =====
        frame.add(leftPanel);
        frame.add(rightPanel);

     loginButton.addActionListener(e -> {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());

    if (UserDatabase.authenticate(username, password)) {
        String role = UserDatabase.getRole(username);

        JOptionPane.showMessageDialog(frame, "Login successful! Role: " + role);

        frame.dispose(); // close login screen

        // Open correct form
        openFormForRole(role);
    } else {
        JOptionPane.showMessageDialog(frame, "Invalid username or password.");
    }
});
    }

    public void show() {
        frame.setVisible(true);
    }


}

   