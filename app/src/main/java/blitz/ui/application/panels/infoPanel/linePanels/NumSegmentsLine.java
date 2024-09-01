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

public class NumSegmentsLine extends AbstractLinePanel implements ActiveEntitiesListener {

    private JTextField numSegTextField;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0");

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

    private void configureTextField(JTextField textField, ValueGetter getter, ValueSetter setter) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(Config.STANDART_TEXT_FIELD_INT_REGEX)); 
        textField.putClientProperty("ValueGetter", getter);
        textField.putClientProperty("ValueSetter", setter);
        textFieldSetup(textField);
    }

    private void updateTextField(){
        ValueGetter getter;
        getter = (ValueGetter) numSegTextField.getClientProperty("ValueGetter");
        numSegTextField.setText(getter.getValue());
    }

    @Override
    public boolean isInteractable() {
        ControlPoint cp = ActiveEntities.getActiveControlPoint();
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if(cp == null)                          return false;
        if(tr.getInterpolationType().equals(Calculations.EQUIDISTANT_INTERPOLATION))    return false;
        if(cp == tr.getLast())                  return false;
        return true;
    }

    @Override
    protected void displayInteractability(){
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        numSegTextField.setEnabled(isInteractable);
    }

    @Override
    public void activeTrajectoryChanged(Trajectory tr) {
    }

    @Override
    public void activeControlPointChanged(ControlPoint cp) {
        displayInteractability();
        updateTextField();
    }

    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
        displayInteractability();
        updateTextField();
    }

    @Override
    public void activeTrajectoryStateEdited(Trajectory tr) {
    }
}
