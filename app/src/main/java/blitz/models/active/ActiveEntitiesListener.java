package blitz.models.active;

import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Listener interface for receiving notifications about changes to the active trajectory and control point.
 */
public interface ActiveEntitiesListener {
    public void activeTrajectoryChanged(Trajectory tr);
    public void activeControlPointChanged(ControlPoint cp);
    public void activeControlPointStateEdited(ControlPoint cp);
    public void activeTrajectoryStateEdited(Trajectory tr);
}
