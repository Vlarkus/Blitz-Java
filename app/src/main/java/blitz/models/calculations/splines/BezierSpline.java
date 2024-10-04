/*
 * Copyright 2024 Valery Rabchanka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blitz.models.calculations.splines;

import blitz.models.calculations.AbstractSpline;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.services.CartesianCoordinate;

/**
 * Represents a Bezier spline that calculates the positions, arc length, 
 * and bent rate for a trajectory segment defined by two control points.
 * 
 * This class extends {@link AbstractSpline}.
 * 
 * <p>The Bezier spline uses cubic Bezier curves to interpolate between the 
 * control points, which includes the control points themselves and two helper 
 * points for adjusting the curve.</p>
 * 
 * @see AbstractSpline
 * @see ControlPoint
 * @see CartesianCoordinate
 * 
 * <p>This implementation provides methods to evaluate points along the curve, 
 * calculate the arc length of a segment, and calculate the bent rate (curvature) 
 * at a given point along the curve.</p>
 * 
 * <p>It assumes the curve is cubic, i.e., it uses four control points for 
 * interpolation: two actual control points and two helper points.</p>
 * 
 * @author Valery Rabchanka
 */
public class BezierSpline extends AbstractSpline {

    /**
     * Evaluates the Cartesian coordinates of a point on the Bezier curve at the given parameter t.
     * 
     * @param cpStart the starting control point
     * @param cpEnd the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return the Cartesian coordinate of the point on the curve at parameter t
     */
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

        // Calculate the x and y coordinates using cubic Bezier formula
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

    /**
     * Calculates the arc length of the Bezier curve between the two control points.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param tMin the start of the parameter range (0 ≤ tMin ≤ tMax ≤ 1)
     * @param tMax the end of the parameter range (0 ≤ tMin ≤ tMax ≤ 1)
     * @return the arc length of the curve segment between tMin and tMax
     * @throws IllegalArgumentException if the control points are null, or if tMin or tMax are out of bounds
     */
    @Override
    public double getArcLength(ControlPoint p0, ControlPoint p1, double tMin, double tMax) {

        if (p0 == null || p1 == null || tMin < 0 || tMax > 1 || tMin > tMax) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        if (tMin == tMax) {
            return 0;
        }

        double t = tMin;
        double step = 0.01;
        double arcLength = 0.0;

        CartesianCoordinate prevPoint = evaluate(p0, p1, tMin);
        
        // Numerical approximation of the arc length by sampling small segments along the curve
        while (t < tMax) {
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

    /**
     * Calculates the bent rate (curvature) of the Bezier curve at the given parameter t.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return the bent rate (curvature) of the curve at parameter t
     */
    @Override
    public double calculateBentRate(ControlPoint p0, ControlPoint p1, double t) {

        double[] firstDerivative = firstDerivative(p0, p1, t);
        double[] secondDerivative = secondDerivative(p0, p1, t);

        double xPrime = firstDerivative[0];
        double yPrime = firstDerivative[1];
        double xDoublePrime = secondDerivative[0];
        double yDoublePrime = secondDerivative[1];

        // Curvature formula: |x' * y'' - y' * x''| / (x'^2 + y'^2)^(3/2)
        double numerator = Math.abs(xPrime * yDoublePrime - yPrime * xDoublePrime);
        double denominator = Math.pow(xPrime * xPrime + yPrime * yPrime, 1.5);

        return numerator / denominator;
    }
    
}
