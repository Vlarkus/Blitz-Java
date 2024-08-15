package blitz;

import javax.swing.SwingUtilities;

import blitz.ui.main.MainFrame;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("\n\n\nApplication Started\n");
        setupMacOS();
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }

    private static void setupMacOS() {
        if (isMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
    }

    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

}
