package blitz.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import blitz.configs.Config;
import blitz.ui.application.panels.canvasPanel.CanvasPanel;

public class FieldImage {

    private BufferedImage fieldImage;
    private BufferedImage initialFieldImage;
    private String path;
    private String fieldName;
    private int originalWidth, originalHeight;
    private int width, height;

    // Constructor
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

    // Method to update the width based on the current zoom scale
    public void updateWidth() {
        this.width = (int) (this.originalWidth * Config.PIXELS_IN_ONE_INCH * CanvasPanel.getZoomScaleX());
    }

    // Method to update the height based on the current zoom scale
    public void updateHeight() {
        this.height = (int) (this.originalHeight * Config.PIXELS_IN_ONE_INCH * CanvasPanel.getZoomScaleY());
    }

    private void updateFieldImage() throws IOException{
        updateWidth();
        updateHeight();
        fieldImage = Utils.resizeImage(initialFieldImage, getWidth(), getHeight());
    }

    // Getters
    public BufferedImage getBufferedImage() {
        try {
            updateFieldImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fieldImage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "FieldImage [fieldName=" + fieldName + ", width=" + width + ", height=" + height + "]";
    }
}
