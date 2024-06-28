package blitz.servises;

/**
 * PolarCoordinate represents a single polar coordinate.
 * Is meant to be immutable.
 * 
 * @author Valery
 */
public class PolarCoordinate {
    
    // -=-=-=- FIELDS -=-=-=-

    // Polar coordinates
    private double r, theta;



    // -=-=-=- CONSTRUCTORS -=-=-=-

    public PolarCoordinate(double r, double theta) {
        this.r = r;
        this.theta = theta;
    }



    // -=-=-=- METHODS -=-=-=-

    // Get r
    public double getR() {
        return r;
    }

    // Get theta
    public double getTheta() {
        return theta;
    }
}
