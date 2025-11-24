package edu.apu.crs.UserManager.gui;

import edu.apu.crs.UserManager.userservice.UserService;

import javax.swing.*;

public class PasswordManager {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField newPasswordField;
    private JButton resetButton;

    public PasswordManager() {
        frame = new JFrame("Reset Password");
        frame.setSize(350, 200);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 100, 25);
        frame.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 20, 150, 25);
        frame.add(usernameField);

        JLabel passLabel = new JLabel("New Password:");
        passLabel.setBounds(20, 60, 100, 25);
        frame.add(passLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(130, 60, 150, 25);
        frame.add(newPasswordField);

        resetButton = new JButton("Reset");
        resetButton.setBounds(100, 110, 100, 30);
        frame.add(resetButton);

        resetButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword());

            UserService service = new UserService();
            boolean success = service.resetPassword(username, newPassword);

            if (success) {
                JOptionPane.showMessageDialog(frame, "Password reset successful!");
                frame.dispose();
                new LoginPage(); // go back to login
            } else {
                JOptionPane.showMessageDialog(frame, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
}

