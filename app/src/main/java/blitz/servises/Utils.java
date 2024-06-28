package blitz.servises;

public class Utils {
    
    public static PolarCoordinate cartesianToPolar(double x, double y) {
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        return new PolarCoordinate(r, theta);
    }

    public static CartesianCoordinate polarToCartesian(double r, double theta) {
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);
        return new CartesianCoordinate(x, y);
    }

}
