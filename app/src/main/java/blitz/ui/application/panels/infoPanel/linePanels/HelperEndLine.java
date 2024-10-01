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
 * Represents a panel for displaying and editing the end-related properties of a control point.
 * 
 * This panel provides user interface components for viewing and modifying the radial end (rE) and
 * angular end (θE) values of an active control point associated with a trajectory. It ensures that
 * inputs are validated and applied correctly, and updates the display based on the panel's interactability.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     HelperEndLine helperEndLine = new HelperEndLine();
 *     infoPanel.add(helperEndLine);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class HelperEndLine extends AbstractLinePanel implements ActiveEntitiesListener {
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Text field for displaying and editing the radial end (rE) value.
     */
    private JTextField rEndTextField;
    
    /**
     * Text field for displaying and editing the angular end (θE) value.
     */
    private JTextField thetaEndTextField;
    
    /**
     * Formatter for decimal values, ensuring consistency in display.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.####");
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code HelperEndLine} panel with configured components and listeners.
     * 
     * Initializes the layout, adds labels and text fields, and sets up interactability based on the active control point.
     * Registers this panel as a listener to active entity changes.
     */
    public HelperEndLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel xLabel = new JLabel("rE:");
        JLabel yLabel = new JLabel("θE:");

        rEndTextField = new JTextField(6);
        configureTextField(rEndTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(ActiveEntities.getActiveControlPoint().getREnd());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, ActiveEntities.getActiveControlPoint().getREnd());
                    ActiveEntities.getActiveControlPoint().setREnd(parsedValue);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
            }
        });

        thetaEndTextField = new JTextField(6);
        configureTextField(thetaEndTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(ActiveEntities.getActiveControlPoint().getThetaEnd());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, ActiveEntities.getActiveControlPoint().getThetaEnd());
                    ActiveEntities.getActiveControlPoint().setThetaEnd(parsedValue);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(xLabel, gbc);

        gbc.gridx++;
        add(rEndTextField, gbc);

        gbc.gridx++;
        Component horizontalStrut = Box.createHorizontalStrut(20);
        add(horizontalStrut, gbc);

        gbc.gridx++;
        add(yLabel, gbc);

        gbc.gridx++;
        add(thetaEndTextField, gbc);

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
        getter = (ValueGetter) rEndTextField.getClientProperty("ValueGetter");
        rEndTextField.setText(getter.getValue());
        
        getter = (ValueGetter) thetaEndTextField.getClientProperty("ValueGetter");
        thetaEndTextField.setText(getter.getValue());
    }

    /**
     * Determines whether the panel is interactable based on the active control point and trajectory.
     * 
     * The panel is interactable if:
     * <ul>
     *   <li>An active control point exists.</li>
     *   <li>The control point is not the first point in its trajectory.</li>
     *   <li>The trajectory's spline type is not linear.</li>
     * </ul>
     * 
     * @return {@code true} if the panel is interactable, {@code false} otherwise
     */
    @Override
    public boolean isInteractable() {
        ControlPoint cp = ActiveEntities.getActiveControlPoint();
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if(cp == null)          return false;
        if(tr == null)          return false;
        if(cp == tr.getFirst()) return false;
        if(tr.getSplineType().equals(Calculations.LINEAR_SPLINE))   return false;
        return true;
    }

    /**
     * Updates the panel's interactability state, enabling or disabling components accordingly.
     * 
     * Changes the background color based on interactability and enables or disables the distance and theta end text fields.
     */
    @Override
    protected void displayInteractability(){
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        rEndTextField.setEnabled(isInteractable);
        thetaEndTextField.setEnabled(isInteractable);
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
