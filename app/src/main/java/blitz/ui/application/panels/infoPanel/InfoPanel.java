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

/**
 * Represents the information panel containing various line panels for editing trajectory properties.
 * 
 * This panel aggregates multiple {@link AbstractLinePanel} instances, each responsible for
 * displaying and editing specific properties of a trajectory's control point. It organizes
 * the line panels vertically and manages their initialization and addition to the panel.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     InfoPanel infoPanel = new InfoPanel();
 *     mainContainer.add(infoPanel);
 * </pre>
 * </p>
 * 
 * @autor Valery
 */
public class InfoPanel extends JPanel{
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * List of line panels contained within this information panel.
     */
    private ArrayList<AbstractLinePanel> lines;

    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs an {@code InfoPanel} with configured background color and layout.
     * 
     * Initializes the panel's background color, sets a vertical box layout, and populates
     * the panel with various line panels responsible for editing different trajectory properties.
     */
    public InfoPanel(){
        setBackground(Config.INFO_PANEL_BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lines = new ArrayList<>();
        fillLines();
    }

    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Populates the information panel with various line panels.
     * 
     * Creates instances of different {@link AbstractLinePanel} subclasses and adds them to
     * the panel's layout. This method ensures that all necessary line panels are present
     * for editing trajectory properties.
     */
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
