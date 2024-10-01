package blitz.ui.application.panels.selectionPanel;

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

import blitz.configs.Config;
import blitz.models.active.ActiveEntities;
import blitz.models.active.ActiveEntitiesListener;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoriesList.TrajectoriesList;
import blitz.models.trajectories.trajectoriesList.TrajectoriesListListener;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.models.trajectories.visibleTrajectories.VisibleTrajectories;
import blitz.ui.application.panels.selectionPanel.selectionLayers.TrajectoryLayer;

/**
 * Represents the selection panel that manages and displays trajectories and their control points.
 * 
 * This panel includes a selection menu where users can interact with different trajectories,
 * and an options bar that provides actions such as adding or deleting trajectories. It listens
 * to changes in active entities and updates the UI accordingly.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     SelectionPanel selectionPanel = new SelectionPanel();
 *     mainContainer.add(selectionPanel);
 * </pre>
 * </p>
 * 
 * @autor Valery
 */
public class SelectionPanel extends JPanel implements ActiveEntitiesListener, TrajectoriesListListener{

    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Panel for the header section (currently commented out).
     */
    private JPanel headerPanel;
    
    /**
     * Panel for the selection menu containing trajectory layers.
     */
    private JPanel selectionMenuPanel;
    
    /**
     * Panel for the options bar containing action buttons.
     */
    private JPanel optionsBarPanel;
    
    /**
     * Scroll pane for the selection menu to enable scrolling through trajectories.
     */
    private JScrollPane selectionMenuScrollPane;
    
