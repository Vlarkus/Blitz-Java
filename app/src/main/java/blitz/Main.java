package blitz;

import javax.swing.SwingUtilities;

import blitz.configs.Config;
import blitz.services.Utils;
import blitz.ui.application.MainFrame;

public class Main {
    
    public static void main(String[] args) {
        System.out.println(BlitzTerminalLogo()); // Print terminal logo
        setupMacOS(); // Setup macOS-specific properties
        SwingUtilities.invokeLater(() -> new MainFrame()); // Launch UI on the EDT
    }

    private static void setupMacOS() {
        if (Utils.isMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "Blitz");
        }
    }

    private static String BlitzTerminalLogo(){
        String blitzLogo = new String();
        blitzLogo += "\n";
        blitzLogo += "\n";
        blitzLogo += "\n";
        blitzLogo += Config.BLITZ_TERMINAL_ICON;
        blitzLogo += "\n";
        blitzLogo += "\n";
        blitzLogo += "\n";
        return blitzLogo;
    }

}
