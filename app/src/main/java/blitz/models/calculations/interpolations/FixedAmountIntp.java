package blitz.models.calculations.interpolations;

import java.lang.reflect.Method;
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

        ArrayList<FollowPoint> fps = new ArrayList<>();
        ArrayList<ControlPoint> cps = tr.getAllControlPoints();

        Method evaluate = null;

        for (int i = 0; i < cps.size()-1; i++) {

            ControlPoint p0 = cps.get(i);
            ControlPoint p1 = cps.get(i + 1);
            int numSegments = p0.getNumSegments();

            for (int j = 0; j < numSegments; j++) {
                
                double t = (double) j / numSegments;
                CartesianCoordinate coord = splineObj.evaluate(p0, p1, t);
                FollowPoint p = new FollowPoint(coord, p0);
                fps.add(p);

            }

        }

        return fps;

    }
    
}
