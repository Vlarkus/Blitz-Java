package blitz.ui.speedControl;

import javax.swing.JFrame;

/*
 * Only one instance of this frame can exist.
 * The frame stays on top of all other frames,
 * but allows for interactions with other frames.
 */
// Singleton pattern implementation for speedControlFrame
public class speedControlFrame extends JFrame {
    private static speedControlFrame instance;

    private speedControlFrame() {
        // Initialize the frame here
    }

    public static speedControlFrame getInstance() {
        if (instance == null) {
            instance = new speedControlFrame();
        }
        return instance;
    }
}
