package edu.apu.crs.courserecovery;

import javax.swing.*;

public class CourseRecoveryDashboard extends JFrame {
    public CourseRecoveryDashboard() {
        setTitle("Course Recovery Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // For now just show a blank label
        JLabel label = new JLabel("Welcome to Course Recovery Dashboard!", SwingConstants.CENTER);
        add(label);
    }
}
