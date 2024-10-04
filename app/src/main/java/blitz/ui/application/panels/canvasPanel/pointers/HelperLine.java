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
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import blitz.configs.Config;

/**
 * Represents a helper line component drawn on the canvas.
 * 
 * This class draws a semi-transparent line between two specified points (x1, y1) and (x2, y2)
 * with configurable thickness and color. The line is rendered within a dedicated {@link JComponent}
 * with appropriate padding to ensure full visibility.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     HelperLine helperLine = new HelperLine(50, 50, 150, 150);
 *     canvasPanel.add(helperLine);
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class HelperLine extends JComponent {

    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * The starting x-coordinate of the helper line.
     */
    private int x1, y1, x2, y2;

    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code HelperLine} with the specified start and end coordinates.
     * 
     * @param x1 the starting x-coordinate of the line
     * @param y1 the starting y-coordinate of the line
     * @param x2 the ending x-coordinate of the line
     * @param y2 the ending y-coordinate of the line
     */
    public HelperLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        setBoundsWithPadding();
    }

    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Sets the bounds of the component based on the line coordinates with added padding.
     * 
     * This ensures that the entire line is visible within the component's area.
     */
    private void setBoundsWithPadding() {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        // Add some padding to ensure the line is fully visible
        int padding = 10;
        setBounds(minX - padding, minY - padding, width + 2 * padding, height + 2 * padding);
    }

    /**
     * Paints the helper line onto the component.
     * 
     * @param g the {@link Graphics} context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            // Set stroke thickness based on configuration
            g2.setStroke(new BasicStroke(Config.HELPER_LINE_THICKNESS));
            // Set the color based on configuration
            g2.setColor(Config.HELPER_LINE_COLOR);
            // Set semi-transparent composite
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            // Draw the line relative to the component's position
            g2.drawLine(x1 - getX(), y1 - getY(), x2 - getX(), y2 - getY());
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
