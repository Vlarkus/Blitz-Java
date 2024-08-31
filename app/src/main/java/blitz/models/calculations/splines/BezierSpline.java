package blitz.models.calculations.splines;

import blitz.models.ControlPoint;
import blitz.models.calculations.AbstractSpline;
import blitz.services.CartesianCoordinate;

public class BezierSpline extends  AbstractSpline{

    @Override
    public CartesianCoordinate evaluate(ControlPoint cpStart, ControlPoint cpEnd, double t) {
        if (cpStart == null || cpEnd == null) {
            System.err.println("Control points cannot be null.");
            return null;
        }
        if (t < 0 || t > 1) {
            System.err.println("Parameter t must be between 0 and 1.");
            return null;
        }

        CartesianCoordinate p0 = cpStart.getPosition();
        CartesianCoordinate p1 = cpStart.getAbsStartHelperPos(); 
        CartesianCoordinate p2 = cpEnd.getAbsEndHelperPos(); 
        CartesianCoordinate p3 = cpEnd.getPosition();

        if (p0 == null || p1 == null || p2 == null || p3 == null) {
            System.err.println("One or more control points are null.");
            return null;
        }

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

        if (p0 == null || p1 == null || tMin < 0 || tMax > 1 || tMin > tMax) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        if(tMin == tMax){
            return 0;
        }

        double t = tMin;
        double step = 0.01;
        double arcLength = 0.0;

        CartesianCoordinate prevPoint = evaluate(p0, p1, tMin);
        
        while(t < tMax) {
            t += step;
            if (t > tMax) t = tMax;

            CartesianCoordinate currPoint = evaluate(p0, p1, t);
            if (currPoint == null) {
                System.err.println("currPoint is null at t: " + t);
                break;
            }
            arcLength += prevPoint.distanceTo(currPoint);
            prevPoint = currPoint;
        }

        return arcLength;
    }


    @Override
    public double calculateBentRate(ControlPoint p0, ControlPoint p1, double t) {

        double[] firstDerivative = firstDerivative(p0, p1, t);
        double[] secondDerivative = secondDerivative(p0, p1, t);

        double xPrime = firstDerivative[0];
        double yPrime = firstDerivative[1];
        double xDoublePrime = secondDerivative[0];
        double yDoublePrime = secondDerivative[1];

        double numerator = Math.abs(xPrime * yDoublePrime - yPrime * xDoublePrime);
        double denominator = Math.pow(xPrime * xPrime + yPrime * yPrime, 1.5);

        return numerator / denominator;

    }
    
}
