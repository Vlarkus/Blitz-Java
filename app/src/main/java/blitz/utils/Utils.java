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

package blitz.utils;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import blitz.configs.Config;

/**
 * Provides a collection of utility methods for coordinate conversions, image processing,
 * file searching, UI focus management, and angle normalization.
 * 
 * This class contains static methods and is not meant to be instantiated.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     // Convert Cartesian to Polar coordinates
 *     PolarCoordinate polar = Utils.cartesianToPolar(3.0, 4.0);
 *     
 *     // Resize an image
 *     BufferedImage originalImage = ImageIO.read(new File("path/to/image.png"));
 *     BufferedImage resizedImage = Utils.resizeImage(originalImage, 200, 200);
 *     
 *     // Search for PNG images in a directory
 *     ArrayList<FieldImage> images = Utils.searchForPngImages("path/to/folder");
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class Utils {

    // -=-=-=- METHODS -=-=-=-

    /**
     * Converts Cartesian coordinates to Polar coordinates.
     * 
     * @param x the x-coordinate in Cartesian system
     * @param y the y-coordinate in Cartesian system
     * @return a {@link PolarCoordinate} object representing the polar coordinates in degrees (r, theta)
     */
    public static PolarCoordinate cartesianToPolar(double x, double y) {
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.toDegrees(Math.atan2(y, x));
        return new PolarCoordinate(r, theta);
    }

    /**
     * Converts Polar coordinates to Cartesian coordinates.
     * 
     * @param r the radius in Polar system
     * @param theta the angle in degrees in Polar system
     * @return a {@link CartesianCoordinate} object representing the Cartesian coordinates (x, y)
     */
    public static CartesianCoordinate polarToCartesian(double r, double theta) {
        theta = Math.toRadians(theta);
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);
        return new CartesianCoordinate(x, y);
    }

    /**
     * Resizes a {@link BufferedImage} to the specified width and height.
     *
     * @param originalImage The original {@link BufferedImage} to resize.
     * @param targetWidth   The desired width of the resized image.
     * @param targetHeight  The desired height of the resized image.
     * @return A new {@link BufferedImage} resized to the specified dimensions.
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    /**
     * Searches for PNG images within the specified folder path.
     * 
     * @param folderPath the path to the folder where PNG images are searched
     * @return an {@link ArrayList} of {@link FieldImage} objects representing the found images
     */
    public static ArrayList<FieldImage> searchForPngImages(String folderPath) {
        ArrayList<FieldImage> fieldImages = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(Config.FIELD_IMAGE_SUFFIX)) {
                        try {
                            FieldImage image = new FieldImage(file.getPath());
                            fieldImages.add(image);
                        } catch (IllegalArgumentException e) {
                            // Log the exception or handle it as needed
                        }
                    }
                }
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }

        return fieldImages;
    }

    /**
     * Requests focus for the specified {@link JComponent} by focusing its parent window.
     * 
     * @param c the {@link JComponent} to request focus for
     */
    public static void requestFocusInWindowFor(JComponent c) {
        Component window = SwingUtilities.getWindowAncestor(c);
        if (window != null) {
            window.requestFocusInWindow();
        }
    }

    /**
     * Normalizes an angle to the range [0, 360) degrees.
     * 
     * @param theta the angle in degrees to normalize
     * @return the normalized angle in degrees
     */
    public static double normalizeAngle(double theta) {
        theta = theta % 360;
        if (theta < 0) {
            theta += 360;
        }
        return theta;
    }

    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

}
