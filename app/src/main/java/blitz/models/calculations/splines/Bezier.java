package blitz.models.calculations.splines;

import blitz.models.ControlPoint;
import blitz.models.calculations.AbstractSpline;
import blitz.services.CartesianCoordinate;

public class Bezier extends  AbstractSpline{

    @Override
    public CartesianCoordinate evaluate(ControlPoint cpStart, ControlPoint cpEnd, double t) {

        if(cpStart == null) return null;
        if(cpEnd == null)   return null;
        if(t < 0)           return null;
        if(1 < t)           return null;

        CartesianCoordinate p0 = cpStart.getPosition();
        CartesianCoordinate p1 = cpStart.getAbsStartHelperPos(); // End helper point of p0
        CartesianCoordinate p2 = cpEnd.getAbsEndHelperPos(); // Start helper point of p1
        CartesianCoordinate p3 = cpEnd.getPosition();
    
        double x = Math.pow(1 - t, 3) * p0.getX() +
                   3 * Math.pow(1 - t, 2) * t * p1.getX() +
                   3 * (1 - t) * Math.pow(t, 2) * p2.getX() +
                   Math.pow(t, 3) * p3.getX();
    
        double y = Math.pow(1 - t, 3) * p0.getY() +
                   3 * Math.pow(1 - t, 2) * t * p1.getY() +
                   3 * (1 - t) * Math.pow(t, 2) * p2.getY() +
                   Math.pow(t, 3) * p3.getY();

        return new CartesianCoordinate(x, y);

    }

    @Override
    public double getArcLength(ControlPoint p0, ControlPoint p1, double tMin, double tMax) {

        if (p0 == null || p1 == null || tMin < 0 || tMax > 1 || tMin >= tMax) {
            return 0;
        }

        double t = tMin;
        double step = 0.005;
        double arcLength = 0.0;

        CartesianCoordinate prevPoint = evaluate(p0, p1, tMin);
        
        while(t <= tMax) {
            t += step;
            CartesianCoordinate currPoint = evaluate(p0, p1, t);
            arcLength += prevPoint.distanceTo(currPoint);
            prevPoint = currPoint;
        }

        return arcLength;
    }
    
}
