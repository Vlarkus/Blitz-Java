package blitz.ui.main.tools;

import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import blitz.configs.MainFrameConfig;

public abstract class Tool extends JRadioButton{

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
    
    protected static Tools selectedTool;

    public Tool(String path, Tools tool) {
        super();
        setPreferredSize(MainFrameConfig.TOOL_DIMENSIONS);
        try {
            setIcon(getScaledIcon(path, MainFrameConfig.TOOL_DIMENSIONS.width, MainFrameConfig.TOOL_DIMENSIONS.height));
        } catch (Exception e) {
            e.printStackTrace();
        }

        addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedTool = tool;
                }
            }

        });

        selectedTool = Tools.MOVE;
    }

    public static Tools getSelectedTool() {
        return selectedTool;
    }

    private static ImageIcon getScaledIcon(String path, int width, int height) {

        try {
            BufferedImage img = ImageIO.read(new File(path));
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }

}
