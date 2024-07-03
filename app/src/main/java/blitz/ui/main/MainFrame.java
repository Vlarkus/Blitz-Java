package blitz.ui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;
import blitz.models.TrajectoriesList;
import blitz.models.Trajectory;
import blitz.ui.main.panels.CanvasPanel;
import blitz.ui.main.panels.InfoPanel;
import blitz.ui.main.panels.SelectionPanel;
import blitz.ui.main.panels.ToolPanel;

public class MainFrame extends JFrame{
    
    private JPanel mainPanel;
    private ToolPanel toolPanel;
    private CanvasPanel canvasPanel;
    private JPanel sidePanel;
    private InfoPanel infoPanel;
    private SelectionPanel SelectionPanel;
    
    private JMenuBar menuBar;

    // -=-=-=- CONSTRUCTOR -=-=-=-

    /**
     * Contstructs JFrame and creates working Trajectory.
     */
    public MainFrame(){
        super(MainFrameConfig.FRAME_TITLE);
        constructMenuBar();
        constructFrame();
        requestFocusInWindow();
        toFront();
        testing();// TODO: Delete this line.
    }


    private void testing(){
        Trajectory tr = new Trajectory("Trajectory 1");
        TrajectoriesList.addTrajectory(tr);

        tr.addControlPoint(new ControlPoint("CP1"));
        tr.addControlPoint(new ControlPoint("CP2", 200, 200));
        tr.addControlPoint(new ControlPoint("CP3", 300, 400, 100, 0, 50, 90));
        tr.addControlPoint(new ControlPoint("CP4", 500, 500, 90.0, -90));

        canvasPanel.setVisibleTrajectories(TrajectoriesList.getTrajectoriesList());
    }


    
    // -=-=-=- METHODS -=-=-=-

    private void constructMenuBar(){
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // -=- File Menu -=-
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Open
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener((ActionEvent e) -> {});
        fileMenu.add(openMenuItem);
        
        // Save As
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener((ActionEvent e) -> {});
        fileMenu.add(saveAsMenuItem);

        // Export
        JMenuItem exportAsMenuItem = new JMenuItem("Export");
        exportAsMenuItem.addActionListener((ActionEvent e) -> {});
        fileMenu.add(exportAsMenuItem);





        // -=- View Menu -=-
        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        // Change Field Options
        JMenu changeFieldMenu = new JMenu("Change Field");
        viewMenu.add(changeFieldMenu);

        JMenuItem matchFieldOption = new JMenuItem("Field Image Name.png");
        matchFieldOption.addActionListener((ActionEvent e) -> {});
        changeFieldMenu.add(matchFieldOption);
        
        // View Options
        JMenuItem viewSettingsMenu = new JMenuItem("View Options");
        viewMenu.add(viewSettingsMenu);
        viewSettingsMenu.addActionListener((ActionEvent e) -> {});

    }

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
