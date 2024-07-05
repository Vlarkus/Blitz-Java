package blitz.servises;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import blitz.configs.ServicesConfig;

public class FieldImage{

    private BufferedImage fieldImage;
    private String path;
    private String fieldName;
    private int width, height;

    /**
     * Constructs a FieldImage object and initializes its fields.
     *
     * @param path the path to the image file
     * @throws IllegalArgumentException if any field is not correctly initialized
     */
    public FieldImage(String path) throws IllegalArgumentException {

        File file = new File(path);
        this.path = path;

        try {
            this.fieldImage = ImageIO.read(file);
            if (this.fieldImage == null) {
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
            end = fileName.indexOf(ServicesConfig.FIELD_IMAGE_DIMENSIONS_SUFFIX);
            if (end == -1) {
                throw new IllegalArgumentException("Invalid file name format: missing extension.");
            }
            this.fieldName = fileName.substring(start, end);

            // Dimensions String
            start = fileName.indexOf(ServicesConfig.FIELD_IMAGE_DIMENSIONS_SUFFIX)
                        + ServicesConfig.FIELD_IMAGE_DIMENSIONS_SUFFIX.length();
            if (start == -1) {
                throw new IllegalArgumentException("Invalid file name format: missing dimensions suffix.");
            }
            end = fileName.indexOf(ServicesConfig.FIELD_IMAGE_EXTENSION);
            String dimensions = fileName.substring(start, end);

            // Width
            start = 0;
            end = dimensions.indexOf(ServicesConfig.FIELD_IMAGE_DIMENSIONS_SEPARATOR);
            if (end == -1) {
                throw new IllegalArgumentException("Invalid dimensions format: missing width.");
            }
            this.width = Integer.parseInt(dimensions.substring(start, end));

            // Height
            start = end + 1;
            this.height = Integer.parseInt(dimensions.substring(start));
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

    public BufferedImage getFieldImage() {
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
