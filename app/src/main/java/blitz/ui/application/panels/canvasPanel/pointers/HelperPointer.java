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

package blitz.ui.application.panels.canvasPanel.pointers;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blitz.configs.Config;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Represents a helper pointer on the canvas that can indicate the start or end of a trajectory.
 * 
 * This pointer changes its color based on its state and is associated with a {@link ControlPoint}.
 * It is semi-transparent and can be customized in terms of size and color intensity.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     ControlPoint controlPoint = new ControlPoint(...);
 *     HelperPointer helperPointer = new HelperPointer(100, 150, controlPoint, true);
 *     canvasPanel.add(helperPointer);
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class HelperPointer extends Pointer{
    
    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * Indicates whether this pointer marks the start of a trajectory.
     */
    private boolean isStart;

    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code HelperPointer} with the specified position, associated control point, and start indicator.
     * 
     * The pointer is initially set to a highlighted color as defined in the configuration.
     * 
     * @param x          the x-coordinate of the pointer's center position
     * @param y          the y-coordinate of the pointer's center position
     * @param relatedCP  the {@link ControlPoint} associated with this pointer
     * @param isStart    {@code true} if this pointer marks the start of a trajectory, {@code false} otherwise
     * @throws NullPointerException if {@code relatedCP} is {@code null}
     */
    public HelperPointer(int x, int y, ControlPoint relatedCP, boolean isStart){
        super(x, y, Config.HIGHLIGHTED_CONTROL_POINTER_COLOR, Config.HELPER_POINTER_DIAMETER, relatedCP);
        setIsStart(isStart);
    }

    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Checks if this pointer marks the start of a trajectory.
     * 
     * @return {@code true} if this pointer is a start pointer, {@code false} otherwise
     */
    public boolean isStart() {
        return isStart;
    }

    /**
     * Sets whether this pointer marks the start of a trajectory.
     * 
     * @param isStart {@code true} to mark as a start pointer, {@code false} otherwise
     */
    private void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    /**
     * Paints the helper pointer component, rendering it as a semi-transparent filled oval.
     * 
     * The appearance of the pointer changes based on its color, which reflects its current state.
     * 
     * @param g the {@link Graphics} context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            // Set semi-transparent composite
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            // Set the color based on the current state
            g2.setColor(color);
            // Draw the filled oval representing the pointer
            g2.fillOval(0, 0, diameter, diameter);
        } finally {
            // Dispose the Graphics2D object to free resources
            g2.dispose();
        }
    }
    
    // -=-=-=- INNER CLASSES -=-=-=-=-
    
    /**
     * (Optional) Inner classes or additional helper classes can be added here if needed.
     */
}
