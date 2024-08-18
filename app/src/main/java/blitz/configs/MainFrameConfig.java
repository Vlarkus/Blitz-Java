package blitz.configs;

import java.awt.Dimension;
import java.awt.Font;

import blitz.services.CartesianCoordinate;
import blitz.services.HexColor;

public class MainFrameConfig {
 
    // MainFrame

    public static final String FRAME_TITLE = "Blitz";
    public static final Dimension MINIMUM_FRAME_DIMENSIONS = new Dimension(1000, 600);
    public static final Dimension DEFAULT_FRAME_DIMENSIONS = MINIMUM_FRAME_DIMENSIONS;

    public static final String SAVE_FILE_EXTENSION_TYPE = ".btz";
    public static final String SAVE_FILE_EXTENSION_TYPE_DESCRIPTION = "BLITZ Save File";
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

    public static final String PATH_TO_ADD_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/add-tool.png";
    public static final String PATH_TO_CUT_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/cut-tool.png";
    public static final String PATH_TO_INSERT_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/insert-tool.png";
    public static final String PATH_TO_EDIT_TIME_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/edit-time-tool.png";
    public static final String PATH_TO_MERGE_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/merge-tool.png";
    public static final String PATH_TO_MOVE_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/move-tool.png";
    public static final String PATH_TO_PAN_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/move-tool.png";
    public static final String PATH_TO_REMOVE_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/remove-tool.png";
    public static final String PATH_TO_RENDER_ALL_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/render-all-tool.png";
    public static final String PATH_TO_SHOW_ROBOT_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/show-robot-tool.png";
    public static final String PATH_TO_SELECTED_TOOL_BACKGROUND_ICON = "app/src/main/java/blitz/resources/images/icons/tools/selected-tool-background.png";
    public static final String PATH_TO_UNSELECTED_TOOL_BACKGROUND_ICON = "app/src/main/java/blitz/resources/images/icons/tools/unselected-tool-background.png";
    
    public static final int RENDER_ALL_DELAY = 200;



    // sidePanel

    public static final Dimension SIDE_PANEL_PREFERRED_DIMENSIONS = new Dimension(350, 0);

    

    // CanvasPanel

    public static final Dimension CANVAS_PANEL_PREFERRED_DIMENSION = new Dimension(3000, 3000);
    public static final HexColor CANVAS_PANEL_BACKGROUND_COLOR = new HexColor("#666666");
    public static final CartesianCoordinate DEFAULT_OFFSET = new CartesianCoordinate(0, 0);
    public static final int CANVAS_PANEL_X_OFFSET = ((int) CANVAS_PANEL_PREFERRED_DIMENSION.getWidth()) / 2;
    public static final int CANVAS_PANEL_Y_OFFSET = ((int) CANVAS_PANEL_PREFERRED_DIMENSION.getHeight()) / 2;
    public static final String PATH_TO_ZOOM_IN_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/zoom-in.png";
    public static final String PATH_TO_ZOOM_OUT_TOOL_ICON = "app/src/main/java/blitz/resources/images/icons/tools/zoom-out.png";
    public static final String PATH_TO_DEFAULT_FIELD = "app/src/main/java/blitz/resources/images/fields/High Stakes Field Setup - Match_size24x24.png";

    public static final int PIXELS_IN_ONE_INCH = 36;
    public static final double MIN_ZOOM_SCALE_VALUE = 0.1;
    public static final double MAX_ZOOM_SCALE_VALUE = 20;

    public static final String PATH_TO_PLUS_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/plus.png";
    public static final String PATH_TO_SCISSORS_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/scissors.png";
    public static final String PATH_TO_MOVE_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/move.png";
    public static final String PATH_TO_HAND_OPEN_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/handopen.png";
    public static final String PATH_TO_HAND_GRABBING_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/handgrabbing.png";
    public static final String PATH_TO_HAND_POINTING_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/handpointing.png";
    public static final String PATH_TO_HELP_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/help.png";
    public static final String PATH_TO_MINUS_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/minus.png";
    public static final String PATH_TO_EYE_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/eye.png";
    public static final String PATH_TO_NOT_ALLOWED_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/notallowed.png";
    public static final String PATH_TO_UNKNOWN_CURSOR_IMAGE = "app/src/main/java/blitz/resources/images/cursors/unknown.png";

    

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
    public static final Dimension HEADER_PANEL_PREFERRED_DIMENSIONS = new Dimension((int) SELECTION_PANEL_PREFERRED_DIMENSIONS.getWidth(), 40);
    public static final Dimension SELECTION_MENU_PREFERRED_DIMENSIONS = new Dimension((int) SELECTION_PANEL_PREFERRED_DIMENSIONS.getWidth(), 1000);
    public static final Dimension OPTIONS_BAR_PREFERRED_DIMENSIONS = new Dimension((int) SELECTION_PANEL_PREFERRED_DIMENSIONS.getWidth(), 30);
    public static final Dimension OPTIONS_BAR_OPTION_BUTTON_PREFERRED_DIMENSIONS = new Dimension(16, 16);
    public static final Dimension OPTIONS_BAR_EMPTY_SPACE_PREFERRED_DIMENSIONS = new Dimension(8, 0);
    
