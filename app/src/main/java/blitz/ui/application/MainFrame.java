package blitz.ui.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Taskbar;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import blitz.configs.Config;
import blitz.services.FieldImage;
import blitz.services.Utils;
import blitz.ui.application.menu.Export;
import blitz.ui.application.menu.Open;
import blitz.ui.application.menu.Save;
import blitz.ui.application.panels.canvasInteractionPanel.CanvasInteractionPanel;
import blitz.ui.application.panels.canvasPanel.CanvasPanel;
import blitz.ui.application.panels.infoPanel.InfoPanel;
import blitz.ui.application.panels.selectionPanel.SelectionPanel;
import blitz.ui.application.panels.toolPanel.ToolPanel;
import blitz.ui.application.panels.toolPanel.tools.Tool.Tools;


/**
 * Represents the main application frame that integrates all UI components.
 * 
 * This frame serves as the primary window of the application, containing menus,
 * tool panels, canvas panels, and other interactive components. It sets up the
 * layout, initializes panels, handles key bindings, and manages the overall
 * user interface interactions.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     public static void main(String[] args) {
 *         new MainFrame();
 *     }
 * </pre>
 * </p>
 * 
 * @autor Valery
 */
public class MainFrame extends JFrame {
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * The main container panel that holds all other panels.
     */
    private JPanel mainPanel;
    
    /**
     * Panel that contains various tools for user interaction.
     */
    private ToolPanel toolPanel;
    
    /**
     * Panel that represents the drawing canvas where users can interact.
     */
    private CanvasPanel canvasPanel;
    
    /**
     * Side panel that holds additional information and selection panels.
     */
    private JPanel sidePanel;
    
    /**
     * Center panel that holds the canvas and its interaction panel.
     */
    private JPanel centerPanel;
    
    /**
     * Panel that displays informational messages or data.
     */
    private InfoPanel infoPanel;
    
    /**
     * Panel that manages the selection of trajectories and control points.
     */
    private SelectionPanel selectionPanel;
    
    /**
     * Scroll pane that provides scrollable access to the canvas.
     */
    private JScrollPane scrollPane;
    
    /**
     * Menu bar that contains various menus like File, View, Preferences, and Help.
     */
    private JMenuBar menuBar;
    
    /**
     * List of field images used for changing the canvas background or field.
     */
    private ArrayList<FieldImage> fieldImages;

    // -=-=-=- CONSTRUCTOR -=-=-=-
    
    /**
     * Constructs the main application frame, setting up the menu bar, panels, and key bindings.
     * 
     * Initializes the frame with the specified title, constructs UI components, sets up
     * key bindings for quick tool access, and makes the frame visible.
     */
    public MainFrame(){
        super(Config.FRAME_TITLE);
        constructMenuBar();
        constructFrame();
        setupKeyBindings();
        requestFocusInWindow();
        toFront();
    }

    // -=-=-=- METHODS -=-=-=-
    
