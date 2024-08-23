package blitz.ui.main.pointers;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;

public class FollowPointer extends Pointer{

    public FollowPointer(int x, int y, ControlPoint relatedCP){
        super(x, y, MainFrameConfig.DEFAULT_FOLLOW_POINTER_COLOR, MainFrameConfig.FOLLOW_POINTER_DIAMETER, relatedCP);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setColor(color);
        g2.fillOval(0, 0, diameter, diameter);
    }
    
}
