package blitz.ui.main.pointers;

import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;

public class BezierPointer extends Pointer{

    public BezierPointer(int x, int y, ControlPoint relatedCP){
        super(x, y, MainFrameConfig.DEFAULT_BEZIER_POINTER_COLOR, MainFrameConfig.BEZIER_POINTER_DIAMETER, relatedCP);
    }
    
}
