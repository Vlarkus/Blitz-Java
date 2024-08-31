package blitz.models.calculations.splines;

import blitz.models.ControlPoint;
import blitz.models.calculations.AbstractSpline;
import blitz.services.CartesianCoordinate;

public class LinearSpline extends AbstractSpline {

    @Override
    public CartesianCoordinate evaluate(ControlPoint p0, ControlPoint p1, double t) {
        if (p0 == null || p1 == null || t < 0 || t > 1) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        double x = (1 - t) * p0.getPosition().getX() + t * p1.getPosition().getX();
        double y = (1 - t) * p0.getPosition().getY() + t * p1.getPosition().getY();

        return new CartesianCoordinate(x, y);
    }

    @Override
    public double getArcLength(ControlPoint p0, ControlPoint p1, double tMin, double tMax) {
        
        if (p0 == null || p1 == null || tMin < 0 || tMax > 1 || tMin > tMax) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        if(tMin == tMax){
            return 0;
        }

        double distance = p0.getPosition().distanceTo(p1.getPosition());
        return distance * (tMax - tMin);
    }

    @Override
    public double calculateBentRate(ControlPoint p0, ControlPoint p1, double t){
        return 0;
    }

}
