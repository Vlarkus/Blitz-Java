package blitz.services;

/**
 * Represents a point in a 2D polar coordinate system.
 * 
 * Instances of this class are immutable, meaning that once a {@code PolarCoordinate} object is created,
 * its state cannot be altered. This ensures thread safety and consistency when used across different
 * parts of the application.
 * 
 * Polar coordinates consist of a radial distance {@code r} and an angle {@code theta}.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     PolarCoordinate point = new PolarCoordinate(5.0, Math.PI / 4);
 *     System.out.println("Radius: " + point.getR()); // Outputs: Radius: 5.0
 *     System.out.println("Theta: " + point.getTheta()); // Outputs: Theta: 0.7853981633974483
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class PolarCoordinate {
    
    // -=-=-=- FIELDS -=-=-=-

    /**
     * The radial distance from the origin to the point.
     */
    private final double r;

    /**
     * The angle in radians from the positive x-axis to the point.
     */
    private final double theta;

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Constructs a {@code PolarCoordinate} with the specified radial distance and angle.
     * 
     * @param r     the radial distance from the origin (must be non-negative)
     * @param theta the angle in radians from the positive x-axis
     * @throws IllegalArgumentException if {@code r} is negative
     */
    public PolarCoordinate(double r, double theta) {
        if (r < 0) {
            throw new IllegalArgumentException("Radial distance (r) must be non-negative.");
        }
        this.r = r;
        this.theta = theta;
    }

    // -=-=-=- METHODS -=-=-=-

    /**
     * Returns the radial distance from the origin to the point.
     * 
     * @return the radial distance {@code r}
     */
    public double getR() {
        return r;
    }

    /**
     * Returns the angle in radians from the positive x-axis to the point.
     * 
     * @return the angle {@code theta} in radians
     */
    public double getTheta() {
        return theta;
    }

    /**
     * Converts this polar coordinate to its equivalent Cartesian coordinate.
     * 
     * @return a {@link CartesianCoordinate} representing the same point in Cartesian space
     */
    public CartesianCoordinate toCartesian() {
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);
        return new CartesianCoordinate(x, y);
    }

    /**
     * Calculates the distance between this polar coordinate and another polar coordinate.
     * 
     * @param other the other {@code PolarCoordinate} to calculate the distance to
     * @return the Euclidean distance between the two points
     * @throws IllegalArgumentException if {@code other} is {@code null}
     */
    public double distanceTo(PolarCoordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Other PolarCoordinate cannot be null.");
        }
        double deltaR = this.r - other.r;
        double deltaTheta = this.theta - other.theta;
        return Math.sqrt(deltaR * deltaR + 2 * this.r * other.r * (1 - Math.cos(deltaTheta)));
    }

    /**
     * Returns a string representation of this {@code PolarCoordinate}.
     * 
     * @return a string in the format {@code "PolarCoordinate[r=<r>, theta=<theta> radians]"}
     */
    @Override
    public String toString() {
        return String.format("PolarCoordinate[r=%.4f, theta=%.4f radians]", r, theta);
    }

    /**
     * Checks if this {@code PolarCoordinate} is equal to another object.
     * 
     * @param obj the object to compare with
     * @return {@code true} if {@code obj} is a {@code PolarCoordinate} with the same {@code r} and {@code theta}, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PolarCoordinate)) return false;
        PolarCoordinate other = (PolarCoordinate) obj;
        return Double.compare(this.r, other.r) == 0 &&
               Double.compare(this.theta, other.theta) == 0;
    }

    /**
     * Returns the hash code for this {@code PolarCoordinate}.
     * 
     * @return the hash code based on {@code r} and {@code theta}
     */
    @Override
    public int hashCode() {
        return Double.hashCode(r) * 31 + Double.hashCode(theta);
    }
}
