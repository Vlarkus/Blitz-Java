package blitz.ui.application.panels.canvasPanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;

import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Represents a generic pointer component on the canvas, serving as the base class for various pointer types.
 * 
 * This abstract class provides foundational properties and methods for different types of pointers
 * that can be displayed on the canvas. It handles positioning, sizing, color management,
 * and hit detection within the pointer's area.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     ControlPoint controlPoint = new ControlPoint(...);
 *     Pointer pointer = new ConcretePointer(x, y, Color.RED, 10, controlPoint);
 *     canvasPanel.add(pointer);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public abstract class Pointer extends JComponent{
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * The diameter of the pointer in pixels.
     */
    protected int diameter;
    
    /**
     * The color of the pointer.
     */
    protected Color color;
    
    /**
     * The {@link ControlPoint} associated with this pointer.
     */
    protected ControlPoint relatedControlPoint;

    /**
     * Represents the possible states of the pointer.
     */
    public enum State{
        UNSELECTED,
        HIGHLIGHTED,
        SELECTED
    }
    
    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code Pointer} with the specified position, color, diameter, and associated control point.
     * 
     * @param x              the x-coordinate of the pointer's top-left corner
     * @param y              the y-coordinate of the pointer's top-left corner
     * @param color          the color of the pointer
     * @param diameter       the diameter of the pointer in pixels
     * @param controlPoint   the {@link ControlPoint} associated with this pointer
     */
    public Pointer(int x, int y, Color color, int diameter, ControlPoint controlPoint){
        setDiameter(diameter);
        this.color = color;
        relatedControlPoint = controlPoint;
        setBounds(x, y, diameter, diameter);
        setCenterPosition(x, y);
        this.color = color;
    }
    
    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Returns the preferred size of the pointer component.
     * 
     * @return a {@link Dimension} representing the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(diameter, diameter);
    }
    
    /**
     * Determines whether a given point (x, y) is within the bounds of the pointer.
     * 
     * @param x the x-coordinate of the point to check
     * @param y the y-coordinate of the point to check
     * @return {@code true} if the point is within the pointer's area, {@code false} otherwise
     */
    public boolean isWithinPointer(int x, int y) {
        int radius = diameter / 2;
        int centerX = this.getCenterX();
        int centerY = this.getCenterY();
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);
    }
    
    /**
     * Returns the x-coordinate of the pointer's center position.
     * 
     * @return the x-coordinate of the center
     */
    public int getCenterX(){
        return getX() + diameter/2;
    }
    
    /**
     * Sets the x-coordinate of the pointer's center position.
     * 
     * @param x the new x-coordinate for the center position
     */
    public void setCenterX(int x){
        setCenterPosition(x, getY());
    }
    
    /**
     * Returns the y-coordinate of the pointer's center position.
     * 
     * @return the y-coordinate of the center
     */
    public int getCenterY(){
        return getY() + diameter/2;
    }
    
    /**
     * Sets the y-coordinate of the pointer's center position.
     * 
     * @param y the new y-coordinate for the center position
     */
    public void setCenterY(int y){
        setCenterPosition(getX(), y);
    }
    
    /**
     * Sets the center position of the pointer.
     * 
     * @param x the new x-coordinate for the center position
     * @param y the new y-coordinate for the center position
     */
    public void setCenterPosition(int x, int y) {
        setBounds(x - diameter / 2, y - diameter / 2, diameter, diameter);
    }
    
    /**
     * Sets the diameter of the pointer and updates its position accordingly.
     * 
     * @param diameter the new diameter of the pointer in pixels
     */
    protected void setDiameter(int diameter){
        this.diameter = diameter;
        setCenterPosition(getCenterX(), getCenterY());
    }
    
    /**
     * Returns the diameter of the pointer.
     * 
     * @return the diameter in pixels
     */
    public int getDiameter(){
        return diameter;
    }
    
    /**
     * Sets the color of the pointer.
     * 
     * @param color the new {@link Color} for the pointer
     */
    protected void setColor(Color color){
        this.color = color;
    }
    
    /**
     * Returns the color of the pointer.
     * 
     * @return the {@link Color} of the pointer
     */
    public Color getColor(){
        return color;
    }
    
    /**
     * Returns the {@link ControlPoint} associated with this pointer.
     * 
     * @return the related {@link ControlPoint}
     */
    public ControlPoint getRelatedControlPoint(){
        return relatedControlPoint;
    }
    
    /**
     * Sets the {@link ControlPoint} associated with this pointer.
     * 
     * @param point the new {@link ControlPoint} to associate with this pointer
     */
    public void setRelatedControlPoint(ControlPoint point){
        relatedControlPoint = point;
    }
}
