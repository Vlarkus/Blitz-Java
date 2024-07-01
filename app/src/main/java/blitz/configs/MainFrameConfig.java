package blitz.configs;

import java.awt.Dimension;

import blitz.servises.HexColor;

public class MainFrameConfig {
 
    // MainFrame

    public static final String FRAME_TITLE = "Blitz";
    public static final Dimension MINIMUM_FRAME_DIMENSIONS = new Dimension(1000, 600);
    public static final Dimension DEFAULT_FRAME_DIMENSIONS = MINIMUM_FRAME_DIMENSIONS;

    public static final String SAVE_FILE_EXTENSION_TYPE = ".tf";
    public static final String EXPORT_FILE_EXTENSION_TYPE = ".txt";



    // ToolPanel

    public static final Dimension TOOL_PANEL_PREFFERED_DIMENSIONS = new Dimension(75, 0);
    public static final HexColor TOOL_PANEL_BACKGROUND_COLOR = new HexColor("#00ff00");



    // sidePanel

    public static final Dimension SIDE_PANEL_PREFFERED_DIMENSIONS = new Dimension(350, 0);

    

    // CanvasPanel
    public static final Dimension CANVAS_PANEL_PREFFERED_DIMENSIONS = new Dimension(0, 0);
    public static final HexColor CANVAS_PANEL_BACKGROUND_COLOR = new HexColor("#ff0000");



    // InfoPanel
    public static final Dimension INFO_PANEL_PREFFERED_DIMENSIONS = new Dimension( (int) SIDE_PANEL_PREFFERED_DIMENSIONS.getWidth(), 300);
    public static final HexColor INFO_PANEL_BACKGROUND_COLOR = new HexColor("#0000ff");



    // SelectionPanel
    public static final Dimension SELECTION_PANEL_PREFFERED_DIMENSIONS = new Dimension( (int) SIDE_PANEL_PREFFERED_DIMENSIONS.getWidth(), 300);
    public static final HexColor SELECTION_PANEL_BACKGROUND_COLOR = new HexColor("#999999");

}
