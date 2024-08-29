package blitz.models.calculations.interpolations;

import java.util.ArrayList;

import blitz.models.ControlPoint;
import blitz.models.FollowPoint;
import blitz.models.Trajectory;
import blitz.models.calculations.AbstractInterpolation;
import blitz.models.calculations.AbstractSpline;
import blitz.services.CartesianCoordinate;

public class EquidistantIntp extends AbstractInterpolation {

    @Override
    public ArrayList<FollowPoint> calculate(Trajectory tr, AbstractSpline splineObj) {

        ArrayList<FollowPoint> followPoints = new ArrayList<>();
        ArrayList<ControlPoint> controlPoints = tr.getAllControlPoints();
        double targetDistance = tr.getSpacing(); // Desired distance between FollowPoints

        if (controlPoints == null || controlPoints.size() < 2) {
            return followPoints; // Not enough points to interpolate
        }

        for (int i = 0; i < controlPoints.size() - 1; i++) {
            ControlPoint p0 = controlPoints.get(i);
            ControlPoint p1 = controlPoints.get(i + 1);
            double segmentLength = splineObj.getArcLength(p0, p1, 0, 1);

            double s = 0; // Accumulated distance along the segment
            double t = 0; // Start with t = 0

            int maxLoopIterations = 1000; // Maximum number of iterations to prevent infinite loop
            int iterationCount = 0;

            while (s < segmentLength - targetDistance) {
                
                // Find the parameter t for the next point using the Newton-Mixed method
                double tNext = findTForArcLength(p0, p1, s + targetDistance, t, splineObj);

                if (tNext >= 1.0) {
                    break; // Reached or exceeded the end of the segment
                }

                CartesianCoordinate nextPoint = splineObj.evaluate(p0, p1, tNext);
                followPoints.add(new FollowPoint(nextPoint, p1));

                // Ensure s is increasing
                double newS = splineObj.getArcLength(p0, p1, 0, tNext);

                s = newS; // Update the accumulated distance
                t = tNext; // Move to the next segment
            }

        }

        return followPoints;
    }

    private double findTForArcLength(ControlPoint p0, ControlPoint p1, double targetArcLength, double initialGuess, AbstractSpline splineObj) {
        double tMin = 0.0;
        double tMax = 1.0;
        double t = initialGuess;
        double epsilon = 1e-4; // Convergence tolerance
        int maxIterations = 100;
    
        for (int i = 0; i < maxIterations; i++) {
            double arcLengthAtT = splineObj.getArcLength(p0, p1, 0, t);
    
            // Ensure that t + epsilon does not exceed 1.0
            double tPlusEpsilon = Math.min(t + epsilon, 1.0);
            
            // Calculate the derivative (speed) considering boundary conditions
            double derivative = splineObj.evaluate(p0, p1, t).distanceTo(splineObj.evaluate(p0, p1, tPlusEpsilon)) / (tPlusEpsilon - t);
    
            if (Math.abs(arcLengthAtT - targetArcLength) < epsilon) {
                return t; // Converged
            }
    
            // Newton's update
            double tNew = t - (arcLengthAtT - targetArcLength) / derivative;
    
            // If Newton's method suggests a t out of bounds, use bisection
            if (tNew < tMin || tNew > tMax) {
                tNew = (tMin + tMax) / 2;
            }
    
            // Update the bounds and t for the next iteration
            if (splineObj.getArcLength(p0, p1, 0, tNew) > targetArcLength) {
                tMax = tNew;
            } else {
                tMin = tNew;
            }
    
            t = tNew;
        }
    
        return t; // Return the best estimate after maxIterations
    }
    
}
