package blitz.ui.application.panels.toolPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.Timer;

import blitz.configs.Config;
import blitz.ui.application.panels.toolPanel.tools.AddTool;
import blitz.ui.application.panels.toolPanel.tools.CutTool;
import blitz.ui.application.panels.toolPanel.tools.EditTimeTool;
import blitz.ui.application.panels.toolPanel.tools.InsertTool;
import blitz.ui.application.panels.toolPanel.tools.MoveTool;
import blitz.ui.application.panels.toolPanel.tools.PanTool;
import blitz.ui.application.panels.toolPanel.tools.RemoveTool;
import blitz.ui.application.panels.toolPanel.tools.ShowRobotTool;
import blitz.ui.application.panels.toolPanel.tools.Tool;
import blitz.ui.application.panels.toolPanel.tools.Tool.Tools;
import blitz.ui.application.panels.toolPanel.tools.ToolListener;

/**
 * Represents the tool panel that contains various tools for user interaction.
 * 
 * This panel organizes different tools such as Move, Add, Insert, Remove, Cut, Pan, etc.,
 * allowing users to perform actions within the application. It manages the selection
 * of tools and handles tool-related events through the {@link ToolListener} interface.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     ToolPanel toolPanel = new ToolPanel();
 *     mainContainer.add(toolPanel);
 * </pre>
 * </p>
 * 
 * @autor Valery
 */
public class ToolPanel extends JPanel implements ToolListener{

    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Panel that holds the primary tools.
     */
    private JPanel toolsPanel;
    
    /**
     * Panel that holds option-related tools or settings.
     */
    private JPanel optionsPanel;
    
    /**
     * Panel that holds additional tools or extras.
     */
    private JPanel extrasPanel;
    
    /**
     * Group to ensure only one tool is selected at a time.
     */
    private ButtonGroup toolGroup;
    
    /**
     * Constraints used for layout management within the tool panels.
     */
    private GridBagConstraints gbc;
    
    /**
     * List of all tools added to the tool panel.
     */
    private ArrayList<Tool> toolList;
    
    /**
     * Index to track the vertical position of tools in the layout.
     */
    private int toolYIndex;

    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code ToolPanel} with initialized panels and tools.
     * 
     * Sets up the layout, initializes the tools, and registers the tool listener.
     */
    public ToolPanel() {

        setBackground(Config.TOOL_PANEL_BACKGROUND_COLOR);
        setPreferredSize(Config.TOOL_PANEL_PREFERRED_DIMENSIONS);

        setLayout(new GridBagLayout());

        // Initialize and configure the toolsPanel
        toolsPanel = new JPanel();
        toolsPanel.setLayout(new GridBagLayout());
        toolsPanel.setBackground(Config.TOOL_PANEL_TOOLS_BACKGROUND_COLOR);
        GridBagConstraints gbcTools = new GridBagConstraints();
        gbcTools.gridx = 0;
        gbcTools.gridy = 0;
        gbcTools.weightx = 1;
        gbcTools.weighty = 0;
        gbcTools.fill = GridBagConstraints.HORIZONTAL;
        add(toolsPanel, gbcTools);

        // Initialize and configure the optionsPanel
        optionsPanel = new JPanel();
        optionsPanel.setBackground(Config.TOOL_PANEL_OPTIONS_BACKGROUND_COLOR);
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcOptions = new GridBagConstraints();
        gbcOptions.gridx = 0;
        gbcOptions.gridy = 1;
        gbcOptions.weightx = 1;
        gbcOptions.weighty = 0;
        gbcOptions.fill = GridBagConstraints.HORIZONTAL;
        add(optionsPanel, gbcOptions);
        
        // Initialize and configure the extrasPanel
        extrasPanel = new JPanel();
        extrasPanel.setBackground(Config.TOOL_PANEL_EXTRA_BACKGROUND_COLOR);
        extrasPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcExtra = new GridBagConstraints();
        gbcExtra.gridx = 0;
        gbcExtra.gridy = 2;
        gbcExtra.weightx = 1;
        gbcExtra.weighty = 1;
        gbcExtra.fill = GridBagConstraints.BOTH;
        add(extrasPanel, gbcExtra);
        // toolsPanel.setPreferredSize(MainFrameConfig.TOOL_DIMENSIONS);

        // Initialize GridBagConstraints for tool placement
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        toolYIndex = 0;

        // Initialize the ButtonGroup and tool list
        toolGroup = new ButtonGroup();
        toolList = new ArrayList<>();

        // Add tools to the tool panel
        addTool(new MoveTool());
        addTool(new AddTool());
        addTool(new InsertTool());
        addTool(new RemoveTool());
        addTool(new CutTool());
        addTool(new PanTool());

        // Register this panel as a listener for tool events
        Tool.addToolListener(this);

    }

    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Adds a tool to the tools panel and registers it within the tool group.
     * 
     * @param tool the {@link Tool} to add
     */
    private void addTool(Tool tool) {
        gbc.gridx = 0;
        gbc.gridy = toolYIndex++;
        gbc.fill = GridBagConstraints.NONE;
        toolsPanel.add(tool, gbc);
        toolGroup.add(tool);
        toolList.add(tool);
    }

    /**
     * Handles changes to the selected tool.
     * 
     * This method is invoked when the selected tool changes. It performs actions based
     * on the new selected tool, such as rendering all elements if the {@code RENDER_ALL}
     * tool is selected.
     * 
     * @param newSelectedTool the newly selected {@link Tools} enum value
     */
    @Override
    public void selectedToolChanged(Tools newSelectedTool) {
        switch (newSelectedTool) {
            case RENDER_ALL:
                Timer timer = new Timer(Config.RENDER_ALL_DELAY, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setActiveTool(Tool.getPreviousTool());
                    }
                });
                timer.setRepeats(false);
                timer.start();
                break;
        }

    }

    /**
     * Retrieves a tool based on its type.
     * 
     * @param t the {@link Tools} enum value representing the tool type
     * @return the corresponding {@link Tool} if found, {@code null} otherwise
     */
    private Tool getTool(Tools t){
        for (Tool tool : toolList) {
            if(tool.getTool() == t){
                return tool;
            }
        }
        return null;
    }

    /**
     * Sets the specified tool as active.
     * 
     * Clears the current selection and selects the tool corresponding to the provided type.
     * 
     * @param t the {@link Tools} enum value representing the tool to activate
     */
    public void setActiveTool(Tools t){
        toolGroup.clearSelection();
        Tool tool = getTool(t);
        if(tool != null){
            tool.setSelected(true);
        }
    }

    // Additional methods and logic can be added here as needed

}
