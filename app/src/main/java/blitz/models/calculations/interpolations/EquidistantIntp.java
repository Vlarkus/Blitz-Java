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

package blitz.models.calculations.interpolations;

import java.util.ArrayList;

import blitz.models.calculations.AbstractInterpolation;
import blitz.models.calculations.AbstractSpline;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.models.trajectories.trajectoryComponents.FollowPoint;
import blitz.services.CartesianCoordinate;

/**
 * Equidistant interpolation algorithm that calculates a set of equidistant
 * follow points along the trajectory. It ensures that the points are evenly
 * spaced based on the arc length between control points while adjusting for
 * speed changes based on curve bent rates and trajectory spacing.
 * 
 * This class extends {@link AbstractInterpolation}.
 * 
 * @author Valery Rabchanka
 */
public class EquidistantIntp extends AbstractInterpolation {

    /**
     * Calculates a list of follow points for the given trajectory based on the
     * equidistant interpolation algorithm.
     * 
     * @param tr the trajectory for which to calculate follow points
     * @param splineObj the spline object representing the curve between control points
     * @return an {@link ArrayList} of {@link FollowPoint} objects representing the equidistant points along the trajectory
     */
    @Override
public ArrayList<FollowPoint> calculate(Trajectory tr, AbstractSpline splineObj) {

    this.splineObj = splineObj;

    double minSpeed = tr.getMinSpeed();
    double maxSpeed = tr.getMaxSpeed();
    double minBentRate = tr.getMinBentRate();
    double maxBentRate = tr.getMaxBentRate();

    ArrayList<FollowPoint> followPoints = new ArrayList<>();
    ArrayList<ControlPoint> controlPoints = tr.getAllControlPoints();

    double offset = 0;
    double spacing = tr.getSpacing();

    boolean isLastCurve;

    // Loop through each segment between consecutive control points
    for (int i = 0; i < controlPoints.size() - 1; i++) {

        ControlPoint p0 = controlPoints.get(i);
        ControlPoint p1 = controlPoints.get(i + 1);

        isLastCurve = (p1 == tr.getLast());

        double arcLength = splineObj.getArcLength(p0, p1, 0, 1);

        double accumulatedLength = offset;

        // Accumulate follow points based on the calculated arc length
        while (accumulatedLength < arcLength) {

            // Use Newton-Raphson to approximate t for the desired arc length
            double t = findTUsingNewtonRaphson(splineObj, p0, p1, accumulatedLength, 0, 1);

            CartesianCoordinate c = splineObj.evaluate(p0, p1, t);
            double currentSpeed = calculateSpeedAtT(minSpeed, maxSpeed, minBentRate, maxBentRate, p0, p1, t);

            if (isLastCurve) {
                double decliningSpeed = maxSpeed - (maxSpeed - minSpeed) * (accumulatedLength / arcLength);
                if (decliningSpeed < currentSpeed) {
                    currentSpeed = decliningSpeed;
                }
            }

            FollowPoint fp = new FollowPoint(c, currentSpeed, p0);
            followPoints.add(fp);
            accumulatedLength += spacing;
        }

        offset = accumulatedLength - arcLength;
    }

    // Add the final follow point at the last control point with a speed of 0
    ControlPoint last = tr.getLast();
    FollowPoint fp = new FollowPoint(last.getPosition(), 0.0, last);
    followPoints.add(fp);

    return followPoints;
    }

    /**
     * Uses Newton-Raphson to find the parameter t for a given arc length.
     *
     * @param splineObj the spline object representing the curve
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param sTarget the target arc length
     * @param tMin the minimum parameter (usually 0)
     * @param tMax the maximum parameter (usually 1)
     * @return the parameter t corresponding to the target arc length
     */
    private double findTUsingNewtonRaphson(AbstractSpline splineObj, ControlPoint p0, ControlPoint p1, double sTarget, double tMin, double tMax) {
        double t = (tMin + tMax) / 2; // Initial guess
        double epsilon = 1e-2;        // Convergence threshold
        int maxIterations = 100;     // Safety limit for iterations

        for (int i = 0; i < maxIterations; i++) {
            double s = splineObj.getArcLength(p0, p1, 0, t);
            double sPrime = computeSpeed(splineObj, p0, p1, t); // ds/dt

            double error = s - sTarget;

            if (Math.abs(error) < epsilon) {
                break; // Converged
            }

            t = t - error / sPrime;

            // Clamp t to the valid range
            t = Math.max(tMin, Math.min(tMax, t));
        }

        return t;
    }

    /**
     * Computes the speed (magnitude of the first derivative) at parameter t.
     *
     * @param splineObj the spline object
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the parameter at which to compute speed
     * @return the speed at parameter t
     */
    private double computeSpeed(AbstractSpline splineObj, ControlPoint p0, ControlPoint p1, double t) {
        double[] firstDerivative = splineObj.firstDerivative(p0, p1, t);
        return Math.sqrt(firstDerivative[0] * firstDerivative[0] + firstDerivative[1] * firstDerivative[1]);
    }

}
