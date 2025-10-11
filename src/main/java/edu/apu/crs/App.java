package edu.apu.crs;

import edu.apu.crs.usermanagement.LoginPage;  // 👈 add this


public class App {
    public static void main(String[] args) {
        new LoginPage().setVisible(true);  // start GUI here
    }
}

