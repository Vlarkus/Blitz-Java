package blitz.ui.main.pointers;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blitz.configs.Config;
import blitz.models.ControlPoint;
import blitz.services.Gradient;
import blitz.services.HexColor;

public class FollowPointer extends Pointer{

    private static Gradient gradient = new Gradient();

    static{
        gradient.addColorPoint(0.0, new HexColor("#FF0000")); // Red
        gradient.addColorPoint(0.75, new HexColor("#FFFF00")); // Yellow
        gradient.addColorPoint(1.0, new HexColor("#00FF00")); // Green
    }

    public FollowPointer(int x, int y, double speedColorCoeff, ControlPoint relatedCP){
        super(x, y, gradient.getColorAt(speedColorCoeff), Config.FOLLOW_POINTER_DIAMETER, relatedCP);
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
