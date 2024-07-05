package blitz.ui.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;
import blitz.ui.main.tools.AddTool;
import blitz.ui.main.tools.CutTool;
import blitz.ui.main.tools.EditTimeTool;
import blitz.ui.main.tools.InsertTool;
import blitz.ui.main.tools.MergeTool;
import blitz.ui.main.tools.MoveTool;
import blitz.ui.main.tools.PanTool;
import blitz.ui.main.tools.RemoveTool;
import blitz.ui.main.tools.RenderAllTool;
import blitz.ui.main.tools.ShowRobotTool;
import blitz.ui.main.tools.Tool;

public class ToolPanel extends JPanel {

    private JPanel tools, options, extra;
    private ButtonGroup toolGroup;
    private GridBagConstraints gbc;
    private int toolYIndex;

    public ToolPanel() {

        setBackground(MainFrameConfig.TOOL_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.TOOL_PANEL_PREFFERED_DIMENSIONS);

        setLayout(new GridBagLayout());

        // Add these GridBagConstraints for the tools panel
        tools = new JPanel();
        tools.setLayout(new GridBagLayout());
        tools.setBackground(MainFrameConfig.TOOL_PANEL_TOOLS_BACKGROUND_COLOR);
        GridBagConstraints gbcTools = new GridBagConstraints();
        gbcTools.gridx = 0;
        gbcTools.gridy = 0;
        gbcTools.weightx = 1;
        gbcTools.weighty = 0;
        gbcTools.fill = GridBagConstraints.HORIZONTAL;
        add(tools, gbcTools);

        
        
        options = new JPanel();
        options.setBackground(MainFrameConfig.TOOL_PANEL_OPTIONS_BACKGROUND_COLOR);
        options.setLayout(new GridBagLayout());
        GridBagConstraints gbcOptions = new GridBagConstraints();
        gbcOptions.gridx = 0;
        gbcOptions.gridy = 1;
        gbcOptions.weightx = 1;
        gbcOptions.weighty = 0;
        gbcOptions.fill = GridBagConstraints.HORIZONTAL;
        add(options, gbcOptions);
        
        // Add these GridBagConstraints for the extra panel
        extra = new JPanel();
        extra.setBackground(MainFrameConfig.TOOL_PANEL_EXTRA_BACKGROUND_COLOR);
        extra.setLayout(new GridBagLayout());
        GridBagConstraints gbcExtra = new GridBagConstraints();
        gbcExtra.gridx = 0;
        gbcExtra.gridy = 2;
        gbcExtra.weightx = 1;
        gbcExtra.weighty = 1;
        gbcExtra.fill = GridBagConstraints.BOTH;
        add(extra, gbcExtra);
        // tools.setPreferredSize(MainFrameConfig.TOOL_DIMENSIONS);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        toolYIndex = 0;

        toolGroup = new ButtonGroup();

        addTool(new MoveTool());
        addTool(new AddTool());
        addTool(new InsertTool());
        addTool(new RemoveTool());
        addTool(new CutTool());
        addTool(new MergeTool());
        addTool(new ShowRobotTool());
        addTool(new RenderAllTool());
        addTool(new PanTool());
        addTool(new EditTimeTool());

    }

    private void addTool(Tool tool) {
        gbc.gridx = 0;
        gbc.gridy = toolYIndex++;
        gbc.fill = GridBagConstraints.NONE;
        tools.add(tool, gbc);
        toolGroup.add(tool);
    }
}
