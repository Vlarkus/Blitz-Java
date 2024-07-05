package blitz.models;

/**
 * Listener interface for receiving notifications about changes to the active trajectory and control point.
 */
public interface ActiveListener {
    public void activeTrajectoryChanged(Trajectory tr);
    public void activeControlPointChanged(ControlPoint cp);
    public void activeControlPointStateEdited(ControlPoint cp);
}
