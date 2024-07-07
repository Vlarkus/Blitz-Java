package blitz.ui.main.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ControlPoint;
import blitz.models.TrajectoriesList;
import blitz.models.Trajectory;

public class SelectionPanel extends JPanel{

    private JPanel headerPanel, selectionMenuPanel, optionsBarPanel;
    private JScrollPane selectionMenuScrollPane;
    
    public SelectionPanel(){

        setBackground(MainFrameConfig.SELECTION_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.SELECTION_PANEL_PREFERRED_DIMENSIONS);

        setLayout(new BorderLayout());

        constructHeaderPanel();
        constructSelectionMenuPanel();
        constructOptionsBar();

    }

    private void constructHeaderPanel() {
        headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setPreferredSize(MainFrameConfig.SELECTION_PANEL_HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setMinimumSize(MainFrameConfig.SELECTION_PANEL_HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setMaximumSize(MainFrameConfig.SELECTION_PANEL_HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setBackground(MainFrameConfig.SELECTION_PANEL_HEADER_PANEL_COLOR);
    
        JLabel selectionLabel = new JLabel("Selection Menu", SwingConstants.CENTER);
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

        fillSelectionMenuPanel();

        add(selectionMenuScrollPane, BorderLayout.CENTER);

    }

    private void fillSelectionMenuPanel(){
        // TODO: fill the panel with TrajectoryLayer objects.
    }

    private void constructOptionsBar() {
        optionsBarPanel = new JPanel();
        optionsBarPanel.setLayout(new BoxLayout(optionsBarPanel, BoxLayout.X_AXIS));
        optionsBarPanel.setPreferredSize(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setMinimumSize(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setMaximumSize(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setBackground(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_COLOR);
        optionsBarPanel.setAlignmentY(CENTER_ALIGNMENT);
        optionsBarPanel.add(Box.createHorizontalGlue());
    

        // Add Trajectory Button
        JButton addTrajectoryButton = new JButton(new ImageIcon(MainFrameConfig.PATH_TO_ADD_TRAJECTORY_OPTION_ICON));
        addTrajectoryButton.setPreferredSize(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_OPTION_BUTTON_PREFERRED_DIMENSIONS);
        addTrajectoryButton.setContentAreaFilled(false);
        addTrajectoryButton.setBorderPainted(false);
        addTrajectoryButton.setOpaque(false);
        addTrajectoryButton.setMaximumSize(addTrajectoryButton.getPreferredSize());
        addTrajectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Trajectory tr = new Trajectory(TrajectoriesList.getNextAvaliableName());
                TrajectoriesList.addTrajectory(tr);
                Active.setActiveTrajectory(tr);

                Component window = SwingUtilities.getWindowAncestor(addTrajectoryButton);
                if (window != null) {
                    window.requestFocusInWindow();
                }
            
            }
        });
        optionsBarPanel.add(addTrajectoryButton);
        optionsBarPanel.add(Box.createRigidArea(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS));

        // Delete Button
        JButton deleteButton = new JButton(new ImageIcon(MainFrameConfig.PATH_TO_DELETE_OPTION_ICON));
        deleteButton.setPreferredSize(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_OPTION_BUTTON_PREFERRED_DIMENSIONS);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setOpaque(false);
        deleteButton.setMaximumSize(deleteButton.getPreferredSize());
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ControlPoint cp = Active.getActiveControlPoint();
                Trajectory tr = Active.getActiveTrajectory();
                if(cp != null){
                    tr.removeControlPoint(cp);
                    Active.setActiveControlPoint(null);
                } else if(tr != null) {
                    TrajectoriesList.removeTrajectory(tr);
                    Active.setActiveTrajectory(null);
                }

                Component window = SwingUtilities.getWindowAncestor(deleteButton);
                if (window != null) {
                    window.requestFocusInWindow();
                }
            
            }
        });
        optionsBarPanel.add(deleteButton);
        optionsBarPanel.add(Box.createRigidArea(MainFrameConfig.SELECTION_PANEL_OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS));
    
        add(optionsBarPanel, BorderLayout.SOUTH);
    }
    

}
