package blitz.ui.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;

/**
 * A panel displaying information about an active control point, allowing for editing if the control point is active.
 */
public class InfoPanel extends JPanel implements ActiveListener{

    private ControlPoint activeControlPoint;
    private ArrayList<JTextField> textFields;

    /**
     * Constructs an InfoPanel with a default layout and appearance.
     */
    public InfoPanel() {
        setBackground(MainFrameConfig.INFO_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.INFO_PANEL_PREFFERED_DIMENSIONS);
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setLayout(new GridBagLayout());

        textFields = new ArrayList<>();
        fillWithContent();

        Active.addActiveListener(this);
    }

    /**
     * Fills the panel with labels and text fields for displaying and editing control point details.
     */
    private void fillWithContent() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel infoLabel = new JLabel("Info Panel", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(infoLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Left column
        gbc.gridx = 0;
        addField(gbc, "x:", () -> activeControlPoint != null ? String.valueOf(activeControlPoint.getX()) : "", (value) -> {
            if (activeControlPoint != null) {
                activeControlPoint.setX(parseDouble(value, activeControlPoint.getX()));
            }
        });
        addField(gbc, "r start:", () -> activeControlPoint != null ? String.valueOf(activeControlPoint.getRStart()) : "", (value) -> {
            if (activeControlPoint != null) {
                activeControlPoint.setRStart(parseDouble(value, activeControlPoint.getRStart()));
            }
        });
        addField(gbc, "r end:", () -> activeControlPoint != null ? String.valueOf(activeControlPoint.getREnd()) : "", (value) -> {
            if (activeControlPoint != null) {
                activeControlPoint.setREnd(parseDouble(value, activeControlPoint.getREnd()));
            }
        });
        addField(gbc, "num segments:", () -> activeControlPoint != null ? String.valueOf(activeControlPoint.getNumSegments()) : "", (value) -> {
            if (activeControlPoint != null) {
                activeControlPoint.setNumSegments(parseInt(value, activeControlPoint.getNumSegments()));
            }
        });

        // Right column
        gbc.gridx = 1;
        gbc.gridy = 1;
        addField(gbc, "y:", () -> activeControlPoint != null ? String.valueOf(activeControlPoint.getY()) : "", (value) -> {
            if (activeControlPoint != null) {
                activeControlPoint.setY(parseDouble(value, activeControlPoint.getY()));
            }
        });
        addField(gbc, "theta start:", () -> activeControlPoint != null ? String.valueOf(activeControlPoint.getThetaStart()) : "", (value) -> {
            if (activeControlPoint != null) {
                activeControlPoint.setThetaStart(parseDouble(value, activeControlPoint.getThetaStart()));
            }
        });
        addField(gbc, "theta end:", () -> activeControlPoint != null ? String.valueOf(activeControlPoint.getThetaEnd()) : "", (value) -> {
            if (activeControlPoint != null) {
                activeControlPoint.setThetaEnd(parseDouble(value, activeControlPoint.getThetaEnd()));
            }
        });
        addField(gbc, "time:", () -> activeControlPoint != null ? String.valueOf(activeControlPoint.getTime()) : "", (value) -> {
            if (activeControlPoint != null) {
                activeControlPoint.setTime(parseDouble(value, activeControlPoint.getTime()));
            }
        });
    }

    /**
     * Adds a label and a text field to the panel for editing a specific property of the control point.
     *
     * @param gbc       The GridBagConstraints specifying the position and size of the components.
     * @param labelText The label text describing the property being edited.
     * @param getter    A function that retrieves the current value of the property as a String.
     * @param setter    A function that sets the new value of the property based on the provided String value.
     */
    private void addField(GridBagConstraints gbc, String labelText, ValueGetter getter, ValueSetter setter) {
        JLabel label = new JLabel(labelText, SwingConstants.LEFT);
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 1, 10);
        add(label, gbc);

        JTextField textField = new JTextField(5);
        textField.putClientProperty("ValueGetter", getter);
        textField.putClientProperty("ValueSetter", setter);
        // textField.putClientProperty(getter, "ValueGetter");
        // textField.putClientProperty(setter, "ValueSetter");
        if (activeControlPoint != null) {
            textField.setText(getter.getValue());
        }

        // ActionListener for Enter key press
        textField.addActionListener(e -> {
            if (activeControlPoint != null) {
                String oldValue = getter.getValue();
                String newValue = textField.getText();
                try {
                    setter.setValue(newValue);
                } catch (NumberFormatException ex) {
                    textField.setText(oldValue);
                }
            }
        });

        // FocusAdapter for focus lost
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (activeControlPoint != null) {
                    String oldValue = getter.getValue();
                    String newValue = textField.getText();
                    try {
                        setter.setValue(newValue);
                    } catch (NumberFormatException ex) {
                        textField.setText(oldValue);
                    }
                } else {
                    textField.setText(""); // Clear text field if activeControlPoint is null
                }
            }
        });

        gbc.gridy++;
        gbc.insets = new Insets(1, 10, 10, 10);
        textFields.add(textField);
        add(textField, gbc);
        gbc.insets = new Insets(10, 10, 10, 10);

    }

    /**
     * Parses a String value to a double, returning a default value if parsing fails.
     *
     * @param value        The String value to parse.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed double value or the default value if parsing fails.
     */
    private double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses a String value to an integer, returning a default value if parsing fails.
     *
     * @param value        The String value to parse.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed integer value or the default value if parsing fails.
     */
    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void updateTextFields() {
        for (JTextField textField : textFields) {
            if (textField != null) {
                ValueGetter getter = (ValueGetter) textField.getClientProperty("ValueGetter");
                if (getter != null) {
                    textField.setText(getter.getValue());
                }
            }
        }
    }

    /**
     * Functional interface for retrieving the current value of a property as a String.
     */
    @FunctionalInterface
    private interface ValueGetter {
        String getValue();
    }

    /**
     * Functional interface for setting the new value of a property based on a provided String value.
     */
    @FunctionalInterface
    private interface ValueSetter {
        void setValue(String value);
    }

    @Override
    public void activeTrajectoryChanged(Trajectory tr) {

    }

    @Override
    public void activeControlPointChanged(ControlPoint cp) {
        activeControlPoint = cp;
        updateTextFields();
    }
}
