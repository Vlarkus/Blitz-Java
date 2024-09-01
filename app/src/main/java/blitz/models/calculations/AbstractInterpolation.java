package blitz.models.calculations;

import java.util.ArrayList;

import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.models.trajectories.trajectoryComponents.FollowPoint;

public abstract class AbstractInterpolation {

        protected AbstractSpline splineObj;

        public abstract ArrayList<FollowPoint> calculate(Trajectory tr, AbstractSpline splineObj);

        protected double calculateSpeedAtT(double minSpeed, double maxSpeed, double minBentRate, double maxBentRate, ControlPoint p0, ControlPoint p1, double t) {
    
                double bentRate = splineObj.calculateBentRate(p0, p1, t);
                bentRate = Math.max(minBentRate, Math.min(bentRate, maxBentRate));
                double speed = maxSpeed - (bentRate - minBentRate) * (maxSpeed - minSpeed) / (maxBentRate - minBentRate);
                return Math.max(minSpeed, speed);

        }
    
}
