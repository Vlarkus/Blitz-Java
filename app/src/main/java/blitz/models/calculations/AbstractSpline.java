package blitz.models.calculations;

import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.services.CartesianCoordinate;

public abstract class AbstractSpline {
    
    public abstract CartesianCoordinate evaluate(ControlPoint p0, ControlPoint p1, double t);
    
    public abstract double getArcLength(ControlPoint p0, ControlPoint p1, double tMin, double tMax);
    
    public abstract double calculateBentRate(ControlPoint p0, ControlPoint p1, double t);

    protected double[] firstDerivative(ControlPoint p0, ControlPoint p1, double t) {
        double delta = 1e-5;
    
        // Clamp t + delta and t - delta to the valid range [0, 1]
        double tPlus = Math.min(1.0, t + delta);
        double tMinus = Math.max(0.0, t - delta);
    
        CartesianCoordinate pPlus = evaluate(p0, p1, tPlus);
        CartesianCoordinate pMinus = evaluate(p0, p1, tMinus);
    
        double dx = (pPlus.getX() - pMinus.getX()) / (tPlus - tMinus);
        double dy = (pPlus.getY() - pMinus.getY()) / (tPlus - tMinus);
    
        return new double[]{dx, dy};
    }

    protected double[] secondDerivative(ControlPoint p0, ControlPoint p1, double t) {
        double delta = 1e-5;
    
        // Clamp t + delta and t - delta to the valid range [0, 1]
        double tPlus = Math.min(1.0, t + delta);
        double tMinus = Math.max(0.0, t - delta);
    
        CartesianCoordinate pPlus = evaluate(p0, p1, tPlus);
        CartesianCoordinate p = evaluate(p0, p1, t);
        CartesianCoordinate pMinus = evaluate(p0, p1, tMinus);
    
        double d2x = (pPlus.getX() - 2 * p.getX() + pMinus.getX()) / ((tPlus - tMinus) * (tPlus - tMinus));
        double d2y = (pPlus.getY() - 2 * p.getY() + pMinus.getY()) / ((tPlus - tMinus) * (tPlus - tMinus));
    
        return new double[]{d2x, d2y};
    }
    

}
