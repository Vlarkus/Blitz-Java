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
import blitz.utils.CartesianCoordinate;

/**
 * Fixed amount interpolation algorithm that calculates a set number of follow points 
 * for each segment between control points, based on the number of segments defined 
 * for each control point.
 * 
 * This class extends {@link AbstractInterpolation}.
 * 
 * <p>This method ensures that each curve segment between control points is subdivided 
 * into a fixed number of equally spaced follow points, regardless of arc length.</p>
 * 
 * @see AbstractInterpolation
 * @see Trajectory
 * @see ControlPoint
 * @see FollowPoint
 * @see AbstractSpline
 * 
 * <p>Each segment between control points will have the same number of follow points 
 * determined by the number of segments defined in the first control point of the segment.</p>
 * 
 * @author Valery Rabchanka
 */
public class FixedAmountIntp extends AbstractInterpolation {

    /**
     * Calculates a list of follow points for the given trajectory using a fixed number 
     * of points for each segment between control points.
     * 
     * @param tr the trajectory for which to calculate follow points
     * @param splineObj the spline object used to evaluate the curve between control points
     * @return an {@link ArrayList} of {@link FollowPoint} objects representing follow points along the trajectory
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

        boolean isLastCurve;

        // Loop through control points to calculate follow points for each segment
        for (int i = 0; i < controlPoints.size() - 1; i++) {

            ControlPoint p0 = controlPoints.get(i);
            ControlPoint p1 = controlPoints.get(i + 1);
            int numSegments = p0.getNumSegments(); // Number of segments to divide the curve into

            isLastCurve = (p1 == tr.getLast());

            // Generate follow points for each segment based on the number of segments
            for (int j = 0; j < numSegments; j++) {
                
                double t = (double) j / numSegments; // Parameter t along the curve
                CartesianCoordinate coord = splineObj.evaluate(p0, p1, t); // Evaluate point on spline
                double currentSpeed = calculateSpeedAtT(minSpeed, maxSpeed, minBentRate, maxBentRate, p0, p1, t);
                
                // Adjust speed for the last curve
                if (isLastCurve) {
                    double decliningSpeed = maxSpeed - (maxSpeed - minSpeed) * t;
                    if (decliningSpeed < currentSpeed) {
                        currentSpeed = decliningSpeed;
                    }
                }

                FollowPoint p = new FollowPoint(coord, currentSpeed, p0); // Create follow point
                followPoints.add(p); // Add follow point to list
            }

        }

        // Add the last control point with speed 0
        ControlPoint last = tr.getLast();
        FollowPoint fp = new FollowPoint(last.getPosition(), 0.0, last);
        followPoints.add(fp);

        return followPoints;
    }

}
