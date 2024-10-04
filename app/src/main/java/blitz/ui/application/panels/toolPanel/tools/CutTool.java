/*
 * Copyright 2024 Valery Rabchanka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blitz.ui.application.panels.toolPanel.tools;

import blitz.configs.Config;

/**
 * Represents the "Cut" tool within the tool panel of the application.
 * 
 * This tool is responsible for handling the cutting (removal) of selected elements or entities
 * within the application's user interface. It extends the abstract {@link Tool} class and initializes
 * itself with the appropriate icon and identifier.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     CutTool cutTool = new CutTool();
 *     toolPanel.addTool(cutTool);
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class CutTool extends Tool{
    
    /**
     * Constructs a {@code CutTool} with the specified icon and identifier.
     * 
     * Initializes the tool with the icon path defined in {@link Config} and sets
     * its type to {@link Tools#CUT}, indicating its functionality as a cutting tool.
     */
    public CutTool(){
        super(Config.PATH_TO_CUT_TOOL_ICON, Tools.CUT);
    }

    /**
     * Performs the cut action on the currently selected elements.
     * 
     * This method is invoked when the user activates the cut tool while certain elements
     * are selected in the application. The specific behavior should be implemented
     * to define how selected elements are cut (removed or processed) based on the current selection context.
     * 
     * <strong>Note:</strong> This method is currently not implemented and should be
     * overridden with the desired cut functionality.
     */
    @Override
    void performOnSelected() {
        // TODO: Implement the cutting logic based on the selected elements
    }
    
}
