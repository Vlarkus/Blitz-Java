package blitz.ui.application.panels.toolPanel.tools;

import blitz.configs.Config;

/**
 * Represents the "Pan" tool within the tool panel of the application.
 * 
 * This tool is responsible for handling panning actions within the application's user interface,
 * allowing users to navigate across different parts of the workspace by dragging or moving the view.
 * It extends the abstract {@link Tool} class and initializes itself with the appropriate icon and identifier.
 * 
 * <p>
 * The Pan tool is selected by default to provide immediate navigation capabilities upon application start.
 * </p>
 * 
 * <p>
 * Example usage:
 * <pre>
 *     PanTool panTool = new PanTool();
 *     toolPanel.addTool(panTool);
 * </pre>
 * </p>
 * 
 * @autor Valery
 */
public class PanTool extends Tool{
    
    /**
     * Constructs a {@code PanTool} with the specified icon and identifier.
     * 
     * Initializes the tool with the icon path defined in {@link Config} and sets
     * its type to {@link Tools#PAN}, indicating its functionality as a panning tool.
     * 
     * Additionally, the Pan tool is set to be selected by default to allow immediate navigation.
     */
    public PanTool(){
        super(Config.PATH_TO_PAN_TOOL_ICON, Tools.PAN);
        setSelected(true);
    }

    /**
     * Performs the pan action on the currently selected elements.
     * 
     * This method is invoked when the user activates the pan tool. It should handle the logic
     * required to enable panning behavior, such as tracking mouse movements and updating the view
     * accordingly.
     * 
     * <strong>Note:</strong> This method is currently not implemented and should be
     * overridden with the desired panning functionality.
     */
    @Override
    void performOnSelected() {
        // TODO: Implement the panning logic to navigate the workspace
    }
    
}
