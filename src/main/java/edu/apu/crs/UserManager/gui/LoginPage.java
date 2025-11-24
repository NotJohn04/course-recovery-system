package edu.apu.crs.UserManager.gui;

import edu.apu.crs.UserManager.userservice.UserService;
import edu.apu.crs.courserecovery.CourseRecoveryDashboard;

import javax.swing.*;

public class LoginPage {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, resetButton, exitButton;

    public LoginPage() {
        frame = new JFrame("User Login");
        frame.setLayout(null);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        frame.add(userLabel);
        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 180, 25);
        frame.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 80, 25);
        frame.add(passLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 180, 25);
        frame.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(50, 180, 80, 30);
        frame.add(loginButton);

        resetButton = new JButton("Reset Password");
        resetButton.setBounds(140, 180, 150, 30);
        frame.add(resetButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(300, 180, 70, 30);
        frame.add(exitButton);

        addEventHandlers();
        frame.setVisible(true);
    }

    private void addEventHandlers() {
        // LOGIN BUTTON
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            UserService service = new UserService();
            String result = service.login(username, password);

            if (result.equals("SUCCESS")) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                new CourseRecoveryDashboard(username).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, result, "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        // RESET PASSWORD
        resetButton.addActionListener(e -> {
            new PasswordManager();
            frame.dispose();
        });

        // EXIT
        exitButton.addActionListener(e -> System.exit(0));
    }
}

