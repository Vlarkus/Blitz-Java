package blitz.models.trajectories.visibleTrajectories;

/**
 * Listener interface for receiving notifications when the visible trajectories change.
 * Implementing classes will be notified whenever there is a change in the visibility 
 * of trajectories.
 * 
 * @author Valery
 */
public interface VisibleTrajectoriesListener {

    /**
     * Invoked when the visible trajectories have changed.
     * Implementing classes should define how they handle changes in visible trajectories.
     */
    public void visibleTrajectoriesChanged();
}
