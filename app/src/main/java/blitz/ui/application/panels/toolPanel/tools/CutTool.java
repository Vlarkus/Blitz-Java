package blitz.ui.application.panels.toolPanel.tools;

import blitz.configs.Config;

public class CutTool extends Tool{
    
    public CutTool(){
        super(Config.PATH_TO_CUT_TOOL_ICON, Tools.CUT);
    }

    @Override
    void performOnSelected() {
        
    }
    
}
