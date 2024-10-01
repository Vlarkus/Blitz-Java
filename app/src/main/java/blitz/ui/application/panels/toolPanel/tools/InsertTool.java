package blitz.ui.application.panels.toolPanel.tools;

import blitz.configs.Config;

/**
 * Represents the "Insert" tool within the tool panel of the application.
 * 
 * This tool is responsible for handling the insertion of new elements or entities
 * within the application's user interface. It extends the abstract {@link Tool}
 * class and initializes itself with the appropriate icon and identifier.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     InsertTool insertTool = new InsertTool();
 *     toolPanel.addTool(insertTool);
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class InsertTool extends Tool{
    
    /**
     * Constructs an {@code InsertTool} with the specified icon and identifier.
     * 
     * Initializes the tool with the icon path defined in {@link Config} and sets
     * its type to {@link Tools#INSERT}, indicating its functionality as an insertion tool.
     */
    public InsertTool(){
        super(Config.PATH_TO_INSERT_TOOL_ICON, Tools.INSERT);
    }

    /**
     * Performs the insert action on the currently selected elements.
     * 
     * This method is invoked when the user activates the insert tool while certain elements
     * are selected in the application. The specific behavior should be implemented
     * to define how new elements are inserted based on the current selection context.
     * 
     * <strong>Note:</strong> This method is currently not implemented and should be
     * overridden with the desired insert functionality.
     */
    @Override
    void performOnSelected() {
        // TODO: Implement the insertion logic based on the selected elements
    }
    
}
