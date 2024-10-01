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
 * Represents a panel for selecting and displaying the symmetry type of a control point.
 * 
 * This panel provides a user interface component that allows users to select the symmetry
 * type for the active control point from a drop-down menu. It ensures that selections are
 * validated and applied correctly, and updates the display based on the panel's interactability.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     SymmetryLine symmetryLine = new SymmetryLine();
 *     infoPanel.add(symmetryLine);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class SymmetryLine extends AbstractLinePanel implements ActiveEntitiesListener {
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Combo box for selecting the symmetry type of the active control point.
     */
    private JComboBox<String> symmetryComboBox;
    
    /**
     * Array of all available symmetry types retrieved from {@link ControlPoint}.
     */
    private final String[] SYMMETRY_TYPES = ControlPoint.ALL_INTERPOLATION_TYPES;
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code SymmetryLine} panel with configured components and listeners.
     * 
     * Initializes the layout, adds labels and combo boxes, and sets up interactability based on the active control point.
     * Registers this panel as a listener to active entity changes.
     */
    public SymmetryLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel symmetryLabel = new JLabel("Symmetry:");

        // Initialize the drop-down menu
        symmetryComboBox = new JComboBox<>(SYMMETRY_TYPES);
        Dimension dim = Config.LINE_PANEL_COMBO_BOX_DIMENSITONS;
        symmetryComboBox.setPreferredSize(dim);
        symmetryComboBox.setMaximumSize(dim);
        symmetryComboBox.setMinimumSize(dim);
        symmetryComboBox.addActionListener(e -> {
            if (isInteractable()) {
                String selectedSymmetry = (String) symmetryComboBox.getSelectedItem();
                if (selectedSymmetry != null) {
                    ControlPoint cp = ActiveEntities.getActiveControlPoint();
                    cp.setSymmetryType(selectedSymmetry);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        Component horizontalStrut = Box.createHorizontalStrut(58);
        add(horizontalStrut, gbc);

        gbc.gridx++;
        add(symmetryLabel, gbc);

        gbc.gridx++;
        add(symmetryComboBox, gbc);

        displayInteractability();

        ActiveEntities.addActiveListener(this);
    }
    
    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Updates the combo box selection based on the active control point's symmetry type.
     * 
     * Retrieves the current symmetry type from the active control point and sets it as the selected item
     * in the combo box. This ensures that the combo box reflects the current state of the control point.
     */
    private void updateComboBox() {
        ControlPoint cp = ActiveEntities.getActiveControlPoint();
        if (cp == null) return;
        ControlPoint.SYMMETRY symmetry = cp.getSymmetryType();
        switch (symmetry) {
            case BROKEN:
                symmetryComboBox.setSelectedItem(ControlPoint.BROKEN_SYMMETRY_KEY);
                break;
            case ALIGNED:
                symmetryComboBox.setSelectedItem(ControlPoint.ALIGNED_SYMMETRY_KEY);
                break;
            case MIRRORED:
                symmetryComboBox.setSelectedItem(ControlPoint.MIRRORED_SYMMETRY_KEY);
                break;
        }
    }

    /**
     * Determines whether the panel is interactable based on the active control point and trajectory.
     * 
     * The panel is interactable if:
     * <ul>
     *   <li>An active control point exists.</li>
     *   <li>The trajectory's spline type is not linear.</li>
     *   <li>The control point is neither the first nor the last point in its trajectory.</li>
     * </ul>
     * 
     * @return {@code true} if the panel is interactable, {@code false} otherwise
     */
    @Override
    public boolean isInteractable() {
        ControlPoint cp = ActiveEntities.getActiveControlPoint();
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if (cp == null)          return false;
        if (tr.getSplineType().equals(Calculations.LINEAR_SPLINE))   return false;
        if (cp == tr.getFirst()) return false;
        if (cp == tr.getLast())  return false;
        return true;
    }

    /**
     * Updates the panel's interactability state, enabling or disabling components accordingly.
     * 
     * Changes the background color based on interactability and enables or disables the symmetry type combo box.
     */
    @Override
    protected void displayInteractability() {
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        symmetryComboBox.setEnabled(isInteractable);
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
        displayInteractability();
        updateComboBox();
    }
}