    /**
     * Sets up key bindings for tool selection and other shortcuts.
     * 
     * This method maps specific key strokes to actions that change the active tool or perform
     * other functionalities like zooming and exporting. It ensures that these shortcuts are
     * only active when the focus is not on text components to prevent interference with text input.
     */
    private void setupKeyBindings() {
        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();
    
        // Define a method to check if the focus is in a text field or similar component
        java.util.function.BooleanSupplier isFocusOnTextField = () -> {
            return getFocusOwner() instanceof JTextComponent;
        };
    
        // Key Bindings for selecting tools
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0), "moveTool");
        actionMap.put("moveTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.MOVE);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "addTool");
        actionMap.put("addTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.ADD);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "insertTool");
        actionMap.put("insertTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.INSERT);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "removeTool");
        actionMap.put("removeTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.REMOVE);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "cutTool");
        actionMap.put("cutTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.CUT);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "showRobotTool");
        actionMap.put("showRobotTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.SHOW_ROBOT);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "mergeTool");
        actionMap.put("mergeTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.MERGE);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "renderAllTool");
        actionMap.put("renderAllTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.RENDER_ALL);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "panTool");
        actionMap.put("panTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.PAN);
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0), "editTimeTool");
        actionMap.put("editTimeTool", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    toolPanel.setActiveTool(Tools.EDIT_TIME);
                }
            }
        });
    
        // Key Bindings for zooming
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "zoomIn");
        actionMap.put("zoomIn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    canvasPanel.zoomInMouse();
                }
            }
        });
    
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "zoomOut");
        actionMap.put("zoomOut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    canvasPanel.zoomOutMouse();
                }
            }
        });

        // Key Binding for creating a new trajectory
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0), "newTrajectory");
        actionMap.put("newTrajectory", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    selectionPanel.createEmptyTrajectory();
                }
            }
        });

        // Key Binding for deleting the active element
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.SHIFT_DOWN_MASK), "deleteActive");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.META_DOWN_MASK), "deleteActive");
        actionMap.put("deleteActive", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    selectionPanel.removeActiveElement();
                }
            }
        });

        // Key Binding for exporting
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.SHIFT_DOWN_MASK), "Export");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.META_DOWN_MASK), "Export");
        actionMap.put("Export", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFocusOnTextField.getAsBoolean()) {
                    Export.createExportDialogue();
                }
            }
        });
    }
    
    /**
     * Constructs the menu bar with File, View, Preferences, and Help menus.
     * 
     * This method sets up the menu bar at the top of the application frame, adding various
     * menu items and their associated action listeners for functionalities like Open, Save,
     * Export, changing fields, importing fields, and accessing preferences and help sections.
     */
    private void constructMenuBar() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // -=- File Menu -=-
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Open Menu Item
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener((ActionEvent e) -> { Open.open(); });
        fileMenu.add(openMenuItem);
        
        // Save As Menu Item
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener((ActionEvent e) -> { Save.saveAs(); });
        fileMenu.add(saveAsMenuItem);

        // Export Menu Item
        JMenuItem exportAsMenuItem = new JMenuItem("Export");
        exportAsMenuItem.addActionListener((ActionEvent e) -> {Export.createExportDialogue();});
        fileMenu.add(exportAsMenuItem);

        // -=- View Menu -=-
        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        // Change Field Submenu
        JMenu changeFieldMenu = new JMenu("Change Field");
        viewMenu.add(changeFieldMenu);

        fillChangeFieldMenu(changeFieldMenu);

        // Import Field Menu Item
        JMenuItem importFieldMenu = new JMenuItem("Import Field");
        viewMenu.add(importFieldMenu);

        // One-Time-Use Field Menu Item
        JMenuItem OneTimeUseFieldMenu = new JMenuItem("One-Time-Use Field");
        viewMenu.add(OneTimeUseFieldMenu);

        // -=- Preferences Menu -=-
        JMenu preferencesMenu = new JMenu("Preferences");
        menuBar.add(preferencesMenu);

        // Units Menu Item
        JMenuItem unitsMenuItem = new JMenuItem("Units");
        unitsMenuItem.addActionListener((ActionEvent e) -> {});
        preferencesMenu.add(unitsMenuItem);

        // Theme Menu Item
        JMenuItem themeMenuItem = new JMenuItem("Theme");
        themeMenuItem.addActionListener((ActionEvent e) -> {});
        preferencesMenu.add(themeMenuItem);

        // -=- Help Menu -=-
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        // Tutorials Menu Item
        JMenuItem tutorialsMenu = new JMenuItem("Tutorials");
        tutorialsMenu.addActionListener((ActionEvent e) -> {});
        helpMenu.add(tutorialsMenu);

        // About Menu Item
        JMenuItem aboutMenu = new JMenuItem("About");
        aboutMenu.addActionListener((ActionEvent e) -> {});
        helpMenu.add(aboutMenu);
    }

    /**
     * Fills the "Change Field" submenu with available field images.
     * 
     * This method searches for PNG images in the specified fields directory and adds each
     * as a menu item to the provided submenu. Selecting a menu item sets the corresponding
     * field image on the canvas.
     * 
     * @param changeFieldMenu the {@link JMenu} to populate with field options
     */
    private void fillChangeFieldMenu(JMenu changeFieldMenu) {
        fieldImages = Utils.searchForPngImages(Config.PATH_TO_FIELDS_DIRECTORY);

        for (FieldImage fieldImage : fieldImages) {
            JMenuItem matchFieldOption = new JMenuItem(fieldImage.getFieldName());
            matchFieldOption.addActionListener((ActionEvent e) -> {
                canvasPanel.setFieldImage(fieldImage.getPath());
            });
            changeFieldMenu.add(matchFieldOption);
        }
    }

    /**
     * Constructs the main frame, setting up panels and their layout.
     * 
     * This method initializes the main panel, tool panel, canvas panel, side panel,
     * and other UI components. It organizes these components within the frame using
     * appropriate layout managers and ensures that the application window is properly
     * sized and visible.
     */
    private void constructFrame() {
        // Constructing the Main Frame
        setTitle(Config.FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Config.DEFAULT_FRAME_DIMENSIONS);
        setMinimumSize(Config.MINIMUM_FRAME_DIMENSIONS);
        setResizable(true);
        setLocationRelativeTo(null);

        // Initializing Main Panel
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(Config.DEFAULT_FRAME_DIMENSIONS));
        mainPanel.setLayout(new BorderLayout());
        fillMainPanelWithContent();
        add(mainPanel);
        setAppIconOnMac();

        pack();
        setVisible(true);
    }

    /**
     * Fills the main panel with content, including tool panel, canvas panel, side panel, and info panels.
     * 
     * This method organizes the primary UI components within the main panel using a BorderLayout.
     * It sets up the tool panel on the west, the canvas in the center with interaction capabilities,
     * and the side panel on the east containing information and selection panels.
     */
    private void setAppIconOnMac(){
        if(Utils.isMac()){
            Image icon = null;
            try {
                icon = ImageIO.read(new File(Config.PATH_TO_APP_ICON));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Set the icon for the application
            if (icon != null) {
                Taskbar.getTaskbar().setIconImage(icon);
                this.setIconImage(icon);
            }
        }
    }

    private void fillMainPanelWithContent() {
        // Initialize and add the Tool Panel to the west
        toolPanel = new ToolPanel();
        mainPanel.add(toolPanel, BorderLayout.WEST);

        // Initialize the Canvas Panel and wrap it in a Scroll Pane
        canvasPanel = new CanvasPanel();
        scrollPane = new JScrollPane(canvasPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        canvasPanel.setScrollPane(scrollPane);

        // Set the initial view position of the viewport
        JViewport viewport = scrollPane.getViewport();
        viewport.setViewPosition(new Point((int) (Config.CANVAS_PANEL_PREFERRED_DIMENSIONS.width / 3), 
                                          (int) (Config.CANVAS_PANEL_PREFERRED_DIMENSIONS.height / 3)));

        // Initialize the Center Panel with the Canvas and Interaction Panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        CanvasInteractionPanel CanvasInteractionPanel = new CanvasInteractionPanel(canvasPanel);
        canvasPanel.setCanvasInteractionPanel(CanvasInteractionPanel);
        centerPanel.add(CanvasInteractionPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Initialize the Side Panel with Info and Selection Panels
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(Config.SIDE_PANEL_PREFERRED_DIMENSIONS);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        mainPanel.add(sidePanel, BorderLayout.EAST);

        // Add the Info Panel to the Side Panel
        infoPanel = new InfoPanel();
        sidePanel.add(infoPanel);

        // Add the Selection Panel to the Side Panel
        selectionPanel = new SelectionPanel();
        sidePanel.add(selectionPanel);
    }
}
