package blitz.configs;

import java.awt.Dimension;
import java.awt.Font;

import blitz.servises.CartesianCoordinate;
import blitz.servises.HexColor;

public class MainFrameConfig {
 
    // MainFrame

    public static final String FRAME_TITLE = "Blitz";
    public static final Dimension MINIMUM_FRAME_DIMENSIONS = new Dimension(1000, 600);
    public static final Dimension DEFAULT_FRAME_DIMENSIONS = MINIMUM_FRAME_DIMENSIONS;

    public static final String SAVE_FILE_EXTENSION_TYPE = ".tf";
    public static final String EXPORT_FILE_EXTENSION_TYPE = ".txt";



    // MenuBar

    public static final String PATH_TO_FIELDS_DIRECTORY = "app/src/main/java/blitz/resources/images/fields";



    // ToolPanel

    public static final Dimension TOOL_PANEL_PREFERRED_DIMENSIONS = new Dimension(75, 0);
    public static final Dimension TOOL_DIMENSIONS = new Dimension(64, 64);

    public static final HexColor TOOL_PANEL_BACKGROUND_COLOR = new HexColor("#00ff00");
    public static final HexColor TOOL_PANEL_TOOLS_BACKGROUND_COLOR = new HexColor("#eeeeee");
    public static final HexColor TOOL_PANEL_OPTIONS_BACKGROUND_COLOR = new HexColor("#aaaaaa");
    public static final HexColor TOOL_PANEL_EXTRA_BACKGROUND_COLOR = new HexColor("#eeeeee");

    public static final String PATH_TO_ADD_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/add.png";
    public static final String PATH_TO_CUT_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/cut.png";
    public static final String PATH_TO_INSERT_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/insert.png";
    public static final String PATH_TO_EDIT_TIME_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/edit-time.png";
    public static final String PATH_TO_MERGE_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/merge.png";
    public static final String PATH_TO_MOVE_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/move.png";
    public static final String PATH_TO_PAN_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/move.png";
    public static final String PATH_TO_REMOVE_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/remove.png";
    public static final String PATH_TO_RENDER_ALL_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/render-all.png";
    public static final String PATH_TO_SHOW_ROBOT_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/show-robot.png";
    public static final String PATH_TO_SELECTED_TOOL_BACKGROUND_ICON = "app/src/main/java/blitz/resources/images/icons/selected-tool-background.png";
    public static final String PATH_TO_UNSELECTED_TOOL_BACKGROUND_ICON = "app/src/main/java/blitz/resources/images/icons/unselected-tool-background.png";
    
    public static final int RENDER_ALL_DELAY = 200;



    // sidePanel

    public static final Dimension SIDE_PANEL_PREFERRED_DIMENSIONS = new Dimension(350, 0);

    

    // CanvasPanel

    public static final Dimension CANVAS_PANEL_PREFERRED_DIMENSION = new Dimension(3000, 3000);
    public static final HexColor CANVAS_PANEL_BACKGROUND_COLOR = new HexColor("#666666");
    public static final CartesianCoordinate DEFAULT_OFFSET = new CartesianCoordinate(0, 0);
    public static final int CANVAS_PANEL_X_OFFSET = ((int) CANVAS_PANEL_PREFERRED_DIMENSION.getWidth()) / 2;
    public static final int CANVAS_PANEL_Y_OFFSET = ((int) CANVAS_PANEL_PREFERRED_DIMENSION.getHeight()) / 2;
    public static final String PATH_TO_ZOOM_IN_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/zoom-in.png";
    public static final String PATH_TO_ZOOM_OUT_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/zoom-out.png";
    public static final String PATH_TO_DEFAULT_FIELD = "app/src/main/java/blitz/resources/images/fields/High Stakes Field Setup - Match_size24x24.png";

    public static final int PIXELS_IN_ONE_INCH = 36;

    // Pointers

    // ControlPointer
    public static final HexColor UNSELECTED_CONTROL_POINTER_COLOR = new HexColor("#3441ed");
    public static final HexColor HIGHLIGHTED_CONTROL_POINTER_COLOR = new HexColor("#fcdf3a");
    public static final HexColor SELECTED_CONTROL_POINTER_COLOR = new HexColor("#43aef0");
    public  static final int CONTROL_POINTER_DIAMETER = 20;

    // BezierPointer
    public static final HexColor DEFAULT_BEZIER_POINTER_COLOR = new HexColor("#33ff1c");
    public static final HexColor HIGHLIGHTED_BEZIER_POINTER_COLOR = new HexColor("#fcdf3a");
    public  static final int BEZIER_POINTER_DIAMETER = 8;

    // HelperPointer
    public static final HexColor DEFAULT_HELPER_POINTER_COLOR = new HexColor("#f57e1d");
    public  static final int HELPER_POINTER_DIAMETER = 12;

    // HelperLine
    public static final float HELPER_LINE_THICKNESS = 3f;
    public static final HexColor HELPER_LINE_COLOR = new HexColor("f57e1d");



    // InfoPanel
    public static final Dimension INFO_PANEL_PREFERRED_DIMENSIONS = new Dimension( (int) SIDE_PANEL_PREFERRED_DIMENSIONS.getWidth(), 300);
    public static final HexColor INFO_PANEL_BACKGROUND_COLOR = new HexColor("#eeeeee");
    public static final Font INFO_PANEL_TITLE_LABEL_FONT = new Font("Verdana", Font.BOLD, 20);
    public static final Font INFO_PANEL_NORMAL_LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    public static final String INFO_PANEL_TEXT_FIELD_REGEX = "-?\\d{0,3}(\\.\\d{0,4})?";



   // SelectionPanel
    public static final Dimension SELECTION_PANEL_PREFERRED_DIMENSIONS = new Dimension((int) SIDE_PANEL_PREFERRED_DIMENSIONS.getWidth(), 300);
    public static final HexColor SELECTION_PANEL_BACKGROUND_COLOR = new HexColor("#999999");
    public static final Dimension SELECTION_PANEL_HEADER_PANEL_PREFERRED_DIMENSIONS = new Dimension((int) SELECTION_PANEL_PREFERRED_DIMENSIONS.getWidth(), 40);
    public static final Dimension SELECTION_PANEL_SELECTION_MENU_PREFERRED_DIMENSIONS = new Dimension((int) SELECTION_PANEL_PREFERRED_DIMENSIONS.getWidth(), 1000);
    public static final Dimension SELECTION_PANEL_OPTIONS_BAR_PREFERRED_DIMENSIONS = new Dimension((int) SELECTION_PANEL_PREFERRED_DIMENSIONS.getWidth(), 30);
    public static final Dimension SELECTION_PANEL_OPTIONS_BAR_OPTION_BUTTON_PREFERRED_DIMENSIONS = new Dimension(16, 16);
    public static final Dimension SELECTION_PANEL_OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS = new Dimension(8, 0);
    public static final HexColor SELECTION_PANEL_HEADER_PANEL_COLOR = new HexColor("#eeeeee");
    public static final HexColor SELECTION_PANEL_SELECTION_MENU_COLOR = new HexColor("#eeeeee");
    public static final HexColor SELECTION_PANEL_OPTIONS_BAR_COLOR = new HexColor("#dddddd");
    public static final Font SELECTION_PANEL_TITLE_LABEL_FONT = new Font("Verdana", Font.BOLD, 20);
    public static final Font SELECTION_PANEL_TRAJECTORY_LABEL_FONT = new Font("Verdana", Font.PLAIN, 14);
    public static final Font SELECTION_PANEL_CONTROL_POINT_LABEL_FONT = new Font("Verdana", Font.PLAIN, 14);

}
