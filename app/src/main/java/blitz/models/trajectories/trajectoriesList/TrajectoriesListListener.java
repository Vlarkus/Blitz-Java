package blitz.models.trajectories.trajectoriesList;

/**
 * Listener interface for receiving notifications when the trajectory list changes.
 * Classes that implement this interface can be registered to be notified whenever 
 * the list of trajectories is modified.
 * 
 * Implementing classes must define how they respond to changes in the trajectory list.
 * 
 * @author Valery
 */
public interface TrajectoriesListListener {

    /**
     * Invoked when the trajectory list has changed.
     * Implementing classes should define the behavior to handle this event.
     */
    public void TrajectoryListChanged();
}
