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
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.services.DecimalFilter;

/**
 * Represents a panel for displaying and editing the position (x, y) of a control point.
 * 
 * This panel provides user interface components that allow users to view and modify the
 * x and y coordinates of an active control point associated with a trajectory. It ensures
 * that inputs are validated and applied correctly, and updates the display based on the panel's
 * interactability.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     PositionLine positionLine = new PositionLine();
 *     infoPanel.add(positionLine);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class PositionLine extends AbstractLinePanel implements ActiveEntitiesListener {
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Text field for displaying and editing the x-coordinate of the control point.
     */
    private JTextField xTextField;
    
    /**
     * Text field for displaying and editing the y-coordinate of the control point.
     */
    private JTextField yTextField;
    
    /**
     * Formatter for decimal values, ensuring consistency in display.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.####");
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code PositionLine} panel with configured components and listeners.
     * 
     * Initializes the layout, adds labels and text fields, and sets up interactability based on the active control point.
     * Registers this panel as a listener to active entity changes.
     */
    public PositionLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel xLabel = new JLabel("x:");
        JLabel yLabel = new JLabel("y:");

        xTextField = new JTextField(6);
        configureTextField(xTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(ActiveEntities.getActiveControlPoint().getX());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, ActiveEntities.getActiveControlPoint().getX());
                    ActiveEntities.getActiveControlPoint().setX(parsedValue);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
            }
        });

        yTextField = new JTextField(6);
        configureTextField(yTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(ActiveEntities.getActiveControlPoint().getY());
                } else {

                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, ActiveEntities.getActiveControlPoint().getY());
                    ActiveEntities.getActiveControlPoint().setY(parsedValue);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        
        gbc.gridx++;
        add(xLabel, gbc);

        gbc.gridx++;
        add(xTextField, gbc);

        gbc.gridx++;
        Component horizontalStrut2 = Box.createHorizontalStrut(31);
        add(horizontalStrut2, gbc);

        gbc.gridx++;
        add(yLabel, gbc);

        gbc.gridx++;
        add(yTextField, gbc);

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
     * Updates the text fields with the current values from their respective {@link ValueGetter}s.
     * 
     * Retrieves the current values using the associated {@link ValueGetter}s and updates the text fields' displays.
     */
    private void updateTextField(){
        ValueGetter getter;
        getter = (ValueGetter) xTextField.getClientProperty("ValueGetter");
        xTextField.setText(getter.getValue());
        getter = (ValueGetter) yTextField.getClientProperty("ValueGetter");
        yTextField.setText(getter.getValue());
    }

    /**
     * Determines whether the panel is interactable based on the presence of an active control point.
     * 
     * @return {@code true} if there is an active control point, {@code false} otherwise
     */
    @Override
    public boolean isInteractable() {
        return ActiveEntities.getActiveControlPoint() != null;
    }

    /**
     * Updates the panel's interactability state, enabling or disabling components accordingly.
     * 
     * Changes the background color based on interactability and enables or disables the x and y text fields.
     */
    @Override
    protected void displayInteractability(){
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        xTextField.setEnabled(isInteractable);
        yTextField.setEnabled(isInteractable);
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
     * Updates the panel's interactability and refreshes the text fields' displays.
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
     * Updates the panel's interactability and refreshes the text fields' displays.
     * 
     * @param cp the {@link ControlPoint} whose state was edited
     */
    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
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
