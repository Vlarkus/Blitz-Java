package blitz.ui.main.tools;

import blitz.configs.MainFrameConfig;

public class RemoveTool extends Tool{
    
    public RemoveTool(){
        super(MainFrameConfig.PATH_TO_REMOVE_TOOL_ICON, Tools.REMOVE);
    }
    
    @Override
    void performOnSelected() {
        
    }

}
