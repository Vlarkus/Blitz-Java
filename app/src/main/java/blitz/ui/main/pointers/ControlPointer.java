package blitz.ui.main.pointers;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blitz.configs.Config;
import blitz.models.ControlPoint;

public class ControlPointer extends Pointer{

    private State state;

    public ControlPointer(int x, int y, ControlPoint relatedCP){
        super(x, y, Config.UNSELECTED_CONTROL_POINTER_COLOR, Config.CONTROL_POINTER_DIAMETER, relatedCP);
        setState(State.UNSELECTED);

    }

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

    public boolean isSelected(){
        return getState() == State.SELECTED;
    }

    public boolean isHighlighted(){
        return getState() == State.HIGHLIGHTED;
    }

    public State getState(){
        return state;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setColor(color);
        g2.fillOval(0, 0, diameter, diameter);
    }
    
}
