package blitz;

import javax.swing.SwingUtilities;

import blitz.configs.Config;
import blitz.ui.application.MainFrame;

public class Main {
    
    public static void main(String[] args) {
        System.out.println(BlitzTerminalLogo()); // Print terminal logo
        setupMacOS(); // Setup macOS-specific properties
        SwingUtilities.invokeLater(() -> new MainFrame()); // Launch UI on the EDT
    }

    private static void setupMacOS() {
        if (isMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "Blitz");
        }
    }

    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    private static String BlitzTerminalLogo() {
        return "\n\n\n" + Config.BLITZ_TERMINAL_ICON + "\n\n\n";
    }
}
