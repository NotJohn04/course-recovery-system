package edu.apu.crs.usermanagement.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class UserAccountForm {

    private JFrame frame;
    private JTable userTable;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JComboBox<String> statusBox;
    private JButton addButton, updateButton, deactivateButton, refreshButton;

    public UserAccountForm() {
        frame = new JFrame("User Account Management");
        frame.setSize(800, 500);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ===== TABLE =====
        String[] columns = {"Username", "Role", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(userTable);
        tableScroll.setBounds(400, 50, 360, 350);
        frame.add(tableScroll);

        // ===== INPUT FIELDS =====
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 50, 100, 25);
        frame.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 50, 200, 25);
        frame.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 90, 100, 25);
        frame.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 90, 200, 25);
        frame.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(30, 130, 100, 25);
        frame.add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"Admin", "User"});
        roleBox.setBounds(130, 130, 200, 25);
        frame.add(roleBox);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(30, 170, 100, 25);
        frame.add(statusLabel);

        statusBox = new JComboBox<>(new String[]{"Active", "Inactive"});
        statusBox.setBounds(130, 170, 200, 25);
        frame.add(statusBox);

        // ===== BUTTONS =====
        addButton = new JButton("Add");
        addButton.setBounds(30, 220, 90, 30);
        frame.add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(140, 220, 90, 30);
        frame.add(updateButton);

        deactivateButton = new JButton("Deactivate");
        deactivateButton.setBounds(250, 220, 120, 30);
        frame.add(deactivateButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(380, 420, 100, 30);
        frame.add(refreshButton);

        frame.setVisible(true);
    }

    // Getters for later use in controller class
    public JTextField getUsernameField() { return usernameField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JComboBox<String> getRoleBox() { return roleBox; }
    public JComboBox<String> getStatusBox() { return statusBox; }
    public JTable getUserTable() { return userTable; }
    public JButton getAddButton() { return addButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeactivateButton() { return deactivateButton; }
    public JButton getRefreshButton() { return refreshButton; }

    public void show() {
    frame.setVisible(true);
}


    

}
