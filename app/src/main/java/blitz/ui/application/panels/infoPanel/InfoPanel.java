package blitz.ui.application.panels.infoPanel;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import blitz.configs.Config;
import blitz.ui.application.panels.infoPanel.linePanels.AbstractLinePanel;
import blitz.ui.application.panels.infoPanel.linePanels.DistanceLine;
import blitz.ui.application.panels.infoPanel.linePanels.HelperEndLine;
import blitz.ui.application.panels.infoPanel.linePanels.HelperStartLine;
import blitz.ui.application.panels.infoPanel.linePanels.InterpolationTypeLine;
import blitz.ui.application.panels.infoPanel.linePanels.NumSegmentsLine;
import blitz.ui.application.panels.infoPanel.linePanels.PositionLine;
import blitz.ui.application.panels.infoPanel.linePanels.SplineTypeLine;
import blitz.ui.application.panels.infoPanel.linePanels.SymmetryLine;

public class InfoPanel extends JPanel{

    private ArrayList<AbstractLinePanel> lines;

    public InfoPanel(){
        setBackground(Config.INFO_PANEL_BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lines = new ArrayList<>();
        fillLines();
    }

    private void fillLines(){
        lines.add(new PositionLine());
        lines.add(new HelperStartLine());
        lines.add(new HelperEndLine());
        lines.add(new DistanceLine());
        lines.add(new NumSegmentsLine());
        lines.add(new SymmetryLine());
        lines.add(new SplineTypeLine());
        lines.add(new InterpolationTypeLine());

        for (AbstractLinePanel linePanel : lines) {
            add(linePanel);
        }
    }
    
}
