package blitz.ui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTreeUI;

import blitz.Main;
import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;
import blitz.ui.main.panels.*;

public class MainFrame extends JFrame{
    
    private JPanel mainPanel;
    private ToolPanel toolPanel;
    private CanvasPanel canvasPanel;
    private JPanel sidePanel;
    private InfoPanel infoPanel;
    private SelectionPanel SelectionPanel;



    // -=-=-=- CONSTRUCTOR -=-=-=-

    /**
     * Contstructs JFrame and creates working Trajectory.
     */
    public MainFrame(){
        constructFrame();
    }




    
    // -=-=-=- METHODS -=-=-=-

    /**
     * Contstructs JFrame and adds Panels onto it.
     */
    private void constructFrame(){

        // Constructing the Main Frame
        setTitle(MainFrameConfig.FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize( MainFrameConfig.DEFAULT_FRAME_DIMENSIONS);
        setMinimumSize(MainFrameConfig.MINIMUM_FRAME_DIMENSIONS);
        setResizable(true);
        setLocationRelativeTo(null);

        // Initializing Panels:
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(MainFrameConfig.DEFAULT_FRAME_DIMENSIONS));
        mainPanel.setLayout(new BorderLayout());
        fillMainPanelwithContent();
        add(mainPanel);

        pack();
        setVisible(true);

    }

    private void fillMainPanelwithContent(){

        toolPanel = new ToolPanel();
        mainPanel.add(toolPanel, BorderLayout.WEST);

        canvasPanel = new CanvasPanel();
        mainPanel.add(canvasPanel, BorderLayout.CENTER);

        sidePanel = new JPanel();
        sidePanel.setPreferredSize(MainFrameConfig.SIDE_PANEL_PREFFERED_DIMENSIONS);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        mainPanel.add(sidePanel, BorderLayout.EAST);

        infoPanel = new InfoPanel();
        sidePanel.add(infoPanel);

        SelectionPanel selectionPanel = new SelectionPanel();
        sidePanel.add(selectionPanel);

    }
    
}
