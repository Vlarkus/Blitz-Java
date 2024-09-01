package blitz.ui.main.tools;

import blitz.configs.Config;

public class PanTool extends Tool{
    
    public PanTool(){
        super(Config.PATH_TO_PAN_TOOL_ICON, Tools.PAN);
        setSelected(true);
    }

    @Override
    void performOnSelected() {
        
    }
    
}
