package edu.apu.crs.courserecovery;

import edu.apu.crs.notification.NotificationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRecoveryDashboard extends JFrame {

    private final String username;
    private final NotificationService notificationService;

    // --- Table Models ---
    private DefaultTableModel recoveryModel;
    private DefaultTableModel eligibilityModel;
    private DefaultTableModel reportModel;

    // --- Components used across methods ---
    private JComboBox<String> studentCombo;
    private JComboBox<String> courseCombo;

    // --- Recovery templates & course names ---
    private Map<String, String[][]> defaultPlans;
    private Map<String, String> courseNames;   // e.g. "CS201" -> "Data Structures"

    // --- File paths ---
    private static final String DATA_DIR = "src/main/resources/data";
    private static final String STUDENT_FILE = DATA_DIR + "/student_information.txt";

    // --- Students loaded from file ---
    private List<StudentRecord> students = new ArrayList<>();

    // --- Module columns from header (dynamic) ---
    private List<String> moduleColumns = new ArrayList<>();

    public CourseRecoveryDashboard(String username) {
        this.username = username;
        this.notificationService = new NotificationService();

        initDefaultPlans();       // recovery templates + friendly names
        loadStudentsFromFile();   // students + failed modules from txt file

        setTitle("Course Recovery Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header
        JLabel welcome = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcome, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Course Recovery Plan", buildCourseRecoveryPanel());
        tabs.add("Eligibility & Enrolment", buildEligibilityPanel());
        tabs.add("Academic Performance Report", buildReportPanel());
        tabs.add("Email Notifications", buildNotificationPanel());

        add(tabs, BorderLayout.CENTER);
    }

    // =====================================================================
    // 1️⃣ COURSE RECOVERY PLAN TAB
    // =====================================================================
    private JPanel buildCourseRecoveryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel selectionPanel = new JPanel(new GridLayout(2, 1));

        // Row 1: student + failed course selection
        JPanel studentRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studentRow.add(new JLabel("Student:"));

        List<String> comboItems = new ArrayList<>();
        for (StudentRecord s : students) {
            if (s.failedCourses > 0) { // only students with at least one failed module
                comboItems.add(s.id + " - " + s.name);
            }
        }
        studentCombo = new JComboBox<>(comboItems.toArray(new String[0]));
        studentRow.add(studentCombo);

        studentRow.add(new JLabel("   Failed Course:"));
        courseCombo = new JComboBox<>();
        studentRow.add(courseCombo);

        JLabel infoLabel = new JLabel("   Status: Needs course recovery plan");
        studentRow.add(infoLabel);

        // Row 2: milestone input
        JPanel inputRow = new JPanel(new GridLayout(1, 6));
        JTextField weekField = new JTextField();
        JTextField taskField = new JTextField();
        JTextField progressField = new JTextField();

        inputRow.add(new JLabel("Week:"));
        inputRow.add(weekField);
        inputRow.add(new JLabel("Task:"));
        inputRow.add(taskField);
        inputRow.add(new JLabel("Progress (%):"));
        inputRow.add(progressField);

        selectionPanel.add(studentRow);
        selectionPanel.add(inputRow);

        // Milestones table
        recoveryModel = new DefaultTableModel(
                new String[]{"Week", "Task", "Progress (%)"}, 0
        );
        JTable table = new JTable(recoveryModel);

        // Behaviour when selection changes
        studentCombo.addActionListener(e -> {
            updateCourseComboForSelectedStudent();
            loadPlanForSelection();
        });
        courseCombo.addActionListener(e -> loadPlanForSelection());

        // Buttons
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton removeBtn = new JButton("Remove");
        JButton clearBtn = new JButton("Clear Plan");
        JButton saveBtn = new JButton("Save Plan");

        addBtn.addActionListener(e -> {
            recoveryModel.addRow(new Object[]{
                    weekField.getText(),
                    taskField.getText(),
                    progressField.getText()
            });
            weekField.setText("");
            taskField.setText("");
            progressField.setText("");
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                recoveryModel.setValueAt(weekField.getText(), row, 0);
                recoveryModel.setValueAt(taskField.getText(), row, 1);
                recoveryModel.setValueAt(progressField.getText(), row, 2);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a milestone row to update.",
                        "No row selected",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                recoveryModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a milestone row to remove.",
                        "No row selected",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        clearBtn.addActionListener(e -> {
            recoveryModel.setRowCount(0);
            deleteCurrentPlanFile();
        });

        saveBtn.addActionListener(e -> saveCurrentPlan());

        JPanel controls = new JPanel();
        controls.add(addBtn);
        controls.add(updateBtn);
        controls.add(removeBtn);
        controls.add(clearBtn);
        controls.add(saveBtn);

        panel.add(selectionPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);

        // Initialise first student view
        updateCourseComboForSelectedStudent();
        loadPlanForSelection();

        return panel;
    }

    // =====================================================================
    // 2️⃣ ELIGIBILITY & ENROLMENT
    // =====================================================================
    private JPanel buildEligibilityPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        eligibilityModel = new DefaultTableModel(
                new String[]{"Student ID", "Name", "CGPA", "Failed Courses", "Failed Modules", "Eligible?"}, 0
        );
        JTable table = new JTable(eligibilityModel);

        JButton calculateBtn = new JButton("Load Eligibility From File");
        calculateBtn.addActionListener(e -> populateEligibilityFromStudents());

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(calculateBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void populateEligibilityFromStudents() {
        eligibilityModel.setRowCount(0);
        for (StudentRecord s : students) {
            eligibilityModel.addRow(new Object[]{
                    s.id,
                    s.name,
                    s.cgpa,
                    s.failedCourses,
                    String.join(";", s.failedModules), // exact modules
                    s.eligible ? "YES" : "NO"
            });
        }
    }

    // =====================================================================
    // 3️⃣ ACADEMIC PERFORMANCE REPORT (demo)
    // =====================================================================
    private JPanel buildReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        reportModel = new DefaultTableModel(
                new String[]{"Course Code", "Title", "Credits", "Grade", "Points"}, 0
        );

        JTable table = new JTable(reportModel);

        JButton loadBtn = new JButton("Load Student Report");
        JButton exportBtn = new JButton("Export to PDF");

        loadBtn.addActionListener(e -> loadReportDemoData());
        exportBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "PDF Export Placeholder (Implement using iText).",
                "PDF Export",
                JOptionPane.INFORMATION_MESSAGE
        ));

        JPanel controls = new JPanel();
        controls.add(loadBtn);
        controls.add(exportBtn);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);

        return panel;
    }

    private void loadReportDemoData() {
        reportModel.setRowCount(0);

        Object[][] data = {
                {"CS201", "Data Structures", 3, "A", 4.0},
                {"CS205", "Database Systems", 3, "B+", 3.3},
                {"CS210", "Software Eng I", 3, "B", 3.0},
                {"MA202", "Discrete Math", 4, "C+", 2.3},
                {"EN201", "Academic Writing", 2, "A-", 3.7},
        };

        for (Object[] row : data) {
            reportModel.addRow(row);
        }
    }

    // =====================================================================
    // 4️⃣ EMAIL NOTIFICATION PANEL
    // =====================================================================
    private JPanel buildNotificationPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1));

        JButton loginEmail = new JButton("Send Login Notification");
        JButton passwordEmail = new JButton("Send Password Reset Email");
        JButton recoveryEmail = new JButton("Send Recovery Reminder");
        JButton reportEmail = new JButton("Send Academic Report Email");

        loginEmail.addActionListener(e ->
                notificationService.sendRecoveryProgressUpdate(
                        "thamkingjoe9@gmail.com",
                        "Login Activity",
                        "System Access",
                        "Logged In"
                )
        );

        passwordEmail.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "TODO: Implement Password Reset email")
        );

        recoveryEmail.addActionListener(e ->
                notificationService.sendRecoveryProgressUpdate(
                        "thamkingjoe9@gmail.com",
                        "Programming",
                        "Milestone 2",
                        "Pending"
                )
        );

        reportEmail.addActionListener(e ->
                notificationService.sendRecoveryProgressUpdate(
                        "thamkingjoe9@gmail.com",
                        "Academic Report",
                        "Semester 1",
                        "Ready for Review"
                )
        );

        panel.add(loginEmail);
        panel.add(passwordEmail);
        panel.add(recoveryEmail);
        panel.add(reportEmail);

        return panel;
    }

    // =====================================================================
    // 🔧 Templates & course names
    // =====================================================================
    private void initDefaultPlans() {
        defaultPlans = new HashMap<>();
        courseNames  = new HashMap<>();

        // You can change these codes & names to match your header
        courseNames.put("CS201", "Data Structures");
        courseNames.put("CS205", "Database Systems");
        courseNames.put("CS210", "Software Engineering I");

        defaultPlans.put("CS201", new String[][]{
                {"Week 1", "Revise Arrays & Linked Lists", "0"},
                {"Week 2", "Stacks & Queues practice", "0"},
                {"Week 3", "Sorting & Searching recap", "0"},
                {"Week 4", "Consultation with lecturer", "0"},
                {"Week 5", "Take recovery assessment", "0"}
        });

        defaultPlans.put("CS205", new String[][]{
                {"Week 1", "Revise ERD & conceptual design", "0"},
                {"Week 2", "Practice SQL queries", "0"},
                {"Week 3", "Normalization exercises", "0"},
                {"Week 4", "Consultation with lecturer", "0"},
                {"Week 5", "Recovery test", "0"}
        });

        defaultPlans.put("CS210", new String[][]{
                {"Week 1", "Review SDLC & requirements", "0"},
                {"Week 2", "Use case & class diagrams", "0"},
                {"Week 3", "Design & documentation", "0"},
                {"Week 4", "Consultation with lecturer", "0"},
                {"Week 5", "Project / exam recovery", "0"}
        });
    }

    // =====================================================================
    // 🔧 Load students from file (dynamic modules)
    // =====================================================================
    private void loadStudentsFromFile() {
        students.clear();
        moduleColumns.clear();

        File file = new File(STUDENT_FILE);
        if (!file.exists()) {
            System.err.println("Student file not found: " + STUDENT_FILE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine(); // header
            if (headerLine == null) return;

            String[] headers = headerLine.split(",");
            if (headers.length < 5) return;

            int idIdx = 0;
            int nameIdx = 1;
            int cgpaIdx = 2;
            int emailIdx = headers.length - 1;

            // Columns between cgpa and email are treated as modules
            for (int i = 3; i < emailIdx; i++) {
                moduleColumns.add(headers[i].trim());   // e.g. "CS201" or "dataanalysis"
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length != headers.length) continue;

                String id = parts[idIdx].trim();
                String name = parts[nameIdx].trim();
                double cgpa = Double.parseDouble(parts[cgpaIdx].trim());
                String email = parts[emailIdx].trim();

                List<String> failedModules = new ArrayList<>();
                for (int i = 3; i < emailIdx; i++) {
                    String moduleCode = headers[i].trim();
                    String value = parts[i].trim();
                    // 1 = failed, 0 = passed
                    if (value.equals("1")) {
                        failedModules.add(moduleCode);
                    }
                }

                students.add(new StudentRecord(id, name, cgpa, failedModules, email));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // =====================================================================
    // 🔧 Update course combo when student changes
    // =====================================================================
    private void updateCourseComboForSelectedStudent() {
        if (studentCombo == null || courseCombo == null) return;

        String studentItem = (String) studentCombo.getSelectedItem();
        if (studentItem == null) return;

        String studentId = studentItem.split(" - ")[0];

        StudentRecord target = null;
        for (StudentRecord s : students) {
            if (s.id.equals(studentId)) {
                target = s;
                break;
            }
        }

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (target != null) {
            for (String code : target.failedModules) {
                String name = courseNames.getOrDefault(code, code);
                model.addElement(code + " - " + name);
            }
        }
        courseCombo.setModel(model);
    }

    // =====================================================================
    // 🔧 Load plan for current (student, course)
    // =====================================================================
    private void loadPlanForSelection() {
        if (recoveryModel == null || studentCombo == null || courseCombo == null) return;

        recoveryModel.setRowCount(0);

        String studentItem = (String) studentCombo.getSelectedItem();
        String courseItem = (String) courseCombo.getSelectedItem();

        if (studentItem == null || courseItem == null) return;

        String studentId = studentItem.split(" - ")[0];
        String courseCode = courseItem.split(" - ")[0];

        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, "recovery_" + studentId + "_" + courseCode + ".txt");

        if (file.exists()) {
            // Load from file
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 3) {
                        recoveryModel.addRow(new Object[]{parts[0], parts[1], parts[2]});
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error loading recovery plan: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Use default template for this course code (if exists)
            String[][] template = defaultPlans.get(courseCode);
            if (template != null) {
                for (String[] row : template) {
                    recoveryModel.addRow(row);
                }
            } else {
                // Generic fallback if no template defined
                recoveryModel.addRow(new Object[]{"Week 1", "Review course materials", "0"});
                recoveryModel.addRow(new Object[]{"Week 2", "Consult lecturer", "0"});
                recoveryModel.addRow(new Object[]{"Week 3", "Assessment / quiz", "0"});
            }
        }
    }

    // =====================================================================
    // 🔧 Save current plan
    // =====================================================================
    private void saveCurrentPlan() {
        if (studentCombo == null || courseCombo == null) return;

        String studentItem = (String) studentCombo.getSelectedItem();
        String courseItem = (String) courseCombo.getSelectedItem();
        if (studentItem == null || courseItem == null) return;

        String studentId = studentItem.split(" - ")[0];
        String courseCode = courseItem.split(" - ")[0];

        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, "recovery_" + studentId + "_" + courseCode + ".txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < recoveryModel.getRowCount(); i++) {
                String week = String.valueOf(recoveryModel.getValueAt(i, 0));
                String task = String.valueOf(recoveryModel.getValueAt(i, 1));
                String progress = String.valueOf(recoveryModel.getValueAt(i, 2));
                pw.println(week + "|" + task + "|" + progress);
            }
            JOptionPane.showMessageDialog(this,
                    "Recovery plan saved for " + studentId + " (" + courseCode + ").");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving recovery plan: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =====================================================================
    // 🔧 Delete plan file when clearing
    // =====================================================================
    private void deleteCurrentPlanFile() {
        if (studentCombo == null || courseCombo == null) return;

        String studentItem = (String) studentCombo.getSelectedItem();
        String courseItem = (String) courseCombo.getSelectedItem();
        if (studentItem == null || courseItem == null) return;

        String studentId = studentItem.split(" - ")[0];
        String courseCode = courseItem.split(" - ")[0];

        File file = new File(DATA_DIR, "recovery_" + studentId + "_" + courseCode + ".txt");
        if (file.exists()) {
            file.delete();
        }
    }

    // =====================================================================
    // 🔧 Student record class
    // =====================================================================
    private static class StudentRecord {
        String id;
        String name;
        double cgpa;
        List<String> failedModules;   // list of module codes (e.g. ["CS201","CS205"])
        String email;
        int failedCourses;
        boolean eligible;

        StudentRecord(String id, String name, double cgpa, List<String> failedModules, String email) {
            this.id = id;
            this.name = name;
            this.cgpa = cgpa;
            this.failedModules = failedModules;
            this.email = email;
            this.failedCourses = failedModules.size();
            // Simple eligibility rule – adjust if needed
            this.eligible = (cgpa >= 2.0 && failedCourses <= 3);
        }
    }
}
