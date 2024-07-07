package blitz.ui.main.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import blitz.configs.MainFrameConfig;

public class SelectionPanel extends JPanel{

    private JPanel headerPanel, selectionMenuPanel, optionsBarPanel;
    private JScrollPane selectionMenuScrollPane;
    
    public SelectionPanel(){

        setBackground(MainFrameConfig.SELECTION_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.SELECTION_PANEL_PREFFERED_DIMENSIONS);

        setLayout(new BorderLayout());

        constructHeaderPanel();
        constructSelectionMenuPanel();
        constructOptionsBar();

    }

    private void constructHeaderPanel() {
        headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setPreferredSize(MainFrameConfig.SELECTION_PANEL_HEADER_PANEL_PREFFERED_DIMENSIONS);
        headerPanel.setMinimumSize(MainFrameConfig.SELECTION_PANEL_HEADER_PANEL_PREFFERED_DIMENSIONS);
        headerPanel.setMaximumSize(MainFrameConfig.SELECTION_PANEL_HEADER_PANEL_PREFFERED_DIMENSIONS);
        headerPanel.setBackground(MainFrameConfig.SELECTION_PANEL_HEADER_PANEL_COLOR);
    
        JLabel selectionLabel = new JLabel("Selection Panel", SwingConstants.CENTER);
        selectionLabel.setFont(MainFrameConfig.SELECTION_PANEL_TITLE_LABEL_FONT);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.weightx = 1.0; // Expand horizontally
        gbc.weighty = 1.0; // Expand vertically
        gbc.insets = new Insets(10, 1, 5, 1);
        headerPanel.add(selectionLabel, gbc);
    
        add(headerPanel, BorderLayout.NORTH);
    }
      

    private void constructSelectionMenuPanel(){
        selectionMenuPanel = new JPanel();
        selectionMenuPanel.setLayout(new FlowLayout(FlowLayout.LEADING, HEIGHT, WIDTH));
        selectionMenuPanel.setBackground(MainFrameConfig.SELECTION_PANEL_SELECTION_MENU_COLOR);

        selectionMenuScrollPane = new JScrollPane(selectionMenuPanel);
        selectionMenuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        selectionMenuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(selectionMenuScrollPane, BorderLayout.CENTER);

    }

    private void constructOptionsBar(){
        optionsBarPanel = new JPanel();
        optionsBarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, HEIGHT, WIDTH));
        optionsBarPanel.setPreferredSize(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_PREFFERED_DIMENSIONS);
        optionsBarPanel.setMinimumSize(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_PREFFERED_DIMENSIONS);
        optionsBarPanel.setMaximumSize(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_PREFFERED_DIMENSIONS);
        optionsBarPanel.setBackground(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_COLOR);
        optionsBarPanel.setAlignmentY(CENTER_ALIGNMENT);

        add(optionsBarPanel, BorderLayout.SOUTH);
    }

    private void fillSelectionMenuPanel(){
        // TODO: fill the panel with TrajectoryLayer objects.
    }

}
