package blitz.ui.main.pointers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import blitz.models.ControlPoint;

public abstract class Pointer extends JComponent{

    private int diameter;
    private Color color;
    private ControlPoint relatedControlPoint;

    public enum State{
        UNSELECTED,
        HIGHLIGHTED,
        SELECTED
    }
    
    public Pointer(int x, int y, Color color, int diameter, ControlPoint controlPoint){


        setDiameter(diameter);
        this.color = color;
        relatedControlPoint = controlPoint;
        setBounds(x, y, diameter, diameter);
        setCenterPosition(x, y);
        this.color = color;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(0, 0, diameter, diameter);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(diameter, diameter);
    }

    public boolean isWithinPointer(int x, int y) {
        int radius = diameter / 2;
        int centerX = this.getCenterX();
        int centerY = this.getCenterY();
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);

    }

    public int getCenterX(){
        return getX() + diameter/2;
    }

    public void setCenterX(int x){
        setCenterPosition(x, getY());
    }

    public int getCenterY(){
        return getY() + diameter/2;
    }

    public void setCenterY(int y){
        setCenterPosition(getX(), y);
    }

    public void setCenterPosition(int x, int y) {
        setBounds(x - diameter / 2, y - diameter / 2, diameter, diameter);
    }
    
    protected void setDiameter(int diameter){
        this.diameter = diameter;
        setCenterPosition(getX(), getY());
    }

    public int getDiameter(){
        return diameter;
    }

    protected void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public ControlPoint getRelatedControlPoint(){
        return relatedControlPoint;
    }

    public void setRelatedControlPoint(ControlPoint point){
        relatedControlPoint = point;
    }

}
