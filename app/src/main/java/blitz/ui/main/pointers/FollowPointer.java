package blitz.ui.main.pointers;

import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;

public class FollowPointer extends Pointer{

    public FollowPointer(int x, int y, ControlPoint relatedCP){
        super(x, y, MainFrameConfig.DEFAULT_FOLLOW_POINTER_COLOR, MainFrameConfig.FOLLOW_POINTER_DIAMETER, relatedCP);
    }
    
}
