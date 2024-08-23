package blitz.ui.main.panels;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.ControlPoint.SYMMETRY;
import blitz.models.Trajectory;
import blitz.services.DecimalFilter;
import blitz.services.Utils;

/**
 * A panel displaying information about an active control point, allowing for editing if the control point is active.
 */
public class ControlPointEditPanel extends JPanel implements ActiveListener {

    private Trajectory activeTrajectory;
    private ControlPoint activeControlPoint;

    private ArrayList<JTextField> textFields;
    private ArrayList<JComboBox> symmetryComboBoxes;

    private CardLayout cardLayout;
    private JPanel linearContinuousCard;
    private JPanel linearNonContinuousCard;
    private JPanel bezierContinuousCard;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.####");

    /**
     * Constructs an ControlPointEditPanel with a default layout and appearance.
     */
    public ControlPointEditPanel() {
        setBackground(MainFrameConfig.CP_EDIT_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.CP_EDIT_PANEL_PREFERRED_DIMENSIONS);
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        textFields = new ArrayList<>();
        symmetryComboBoxes = new ArrayList<>();
        createCards();

        cardLayout.show(this, "linearNonContinuous");

        Active.addActiveListener(this);
    }

    /**
     * Fills the panel with labels and text fields for displaying and editing control point details.
     */
    private void createCards() {
        createLinearContinuousCard();
        createLinearNonContinuousCard();
        createBezierContinuousCard();
    }

    private void createLinearContinuousCard() {

        // -=- Setup -=-

        linearContinuousCard = new JPanel(new GridBagLayout());
    
        ValueGetter getter;
        ValueSetter setter;
        AbstractDocument doc;
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
    


        // -=- Elements -=-

        // Top Glue 
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        linearContinuousCard.add(Box.createVerticalGlue(), gbc);
    


        // x Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 1);
        
        linearContinuousCard.add(new JLabel("x:"), gbc);
    


        // x Text Field
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 1, 10, 10);

