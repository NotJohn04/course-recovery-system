package edu.apu.crs;

import javax.swing.SwingUtilities;
import edu.apu.crs.UserManager.gui.LoginPage;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage();
        });
    }
}
