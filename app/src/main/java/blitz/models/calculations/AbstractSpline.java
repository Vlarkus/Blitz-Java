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

package blitz.models.calculations;

import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.services.CartesianCoordinate;

/**
 * Abstract base class for spline calculations, providing methods for evaluating points 
 * on a curve, calculating arc length, and determining the bent rate (curvature).
 * 
 * This class also includes helper methods for calculating the first and second 
 * derivatives of the spline at a given point.
 * 
 * Subclasses should implement the {@link #evaluate(ControlPoint, ControlPoint, double)}, 
 * {@link #getArcLength(ControlPoint, ControlPoint, double, double)}, and 
 * {@link #calculateBentRate(ControlPoint, ControlPoint, double)} methods.
 * 
 * @see ControlPoint
 * @see CartesianCoordinate
 * 
 * @author Valery Rabchanka
 */
public abstract class AbstractSpline {
    
    /**
     * Evaluates the Cartesian coordinates of a point on the spline between two control points at the given parameter t.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return the Cartesian coordinate of the point on the curve at parameter t
     */
    public abstract CartesianCoordinate evaluate(ControlPoint p0, ControlPoint p1, double t);

    /**
     * Calculates the arc length of the spline between two control points over a specified range of t.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param tMin the minimum parameter (0 ≤ tMin ≤ tMax ≤ 1)
     * @param tMax the maximum parameter (0 ≤ tMin ≤ tMax ≤ 1)
     * @return the arc length of the curve segment between tMin and tMax
     */
    public abstract double getArcLength(ControlPoint p0, ControlPoint p1, double tMin, double tMax);

    /**
     * Calculates the bent rate (curvature) of the spline at the given parameter t.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return the bent rate (curvature) of the spline at parameter t
     */
    public abstract double calculateBentRate(ControlPoint p0, ControlPoint p1, double t);

    /**
     * Calculates the first derivative of the spline at a given parameter t using a small delta for numerical differentiation.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return an array containing the first derivative in x and y coordinates
     */
    protected double[] firstDerivative(ControlPoint p0, ControlPoint p1, double t) {
        double delta = 1e-5;
    
        // Clamp t + delta and t - delta to the valid range [0, 1]
        double tPlus = Math.min(1.0, t + delta);
        double tMinus = Math.max(0.0, t - delta);
    
        CartesianCoordinate pPlus = evaluate(p0, p1, tPlus);
        CartesianCoordinate pMinus = evaluate(p0, p1, tMinus);
    
        double dx = (pPlus.getX() - pMinus.getX()) / (tPlus - tMinus);
        double dy = (pPlus.getY() - pMinus.getY()) / (tPlus - tMinus);
    
        return new double[]{dx, dy};
    }

    /**
     * Calculates the second derivative of the spline at a given parameter t using a small delta for numerical differentiation.
     * 
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return an array containing the second derivative in x and y coordinates
     */
    protected double[] secondDerivative(ControlPoint p0, ControlPoint p1, double t) {
        double delta = 1e-5;
    
        // Clamp t + delta and t - delta to the valid range [0, 1]
        double tPlus = Math.min(1.0, t + delta);
        double tMinus = Math.max(0.0, t - delta);
    
        CartesianCoordinate pPlus = evaluate(p0, p1, tPlus);
        CartesianCoordinate p = evaluate(p0, p1, t);
        CartesianCoordinate pMinus = evaluate(p0, p1, tMinus);
    
        double d2x = (pPlus.getX() - 2 * p.getX() + pMinus.getX()) / ((tPlus - tMinus) * (tPlus - tMinus));
        double d2y = (pPlus.getY() - 2 * p.getY() + pMinus.getY()) / ((tPlus - tMinus) * (tPlus - tMinus));
    
        return new double[]{d2x, d2y};
    }
}
