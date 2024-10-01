package blitz.ui.application.panels.infoPanel.linePanels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import blitz.configs.Config;
import blitz.models.active.ActiveEntities;
import blitz.models.active.ActiveEntitiesListener;
import blitz.models.calculations.Calculations;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Represents a panel for selecting and displaying the spline type of a trajectory.
 * 
 * This panel provides a user interface component that allows users to select the spline
 * type for the active trajectory from a drop-down menu. It ensures that selections are
 * validated and applied correctly, and updates the display based on the panel's interactability.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     SplineTypeLine splineTypeLine = new SplineTypeLine();
 *     infoPanel.add(splineTypeLine);
 * </pre>
 * </p>
 * 
 * @autor Valery
 */
public class SplineTypeLine extends AbstractLinePanel implements ActiveEntitiesListener {
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Combo box for selecting the spline type of the active trajectory.
     */
    private JComboBox<String> splineTypeComboBox;
    
    /**
     * Array of all available spline types retrieved from {@link Calculations}.
     */
    private final String[] CURVE_TYPES = Calculations.ALL_SPLINE_TYPES;
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code SplineTypeLine} panel with configured components and listeners.
     * 
     * Initializes the layout, adds labels and combo boxes, and sets up interactability based on the active trajectory.
     * Registers this panel as a listener to active entity changes.
     */
    public SplineTypeLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel splineTypeLabel = new JLabel("Spline:");

        // Initialize the drop-down menu
        splineTypeComboBox = new JComboBox<>(CURVE_TYPES);
        Dimension dim = Config.LINE_PANEL_COMBO_BOX_DIMENSITONS;
        splineTypeComboBox.setPreferredSize(dim);
        splineTypeComboBox.setMaximumSize(dim);
        splineTypeComboBox.setMinimumSize(dim);
        splineTypeComboBox.addActionListener(e -> {
            if (isInteractable()) {
                String selectedSplineType = (String) splineTypeComboBox.getSelectedItem();
                if (selectedSplineType != null) {
                    Trajectory tr = ActiveEntities.getActiveTrajectory();
                    tr.setSplineType(selectedSplineType);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
                ActiveEntities.notifyActiveTrajectoryStateEdited();
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        Component horizontalStrut = Box.createHorizontalStrut(82);
        add(horizontalStrut, gbc);

        gbc.gridx++;
        add(splineTypeLabel, gbc);

        gbc.gridx++;
        add(splineTypeComboBox, gbc);

        displayInteractability();

        ActiveEntities.addActiveListener(this);
    }
    
    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Updates the combo box selection based on the active trajectory's spline type.
     * 
     * Retrieves the current spline type from the active trajectory and sets it as the selected item
     * in the combo box. This ensures that the combo box reflects the current state of the trajectory.
     */
    private void updateComboBox() {
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if (tr != null) {
            splineTypeComboBox.setSelectedItem(tr.getSplineType());
        }
    }

    /**
     * Determines whether the panel is interactable based on the presence of an active trajectory.
     * 
     * The panel is interactable if there is an active trajectory selected. If no trajectory is active,
     * the panel is non-interactable.
     * 
     * @return {@code true} if there is an active trajectory, {@code false} otherwise
     */
    @Override
    public boolean isInteractable() {
        return ActiveEntities.getActiveTrajectory() != null;
    }

    /**
     * Updates the panel's interactability state, enabling or disabling components accordingly.
     * 
     * Changes the background color based on interactability and enables or disables the spline type combo box.
     */
    @Override
    protected void displayInteractability() {
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        splineTypeComboBox.setEnabled(isInteractable);
    }

    /**
     * Handles changes to the active trajectory.
     * 
     * <strong>Note:</strong> Currently not implemented. Can be expanded if needed.
     * 
     * @param tr the updated {@link Trajectory}
     */
    @Override
    public void activeTrajectoryChanged(Trajectory tr) {
        // Implementation can be added if needed
    }

    /**
     * Handles changes to the active control point.
     * 
     * Updates the panel's interactability and refreshes the combo box selection.
     * 
     * @param cp the updated {@link ControlPoint}
     */
    @Override
    public void activeControlPointChanged(ControlPoint cp) {
        displayInteractability();
        updateComboBox();
    }

    /**
     * Handles edits to the state of the active control point.
     * 
     * Updates the combo box selection to reflect any changes.
     * 
     * @param cp the {@link ControlPoint} whose state was edited
     */
    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
        updateComboBox();
    }

    /**
     * Handles changes to the state of the active trajectory.
     * 
     * <strong>Note:</strong> Currently not implemented. Can be expanded if needed.
     * 
     * @param tr the updated {@link Trajectory}
     */
    @Override
    public void activeTrajectoryStateEdited(Trajectory tr) {
        // Implementation can be added if needed
    }
}
