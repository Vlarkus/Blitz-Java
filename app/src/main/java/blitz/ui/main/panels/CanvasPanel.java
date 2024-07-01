package blitz.ui.main.panels;

import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;

public class CanvasPanel extends JPanel{

    public int xOffset, yOffset;

    public CanvasPanel(){
        setBackground(MainFrameConfig.CANVAS_PANEL_BACKGROUND_COLOR);
        setLayout(null);
        add(new ControlPointer(100, 100, null));
        add(new HelperPointer(150, 150, null, true));
        add(new BezierPointer(125, 125, null));
    }
    
}
