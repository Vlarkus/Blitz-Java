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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import blitz.configs.Config;
import blitz.models.active.ActiveEntities;
import blitz.models.active.ActiveEntitiesListener;
import blitz.models.calculations.Calculations;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.services.DecimalFilter;

/**
 * Represents a panel for displaying and editing the number of segments of a control point.
 * 
 * This panel provides a user interface component that allows users to view and modify the number
 * of segments (Num Segments) associated with the active control point of a trajectory. It ensures
 * that inputs are validated and applied correctly, and updates the display based on the panel's
 * interactability.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     NumSegmentsLine numSegmentsLine = new NumSegmentsLine();
 *     infoPanel.add(numSegmentsLine);
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class NumSegmentsLine extends AbstractLinePanel implements ActiveEntitiesListener {
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Text field for displaying and editing the number of segments.
     */
    private JTextField numSegTextField;
    
    /**
     * Formatter for integer values, ensuring consistency in display.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0");
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code NumSegmentsLine} panel with configured components and listeners.
     * 
     * Initializes the layout, adds labels and text fields, and sets up interactability based on the active control point.
     * Registers this panel as a listener to active entity changes.
     */
    public NumSegmentsLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel distanceLabel = new JLabel("Num Segments:");

        numSegTextField = new JTextField(6);
        configureTextField(numSegTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(ActiveEntities.getActiveControlPoint().getNumSegments());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    int parsedValue = parseInt(value, ActiveEntities.getActiveControlPoint().getNumSegments());
                    ActiveEntities.getActiveControlPoint().setNumSegments(parsedValue);
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
        add(distanceLabel, gbc);

        gbc.gridx++;
        add(numSegTextField, gbc);

        displayInteractability();

        ActiveEntities.addActiveListener(this);
    }
    
    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Configures a {@link JTextField} with value getters and setters, applying input filters and listeners.
     * 
     * @param textField the {@link JTextField} to configure
     * @param getter    the {@link ValueGetter} to retrieve the current value
     * @param setter    the {@link ValueSetter} to apply a new value
     */
    private void configureTextField(JTextField textField, ValueGetter getter, ValueSetter setter) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(Config.STANDART_TEXT_FIELD_INT_REGEX)); 
        textField.putClientProperty("ValueGetter", getter);
        textField.putClientProperty("ValueSetter", setter);
        textFieldSetup(textField);
    }

    /**
     * Updates the text field with the current value from the {@link ValueGetter}.
     * 
     * Retrieves the current value using the associated {@link ValueGetter} and updates the text field's display.
     */
    private void updateTextField(){
        ValueGetter getter;
        getter = (ValueGetter) numSegTextField.getClientProperty("ValueGetter");
        numSegTextField.setText(getter.getValue());
    }

    /**
     * Determines whether the panel is interactable based on the active control point and trajectory.
     * 
     * The panel is interactable if:
     * <ul>
     *   <li>An active control point exists.</li>
     *   <li>The control point is not the last point in its trajectory.</li>
     *   <li>The trajectory's interpolation type is not equidistant.</li>
     * </ul>
     * 
     * @return {@code true} if the panel is interactable, {@code false} otherwise
     */
    @Override
    public boolean isInteractable() {
        ControlPoint cp = ActiveEntities.getActiveControlPoint();
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if(cp == null)                          return false;
        if(tr == null)                          return false;
        if(cp == tr.getLast())                  return false;
        if(tr.getInterpolationType().equals(Calculations.EQUIDISTANT_INTERPOLATION))    return false;
        return true;
    }

    /**
     * Updates the panel's interactability state, enabling or disabling components accordingly.
     * 
     * Changes the background color based on interactability and enables or disables the number of segments text field.
     */
    @Override
    protected void displayInteractability(){
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        numSegTextField.setEnabled(isInteractable);
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
     * Updates the panel's interactability and refreshes the text field display.
     * 
     * @param cp the updated {@link ControlPoint}
     */
    @Override
    public void activeControlPointChanged(ControlPoint cp) {
        displayInteractability();
        updateTextField();
    }

    /**
     * Handles edits to the state of the active control point.
     * 
     * Updates the panel's interactability and refreshes the text field display.
     * 
     * @param cp the {@link ControlPoint} whose state was edited
     */
    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
        displayInteractability();
        updateTextField();
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
