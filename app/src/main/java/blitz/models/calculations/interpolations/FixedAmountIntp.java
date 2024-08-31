package blitz.models.calculations.interpolations;

import java.util.ArrayList;

import blitz.models.ControlPoint;
import blitz.models.FollowPoint;
import blitz.models.Trajectory;
import blitz.models.calculations.AbstractInterpolation;
import blitz.models.calculations.AbstractSpline;
import blitz.services.CartesianCoordinate;

public class FixedAmountIntp extends AbstractInterpolation{

    @Override
    public ArrayList<FollowPoint> calculate(Trajectory tr, AbstractSpline splineObj) {

        this.splineObj = splineObj;

        double minSpeed = tr.getMinSpeed();
        double maxSpeed = tr.getMaxSpeed();
        double minBentRate = tr.getMinBentRate();
        double maxBentRate = tr.getMaxBentRate();

        ArrayList<FollowPoint> followPoints = new ArrayList<>();
        ArrayList<ControlPoint> controlPoints = tr.getAllControlPoints();

        for (int i = 0; i < controlPoints.size()-1; i++) {

            ControlPoint p0 = controlPoints.get(i);
            ControlPoint p1 = controlPoints.get(i + 1);
            int numSegments = p0.getNumSegments();

            for (int j = 0; j < numSegments; j++) {
                
                double t = (double) j / numSegments;
                CartesianCoordinate coord = splineObj.evaluate(p0, p1, t);
                double speed = calculateSpeedAtT(minSpeed, maxSpeed, minBentRate, maxBentRate, p0, p1, t);
                FollowPoint p = new FollowPoint(coord, speed, p0);
                followPoints.add(p);

            }

        }

        ControlPoint last = tr.getLast();
        FollowPoint fp = new FollowPoint(last.getPosition(), 0.0, last);
        followPoints.add(fp);

        return followPoints;

    }
    
}
