package blitz.ui.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Example extends JFrame {
    public Example() {
        super("Resizable Panels Example");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Frame configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);  // Initial size
        setMinimumSize(new Dimension(600, 400));  // Minimum size for the frame
        setLocationRelativeTo(null);  // Center on screen

        JPanel toolPanel = new JPanel();
        mainPanel.add(toolPanel, BorderLayout.WEST);
        toolPanel.setBackground(Color.GREEN);
        toolPanel.setPreferredSize(new Dimension(50, 100));

        JPanel canvasPanel = new JPanel();
        mainPanel.add(canvasPanel, BorderLayout.CENTER);
        canvasPanel.setBackground(Color.RED);

        JPanel sidePanel = new JPanel();
        mainPanel.add(sidePanel, BorderLayout.EAST);
        sidePanel.setPreferredSize(new Dimension(150, 100));

        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        
        JPanel infoPanel = new JPanel();
        sidePanel.add(infoPanel);
        infoPanel.setBackground(Color.BLUE);
        infoPanel.setPreferredSize(new Dimension(150, 200));
        infoPanel.setMaximumSize(infoPanel.getPreferredSize()); // Lock maximum size to preferred size
        infoPanel.setMinimumSize(infoPanel.getPreferredSize()); // Lock minimum size to preferred size


        JPanel selectionPanel = new JPanel();
        sidePanel.add(selectionPanel);
        selectionPanel.setBackground(Color.GRAY);

        // Add main panel to the frame
        getContentPane().add(mainPanel);
        setVisible(true);

    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Example();
        });
    }
}
