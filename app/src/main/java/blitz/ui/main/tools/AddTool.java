package blitz.ui.main.tools;

import blitz.configs.Config;
import blitz.ui.main.tools.Tool.Tools;

public class AddTool extends Tool{
    
    public AddTool(){
        super(Config.PATH_TO_ADD_TOOL_ICON, Tools.ADD);
    }

    @Override
    void performOnSelected() {
        
    }

}
