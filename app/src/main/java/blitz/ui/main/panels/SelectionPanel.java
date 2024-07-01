package blitz.ui.main.panels;

import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;

public class SelectionPanel extends JPanel{
    
    public SelectionPanel(){
        setBackground(MainFrameConfig.SELECTION_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.SELECTION_PANEL_PREFFERED_DIMENSIONS);
    }

}