        JTextField xTextField = new JTextField(6);
        doc = (AbstractDocument) xTextField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));
    
        getter = () -> {
            if (activeControlPoint != null) {
                return DECIMAL_FORMAT.format(activeControlPoint.getX());
            }
            return "";
        };
    
        setter = (value) -> {
            if (activeControlPoint != null) {
                double parsedValue = parseDouble(value, activeControlPoint.getX());
                activeControlPoint.setX(parsedValue);
                Active.notifyActiveControlPointStateEdited();
            }
        };
    
        xTextField.putClientProperty("ValueGetter", getter);
        xTextField.putClientProperty("ValueSetter", setter);
        textFieldSetup(xTextField);
        linearContinuousCard.add(xTextField, gbc);
        textFields.add(xTextField);
    


        // y Label
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 1);

        linearContinuousCard.add(new JLabel("y:"), gbc);
    


        // y Text Field
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 1, 10, 10);

        JTextField yTextField = new JTextField(6);
        doc = (AbstractDocument) yTextField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));
    
        getter = () -> {
            if (activeControlPoint != null) {
                return DECIMAL_FORMAT.format(activeControlPoint.getY());
            }
            return "";
        };
    
        setter = (value) -> {
            if (activeControlPoint != null) {
                double parsedValue = parseDouble(value, activeControlPoint.getY());
                activeControlPoint.setY(parsedValue);
                Active.notifyActiveControlPointStateEdited();
            }
        };
    
        yTextField.putClientProperty("ValueGetter", getter);
        yTextField.putClientProperty("ValueSetter", setter);
        textFieldSetup(yTextField);
        linearContinuousCard.add(yTextField, gbc);
        textFields.add(yTextField);



        // Spacing Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 1);

        linearContinuousCard.add(new JLabel("Dist:"), gbc);



        // Spacing Text Field

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 1, 10, 10);

        JTextField spacingTextField = new JTextField(6);
        doc = (AbstractDocument) spacingTextField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));
    
        getter = () -> {
            if (activeControlPoint != null) {
                return DECIMAL_FORMAT.format(activeTrajectory.getDistance());
            }
            return "";
        };
    
        setter = (value) -> {
            if (activeTrajectory != null) {
                double parsedValue = parseDouble(value, activeTrajectory.getDistance());
                activeTrajectory.setDistance(parsedValue);
                Active.notifyActiveTrajectoryStateEdited();
            }
        };
    
        spacingTextField.putClientProperty("ValueGetter", getter);
        spacingTextField.putClientProperty("ValueSetter", setter);
        textFieldSetup(spacingTextField);
        linearContinuousCard.add(spacingTextField, gbc);
        textFields.add(spacingTextField);
    


        // Bottom Glue
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1; 
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        linearContinuousCard.add(Box.createVerticalGlue(), gbc);



        // Add the card to the layout
        this.add(linearContinuousCard, "linearContinuous");
    }
    
    private void createLinearNonContinuousCard() {

        // -=- Setup -=-

        linearNonContinuousCard = new JPanel(new GridBagLayout());
    
        ValueGetter getter;
        ValueSetter setter;
        AbstractDocument doc;
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
    


        // -=- Elements -=-

        // Top Glue 
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        linearNonContinuousCard.add(Box.createVerticalGlue(), gbc);
    


        // x Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 1);
        
        linearNonContinuousCard.add(new JLabel("x:"), gbc);
    


        // x Text Field
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 1, 10, 10);

        JTextField xTextField = new JTextField(6);
        doc = (AbstractDocument) xTextField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));
    
        getter = () -> {
            if (activeControlPoint != null) {
                return DECIMAL_FORMAT.format(activeControlPoint.getX());
            }
            return "";
        };
    
        setter = (value) -> {
            if (activeControlPoint != null) {
                double parsedValue = parseDouble(value, activeControlPoint.getX());
                activeControlPoint.setX(parsedValue);
                Active.notifyActiveControlPointStateEdited();
            }
        };
    
        xTextField.putClientProperty("ValueGetter", getter);
        xTextField.putClientProperty("ValueSetter", setter);
        textFieldSetup(xTextField);
        linearNonContinuousCard.add(xTextField, gbc);
        textFields.add(xTextField);
    


        // y Label
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 1);

        linearNonContinuousCard.add(new JLabel("y:"), gbc);
    


        // y Text Field
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 1, 10, 10);

        JTextField yTextField = new JTextField(6);
        doc = (AbstractDocument) yTextField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));
    
        getter = () -> {
            if (activeControlPoint != null) {
                return DECIMAL_FORMAT.format(activeControlPoint.getY());
            }
            return "";
        };
    
        setter = (value) -> {
            if (activeControlPoint != null) {
                double parsedValue = parseDouble(value, activeControlPoint.getY());
                activeControlPoint.setY(parsedValue);
                Active.notifyActiveControlPointStateEdited();
            }
        };
    
        yTextField.putClientProperty("ValueGetter", getter);
        yTextField.putClientProperty("ValueSetter", setter);
        textFieldSetup(yTextField);
        linearNonContinuousCard.add(yTextField, gbc);
        textFields.add(yTextField);



        // NumSeg Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 1);

        linearNonContinuousCard.add(new JLabel("NumSeg:"), gbc);



        // NumSeg Text Field

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 1, 10, 10);

        JTextField NumSegTextField = new JTextField(6);
        doc = (AbstractDocument) NumSegTextField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));
    
        getter = () -> {
            if (activeControlPoint != null) {
                return DECIMAL_FORMAT.format(activeControlPoint.getNumSegments());
            }
            return "";
        };
    
        setter = (value) -> {
            if (activeTrajectory != null) {
                int parsedValue = parseInt(value, activeControlPoint.getNumSegments());
                activeControlPoint.setNumSegments(parsedValue);
                Active.notifyActiveControlPointStateEdited();
            }
        };
    
        NumSegTextField.putClientProperty("ValueGetter", getter);
        NumSegTextField.putClientProperty("ValueSetter", setter);
        textFieldSetup(NumSegTextField);
        linearNonContinuousCard.add(NumSegTextField, gbc);
        textFields.add(NumSegTextField);
    


        // Bottom Glue
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1; 
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        linearNonContinuousCard.add(Box.createVerticalGlue(), gbc);



        // Add the card to the layout
        this.add(linearNonContinuousCard, "linearNonContinuous");
    }
    
    private void createBezierContinuousCard() {

        // -=- Setup -=-
    
        bezierContinuousCard = new JPanel(new GridBagLayout());
        ValueGetter getter;
        ValueSetter setter;
        AbstractDocument doc;
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow components to stretch horizontally
        gbc.weightx = 1.0;  // Give components room to grow horizontally
        gbc.anchor = GridBagConstraints.CENTER; // Center components within their cell
    
        // -=- Elements -=-
    
        // x Label
        gbc.gridx = 0;
        gbc.gridy = 0; // Start at row 0
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 10, 10, 1);
        gbc.fill = GridBagConstraints.NONE; // Ensure label doesn't stretch
        gbc.anchor = GridBagConstraints.EAST; // Align label to the right
        bezierContinuousCard.add(new JLabel("x:"), gbc);
    
        // x Text Field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 1, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow the text field to stretch horizontally
        gbc.anchor = GridBagConstraints.WEST; // Align text field to the left
        JTextField xTextField = new JTextField(10); // Increased columns for width
        doc = (AbstractDocument) xTextField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));
        getter = () -> activeControlPoint != null ? DECIMAL_FORMAT.format(activeControlPoint.getX()) : "";
        setter = value -> {
            if (activeControlPoint != null) {
                activeControlPoint.setX(parseDouble(value, activeControlPoint.getX()));
                Active.notifyActiveControlPointStateEdited();
            }
        };
        xTextField.putClientProperty("ValueGetter", getter);
        xTextField.putClientProperty("ValueSetter", setter);
        textFieldSetup(xTextField);
        bezierContinuousCard.add(xTextField, gbc);
        textFields.add(xTextField);
    
        // y Label
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 1);
        gbc.fill = GridBagConstraints.NONE; // Ensure label doesn't stretch
        gbc.anchor = GridBagConstraints.EAST;
        bezierContinuousCard.add(new JLabel("y:"), gbc);
    
        // y Text Field
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 1, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow the text field to stretch horizontally
        gbc.anchor = GridBagConstraints.WEST;
        JTextField yTextField = new JTextField(10); // Increased columns for width
        doc = (AbstractDocument) yTextField.getDocument();
        doc.setDocumentFilter(new DecimalFilter(MainFrameConfig.STANDART_TEXT_FIELD_REGEX));
        getter = () -> activeControlPoint != null ? DECIMAL_FORMAT.format(activeControlPoint.getY()) : "";
        setter = value -> {
            if (activeControlPoint != null) {
                activeControlPoint.setY(parseDouble(value, activeControlPoint.getY()));
                Active.notifyActiveControlPointStateEdited();
            }
        };
        yTextField.putClientProperty("ValueGetter", getter);
        yTextField.putClientProperty("ValueSetter", setter);
        textFieldSetup(yTextField);
        bezierContinuousCard.add(yTextField, gbc);
        textFields.add(yTextField);
    
        // Symmetry ComboBox
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Span across one cell
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        JComboBox<String> symmetryComboBox = new JComboBox<>(new String[]{"Broken", "Aligned", "Mirrored"});
        if (Active.getActiveTrajectory() != null) {
            symmetryComboBox.setSelectedItem(Active.getActiveTrajectory().getSplineType());
        }
    
        symmetryComboBox.addActionListener(e -> {
            if (activeControlPoint == null) return;
            String selectedSymmetry = (String) symmetryComboBox.getSelectedItem();
            switch (selectedSymmetry) {
                case "Broken":
                    activeControlPoint.setSymmetry(SYMMETRY.BROKEN);
                    break;
                case "Aligned":
                    activeControlPoint.setSymmetry(SYMMETRY.ALIGNED);
                    break;
                case "Mirrored":
                    activeControlPoint.setSymmetry(SYMMETRY.MIRRORED);
                    break;
            }
        });

        Dimension textFieldSize = new Dimension(30, 40);
        symmetryComboBox.setPreferredSize(textFieldSize);
        symmetryComboBox.setMaximumSize(textFieldSize);
        symmetryComboBox.setMinimumSize(textFieldSize);
    
        bezierContinuousCard.add(symmetryComboBox, gbc);
        symmetryComboBoxes.add(symmetryComboBox);
    
        // Bottom Glue (optional for padding or additional spacing)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        bezierContinuousCard.add(Box.createVerticalGlue(), gbc);
    
        // Add the card to the layout
        this.add(bezierContinuousCard, "bezierContinuous");
    }
    



    
    private void textFieldSetup(JTextField textField) {
        ValueGetter getter = (ValueGetter) textField.getClientProperty("ValueGetter");
        ValueSetter setter = (ValueSetter) textField.getClientProperty("ValueSetter");

        // ActionListener for Enter key press
        textField.addActionListener(e -> {
            if (activeControlPoint != null) {
                String oldValue = getter.getValue();
                String newValue = textField.getText();
                try {
                    setter.setValue(newValue);
                    textField.setText(getter.getValue());
                } catch (NumberFormatException ex) {
                    textField.setText(oldValue);
                }
            }
            Utils.requestFocusInWindowFor(textField);
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
                        textField.setText(getter.getValue());
                    } catch (NumberFormatException ex) {
                        textField.setText(oldValue);
                    }
                } else {
                    textField.setText(""); // Clear text field if activeControlPoint is null
                }
            }
        });
    }

    private double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

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
                    String value = getter.getValue();
                    textField.setText(value);
                }
            }
        }

        if (activeControlPoint != null) {
            for (JComboBox symmetryComboBox : symmetryComboBoxes) {
                SYMMETRY currentSymmetry = activeControlPoint.getSymmetry();
                switch (currentSymmetry) {
                    case BROKEN:
                        symmetryComboBox.setSelectedItem("Broken");
                        break;
                    case ALIGNED:
                        symmetryComboBox.setSelectedItem("Aligned");
                        break;
                    case MIRRORED:
                        symmetryComboBox.setSelectedItem("Mirrored");
                        break;
                }
            }
        }
    }

    private void showProperCard(){
        if(activeTrajectory == null) return;
        boolean isContinuous = activeTrajectory.isContinuous();
        if(activeTrajectory.isTypeLinear()){
            if(isContinuous){
                cardLayout.show(this, "linearContinuous");
            } else{
                cardLayout.show(this, "linearNonContinuous");
            }

        } else if(activeTrajectory.isTypeBezier()){
            if(isContinuous){
                cardLayout.show(this, "bezierContinuous");
            } else {

            }
        }
    }

    @FunctionalInterface
    private interface ValueGetter {
        String getValue();
    }

    @FunctionalInterface
    private interface ValueSetter {
        void setValue(String value);
    }

    @Override
    public void activeTrajectoryChanged(Trajectory tr) {
        activeTrajectory = tr;
        showProperCard();
    }

    @Override
    public void activeControlPointChanged(ControlPoint cp) {
        activeControlPoint = cp;
        updateTextFields();
    }

    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
        updateTextFields();
    }

    @Override
    public void activeTrajectoryStateEdited(Trajectory tr) {
        showProperCard();
    }
}
