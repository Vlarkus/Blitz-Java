package blitz.services;

import java.util.ArrayList;
import java.util.List;

public class Gradient {

    private static class ColorPoint {
        double position;
        HexColor color;

        ColorPoint(double position, HexColor color) {
            this.position = position;
            this.color = color;
        }
    }

    private final List<ColorPoint> colorPoints = new ArrayList<>();

    // Add a color to the gradient at a specific position (0.0 to 1.0)
    public void addColorPoint(double position, HexColor color) {
        if (position < 0.0 || position > 1.0) {
            throw new IllegalArgumentException("Position must be between 0 and 1.");
        }
        colorPoints.add(new ColorPoint(position, color));
        colorPoints.sort((cp1, cp2) -> Double.compare(cp1.position, cp2.position));
    }

    // Calculate the color at a specific value (0.0 to 1.0) on the gradient
    public HexColor getColorAt(double value) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("Value must be between 0 and 1.");
        }

        // Find two color points surrounding the value
        ColorPoint start = null, end = null;
        for (int i = 0; i < colorPoints.size() - 1; i++) {
            if (value >= colorPoints.get(i).position && value <= colorPoints.get(i + 1).position) {
                start = colorPoints.get(i);
                end = colorPoints.get(i + 1);
                break;
            }
        }

        // If value is exactly at the position of a color point, return that color
        if (start == null || end == null) {
            return colorPoints.get(colorPoints.size() - 1).color;
        }

        // Calculate the ratio between the two points
        double ratio = (value - start.position) / (end.position - start.position);

        // Interpolate between the two colors
        int startRGB = start.color.getRGB();
        int endRGB = end.color.getRGB();

        int red = (int) (((startRGB >> 16) & 0xFF) * (1 - ratio) + ((endRGB >> 16) & 0xFF) * ratio);
        int green = (int) (((startRGB >> 8) & 0xFF) * (1 - ratio) + ((endRGB >> 8) & 0xFF) * ratio);
        int blue = (int) ((startRGB & 0xFF) * (1 - ratio) + ((endRGB & 0xFF) * ratio));

        return new HexColor(String.format("#%02X%02X%02X", red, green, blue));
    }
    
}
