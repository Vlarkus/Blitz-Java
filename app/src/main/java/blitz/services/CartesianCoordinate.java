package blitz.services;

/**
 * CartesianCoordinate represents a single cartesian coordinate.
 * Is meant to be immutable.
 * 
 * @author Valery
 */
public class CartesianCoordinate {
    
    // -=-=-=- FIELDS -=-=-=-

    // Cartesian coordinates
    private double x, y;



    // -=-=-=- CONSTRUCTORS -=-=-=-

    public CartesianCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }



    // -=-=-=- METHODS -=-=-=-

    // Get x
    public double getX() {
        return x;
    }

    // Get y
    public double getY() {
        return y;
    } 

}
