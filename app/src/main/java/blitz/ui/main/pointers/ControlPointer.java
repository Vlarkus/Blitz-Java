package blitz.ui.main.pointers;

import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;

public class ControlPointer extends Pointer{

    private State state;

    public ControlPointer(int x, int y, ControlPoint relatedCP){
        super(x, y, MainFrameConfig.UNSELECTED_CONTROL_POINTER_COLOR, MainFrameConfig.CONTROL_POINTER_DIAMETER, relatedCP);
        setState(State.UNSELECTED);
    }

    public void setState(State state){
        this.state = state;
        switch (state) {
            case UNSELECTED:
                setColor(MainFrameConfig.UNSELECTED_CONTROL_POINTER_COLOR);
                break;

            case HIGHLIGHTED:
                setColor(MainFrameConfig.HIGHLIGHTED_CONTROL_POINTER_COLOR);
                break;

            case SELECTED:
                setColor(MainFrameConfig.SELECTED_CONTROL_POINTER_COLOR);
                break;
        }
    }

    public State getState(){
        return state;
    }
    
}
