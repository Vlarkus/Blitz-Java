package blitz.ui.application.panels.toolPanel.tools;

import blitz.configs.Config;

public class RemoveTool extends Tool{
    
    public RemoveTool(){
        super(Config.PATH_TO_REMOVE_TOOL_ICON, Tools.REMOVE);
    }
    
    @Override
    void performOnSelected() {
        
    }

}
