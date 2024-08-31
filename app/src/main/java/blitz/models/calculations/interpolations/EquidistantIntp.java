package blitz.models.calculations.interpolations;

import java.util.ArrayList;

import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;
import blitz.models.FollowPoint;
import blitz.models.Trajectory;
import blitz.models.calculations.AbstractInterpolation;
import blitz.models.calculations.AbstractSpline;
import blitz.services.CartesianCoordinate;
import blitz.services.Table;

public class EquidistantIntp extends AbstractInterpolation {

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
        
        for (int i = 0; i < controlPoints.size() - 1; i++) {

            ControlPoint p0 = controlPoints.get(i);
            ControlPoint p1 = controlPoints.get(i + 1);

            double arcLength = splineObj.getArcLength(p0, p1, 0, 1);
            
            Table table = new Table(0, arcLength, 0, 1);
            for (int j = 0; j < MainFrameConfig.TABLE_DIVISION_COEFF; j++) {
                double t = (double) j / MainFrameConfig.TABLE_DIVISION_COEFF;
                table.add(splineObj.getArcLength(p0, p1, 0, t), t);
            }

            double accumulatedLength = offset;

            while(accumulatedLength < arcLength){
                double t = table.approximate(accumulatedLength);
                CartesianCoordinate c = splineObj.evaluate(p0, p1, t);
                double speed = calculateSpeedAtT(minSpeed, maxSpeed, minBentRate, maxBentRate, p0, p1, t);
                FollowPoint fp = new FollowPoint(c, speed, p0);
                followPoints.add(fp);
                accumulatedLength += spacing;
            }

            offset = accumulatedLength - arcLength;

        }

        ControlPoint last = tr.getLast();
        FollowPoint fp = new FollowPoint(last.getPosition(), 0.0, last);
        followPoints.add(fp);

        return followPoints;
    }


}