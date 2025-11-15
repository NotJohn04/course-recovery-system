package edu.apu.crs.usermanagement;

import edu.apu.crs.courserecovery.CourseRecoveryDashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                if(username.equals("admin") && password.equals("1234")) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new CourseRecoveryDashboard(username).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });
    }
}
