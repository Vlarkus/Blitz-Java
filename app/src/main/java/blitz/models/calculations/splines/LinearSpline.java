package blitz.models.calculations.splines;

import blitz.models.calculations.AbstractSpline;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.services.CartesianCoordinate;

/**
 * Represents a linear spline that interpolates points along a straight line 
 * between two control points.
 * 
 * This class provides methods to evaluate points along the line, calculate 
 * the arc length of the line segment, and calculate the bent rate (curvature), 
 * which is always zero for a linear spline.
 * 
 * This class extends {@link AbstractSpline}.
 * 
 * @author Valery
 */
public class LinearSpline extends AbstractSpline {

    /**
     * Evaluates a point on the linear spline between two control points at the given parameter t.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return the Cartesian coordinate of the point on the line at parameter t
     * @throws IllegalArgumentException if control points are null or t is out of bounds
     */
    @Override
    public CartesianCoordinate evaluate(ControlPoint p0, ControlPoint p1, double t) {
        if (p0 == null || p1 == null || t < 0 || t > 1) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        double x = (1 - t) * p0.getPosition().getX() + t * p1.getPosition().getX();
        double y = (1 - t) * p0.getPosition().getY() + t * p1.getPosition().getY();

        return new CartesianCoordinate(x, y);
    }

    /**
     * Calculates the arc length of the linear spline between two control points over the specified parameter range.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param tMin the minimum parameter (0 ≤ tMin ≤ tMax ≤ 1)
     * @param tMax the maximum parameter (0 ≤ tMin ≤ tMax ≤ 1)
     * @return the arc length of the line segment between tMin and tMax
     * @throws IllegalArgumentException if control points are null or tMin/tMax are out of bounds
     */
    @Override
    public double getArcLength(ControlPoint p0, ControlPoint p1, double tMin, double tMax) {
        
        if (p0 == null || p1 == null || tMin < 0 || tMax > 1 || tMin > tMax) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        if(tMin == tMax) {
            return 0;
        }

        double distance = p0.getPosition().distanceTo(p1.getPosition());
        return distance * (tMax - tMin);
    }

    /**
     * Calculates the bent rate (curvature) of the linear spline, which is always zero for a straight line.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return the bent rate, which is always 0 for a linear spline
     */
    @Override
    public double calculateBentRate(ControlPoint p0, ControlPoint p1, double t){
        return 0; // Linear splines have no curvature, so the bent rate is always zero.
    }

}
