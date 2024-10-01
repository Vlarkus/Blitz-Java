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
 * Represents a panel for displaying and editing the distance-related properties of a trajectory.
 * 
 * This panel provides a user interface for viewing and modifying the spacing between control points
 * of an active trajectory. It includes text fields for inputting distance values and ensures that
 * inputs are validated and applied correctly.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     DistanceLine distanceLine = new DistanceLine();
 *     infoPanel.add(distanceLine);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class DistanceLine extends AbstractLinePanel implements ActiveEntitiesListener {
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Text field for displaying and editing the distance value.
     */
    private JTextField distanceTextField;
    
    /**
     * Text field for displaying and editing the theta end value.
     * 
     * <strong>Note:</strong> Although declared, this field is not initialized or used in the current implementation.
     */
    private JTextField thetaEndTextField;
    
    /**
     * Formatter for decimal values, ensuring consistency in display.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.####");
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code DistanceLine} panel with configured components and listeners.
     * 
     * Initializes the layout, adds labels and text fields, and sets up interactability based on the active trajectory.
     * Registers this panel as a listener to active entity changes.
     */
    public DistanceLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel distanceLabel = new JLabel("Distance:");

        distanceTextField = new JTextField(6);
        configureTextField(distanceTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(ActiveEntities.getActiveTrajectory().getSpacing());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, ActiveEntities.getActiveTrajectory().getSpacing());
                    ActiveEntities.getActiveTrajectory().setSpacing(parsedValue);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        Component horizontalStrut = Box.createHorizontalStrut(97);
        add(horizontalStrut, gbc);
        
        gbc.gridx++;
        add(distanceLabel, gbc);

        gbc.gridx++;
        add(distanceTextField, gbc);

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
        doc.setDocumentFilter(new DecimalFilter(Config.STANDART_TEXT_FIELD_DOUBLE_REGEX));

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
        getter = (ValueGetter) distanceTextField.getClientProperty("ValueGetter");
        distanceTextField.setText(getter.getValue());
    }

    /**
     * Determines whether the panel is interactable based on the active trajectory's interpolation type.
     * 
     * @return {@code true} if the active trajectory allows interaction, {@code false} otherwise
     */
    @Override
    public boolean isInteractable() {
        ControlPoint cp = ActiveEntities.getActiveControlPoint();
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if(tr == null)          return false;
        if(tr.getInterpolationType().equals(Calculations.FIXED_SPACING_INTERPOLATION))  return false;
        if(tr.getInterpolationType().equals(Calculations.UNIFORM_INTERPOLATION))        return false;
        return true;
    }

    /**
     * Updates the panel's interactability state, enabling or disabling components accordingly.
     * 
     * Changes the background color based on interactability and enables or disables the distance text field.
     */
    @Override
    protected void displayInteractability(){
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        distanceTextField.setEnabled(isInteractable);
    }

    /**
     * Handles changes to the active trajectory.
     * 
     * <strong>Note:</strong> Currently not implemented.
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
     * <strong>Note:</strong> Currently not implemented.
     * 
     * @param tr the updated {@link Trajectory}
     */
    @Override
    public void activeTrajectoryStateEdited(Trajectory tr) {
        // Implementation can be added if needed
    }
}
