package blitz.ui.main.panels.linePanels;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;
import blitz.models.calculations.Calculations;
import blitz.services.DecimalFilter;

public class DistanceLine extends LinePanel implements ActiveListener {

    private JTextField distanceTextField;
    private JTextField thetaEndTextField;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.####");

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
                    return DECIMAL_FORMAT.format(Active.getActiveTrajectory().getSpacing());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, Active.getActiveTrajectory().getSpacing());
                    Active.getActiveTrajectory().setSpacing(parsedValue);
                    Active.notifyActiveControlPointStateEdited();
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

        Active.addActiveListener(this);
    }

    private void configureTextField(JTextField textField, ValueGetter getter, ValueSetter setter) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_DOUBLE_REGEX));

        textField.putClientProperty("ValueGetter", getter);
        textField.putClientProperty("ValueSetter", setter);
        textFieldSetup(textField);
    }

    private void updateTextField(){
        ValueGetter getter;
        getter = (ValueGetter) distanceTextField.getClientProperty("ValueGetter");
        distanceTextField.setText(getter.getValue());
    }

    @Override
    public boolean isInteractable() {
        ControlPoint cp = Active.getActiveControlPoint();
        Trajectory tr = Active.getActiveTrajectory();
        if(tr == null)          return false;
        if(tr.getInterpolationType().equals(Calculations.UNIFORM_INTERPOLATION))  return false;
        return true;
    }

    @Override
    protected void displayInteractability(){
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        distanceTextField.setEnabled(isInteractable);
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
