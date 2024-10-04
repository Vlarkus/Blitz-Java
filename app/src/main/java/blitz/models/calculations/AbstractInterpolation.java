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

import java.util.ArrayList;

import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.models.trajectories.trajectoryComponents.FollowPoint;

/**
 * Abstract base class for interpolation algorithms that generate follow points along a trajectory.
 * 
 * This class provides the framework for specific interpolation implementations by defining the 
 * abstract {@link #calculate(Trajectory, AbstractSpline)} method and a helper method for calculating 
 * the speed at a given parameter t based on the curvature (bent rate) of the spline.
 * 
 * Subclasses should implement the {@link #calculate(Trajectory, AbstractSpline)} method to generate 
 * follow points based on the specific interpolation strategy.
 * 
 * @see AbstractSpline
 * @see Trajectory
 * @see ControlPoint
 * @see FollowPoint
 * 
 * @author Valery Rabchanka
 */
public abstract class AbstractInterpolation {

    /**
     * The spline object used for interpolation, providing methods to evaluate the curve and 
     * calculate bent rate (curvature).
     */
    protected AbstractSpline splineObj;

    /**
     * Calculates the list of follow points for a given trajectory using the specified spline object.
     * 
     * @param tr the trajectory for which to calculate follow points
     * @param splineObj the spline object representing the curve between control points
     * @return an {@link ArrayList} of {@link FollowPoint} objects representing the calculated follow points
     */
    public abstract ArrayList<FollowPoint> calculate(Trajectory tr, AbstractSpline splineObj);

    /**
     * Calculates the speed at a given parameter t based on the curvature (bent rate) of the spline 
     * and the minimum and maximum speed constraints.
     * 
     * @param minSpeed the minimum allowable speed
     * @param maxSpeed the maximum allowable speed
     * @param minBentRate the minimum bent rate (curvature)
     * @param maxBentRate the maximum bent rate (curvature)
     * @param p0 the starting control point
     * @param p1 the ending control point
     * @param t the interpolation parameter (0 ≤ t ≤ 1)
     * @return the calculated speed at parameter t, constrained between minSpeed and maxSpeed
     */
    protected double calculateSpeedAtT(double minSpeed, double maxSpeed, double minBentRate, double maxBentRate, ControlPoint p0, ControlPoint p1, double t) {

        double bentRate = splineObj.calculateBentRate(p0, p1, t);
        bentRate = Math.max(minBentRate, Math.min(bentRate, maxBentRate));
        double speed = maxSpeed - (bentRate - minBentRate) * (maxSpeed - minSpeed) / (maxBentRate - minBentRate);
        return Math.max(minSpeed, speed);

    }
    
}
