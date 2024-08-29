package blitz.models.calculations;

import java.util.ArrayList;

import blitz.models.FollowPoint;
import blitz.models.Trajectory;

public abstract class AbstractInterpolation {

        public abstract  ArrayList<FollowPoint> calculate(Trajectory tr, AbstractSpline splineObj);
    
}
