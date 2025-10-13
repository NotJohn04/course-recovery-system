package edu.apu.crs.courserecovery;

import edu.apu.crs.notification.NotificationService;
import javax.swing.*;
import java.awt.*;

public class CourseRecoveryDashboard extends JFrame {

    private String username;
    private NotificationService notificationService;    

    // ✅ Constructor with username
    public CourseRecoveryDashboard(String username) {
        this.username = username;
        this.notificationService = new NotificationService();

        setTitle("Course Recovery Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);

        JButton testEmailBtn = new JButton("Send Test Email");
        testEmailBtn.addActionListener(e -> {
            notificationService.sendRecoveryProgressUpdate("thamkingjoe9@gmail.com", "Algorithms", "Milestone 2", "Completed 70%");
        });
        

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(testEmailBtn, BorderLayout.CENTER);

        add(panel);
    }

    // ✅ Optional no-arg constructor (for testing only)
    public CourseRecoveryDashboard() {
        this("Guest");
    }
}
