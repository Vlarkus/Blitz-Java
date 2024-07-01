package blitz.ui.main.panels;

import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;

public class ToolPanel extends JPanel{

    public ToolPanel(){
        setBackground(MainFrameConfig.TOOL_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.TOOL_PANEL_PREFFERED_DIMENSIONS);
    }
    
}
