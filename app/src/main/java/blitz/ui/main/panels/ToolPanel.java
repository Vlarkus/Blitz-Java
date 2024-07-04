package blitz.ui.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
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

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        tools = new JPanel();
        // tools.setPreferredSize(MainFrameConfig.TOOL_DIMENSIONS);
        tools.setLayout(new GridBagLayout());
        add(tools);

        options = new JPanel();
        options.setLayout(new GridBagLayout());
        add(options);

        extra = new JPanel();
        extra.setLayout(new GridBagLayout());
        add(extra);

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
