package blitz.ui.main.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.TrajectoriesList;
import blitz.models.Trajectory;
import blitz.models.TrajectoriesListListener;
import blitz.ui.main.selectionLayers.TrajectoryLayer;

public class SelectionPanel extends JPanel implements ActiveListener, TrajectoriesListListener{

    private JPanel headerPanel, selectionMenuPanel, optionsBarPanel;
    private JScrollPane selectionMenuScrollPane;
    GridBagConstraints gbc;
    
    public SelectionPanel(){

        setBackground(MainFrameConfig.SELECTION_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.SELECTION_PANEL_PREFERRED_DIMENSIONS);

        setLayout(new BorderLayout());

        constructHeaderPanel();
        constructSelectionMenuPanel();
        constructOptionsBar();

        Active.addActiveListener(this);
        TrajectoriesList.addTrajecoriesListListener(this);

    }

    private void constructHeaderPanel() {
        headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setPreferredSize(MainFrameConfig.HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setMinimumSize(MainFrameConfig.HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setMaximumSize(MainFrameConfig.HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setBackground(MainFrameConfig.HEADER_PANEL_COLOR);
    
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
      

    private void constructSelectionMenuPanel() {
        selectionMenuPanel = new JPanel();
        selectionMenuPanel.setLayout(new BoxLayout(selectionMenuPanel, BoxLayout.Y_AXIS));

        selectionMenuPanel.setBackground(MainFrameConfig.SELECTION_MENU_COLOR);
    
        selectionMenuScrollPane = new JScrollPane(selectionMenuPanel);
        selectionMenuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        selectionMenuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        renderSelectionMenuPanel();
    
        add(selectionMenuScrollPane, BorderLayout.CENTER);
    }
    
    private void renderSelectionMenuPanel() {
        selectionMenuPanel.removeAll();
    
        ArrayList<Trajectory> trajectories = TrajectoriesList.getTrajectoriesList();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Adjust as needed for your spacing
    
        for (int i = 0; i < trajectories.size(); i++) {
            Trajectory tr = trajectories.get(i);
            TrajectoryLayer layer = new TrajectoryLayer(tr);
    
            // Add the layer
            gbc.gridy++;
            selectionMenuPanel.add(layer, gbc);
    
            // Add filler between layers (except after the last one)
            if (i < trajectories.size() - 1) {
                gbc.gridy++;
                JPanel fillerPanel = new JPanel();
                fillerPanel.setBackground(Color.BLACK);
                fillerPanel.setPreferredSize(MainFrameConfig.FILLER_BETWEEN_TRAJECTORY_LAYERS);
                fillerPanel.setMaximumSize(MainFrameConfig.FILLER_BETWEEN_TRAJECTORY_LAYERS);
                fillerPanel.setMinimumSize(MainFrameConfig.FILLER_BETWEEN_TRAJECTORY_LAYERS);
                selectionMenuPanel.add(fillerPanel, gbc); // Adjust the height as needed
            }
        }
    
        // Add a last filler panel to take up remaining vertical space
        gbc.gridy++;
        gbc.weighty = 1.0; // Allows the filler panel to take up remaining space vertically
        JPanel fillerPanel = new JPanel();
        fillerPanel.setBackground(Color.BLACK); // Adjust as needed
        selectionMenuPanel.add(fillerPanel, gbc);
    
        selectionMenuPanel.revalidate();
        selectionMenuPanel.repaint();
    }
    
    


    private void constructOptionsBar() {
        optionsBarPanel = new JPanel();
        optionsBarPanel.setLayout(new BoxLayout(optionsBarPanel, BoxLayout.X_AXIS));
        optionsBarPanel.setPreferredSize(MainFrameConfig.OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setMinimumSize(MainFrameConfig.OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setMaximumSize(MainFrameConfig.OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setBackground(MainFrameConfig.OPTIONS_BAR_COLOR);
        optionsBarPanel.setAlignmentY(CENTER_ALIGNMENT);
        optionsBarPanel.add(Box.createHorizontalGlue());
    

        // Add Trajectory Button
        JButton addTrajectoryButton = new JButton(new ImageIcon(MainFrameConfig.PATH_TO_ADD_TRAJECTORY_OPTION_ICON));
        configureOptionButton(addTrajectoryButton);
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
        optionsBarPanel.add(Box.createRigidArea(MainFrameConfig.OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS));

        // Delete Button
        JButton deleteButton = new JButton(new ImageIcon(MainFrameConfig.PATH_TO_DELETE_OPTION_ICON));
        configureOptionButton(deleteButton);
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
        optionsBarPanel.add(Box.createRigidArea(MainFrameConfig.OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS));
    
        add(optionsBarPanel, BorderLayout.SOUTH);
    }

    private void configureOptionButton(JButton b){
        b.setPreferredSize(MainFrameConfig.OPTIONS_BAR_OPTION_BUTTON_PREFERRED_DIMENSIONS);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setMaximumSize(b.getPreferredSize());
    }

    @Override
    public void activeTrajectoryChanged(Trajectory tr) {
        renderSelectionMenuPanel();
    }

    @Override
    public void activeControlPointChanged(ControlPoint cp) {
        renderSelectionMenuPanel();
    }

    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
    }

    @Override
    public void TrajectoryListChanged() {
        renderSelectionMenuPanel();
    }

}
