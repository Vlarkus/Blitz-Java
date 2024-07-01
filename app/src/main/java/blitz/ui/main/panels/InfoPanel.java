package blitz.ui.main.panels;

import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;

public class InfoPanel extends JPanel{

    public InfoPanel(){
        setBackground(MainFrameConfig.INFO_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.INFO_PANEL_PREFFERED_DIMENSIONS);
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
    }
    
}
