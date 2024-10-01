package blitz.services;

/**
 * Represents a point in a 2D Cartesian coordinate system.
 * Instances of this class are immutable.
 * 
 * Provides basic functionality for getting coordinates and calculating the distance 
 * to another point.
 * 
 * @author Valery
 */
public class CartesianCoordinate {
    
    // -=-=-=- FIELDS -=-=-=-

    private final double x;
    private final double y;

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Initializes a CartesianCoordinate with the specified x and y values.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public CartesianCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // -=-=-=- METHODS -=-=-=-

    /**
     * Returns the x-coordinate of this point.
     * 
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this point.
     * 
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Calculates the distance from this point to another CartesianCoordinate.
     * 
     * @param other the other CartesianCoordinate
     * @return the Euclidean distance between this point and the other
     */
    public double distanceTo(CartesianCoordinate other) {
        double deltaX = this.x - other.getX();
        double deltaY = this.y - other.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
