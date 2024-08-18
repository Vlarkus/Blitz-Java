package blitz.ui.main.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;

public class MouseInfoPanel extends JPanel {

    private CanvasPanel canvasPanel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JButton plusButton;
    private JButton minusButton;

    private static final int LABEL_WIDTH = 50;  // fixed width for labels
    private static final int LABEL_HEIGHT = 20; // fixed height for labels

    public MouseInfoPanel(CanvasPanel p) {
        canvasPanel = p;
        setBackground(MainFrameConfig.MOUSE_INFO_PANEL_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(MainFrameConfig.CANVAS_PANEL_PREFERRED_DIMENSION.width, 30));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setLayout(new BorderLayout());

        // Create labels
        xLabel = createFixedSizeLabel("x: 123");
        yLabel = createFixedSizeLabel("y: 456");

        // Create a panel for the labels and add them
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

        // Create buttons
        plusButton = new JButton("+");
        minusButton = new JButton("-");

        // Add action listeners to buttons
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasPanel.zoomInCenter();
            }
        });

        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasPanel.zoomOutCenter();
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(plusButton);
        buttonPanel.add(minusButton);

        add(buttonPanel, BorderLayout.EAST);
    }

    private JLabel createFixedSizeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        label.setMaximumSize(label.getPreferredSize());
        label.setMinimumSize(label.getPreferredSize());
        return label;
    }

    public void setXValue(int x) {
        xLabel.setText(formatLabel("x: ", x));
    }

    public void setYValue(int y) {
        yLabel.setText(formatLabel("y: ", y));
    }

    private String formatLabel(String prefix, int value) {
        String text = prefix + value;
        if (text.length() > 5) {
            return text.substring(0, 5) + "...";
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
