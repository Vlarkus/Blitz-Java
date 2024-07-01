package blitz.servises;

import java.awt.Color;

/**
 * Allows Color to use HEX values.
 * 
 * @author Valery
 */
public class HexColor extends Color {

    /**
     * Converts HEX to RGB and creates color object.
     * 
     * @param hex
     */
    public HexColor(String hex) {
        super(hexToRGB(hex));
    }

    /**
     * Converts HEX to RGB.
     * 
     * @param hex           HEX color value
     * @return              RGB color value
     */
    private static int hexToRGB(String hex) {

        if (hex.charAt(0) == '#') {
            hex = hex.substring(1);
        }

        int rgb = Integer.parseInt(hex, 16);

        if (hex.length() == 6) {
            return rgb | 0xFF000000;
        } else {
            throw new IllegalArgumentException("Invalid HEX color format");
        }

    }
    
}

