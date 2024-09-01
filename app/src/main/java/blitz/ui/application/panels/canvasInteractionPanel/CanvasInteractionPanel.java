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

public class CanvasInteractionPanel extends JPanel {

    private CanvasPanel canvasPanel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JButton plusButton;
    private JButton minusButton;

    private static final int LABEL_WIDTH = 80;  // fixed width for labels
    private static final int LABEL_HEIGHT = 20; // fixed height for labels

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

    private JLabel createFixedSizeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        label.setMaximumSize(label.getPreferredSize());
        label.setMinimumSize(label.getPreferredSize());
        return label;
    }

    public void setXValue(double x) {
        xLabel.setText(formatLabel("x: ", x));
    }

    public void setYValue(double y) {
        yLabel.setText(formatLabel("y: ", y));
    }

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
    

    public void hideLabels() {
        xLabel.setVisible(false);
        yLabel.setVisible(false);
    }

    public void showLabels() {
        xLabel.setVisible(true);
        yLabel.setVisible(true);
    }
}
