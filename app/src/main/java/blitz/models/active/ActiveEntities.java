package blitz.models.active;

import java.util.ArrayList;

import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoriesList.TrajectoriesList;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Manages the active trajectory and control point with listener support.
 * 
 * @author Valery
 */
public class ActiveEntities {

    private static Trajectory activeTrajectory;
    private static ControlPoint activeControlPoint;
    private static ArrayList<ActiveEntitiesListener> listeners = new ArrayList<>();

    /**
     * Gets the currently active trajectory.
     * 
     * @return the active trajectory, or {@code null} if none is set
     */
    public static Trajectory getActiveTrajectory() {
        return activeTrajectory;
    }

    /**
     * Gets the currently active control point.
     * 
     * @return the active control point, or {@code null} if none is set
     */
    public static ControlPoint getActiveControlPoint() {
        return activeControlPoint;
    }

    /**
     * Sets the active trajectory and notifies listeners about the change.
     * If the given trajectory is {@code null}, both the active trajectory 
     * and control point are cleared.
     * 
     * @param activeTrajectory the trajectory to set as active, or {@code null} to clear
     */
    public static void setActiveTrajectory(Trajectory activeTrajectory) {
        if (activeTrajectory == null) {
            ActiveEntities.activeTrajectory = null;
            ActiveEntities.activeControlPoint = null;
            notifyActiveTrajectoryChanged();
            notifyActiveControlPointChanged();
            return;
        }
        if (activeTrajectory.isLocked() || !activeTrajectory.isVisible()) { return; }
        ActiveEntities.activeTrajectory = activeTrajectory;
        ActiveEntities.activeControlPoint = null;
        notifyActiveTrajectoryChanged();
        notifyActiveControlPointChanged();
    }

    /**
     * Sets the active control point and updates the active trajectory accordingly.
     * If the given control point is {@code null}, the active control point is cleared.
     * 
     * @param activeControlPoint the control point to set as active, or {@code null} to clear
     */
    public static void setActiveControlPoint(ControlPoint activeControlPoint) {
        if (activeControlPoint == null) {
            ActiveEntities.activeControlPoint = null;
            notifyActiveControlPointChanged();
            return;
        }
        if (activeControlPoint.isLocked()) { return; }

        Trajectory newTrajectory = TrajectoriesList.getTrajectoryByControlPoint(activeControlPoint);
        if (newTrajectory == null || newTrajectory.isLocked() || !newTrajectory.isVisible()) { return; }

        ActiveEntities.activeControlPoint = activeControlPoint;
        ActiveEntities.activeTrajectory = newTrajectory;
        notifyActiveControlPointChanged();
        notifyActiveTrajectoryChanged();
    }

    /**
     * Adds a listener to the list of listeners that will be notified of 
     * changes to active entities.
     * 
     * @param listener the listener to add
     */
    public static void addActiveListener(ActiveEntitiesListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list of listeners that are notified of
     * changes to active entities.
     * 
     * @param listener the listener to remove
     */
    public static void removeActiveListener(ActiveEntitiesListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners that the active trajectory has changed.
     */
    private static void notifyActiveTrajectoryChanged() {
        for (ActiveEntitiesListener listener : listeners) {
            listener.activeTrajectoryChanged(activeTrajectory);
        }
    }

    /**
     * Notifies all listeners that the active control point has changed.
     */
    private static void notifyActiveControlPointChanged() {
        for (ActiveEntitiesListener listener : listeners) {
            listener.activeControlPointChanged(activeControlPoint);
        }
    }

    /**
     * Notifies all listeners that the state of the active control point has been edited.
     */
    public static void notifyActiveControlPointStateEdited() {
        for (ActiveEntitiesListener listener : listeners) {
            listener.activeControlPointStateEdited(activeControlPoint);
        }
    }

    /**
     * Notifies all listeners that the state of the active trajectory has been edited.
     */
    public static void notifyActiveTrajectoryStateEdited() {
        for (ActiveEntitiesListener listener : listeners) {
            listener.activeTrajectoryStateEdited(activeTrajectory);
        }
    }
}
