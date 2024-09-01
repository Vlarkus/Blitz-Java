package blitz.ui.application.panels.toolPanel.tools;

import blitz.configs.Config;

public class RenderAllTool extends Tool{
    
    public RenderAllTool(){
        super(Config.PATH_TO_RENDER_ALL_TOOL_ICON, Tools.RENDER_ALL);
    }

    @Override
    void performOnSelected() {
    }
    
}
