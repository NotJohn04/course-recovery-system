package edu.apu.crs.usermanagement;

import javax.swing.*;

public class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Login Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passText = new JPasswordField(15);

        JButton loginButton = new JButton("Login");

        JPanel panel = new JPanel();
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(loginButton);

        add(panel);
    }
}
