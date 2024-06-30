package blitz.models;

import blitz.servises.CartesianCoordinate;
import blitz.servises.PolarCoordinate;
import blitz.servises.Utils;

public class ControlPoint {
    
    // -=-=-=- FIELDS -=-=-=-

    private String name;

    // Control Point position on the field
    private double x, y;

    // Helpers relative position
    private double rStart, thetaStart, rEnd, thetaEnd;

    // Number of segments of this curve, minimum look ahead distance
    private int numSegments;

    // Time the robot reaches this segment at
    private double time;

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Instantiates Control Point with specified parameters.
     * 
     * @param name  name of Control Point
     * @param x     x position of Control Point
     * @param y     y position of Control Point
     * @param rS    r of Start Helper Point
     * @param tS    theta of Start Helper Point
     * @param rE    r of End Helper Point
     * @param tE    theta of End Helper Point
     */
    public ControlPoint(String name, double x, double y, double rS, double tS, double rE, double tE) {
        setName(name);
        setPosition(x, y);
        setRStart(rS);
        setThetaStart(tS);
        setREnd(rE);
        setThetaEnd(tE);
    }

    // -=-=-=- METHODS -=-=-=-

    // -=- Control Point -=-

    /**
     * Sets the name of the Control Point.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the Control Point.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the x and y position of the Control Point.
     * 
     * @param x x position
     * @param y y position
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x position of the Control Point.
     * 
     * @param x x position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y position of the Control Point.
     * 
     * @param y y position
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns the x position of the Control Point.
     * 
     * @return x position
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y position of the Control Point.
     * 
     * @return y position
     */
    public double getY() {
        return y;
    }

    // -=- Start Helper Point -=-

    /**
     * Sets the radial distance of the Start Helper Point from the Control Point.
     * 
     * @param r radial distance
     */
    public void setRStart(double r) {
        this.rStart = r;
    }

    /**
     * Sets the angular position of the Start Helper Point from the Control Point.
     * 
     * @param theta angular position
     */
    public void setThetaStart(double theta) {
        this.thetaStart = theta;
    }

    /**
     * Sets the Start Helper Point position relative to the Control Point.
     * 
     * @param x x position relative to Control Point
     * @param y y position relative to Control Point
     */
    public void setRelStartHelperPos(double x, double y) {
        PolarCoordinate p = Utils.cartesianToPolar(x, y);
        this.rStart = p.getR();
        this.thetaStart = p.getTheta();
    }

    /**
     * Sets the Start Helper Point position relative to the field.
     * 
     * @param x absolute x position on the field
     * @param y absolute y position on the field
     */
    public void setAbsStartHelperPos(double x, double y) {
        setRelStartHelperPos((x - this.x), (y - this.y));
    }

    /**
     * Returns the relative position of the Start Helper Point in Cartesian coordinates.
     * 
     * @return relative Cartesian coordinates of the Start Helper Point
     */
    public CartesianCoordinate getRelStartHelperPos() {
        return Utils.polarToCartesian(rStart, thetaStart);
    }

    /**
     * Returns the absolute position of the Start Helper Point in Cartesian coordinates.
     * 
     * @return absolute Cartesian coordinates of the Start Helper Point
     */
    public CartesianCoordinate getAbsStartHelperPos() {
        CartesianCoordinate relStart = getRelStartHelperPos();
        double absX = this.x + relStart.getX();
        double absY = this.y + relStart.getY();
        return new CartesianCoordinate(absX, absY);
    }

    // -=- End Helper Point -=-

    /**
     * Sets the radial distance of the End Helper Point from the Control Point.
     * 
     * @param r radial distance
     */
    public void setREnd(double r) {
        this.rEnd = r;
    }

    /**
     * Sets the angular position of the End Helper Point from the Control Point.
     * 
     * @param theta angular position
     */
    public void setThetaEnd(double theta) {
        this.thetaEnd = theta;
    }

    /**
     * Sets the End Helper Point position relative to the Control Point.
     * 
     * @param x x position relative to Control Point
     * @param y y position relative to Control Point
     */
    public void setRelEndHelperPos(double x, double y) {
        PolarCoordinate p = Utils.cartesianToPolar(x, y);
        this.rEnd = p.getR();
        this.thetaEnd = p.getTheta();
    }

    /**
     * Sets the End Helper Point position relative to the field.
     * 
     * @param x absolute x position on the field
     * @param y absolute y position on the field
     */
    public void setAbsEndHelperPos(double x, double y) {
        setRelEndHelperPos((x - this.x), (y - this.y));
    }

    /**
     * Returns the relative position of the End Helper Point in Cartesian coordinates.
     * 
     * @return relative Cartesian coordinates of the End Helper Point
     */
    public CartesianCoordinate getRelEndHelperPos() {
        return Utils.polarToCartesian(rEnd, thetaEnd);
    }

    /**
     * Returns the absolute position of the End Helper Point in Cartesian coordinates.
     * 
     * @return absolute Cartesian coordinates of the End Helper Point
     */
    public CartesianCoordinate getAbsEndHelperPos() {
        CartesianCoordinate relEnd = getRelEndHelperPos();
        double absX = this.x + relEnd.getX();
        double absY = this.y + relEnd.getY();
        return new CartesianCoordinate(absX, absY);
    }
}
