package edu.apu.crs.usermanagement.GUI;

import javax.swing.*;
import java.awt.*;

public class RestPasswordForm {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton resetButton;
    private JButton cancelButton;


    public RestPasswordForm() {
        frame = new JFrame("Reset Password");
        frame.setSize(500, 350);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ===== Labels and Fields =====
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 120, 25);
        frame.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 50, 250, 25);
        frame.add(usernameField);

        JLabel newPassLabel = new JLabel("New Password:");
        newPassLabel.setBounds(50, 100, 120, 25);
        frame.add(newPassLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(180, 100, 250, 25);
        frame.add(newPasswordField);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setBounds(50, 150, 120, 25);
        frame.add(confirmPassLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(180, 150, 250, 25);
        frame.add(confirmPasswordField);

        // ===== Buttons =====
        resetButton = new JButton("Reset Password");
        resetButton.setBounds(100, 220, 150, 35);
        frame.add(resetButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(270, 220, 100, 35);
        frame.add(cancelButton);

        frame.setVisible(true);
    }

    // Getters for later use in controller class
    public JTextField getUsernameField() { return usernameField; }
    public JPasswordField getNewPasswordField() { return newPasswordField; }
    public JPasswordField getConfirmPasswordField() { return confirmPasswordField; }
    public JButton getResetButton() { return resetButton; }
    public JButton getCancelButton() { return cancelButton; }

}
