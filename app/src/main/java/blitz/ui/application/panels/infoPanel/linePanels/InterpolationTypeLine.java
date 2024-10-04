/*
 * Copyright 2024 Valery Rabchanka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * Represents a panel for selecting and displaying the interpolation type of a trajectory.
 * 
 * This panel provides a user interface component that allows users to select the interpolation
 * method (spline type) for the active trajectory. It includes a drop-down menu populated with
 * available interpolation types and ensures that selections are validated and applied correctly.
 * The panel updates its state based on the interactability determined by the active trajectory.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     InterpolationTypeLine interpolationLine = new InterpolationTypeLine();
 *     infoPanel.add(interpolationLine);
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class InterpolationTypeLine extends AbstractLinePanel implements ActiveEntitiesListener {
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Combo box for selecting the interpolation type of the active trajectory.
     */
    private JComboBox<String> interpolationTypeComboBox;
    
    /**
     * Array of all available interpolation types retrieved from {@link Calculations}.
     */
    private final String[] CURVE_TYPES = Calculations.ALL_INTERPOLATION_TYPES;
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs an {@code InterpolationTypeLine} panel with configured components and listeners.
     * 
     * Initializes the layout, adds labels and combo boxes, and sets up interactability based on the active trajectory.
     * Registers this panel as a listener to active entity changes.
     */
    public InterpolationTypeLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel splineTypeLabel = new JLabel("Interpolation:");

        // Initialize the drop-down menu
        interpolationTypeComboBox = new JComboBox<>(CURVE_TYPES);
        Dimension dim = Config.LINE_PANEL_COMBO_BOX_DIMENSITONS;
        interpolationTypeComboBox.setPreferredSize(dim);
        interpolationTypeComboBox.setMaximumSize(dim);
        interpolationTypeComboBox.setMinimumSize(dim);
        interpolationTypeComboBox.addActionListener(e -> {
            if (isInteractable()) {
                String selectedSplineType = (String) interpolationTypeComboBox.getSelectedItem();
                if (selectedSplineType != null) {
                    Trajectory tr = ActiveEntities.getActiveTrajectory();
                    tr.setInterpolationType(selectedSplineType);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
                ActiveEntities.notifyActiveTrajectoryStateEdited();
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        Component horizontalStrut = Box.createHorizontalStrut(36);
        add(horizontalStrut, gbc);

        gbc.gridx++;
        add(splineTypeLabel, gbc);

        gbc.gridx++;
        add(interpolationTypeComboBox, gbc);

        displayInteractability();

        ActiveEntities.addActiveListener(this);
    }
    
    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Updates the combo box selection based on the active trajectory's interpolation type.
     * 
     * Retrieves the current interpolation type from the active trajectory and sets it as the selected item
     * in the combo box. This ensures that the combo box reflects the current state of the trajectory.
     */
    private void updateComboBox() {
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if (tr != null) {
            interpolationTypeComboBox.setSelectedItem(tr.getInterpolationType());
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
     * Changes the background color based on interactability and enables or disables the interpolation
     * type combo box.
     */
    @Override
    protected void displayInteractability() {
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        interpolationTypeComboBox.setEnabled(isInteractable);
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
     * Updates the panel's interactability and refreshes the combo box selection.
     * 
     * @param cp the {@link ControlPoint} whose state was edited
     */
    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
        displayInteractability();
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
