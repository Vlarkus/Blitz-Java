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
import blitz.services.DecimalFilter;

public class PositionLine extends LinePanel implements ActiveListener {

    private JTextField xTextField;
    private JTextField yTextField;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.####");

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
                    return DECIMAL_FORMAT.format(Active.getActiveControlPoint().getX());
                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, Active.getActiveControlPoint().getX());
                    Active.getActiveControlPoint().setX(parsedValue);
                    Active.notifyActiveControlPointStateEdited();
                }
            }
        });

        yTextField = new JTextField(6);
        configureTextField(yTextField, new ValueGetter() {
            @Override
            public String getValue() {
                if (isInteractable()) {
                    return DECIMAL_FORMAT.format(Active.getActiveControlPoint().getY());
                } else {

                }
                return "";
            }
        }, new ValueSetter() {
            @Override
            public void setValue(String value) {
                if (isInteractable()) {
                    double parsedValue = parseDouble(value, Active.getActiveControlPoint().getY());
                    Active.getActiveControlPoint().setY(parsedValue);
                    Active.notifyActiveControlPointStateEdited();
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

        Active.addActiveListener(this);
    }

    private void configureTextField(JTextField textField, ValueGetter getter, ValueSetter setter) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));

        textField.putClientProperty("ValueGetter", getter);
        textField.putClientProperty("ValueSetter", setter);
        textFieldSetup(textField);
    }

    private void updateTextField(){
        ValueGetter getter;
        getter = (ValueGetter) xTextField.getClientProperty("ValueGetter");
        xTextField.setText(getter.getValue());
        getter = (ValueGetter) yTextField.getClientProperty("ValueGetter");
        yTextField.setText(getter.getValue());
    }

    @Override
    public boolean isInteractable() {
        return Active.getActiveControlPoint() != null;
    }

    @Override
    protected void displayInteractability(){
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        xTextField.setEnabled(isInteractable);
        yTextField.setEnabled(isInteractable);
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
        updateTextField();
    }

    @Override
    public void activeTrajectoryStateEdited(Trajectory tr) {
    }
}