    public static final HexColor SELECTION_PANEL_BACKGROUND_COLOR = new HexColor("#999999");
    public static final HexColor HEADER_PANEL_COLOR = new HexColor("#eeeeee");
    public static final HexColor SELECTION_MENU_COLOR = new HexColor("#eeeeee");
    public static final HexColor OPTIONS_BAR_COLOR = new HexColor("#dddddd");

    public static final Font SELECTION_PANEL_TITLE_LABEL_FONT = new Font("Verdana", Font.BOLD, 20);
    public static final Font SELECTION_PANEL_TRAJECTORY_LABEL_FONT = new Font("Verdana", Font.PLAIN, 14);
    public static final Font SELECTION_PANEL_CONTROL_POINT_LABEL_FONT = new Font("Verdana", Font.PLAIN, 14);

    public static final String PATH_TO_DELETE_OPTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/delete-option.png";
    public static final String PATH_TO_ADD_TRAJECTORY_OPTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/add-trajectory-option.png";

    public static final HexColor TRAJECTORY_LAYER_BACKGROUND_COLOR = new HexColor("#fefefe");
    public static final Dimension TRAJECTORY_LAYER_PREFERRED_DIMENSION = new Dimension((int)SELECTION_PANEL_PREFERRED_DIMENSIONS.getWidth(), 40);
    public static final Dimension TRAJECTORY_LAYER_FILLER_PANEL_PREFERRED_DIMENSION = new Dimension((int)TRAJECTORY_LAYER_PREFERRED_DIMENSION.getHeight(), (int)TRAJECTORY_LAYER_PREFERRED_DIMENSION.getHeight());
    public static final Dimension TRAJECTORY_LAYER_REGULAR_BUTTON_PREFERRED_DIMENSION = new Dimension(16, 16);
    public static final Dimension TRAJECTORY_LAYER_HALF_SIZE_BUTTON_PREFERRED_DIMENSION = new Dimension(10, 10);
    public static final Dimension TRAJECTORY_LAYER_NAME_ELEMENT_PREFERRED_DIMENSION = new Dimension(110, 16);
    public static final Dimension FILLER_BETWEEN_TRAJECTORY_LAYERS = new Dimension(0, 10);


    public static final HexColor CONTROL_POINT_LAYER_BACKGROUND_COLOR = new HexColor("#dedede");
    public static final Dimension CONTROL_POINT_LAYER_PREFERRED_DIMENSION = new Dimension((int)(TRAJECTORY_LAYER_PREFERRED_DIMENSION.getWidth() - TRAJECTORY_LAYER_PREFERRED_DIMENSION.getHeight()), (int)TRAJECTORY_LAYER_PREFERRED_DIMENSION.getHeight());

    public static final String PATH_TO_COLLAPSE_LAYERS_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/collapse-layers-selection-icon.png";
    public static final String PATH_TO_HIDDEN_LAYER_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/hidden-layer-selection-icon.png";
    public static final String PATH_TO_LOCKED_LAYER_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/locked-layer-selection-icon.png";
    public static final String PATH_TO_MOVE_DOWN_LAYER_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/move-down-layer-selection-icon.png";
    public static final String PATH_TO_MOVE_UP_LAYER_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/move-up-layer-selection-icon.png";
    public static final String PATH_TO_SELECTED_LAYER_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/selected-layer-selection-icon.png";
    public static final String PATH_TO_SHOWN_LAYER_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/shown-layer-selection-icon.png";
    public static final String PATH_TO_UNLOCKED_LAYER_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/unlocked-layer-selection-icon.png";
    public static final String PATH_TO_UNSELECTED_LAYER_SELECTION_ICON = "app/src/main/java/blitz/resources/images/icons/selection/unselected-layer-selection-icon.png";
    public static final String PATH_TO_UNCOLLAPSE_LAYERS_SELECTION_ICON = ""; // TODO: EMPTY!

}
