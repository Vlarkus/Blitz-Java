package blitz.models.trajectories.trajectoryComponents;

import blitz.services.CartesianCoordinate;

public class FollowPoint {

    private double x, y;
    private double speed;
    private ControlPoint relatedCP;

    public FollowPoint(double x, double y, double speed, ControlPoint relatedCP){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.relatedCP = relatedCP;
    }

    public FollowPoint(CartesianCoordinate c, double speed, ControlPoint relatedCP){
        this.x = c.getX();
        this.y = c.getY();
        this.speed = speed;
        this.relatedCP = relatedCP;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public CartesianCoordinate getPosition(){
        return new CartesianCoordinate(x, y);
    }

    public double getSpeed(){
        return speed;
    }

    public ControlPoint getRelatedControlPoint(){
        return relatedCP;
    }
    
}
