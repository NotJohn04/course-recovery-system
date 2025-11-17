package edu.apu.crs;



import edu.apu.crs.usermanagement.GUI.Login;
import edu.apu.crs.usermanagement.GUI.RestPasswordForm;



public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Login login = new Login();   // build the login form
            login.show();                // display it
        });

    }
}
    



