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

public class HelperStartLine extends AbstractLinePanel implements ActiveEntitiesListener {

    private JTextField rStartTextField;
    private JTextField thetaStartTextField;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.####");

    public HelperStartLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel xLabel = new JLabel("rS:");
        JLabel yLabel = new JLabel("θS:");

        rStartTextField = new JTextField(6);
        configureTextField(rStartTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(ActiveEntities.getActiveControlPoint().getRStart());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, ActiveEntities.getActiveControlPoint().getRStart());
                    ActiveEntities.getActiveControlPoint().setRStart(parsedValue);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
            }
        });

        thetaStartTextField = new JTextField(6);
        configureTextField(thetaStartTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(ActiveEntities.getActiveControlPoint().getThetaStart());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, ActiveEntities.getActiveControlPoint().getThetaStart());
                    ActiveEntities.getActiveControlPoint().setThetaStart(parsedValue);
                    ActiveEntities.notifyActiveControlPointStateEdited();
                }
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(xLabel, gbc);

        gbc.gridx++;
        add(rStartTextField, gbc);

        gbc.gridx++;
        Component horizontalStrut = Box.createHorizontalStrut(20);
        add(horizontalStrut, gbc);

        gbc.gridx++;
        add(yLabel, gbc);

        gbc.gridx++;
        add(thetaStartTextField, gbc);

        displayInteractability();

        ActiveEntities.addActiveListener(this);
    }

    private void configureTextField(JTextField textField, ValueGetter getter, ValueSetter setter) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(Config.STANDART_TEXT_FIELD_DOUBLE_REGEX));

        textField.putClientProperty("ValueGetter", getter);
        textField.putClientProperty("ValueSetter", setter);
        textFieldSetup(textField);
    }

    private void updateTextField(){
        ValueGetter getter;
        getter = (ValueGetter) rStartTextField.getClientProperty("ValueGetter");
        rStartTextField.setText(getter.getValue());
        getter = (ValueGetter) thetaStartTextField.getClientProperty("ValueGetter");
        thetaStartTextField.setText(getter.getValue());
    }

    @Override
    public boolean isInteractable() {
        ControlPoint cp = ActiveEntities.getActiveControlPoint();
        Trajectory tr = ActiveEntities.getActiveTrajectory();
        if(cp == null)          return false;
        if(cp == tr.getLast())  return false;
        if(tr.getSplineType().equals(Calculations.LINEAR_SPLINE))   return false;
        return true;
    }

    @Override
    protected void displayInteractability(){
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        rStartTextField.setEnabled(isInteractable);
        thetaStartTextField.setEnabled(isInteractable);
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
