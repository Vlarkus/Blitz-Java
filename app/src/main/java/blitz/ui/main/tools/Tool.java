package blitz.ui.main.tools;

import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import blitz.configs.MainFrameConfig;

public abstract class Tool extends JRadioButton{
    
    protected static Tools selectedTool;

    public enum Tools{
        ADD,
        CUT,
        EDIT_TIME,
        INSERT,
        MERGE,
        MOVE,
        PAN,
        REMOVE,
        RENDER_ALL,
        SHOW_ROBOT,
    }

    protected ImageIcon frontIcon;
    protected ImageIcon backSelectedIcon;
    protected ImageIcon backUnselectedIcon;

    public Tool(String path, Tools tool) {
        super();

        try {
            // Load icons
            frontIcon = new ImageIcon(ImageIO.read(new File(path)));
            backSelectedIcon = new ImageIcon(ImageIO.read(new File(MainFrameConfig.PATH_TO_SELECTED_TOOL_BACKGROUND_ICON)));
            backUnselectedIcon = new ImageIcon(ImageIO.read(new File(MainFrameConfig.PATH_TO_UNSELECTED_TOOL_BACKGROUND_ICON)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(MainFrameConfig.TOOL_DIMENSIONS);

        addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedTool = tool;
                    repaint(); // Redraw when selected state changes
                }
            }
        });

        selectedTool = Tools.MOVE;
    }

    public static Tools getSelectedTool() {
        return selectedTool;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the back icon
        if (backSelectedIcon != null) {
            if(isSelected()){
                g.drawImage(backSelectedIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
            } else {
                g.drawImage(backUnselectedIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        }

        // Draw the front icon only when selected
        if (frontIcon != null) {
            g.drawImage(frontIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
    }

}
