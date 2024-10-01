package blitz.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import blitz.configs.Config;
import blitz.ui.application.panels.canvasPanel.CanvasPanel;

/**
 * Represents a field image with scaling support based on zoom levels.
 * Handles reading the image from file, extracting dimensions, and updating its size based on zoom.
 * 
 * @author Valery
 */
public class FieldImage {

    // -=-=-=- FIELDS -=-=-=-

    private BufferedImage fieldImage;
    private BufferedImage initialFieldImage;
    private String path;
    private String fieldName;
    private int originalWidth, originalHeight;
    private int width, height;

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Initializes a FieldImage from the specified path, extracting the field name and dimensions from the file name.
     * 
     * @param path the file path of the field image
     * @throws IllegalArgumentException if the image file is not valid or dimensions are incorrect
     */
    public FieldImage(String path) throws IllegalArgumentException {

        File file = new File(path);
        this.path = path;

        try {
            this.initialFieldImage = ImageIO.read(file);
            if (this.initialFieldImage == null) {
                throw new IllegalArgumentException("Failed to read image from file.");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading the image file: " + e.getMessage());
        }

        try {
            String fileName = file.getName();
            int start, end;

            // Field Name
            start = 0;
            end = fileName.indexOf(Config.FIELD_IMAGE_DIMENSIONS_SUFFIX);
            if (end == -1) {
                throw new IllegalArgumentException("Invalid file name format: missing extension.");
            }
            this.fieldName = fileName.substring(start, end);

            // Dimensions String
            start = fileName.indexOf(Config.FIELD_IMAGE_DIMENSIONS_SUFFIX)
                        + Config.FIELD_IMAGE_DIMENSIONS_SUFFIX.length();
            if (start == -1) {
                throw new IllegalArgumentException("Invalid file name format: missing dimensions suffix.");
            }
            end = fileName.indexOf(Config.FIELD_IMAGE_EXTENSION);
            String dimensions = fileName.substring(start, end);

            // Width
            start = 0;
            end = dimensions.indexOf(Config.FIELD_IMAGE_DIMENSIONS_SEPARATOR);
            if (end == -1) {
                throw new IllegalArgumentException("Invalid dimensions format: missing width.");
            }
            this.originalWidth = Integer.parseInt(dimensions.substring(start, end));

            // Height
            start = end + 1;
            this.originalHeight = Integer.parseInt(dimensions.substring(start));

            // Initialize the scaled width and height
            updateWidth();
            updateHeight();

        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing the file name: " + e.getMessage());
        }

        // Additional checks to ensure fields are set correctly
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be null or empty.");
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be a positive integer.");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be a positive integer.");
        }
    }

    // -=-=-=- METHODS -=-=-=-

    // -=-=-=- GETTERS -=-=-=-

    /**
     * Updates the width of the field image based on the current zoom scale.
     */
    public void updateWidth() {
        this.width = (int) (this.originalWidth * Config.PIXELS_IN_ONE_INCH * CanvasPanel.getZoomScaleX());
    }

    /**
     * Updates the height of the field image based on the current zoom scale.
     */
    public void updateHeight() {
        this.height = (int) (this.originalHeight * Config.PIXELS_IN_ONE_INCH * CanvasPanel.getZoomScaleY());
    }

    /**
     * Updates the field image by resizing it according to the current width and height.
     * 
     * @throws IOException if the image cannot be resized
     */
    private void updateFieldImage() throws IOException {
        updateWidth();
        updateHeight();
        fieldImage = Utils.resizeImage(initialFieldImage, getWidth(), getHeight());
    }

    /**
     * Returns the buffered image after updating its size based on zoom scale.
     * 
     * @return the buffered image
     */
    public BufferedImage getBufferedImage() {
        try {
            updateFieldImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fieldImage;
    }

    /**
     * Returns the field name extracted from the file name.
     * 
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the current width of the field image.
     * 
     * @return the width of the field image
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the current height of the field image.
     * 
     * @return the height of the field image
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the file path of the field image.
     * 
     * @return the file path
     */
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "FieldImage [fieldName=" + fieldName + ", width=" + width + ", height=" + height + "]";
    }
}
