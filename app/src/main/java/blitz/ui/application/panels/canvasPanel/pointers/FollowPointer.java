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
import blitz.utils.Gradient;
import blitz.utils.HexColor;

/**
 * Represents a follow pointer that visually indicates a control point on the canvas.
 * 
 * This pointer changes its color based on a speed coefficient, transitioning through a gradient
 * from red to yellow to green. It is semi-transparent and can be customized in terms of size
 * and color intensity.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     ControlPoint controlPoint = new ControlPoint(...);
 *     double speedCoeff = 0.5; // Example speed coefficient
 *     FollowPointer followPointer = new FollowPointer(100, 150, speedCoeff, controlPoint);
 *     canvasPanel.add(followPointer);
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class FollowPointer extends Pointer{

    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * A gradient instance defining the color transitions based on speed coefficient.
     * This gradient is shared across all instances of {@code FollowPointer}.
     */
    private static Gradient gradient = new Gradient();

    static{
        gradient.addColorPoint(0.0, new HexColor("#FF0000")); // Red
        gradient.addColorPoint(0.75, new HexColor("#FFFF00")); // Yellow
        gradient.addColorPoint(1.0, new HexColor("#00FF00")); // Green
    }

    // -=-=-=- CONSTRUCTORS -=-=-=-=-

    /**
     * Constructs a {@code FollowPointer} with the specified position, speed coefficient, and associated control point.
     * 
     * The color of the pointer is determined based on the provided speed coefficient using a predefined gradient.
     * 
     * @param x               the x-coordinate of the pointer's center position
     * @param y               the y-coordinate of the pointer's center position
     * @param speedColorCoeff the speed coefficient determining the pointer's color (expected range: 0.0 to 1.0)
     * @param relatedCP       the {@link ControlPoint} associated with this pointer
     * @throws IllegalArgumentException if {@code speedColorCoeff} is outside the range [0.0, 1.0]
     * @throws NullPointerException     if {@code relatedCP} is {@code null}
     */
    public FollowPointer(int x, int y, double speedColorCoeff, ControlPoint relatedCP){
        super(x, y, gradient.getColorAt(speedColorCoeff), Config.FOLLOW_POINTER_DIAMETER, relatedCP);
        
        // Validate speedColorCoeff
        if (speedColorCoeff < 0.0 || speedColorCoeff > 1.0) {
            throw new IllegalArgumentException("speedColorCoeff must be between 0.0 and 1.0");
        }
        
        // Validate relatedCP
        if (relatedCP == null) {
            throw new NullPointerException("relatedCP cannot be null");
        }
    }

    // -=-=-=- METHODS -=-=-=-=-

    /**
     * Paints the follow pointer component, rendering it as a semi-transparent filled oval.
     * 
     * @param g the {@link Graphics} context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            // Set semi-transparent composite
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            // Set the color based on the speed coefficient
            g2.setColor(color);
            // Draw the filled oval representing the pointer
            g2.fillOval(0, 0, diameter, diameter);
        } finally {
            // Dispose the Graphics2D object to free resources
            g2.dispose();
        }
    }
    
}
