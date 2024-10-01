package blitz.services;

import java.awt.Color;

/**
 * Represents a color using a hexadecimal string.
 * 
 * This class extends {@link Color} to allow the creation of color objects using HEX values.
 * It ensures that the HEX string is properly formatted and converts it to RGB values.
 * 
 * Example usage:
 * <pre>
 *     HexColor red = new HexColor("#FF0000");
 * </pre>
 * 
 * @author Valery
 */
public class HexColor extends Color {

    // -=-=-=- FIELDS -=-=-=-
    // (No additional fields are defined as this class extends {@link Color})

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Constructs a {@code HexColor} object from a hexadecimal color string.
     * 
     * The hexadecimal string must be in the format {@code "#RRGGBB"}.
     * 
     * @param hex the hexadecimal color string (e.g., {@code "#FFFFFF"} for white)
     * @throws IllegalArgumentException if the HEX string is not in the correct format
     */
    public HexColor(String hex) {
        super(hexToRGB(hex));
    }

    // -=-=-=- METHODS -=-=-=-

    /**
     * Converts a hexadecimal color string to its corresponding RGB integer value.
     * 
     * The method expects the HEX string to be in the format {@code "RRGGBB"} or {@code "#RRGGBB"}.
     * It parses the HEX string and returns the RGB value with full opacity.
     * 
     * @param hex the hexadecimal color string
     * @return the RGB integer value representing the color with full opacity
     * @throws IllegalArgumentException if the HEX string is not in the correct format
     */
    private static int hexToRGB(String hex) {
        if (hex == null) {
            throw new IllegalArgumentException("HEX color string cannot be null.");
        }

        hex = hex.trim();

        // Remove the '#' character if present
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        // Validate HEX string length
        if (hex.length() != 6) {
            throw new IllegalArgumentException("Invalid HEX color format. Expected format is \"#RRGGBB\".");
        }

        // Parse HEX string to integer
        int rgb;
        try {
            rgb = Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid HEX color format. Contains non-hexadecimal characters.", e);
        }

        // Add full opacity to the RGB value
        return (0xFF << 24) | rgb;
    }

    /**
     * Returns the hexadecimal string representation of this color.
     * 
     * @return the HEX string in the format {@code "#RRGGBB"}
     */
    public String getHex() {
        return String.format("#%02X%02X%02X", getRed(), getGreen(), getBlue());
    }

    /**
     * Returns a string representation of this {@code HexColor}.
     * 
     * @return the HEX string representation of the color
     */
    @Override
    public String toString() {
        return getHex();
    }
}