    /**
     * Constraints used for layout management.
     */
    GridBagConstraints gbc;
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code SelectionPanel} with configured background, size, and layout.
     * 
     * Initializes the selection menu and options bar panels, and registers listeners for active entities
     * and trajectory list changes.
     */
    public SelectionPanel(){

        setBackground(Config.SELECTION_PANEL_BACKGROUND_COLOR);
        setPreferredSize(Config.SELECTION_PANEL_PREFERRED_DIMENSIONS);
        setLayout(new BorderLayout());

        // constructHeaderPanel(); // Header panel is currently not used
        constructSelectionMenuPanel();
        constructOptionsBar();

        // Register listeners to handle active entity and trajectory list changes
        ActiveEntities.addActiveListener(this);
        TrajectoriesList.addTrajecoriesListListener(this);

    }

    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Constructs the header panel.
     * 
     * This method sets up the header section of the selection panel, including the title label.
     * Currently commented out and not in use.
     */
    private void constructHeaderPanel() {
        headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setPreferredSize(Config.HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setMinimumSize(Config.HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setMaximumSize(Config.HEADER_PANEL_PREFERRED_DIMENSIONS);
        headerPanel.setBackground(Config.HEADER_PANEL_COLOR);
    
        JLabel selectionLabel = new JLabel("Selection Menu", SwingConstants.CENTER);
        selectionLabel.setFont(Config.SELECTION_PANEL_TITLE_LABEL_FONT);
    
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
      
    /**
     * Constructs the selection menu panel that holds all trajectory layers.
     * 
     * Initializes the selection menu panel with a vertical box layout and wraps it inside a scroll pane.
     * Calls {@link #renderSelectionMenuPanel()} to populate the panel with existing trajectories.
     */
    private void constructSelectionMenuPanel() {
        selectionMenuPanel = new JPanel();
        selectionMenuPanel.setLayout(new BoxLayout(selectionMenuPanel, BoxLayout.Y_AXIS));

        selectionMenuPanel.setBackground(Config.SELECTION_MENU_COLOR);
    
        selectionMenuScrollPane = new JScrollPane(selectionMenuPanel);
        selectionMenuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        selectionMenuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        renderSelectionMenuPanel();
    
        add(selectionMenuScrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Renders the selection menu panel by adding all existing trajectories.
     * 
     * This method retrieves the list of trajectories, preserves their collapsed states, and adds
     * each trajectory as a {@link TrajectoryLayer} to the selection menu panel. It also adds
     * spacers between trajectory layers for better visual separation.
     */
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
    
        // Clear existing components
        selectionMenuPanel.removeAll();
        selectionMenuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
    
        // Add TrajectoryLayers to the selection menu
        for (int i = 0; i < trajectories.size(); i++) {
            Trajectory tr = trajectories.get(i);
            TrajectoryLayer layer = new TrajectoryLayer(tr);
    
            // Restore the collapsed state if previously collapsed
            if (i < collapsedStates.size()) {
                if (collapsedStates.get(i)) {
                    layer.collapse(); // Collapse the layer if it was previously collapsed
                }
            }
    
            gbc.gridy = i * 2;
            selectionMenuPanel.add(layer, gbc);
    
            // Add spacer between trajectory layers except after the last one
            if (!layer.isEmpty() && i < trajectories.size() - 1) {
                gbc.gridy = i * 2 + 1;
                gbc.insets = new Insets(0, 0, Config.SPACING_BETWEEN_TRAJECTORY_LAYERS, 0);
                JPanel spacer = new JPanel();
                spacer.setOpaque(false);
                selectionMenuPanel.add(spacer, gbc);
            }
        }
    
        // Add glue to push components to the top
        gbc.gridy = trajectories.size() * 2;
        gbc.weighty = 1.0;
        JPanel gluePanel = new JPanel();
        gluePanel.setOpaque(false);
        selectionMenuPanel.add(gluePanel, gbc);
    
        // Refresh the selection menu panel
        selectionMenuPanel.revalidate();
        selectionMenuPanel.repaint();
    }
    
    /**
     * Constructs the options bar panel that contains action buttons.
     * 
     * Initializes the options bar with a horizontal box layout and adds buttons for adding
     * and deleting trajectories. Each button is configured with appropriate icons and action listeners.
     */
    private void constructOptionsBar() {
        optionsBarPanel = new JPanel();
        optionsBarPanel.setLayout(new BoxLayout(optionsBarPanel, BoxLayout.X_AXIS));
        optionsBarPanel.setPreferredSize(Config.OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setMinimumSize(Config.OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setMaximumSize(Config.OPTIONS_BAR_PREFERRED_DIMENSIONS);
        optionsBarPanel.setBackground(Config.OPTIONS_BAR_COLOR);
        optionsBarPanel.setAlignmentY(CENTER_ALIGNMENT);
        optionsBarPanel.add(Box.createHorizontalGlue());

        // Add Trajectory Button
        JButton addTrajectoryButton = new JButton(new ImageIcon(Config.PATH_TO_ADD_TRAJECTORY_OPTION_ICON));
        configureOptionButton(addTrajectoryButton);
        addTrajectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                createEmptyTrajectory();

            }
        });
        optionsBarPanel.add(addTrajectoryButton);
        optionsBarPanel.add(Box.createRigidArea(Config.OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS));

        // Delete Button
        JButton deleteButton = new JButton(new ImageIcon(Config.PATH_TO_DELETE_OPTION_ICON));
        configureOptionButton(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeActiveElement();
            }
        });
        optionsBarPanel.add(deleteButton);
        optionsBarPanel.add(Box.createRigidArea(Config.OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS));

        add(optionsBarPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates an empty trajectory, adds it to the trajectories list, and sets it as active.
     * 
     * This method generates a new {@link Trajectory} with a unique name, adds it to the
     * {@link TrajectoriesList}, sets it as the active trajectory, and notifies visible trajectories
     * of the change to update the UI.
     */
    public void createEmptyTrajectory(){
        Trajectory tr = new Trajectory(TrajectoriesList.getNextAvaliableName());
        TrajectoriesList.addTrajectory(tr);
        ActiveEntities.setActiveTrajectory(tr);
        VisibleTrajectories.notifyVisibleTrajectoriesChanged();
    }

    /**
     * Removes the currently active trajectory or control point from the system.
     * 
     * If a control point is active, it is removed from its trajectory. If a trajectory is active,
     * it is removed from the trajectories list. After removal, the active entity is cleared, and
     * visible trajectories are updated.
     */
    public void removeActiveElement(){
        ControlPoint cp = ActiveEntities.getActiveControlPoint();
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if(cp != null){
            tr.removeControlPoint(cp);
            ActiveEntities.setActiveControlPoint(null);
        } else if(tr != null) {
            TrajectoriesList.removeTrajectory(tr);
            ActiveEntities.setActiveTrajectory(null);
        }
        VisibleTrajectories.notifyVisibleTrajectoriesChanged();
    }

    /**
     * Configures an option button with specified dimensions and appearance settings.
     * 
     * @param b the {@link JButton} to configure
     */
    private void configureOptionButton(JButton b){
        b.setPreferredSize(Config.OPTIONS_BAR_OPTION_BUTTON_PREFERRED_DIMENSIONS);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setMaximumSize(b.getPreferredSize());
    }

    // -=-=-=- LISTENER METHODS -=-=-=-=-
    
    /**
     * Handles changes to the active trajectory.
     * 
     * Re-renders the selection menu panel to reflect the new active trajectory.
     * 
     * @param tr the updated {@link Trajectory}
     */
    @Override
    public void activeTrajectoryChanged(Trajectory tr) {
        renderSelectionMenuPanel();
    }

    /**
     * Handles changes to the active control point.
     * 
     * Re-renders the selection menu panel to reflect the new active control point.
     * 
     * @param cp the updated {@link ControlPoint}
     */
    @Override
    public void activeControlPointChanged(ControlPoint cp) {
        renderSelectionMenuPanel();
    }

    /**
     * Handles edits to the state of the active control point.
     * 
     * <strong>Note:</strong> Currently not implemented.
     * 
     * @param cp the {@link ControlPoint} whose state was edited
     */
    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
        // Implementation can be added if needed
    }

    /**
     * Handles changes to the trajectory list.
     * 
     * Re-renders the selection menu panel to reflect additions or removals in the trajectories list.
     */
    @Override
    public void TrajectoryListChanged() {
        renderSelectionMenuPanel();
    }

    /**
     * Handles edits to the state of the active trajectory.
     * 
     * <strong>Note:</strong> Currently not implemented.
     * 
     * @param tr the {@link Trajectory} whose state was edited
     */
    @Override
    public void activeTrajectoryStateEdited(Trajectory tr) {

    }
}
