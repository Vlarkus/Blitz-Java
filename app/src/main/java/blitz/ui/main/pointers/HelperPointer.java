package blitz.ui.main.pointers;

import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;

public class HelperPointer extends Pointer{

    private boolean isStart;

    public HelperPointer(int x, int y, ControlPoint relatedCP, boolean isStart){
        super(x, y, MainFrameConfig.HIGHLIGHTED_CONTROL_POINTER_COLOR, MainFrameConfig.HELPER_POINTER_DIAMETER, relatedCP);
        setIsStart(isStart);
    }

    public boolean isStart() {
        return isStart;
    }

    private void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

}
