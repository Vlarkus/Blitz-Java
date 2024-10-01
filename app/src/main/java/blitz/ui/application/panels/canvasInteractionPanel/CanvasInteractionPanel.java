package blitz.ui.application.panels.canvasInteractionPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import blitz.configs.Config;
import blitz.ui.application.panels.canvasPanel.CanvasPanel;

/**
 * Represents the interaction panel for the canvas, displaying mouse coordinates and providing zoom controls.
 * 
 * This panel displays the current x and y coordinates of the mouse on the canvas and includes buttons
 * to zoom in and out centered on the canvas. It ensures a consistent layout and fixed label sizes
 * for a uniform user interface.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     CanvasPanel canvas = new CanvasPanel();
 *     CanvasInteractionPanel interactionPanel = new CanvasInteractionPanel(canvas);
 *     someFrame.add(interactionPanel, BorderLayout.SOUTH);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class CanvasInteractionPanel extends JPanel {

    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * The associated {@link CanvasPanel} that this interaction panel controls.
     */
    private CanvasPanel canvasPanel;
    
    /**
     * Label to display the x-coordinate of the mouse position.
     */
    private JLabel xLabel;
    
    /**
     * Label to display the y-coordinate of the mouse position.
     */
    private JLabel yLabel;
    
    /**
     * Button to zoom in on the canvas.
     */
    private JButton plusButton;
    
    /**
     * Button to zoom out on the canvas.
     */
    private JButton minusButton;

    /**
     * Fixed width for coordinate labels.
     */
    private static final int LABEL_WIDTH = 80;  // fixed width for labels

    /**
     * Fixed height for coordinate labels.
     */
    private static final int LABEL_HEIGHT = 20; // fixed height for labels

    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code CanvasInteractionPanel} associated with the given {@link CanvasPanel}.
     * 
     * @param p the {@link CanvasPanel} to associate with this interaction panel
     */
    public CanvasInteractionPanel(CanvasPanel p) {
        canvasPanel = p;
        setBackground(Config.MOUSE_INFO_PANEL_BACKGROUND_COLOR);
        setPreferredSize(Config.MOUSE_INFO_PANEL_PREFERRED_DIMENSIONS);
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setLayout(new BorderLayout());

        // Mouse Position

        xLabel = createFixedSizeLabel("");
        yLabel = createFixedSizeLabel("");

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        labelPanel.add(xLabel, gbc);
        gbc.gridx = 1;
        labelPanel.add(yLabel, gbc);

        add(labelPanel, BorderLayout.WEST);

        // View Selection

        JPanel viewSelectionPanel = new JPanel();
        

        add(viewSelectionPanel, BorderLayout.CENTER);

        // Zoom Panel
        gbc.insets = new Insets(0, 0, 0, 0);
        int size = Config.MOUSE_INFO_PANEL_PREFERRED_DIMENSIONS.height;
        
        plusButton = new JButton("+");
        plusButton.setPreferredSize(new Dimension(size, size));        

        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasPanel.zoomInCenter();
            }
        });
        
        minusButton = new JButton("-");
        minusButton.setPreferredSize(new Dimension(size, size));        

        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasPanel.zoomOutCenter();
            }
        });

        JPanel zoomPanel = new JPanel();
        zoomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        zoomPanel.add(plusButton);
        zoomPanel.add(minusButton);

        add(zoomPanel, BorderLayout.EAST);
    }

    // -=-=-=- METHODS -=-=-=-=-

    /**
     * Creates a {@link JLabel} with fixed dimensions.
     * 
     * @param text the initial text to display in the label
     * @return a {@link JLabel} with predefined width and height
     */
    private JLabel createFixedSizeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        label.setMaximumSize(label.getPreferredSize());
        label.setMinimumSize(label.getPreferredSize());
        return label;
    }

    /**
     * Sets the x-coordinate value displayed on the panel.
     * 
     * @param x the x-coordinate value to display
     */
    public void setXValue(double x) {
        xLabel.setText(formatLabel("x: ", x));
    }

    /**
     * Sets the y-coordinate value displayed on the panel.
     * 
     * @param y the y-coordinate value to display
     */
    public void setYValue(double y) {
        yLabel.setText(formatLabel("y: ", y));
    }

    /**
     * Formats the label text with a prefix and ensures it does not exceed the maximum length.
     * 
     * @param prefix the prefix to add before the value (e.g., "x: ", "y: ")
     * @param value  the numerical value to display
     * @return the formatted label text, truncated with "..." if necessary
     */
    private String formatLabel(String prefix, double value) {
        // Format the value to show up to 3 decimal points
        String formattedValue = String.format("%.4f", value);
    
        // Concatenate the prefix and formatted value
        String text = prefix + formattedValue;
    
        // Check if the length exceeds 10 characters and truncate if necessary
        if (text.length() > 11) {
            return text.substring(0, 8) + "..."; // Adjust to ensure it shows up to 3 decimal points before truncating
        }
        
        return text;
    }

    /**
     * Hides the coordinate labels from the panel.
     */
    public void hideLabels() {
        xLabel.setVisible(false);
        yLabel.setVisible(false);
    }

    /**
     * Shows the coordinate labels on the panel.
     */
    public void showLabels() {
        xLabel.setVisible(true);
        yLabel.setVisible(true);
    }
}
