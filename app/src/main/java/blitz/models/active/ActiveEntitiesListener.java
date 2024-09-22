package blitz.models.active;

import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Listener interface for receiving notifications about changes to the active trajectory and control point.
 * Classes that implement this interface can be registered to be notified when the active trajectory 
 * or control point changes, or when their state is edited.
 * 
 * <p>Listeners can handle the following events:
 * <ul>
 *   <li>When the active trajectory is changed.</li>
 *   <li>When the active control point is changed.</li>
 *   <li>When the state of the active control point is edited.</li>
 *   <li>When the state of the active trajectory is edited.</li>
 * </ul>
 * </p>
 * 
 * @author Valery
 */
public interface ActiveEntitiesListener {
    
    /**
     * Invoked when the active trajectory has changed.
     *
     * @param tr the new active trajectory, or {@code null} if there is no active trajectory
     */
    public void activeTrajectoryChanged(Trajectory tr);

    /**
     * Invoked when the active control point has changed.
     *
     * @param cp the new active control point, or {@code null} if there is no active control point
     */
    public void activeControlPointChanged(ControlPoint cp);

    /**
     * Invoked when the state of the active control point has been edited.
     * 
     * @param cp the active control point that has been edited
     */
    public void activeControlPointStateEdited(ControlPoint cp);

    /**
     * Invoked when the state of the active trajectory has been edited.
     * 
     * @param tr the active trajectory that has been edited
     */
    public void activeTrajectoryStateEdited(Trajectory tr);
}
