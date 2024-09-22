package blitz.models.trajectories.trajectoryComponents;

import blitz.services.CartesianCoordinate;

/**
 * Represents a follow point on a trajectory with its position, speed, 
 * and the related control point.
 * 
 * Follow points are generated during interpolation and represent 
 * discrete points on the path for the robot to follow.
 * 
 * @author Valery
 */
public class FollowPoint {

    // -=-=-=- FIELDS -=-=-=-
    
    private double x, y;
    private double speed;
    private ControlPoint relatedCP;

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Constructs a FollowPoint with the specified x, y coordinates, speed, and related control point.
     * 
     * @param x         the x-coordinate of the follow point
     * @param y         the y-coordinate of the follow point
     * @param speed     the speed at this point
     * @param relatedCP the control point related to this follow point
     */
    public FollowPoint(double x, double y, double speed, ControlPoint relatedCP) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.relatedCP = relatedCP;
    }

    /**
     * Constructs a FollowPoint from a {@link CartesianCoordinate}, speed, and related control point.
     * 
     * @param c         the Cartesian coordinate of the follow point
     * @param speed     the speed at this point
     * @param relatedCP the control point related to this follow point
     */
    public FollowPoint(CartesianCoordinate c, double speed, ControlPoint relatedCP) {
        this.x = c.getX();
        this.y = c.getY();
        this.speed = speed;
        this.relatedCP = relatedCP;
    }

    // -=-=-=- METHODS -=-=-=-

    /**
     * Returns the x-coordinate of the follow point.
     * 
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the follow point.
     * 
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the position of the follow point as a {@link CartesianCoordinate}.
     * 
     * @return the Cartesian coordinate of the follow point
     */
    public CartesianCoordinate getPosition() {
        return new CartesianCoordinate(x, y);
    }

    /**
     * Returns the speed at this follow point.
     * 
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Returns the control point related to this follow point.
     * 
     * @return the related control point
     */
    public ControlPoint getRelatedControlPoint() {
        return relatedCP;
    }

}
