package blitz.data;

import blitz.utils.PolarCoordinate;
import blitz.utils.Utils;

public class ControlPoint {
    
    // -=-=-=- FIELDS -=-=-=-

    // CP position on the field
    private double x, y;

    // Helpers relative position
    private double rStart, thetaStart, rEnd, thetaEnd;

    // Number of segments of this cuve, minimum look ahead distance
    private int numSegments;

    // Time the robot reaches this segment at
    private double time;



    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Instantiates Control Point.
     * 
     * @param x     x position of Control Point
     * @param y     y position of Control Point
     * @param rS    r of Start Helper Point
     * @param tS    theta of Start Helper Point
     * @param rE    r of End Control Point
     * @param tE    theta of End Control Point
     */
    public ControlPoint(double x, double  y, double rS, double tS, double rE, double tE) {

    }



    // -=-=-=- METHODS -=-=-=-

    // -=- Control Point -=-

    /**
     * Set x and y position of Control Point.
     * 
     * @param x
     * @param y
     */
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Set x position of Control Point.
     * 
     * @param x
     */
    public void setX(double x){
        setPosition(x, y);
    }

    /**
     * Set y position of Control Point.
     * 
     * @param y
     */
    public void setY(double y){
        setPosition(x, y);
    }

    /**
     * Returns x position of Control Point.
     * @return x position
     */
    public double getX(){
        return x;
    }

    /**
     * Returns y position of Control Point.
     * @return y position
     */
    public double getY(){
        return y;
    }

    // -=- Start Helper Point -=-
    
    /**
     * Set rStart
     * 
     * @param r
     */
    public void setRStart(double r){
        rStart = r;
    }

    /**
     * Set thetaStart.
     * @param theta
     */
    public void setThetaStart(double theta){
        thetaStart = theta;
    }

    /**
     * Set Start Helper Point position relative to Control Point.
     * 
     * @param x
     * @param y
     */
    public void setRelStartHelperPos(double x, double y){
        PolarCoordinate p = Utils.cartesianToPolar(x, y);
        rStart = p.getR();
        thetaStart = p.getTheta();
    }

    /**
     * Set Start Helper Point position relative to the field.
     * 
     * @param x
     * @param y
     */
    public void setAbsStartHelperPos(double x, double y){
        setRelStartHelperPos((x-this.x), (y-this.y));
    }

    // -=- End Helper Point -=-
    
    /**
     * Set rEnd
     * 
     * @param r
     */
    public void setREnd(double r){
        rEnd = r;
    }

    /**
     * Set thetaEnd.
     * @param theta
     */
    public void setThetaEnd(double theta){
        thetaEnd = theta;
    }

    /**
     * Set End Helper Point position relative to Control Point.
     * 
     * @param x
     * @param y
     */
    public void setRelEndHelperPos(double x, double y){
        PolarCoordinate p = Utils.cartesianToPolar(x, y);
        rEnd = p.getR();
        thetaEnd = p.getTheta();
    }

    /**
     * Set End Helper Point position relative to the field.
     * 
     * @param x
     * @param y
     */
    public void setAbsEndHelperPos(double x, double y){
        setRelStartHelperPos((x-this.x), (y-this.y));
    }

}
