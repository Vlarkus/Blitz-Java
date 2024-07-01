package blitz.servises;

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



}
