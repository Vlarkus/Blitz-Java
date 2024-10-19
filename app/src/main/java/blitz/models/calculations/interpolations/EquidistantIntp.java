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

import blitz.configs.Config;
import blitz.models.calculations.AbstractInterpolation;
import blitz.models.calculations.AbstractSpline;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.models.trajectories.trajectoryComponents.FollowPoint;
import blitz.utils.CartesianCoordinate;
import blitz.utils.Table;

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
            
            Table table = new Table(0, arcLength, 0, 1);
            for (int j = 0; j < Config.TABLE_DIVISION_COEFF; j++) {
                double t = (double) j / Config.TABLE_DIVISION_COEFF;
                table.add(splineObj.getArcLength(p0, p1, 0, t), t);
            }

            double accumulatedLength = offset;

            // Accumulate follow points based on the calculated arc length
            while (accumulatedLength < arcLength) {
                double t = table.approximate(accumulatedLength);
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
}
