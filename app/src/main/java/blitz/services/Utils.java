package blitz.services;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import blitz.configs.ServicesConfig;

public class Utils {
    

    /**
     * Converts Cartesian coordinates to Polar coordinates.
     * 
     * @param x the x-coordinate in Cartesian system
     * @param y the y-coordinate in Cartesian system
     * @return a PolarCoordinate object representing the polar coordinates in degrees (r, theta)
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
     * @return a CartesianCoordinate object representing the Cartesian coordinates (x, y)
     */
    public static CartesianCoordinate polarToCartesian(double r, double theta) {
        theta = Math.toRadians(theta);
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);
        return new CartesianCoordinate(x, y);
    }

    /**
     * Resizes a BufferedImage to the specified width and height.
     *
     * @param originalImage The original BufferedImage to resize.
     * @param targetWidth   The desired width of the resized image.
     * @param targetHeight  The desired height of the resized image.
     * @return A new BufferedImage resized to the specified dimensions.
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static ArrayList<FieldImage> searchForPngImages(String folderPath) {
        ArrayList<FieldImage> fieldImages = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(ServicesConfig.FIELD_IMAGE_EXTENSION)) {
                        try{
                            FieldImage image = new FieldImage(file.getPath());
                            fieldImages.add(image);
                        } catch(IllegalArgumentException e){}
                    }
                }
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }

        return fieldImages;
    }

    public static void requestFocusInWindowFor(JComponent c){
        Component window = SwingUtilities.getWindowAncestor(c);
                if (window != null) {
                    window.requestFocusInWindow();
                }
    }

    public static double normalizeAngle(double theta){
        theta = theta % 360;
        if (theta < 0) {
            theta += 360;
        }
        return theta;   
    }

}
