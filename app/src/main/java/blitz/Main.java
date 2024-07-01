package blitz;

import javax.swing.SwingUtilities;

import blitz.ui.main.MainFrame;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }

}
