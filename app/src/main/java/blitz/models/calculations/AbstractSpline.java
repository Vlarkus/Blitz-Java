package blitz.models.calculations;

import blitz.models.ControlPoint;
import blitz.services.CartesianCoordinate;

public abstract class AbstractSpline {
    
    public abstract CartesianCoordinate evaluate(ControlPoint p0, ControlPoint p1, double t);
    public abstract double getArcLength(ControlPoint p0, ControlPoint p1, double tMin, double tMax);

}
