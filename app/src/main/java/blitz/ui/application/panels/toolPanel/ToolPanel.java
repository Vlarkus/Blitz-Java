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

public class ToolPanel extends JPanel implements ToolListener{

    private JPanel toolsPanel, optionsPanel, extrasPanel;
    private ButtonGroup toolGroup;
    private GridBagConstraints gbc;
    private ArrayList<Tool> toolList;
    private int toolYIndex;

    public ToolPanel() {

        setBackground(Config.TOOL_PANEL_BACKGROUND_COLOR);
        setPreferredSize(Config.TOOL_PANEL_PREFERRED_DIMENSIONS);

        setLayout(new GridBagLayout());

        // Add these GridBagConstraints for the toolsPanel panel
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
        
        // Add these GridBagConstraints for the extrasPanel panel
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

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        toolYIndex = 0;

        toolGroup = new ButtonGroup();
        toolList = new ArrayList<>();

        addTool(new MoveTool());
        addTool(new AddTool());
        addTool(new InsertTool());
        addTool(new RemoveTool());
        addTool(new CutTool());
        addTool(new PanTool());

        Tool.addToolListener(this);

    }

    private void addTool(Tool tool) {
        gbc.gridx = 0;
        gbc.gridy = toolYIndex++;
        gbc.fill = GridBagConstraints.NONE;
        toolsPanel.add(tool, gbc);
        toolGroup.add(tool);
        toolList.add(tool);
    }

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

    private Tool getTool(Tools t){
        for (Tool tool : toolList) {
            if(tool.getTool() == t){
                return tool;
            }
        }
        return null;
    }

    public void setActiveTool(Tools t){
        toolGroup.clearSelection();
        getTool(t).setSelected(true);
    }

    

}
