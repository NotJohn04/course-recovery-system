package edu.apu.crs;



import edu.apu.crs.usermanagement.GUI.Login;
import edu.apu.crs.usermanagement.GUI.RestPasswordForm;
import edu.apu.crs.usermanagement.GUI.UserAccountForm;



public class App {
    public static void main(String[] args) {
       javax.swing.SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.show();
        });

    }
}
    



