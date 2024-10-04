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
 * Represents the "Merge" tool within the tool panel of the application.
 * 
 * This tool is responsible for handling the merging of elements or entities
 * within the application's user interface. It extends the abstract {@link Tool}
 * class and initializes itself with the appropriate icon and identifier.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     MergeTool mergeTool = new MergeTool();
 *     toolPanel.addTool(mergeTool);
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class MergeTool extends Tool{
    
    /**
     * Constructs a {@code MergeTool} with the specified icon and identifier.
     * 
     * Initializes the tool with the icon path defined in {@link Config} and sets
     * its type to {@link Tools#MERGE}, indicating its functionality as a merging tool.
     */
    public MergeTool(){
        super(Config.PATH_TO_MERGE_TOOL_ICON, Tools.MERGE);
    }

    /**
     * Performs the merge action on the currently selected elements.
     * 
     * This method is invoked when the user activates the merge tool while certain elements
     * are selected in the application. The specific behavior should be implemented
     * to define how selected elements are merged based on the current selection context.
     * 
     * <strong>Note:</strong> This method is currently not implemented and should be
     * overridden with the desired merge functionality.
     */
    @Override
    void performOnSelected() {
        // TODO: Implement the merging logic based on the selected elements
    }
    
}
