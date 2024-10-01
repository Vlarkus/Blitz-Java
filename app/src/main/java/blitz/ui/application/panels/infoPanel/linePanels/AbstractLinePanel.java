package blitz.ui.application.panels.infoPanel.linePanels;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import blitz.configs.Config;
import blitz.models.active.ActiveEntities;
import blitz.services.Utils;

/**
 * Represents an abstract panel that provides common functionalities for line-related panels.
 * 
 * This class serves as a base for various line panels, offering methods for parsing input values,
 * setting up text fields with listeners, and managing interactability based on the panel's state.
 * It ensures a consistent layout and size as defined in the configuration.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     AbstractLinePanel linePanel = new ConcreteLinePanel();
 *     someContainer.add(linePanel);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public abstract class AbstractLinePanel extends JPanel{
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    // No additional fields are defined in this abstract class.
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs an {@code AbstractLinePanel} with predefined size and layout settings.
     * 
     * The panel is set to a fixed size as defined in the configuration and uses a horizontal box layout.
     */
    public AbstractLinePanel(){
        setPreferredSize(Config.LINE_PANEL_DIMENSITONS);
        setMinimumSize(Config.LINE_PANEL_DIMENSITONS);
        setMaximumSize(Config.LINE_PANEL_DIMENSITONS);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Determines whether the panel is interactable.
     * 
     * Subclasses must implement this method to specify their interactability status.
     * 
     * @return {@code true} if the panel is interactable, {@code false} otherwise
     */
    public abstract boolean isInteractable();
    
    /**
     * Parses a {@code String} to a {@code double}.
     * 
     * If the parsing fails, returns the specified default value.
     * 
     * @param value        the {@code String} to parse
     * @param defaultValue the default {@code double} value to return if parsing fails
     * @return the parsed {@code double} value or {@code defaultValue} if parsing fails
     */
    protected double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Parses a {@code String} to an {@code int}.
     * 
     * If the parsing fails, returns the specified default value.
     * 
     * @param value        the {@code String} to parse
     * @param defaultValue the default {@code int} value to return if parsing fails
     * @return the parsed {@code int} value or {@code defaultValue} if parsing fails
     */
    protected int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Sets up a {@link JTextField} with listeners for action events and focus changes.
     * 
     * This method attaches an {@code ActionListener} to handle Enter key presses and a {@code FocusListener}
     * to handle focus loss events. It ensures that changes are validated and applied to the associated
     * {@link ControlPoint} when applicable.
     * 
     * @param textField the {@link JTextField} to set up
     */
    protected void textFieldSetup(JTextField textField) {
        ValueGetter getter = (ValueGetter) textField.getClientProperty("ValueGetter");
        ValueSetter setter = (ValueSetter) textField.getClientProperty("ValueSetter");

        // ActionListener for Enter key press
        textField.addActionListener(e -> {
            if (ActiveEntities.getActiveControlPoint() != null) {
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
                if (ActiveEntities.getActiveControlPoint() != null) {
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
    
    /**
     * Updates the panel's background color based on its interactability.
     * 
     * If the panel is interactable, it sets the background to the active color;
     * otherwise, it sets it to the inactive color as defined in the configuration.
     */
    protected void displayInteractability(){
        if(isInteractable()){
            setBackground(Config.ACTIVE_LAYER_PANEL_BACKGROUND_COLOR);
        } else {
            setBackground(Config.INACTIVE_LAYER_PANEL_BACKGROUND_COLOR);
        }
    }
    
    // -=-=-=- INNER CLASSES -=-=-=-=-
    
    /**
     * Functional interface for getting a value from a component.
     * 
     * Implementations should provide a method to retrieve the current value as a {@code String}.
     */
    @FunctionalInterface
    protected interface ValueGetter {
        /**
         * Retrieves the current value.
         * 
         * @return the current value as a {@code String}
         */
        String getValue();
    }
    
    /**
     * Functional interface for setting a value to a component.
     * 
     * Implementations should provide a method to set a new value from a {@code String}.
     */
    @FunctionalInterface
    protected interface ValueSetter {
        /**
         * Sets the value based on the provided {@code String}.
         * 
         * @param value the new value to set
         * @throws NumberFormatException if the provided value is invalid
         */
        void setValue(String value);
    }
}
