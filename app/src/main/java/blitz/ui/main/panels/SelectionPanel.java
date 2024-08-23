package blitz.ui.main.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.TrajectoriesList;
import blitz.models.TrajectoriesListListener;
import blitz.models.Trajectory;
import blitz.models.VisibleTrajectories;
import blitz.ui.main.selectionLayers.TrajectoryLayer;

public class SelectionPanel extends JPanel implements ActiveListener, TrajectoriesListListener{

    private JPanel headerPanel, selectionMenuPanel, optionsBarPanel;
    private JScrollPane selectionMenuScrollPane;
    GridBagConstraints gbc;
    
    public SelectionPanel(){

        setBackground(MainFrameConfig.SELECTION_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.SELECTION_PANEL_PREFERRED_DIMENSIONS);
        setLayout(new BorderLayout());

        // constructHeaderPanel();
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
        // Store the collapsed states of existing TrajectoryLayers
        ArrayList<Trajectory> trajectories = TrajectoriesList.getTrajectoriesList();
        ArrayList<Boolean> collapsedStates = new ArrayList<>();
        for (Trajectory tr : trajectories) {
            for (int i = 0; i < selectionMenuPanel.getComponentCount(); i++) {
                if (selectionMenuPanel.getComponent(i) instanceof TrajectoryLayer) {
                    TrajectoryLayer layer = (TrajectoryLayer) selectionMenuPanel.getComponent(i);
                    if (layer.getRelatedTrajectory().equals(tr)) {
                        collapsedStates.add(layer.isCollapsed());
                        break;
                    }
                }
            }
        }
    
        selectionMenuPanel.removeAll();
        selectionMenuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
    
        for (int i = 0; i < trajectories.size(); i++) {
            Trajectory tr = trajectories.get(i);
            TrajectoryLayer layer = new TrajectoryLayer(tr);
    
            // Restore the collapsed state
            if (i < collapsedStates.size()) {
                if (collapsedStates.get(i)) {
                    layer.collapse(); // Assuming you have a collapse() method to collapse the layer
                }
            }
    
            gbc.gridy = i * 2;
            selectionMenuPanel.add(layer, gbc);
    
            if (!layer.isEmpty() && i < trajectories.size() - 1) {
                gbc.gridy = i * 2 + 1;
                gbc.insets = new Insets(0, 0, MainFrameConfig.SPACING_BETWEEN_TRAJECTORY_LAYERS, 0);
                JPanel spacer = new JPanel();
                spacer.setOpaque(false);
                selectionMenuPanel.add(spacer, gbc);
            }
        }
    
        gbc.gridy = trajectories.size() * 2;
        gbc.weighty = 1.0;
        JPanel gluePanel = new JPanel();
        gluePanel.setOpaque(false);
        selectionMenuPanel.add(gluePanel, gbc);
    
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

                createEmptyTrajectory();

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
                removeActiveElement();
            }
        });
        optionsBarPanel.add(deleteButton);
        optionsBarPanel.add(Box.createRigidArea(MainFrameConfig.OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS));
    
        add(optionsBarPanel, BorderLayout.SOUTH);
    }

    public void createEmptyTrajectory(){
        Trajectory tr = new Trajectory(TrajectoriesList.getNextAvaliableName());
        TrajectoriesList.addTrajectory(tr);
        Active.setActiveTrajectory(tr);
        VisibleTrajectories.notifyVisibleTrajectoriesChanged();
    }

    public void removeActiveElement(){
        ControlPoint cp = Active.getActiveControlPoint();
        Trajectory tr = Active.getActiveTrajectory();
        if(cp != null){
            tr.removeControlPoint(cp);
            Active.setActiveControlPoint(null);
        } else if(tr != null) {
            TrajectoriesList.removeTrajectory(tr);
            Active.setActiveTrajectory(null);
        }
        VisibleTrajectories.notifyVisibleTrajectoriesChanged();
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

    @Override
    public void activeTrajectoryStateEdited(ControlPoint cp) {

    }

}
