package blitz.ui.application.panels.canvasPanel.pointers;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blitz.configs.Config;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Represents a control pointer on the canvas that can be in different states (UNSELECTED, HIGHLIGHTED, SELECTED).
 * 
 * This pointer changes its color based on its state to provide visual feedback to the user.
 * It is associated with a {@link ControlPoint} which it represents on the canvas.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     ControlPoint controlPoint = new ControlPoint(...);
 *     ControlPointer controlPointer = new ControlPointer(100, 150, controlPoint);
 *     canvasPanel.add(controlPointer);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class ControlPointer extends Pointer{

    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * The current state of the pointer.
     */
    private State state;

    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code ControlPointer} with the specified position and associated control point.
     * 
     * The pointer is initially set to the {@link State#UNSELECTED} state.
     * 
     * @param x          the x-coordinate of the pointer's center position
     * @param y          the y-coordinate of the pointer's center position
     * @param relatedCP  the {@link ControlPoint} associated with this pointer
     */
    public ControlPointer(int x, int y, ControlPoint relatedCP){
        super(x, y, Config.UNSELECTED_CONTROL_POINTER_COLOR, Config.CONTROL_POINTER_DIAMETER, relatedCP);
        setState(State.UNSELECTED);
    }

    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Sets the state of the pointer and updates its color accordingly.
     * 
     * @param state the new {@link State} to set for the pointer
     */
    public void setState(State state){
        this.state = state;
        switch (state) {
            case UNSELECTED:
                setColor(Config.UNSELECTED_CONTROL_POINTER_COLOR);
                break;

            case HIGHLIGHTED:
                setColor(Config.HIGHLIGHTED_CONTROL_POINTER_COLOR);
                break;

            case SELECTED:
                setColor(Config.SELECTED_CONTROL_POINTER_COLOR);
                break;
        }
    }

    /**
     * Checks if the pointer is currently in the {@link State#SELECTED} state.
     * 
     * @return {@code true} if the pointer is selected, {@code false} otherwise
     */
    public boolean isSelected(){
        return getState() == State.SELECTED;
    }

    /**
     * Checks if the pointer is currently in the {@link State#HIGHLIGHTED} state.
     * 
     * @return {@code true} if the pointer is highlighted, {@code false} otherwise
     */
    public boolean isHighlighted(){
        return getState() == State.HIGHLIGHTED;
    }

    /**
     * Retrieves the current state of the pointer.
     * 
     * @return the current {@link State} of the pointer
     */
    public State getState(){
        return state;
    }

    /**
     * Paints the pointer component, rendering it as a semi-transparent filled oval.
     * 
     * The appearance of the pointer changes based on its current color, which reflects its state.
     * 
     * @param g the {@link Graphics} context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            // Set semi-transparent composite
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            // Set the color based on the current state
            g2.setColor(color);
            // Draw the filled oval representing the pointer
            g2.fillOval(0, 0, diameter, diameter);
        } finally {
            // Dispose the Graphics2D object to free resources
            g2.dispose();
        }
    }
}
