package blitz.models.active;

import java.util.ArrayList;

import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoriesList.TrajectoriesList;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Represents the active trajectory and control point with listener support for changes.
 * 
 * @author Valery
 */
public class ActiveEntities {

    private static Trajectory activeTrajectory;
    private static ControlPoint activeControlPoint;
    private static ArrayList<ActiveEntitiesListener> listeners = new ArrayList<>();

    /**
     * Returns the active trajectory.
     * 
     * @return the active trajectory
     */
    public static Trajectory getActiveTrajectory() {
        return activeTrajectory;
    }

    /**
     * Returns the active control point.
     * 
     * @return the active control point
     */
    public static ControlPoint getActiveControlPoint() {
        return activeControlPoint;
    }

    /**
     * Sets the active trajectory.
     * 
     * @param activeTrajectory the trajectory to set as active
     */
    public static void setActiveTrajectory(Trajectory activeTrajectory) {
        if(activeTrajectory == null){
            ActiveEntities.activeTrajectory = null;
            ActiveEntities.activeControlPoint = null;
            notifyActiveTrajectoryChanged();
            notifyActiveControlPointChanged();
            return;
        }
        if(activeTrajectory.isLocked() || !activeTrajectory.isVisible()) { return; }
        ActiveEntities.activeTrajectory = activeTrajectory;
        ActiveEntities.activeControlPoint = null;
        notifyActiveTrajectoryChanged();
        notifyActiveControlPointChanged();
    }

    /**
     * Sets the active control point and updates the active trajectory accordingly.
     * 
     * @param activeControlPoint the control point to set as active
     * @throws IllegalArgumentException if the corresponding trajectory is not found
     */
    public static void setActiveControlPoint(ControlPoint activeControlPoint) {
        
        if(activeControlPoint == null) {
            ActiveEntities.activeControlPoint = null;
            notifyActiveControlPointChanged();
            return;
        }

        if(activeControlPoint.isLocked()) { return; }
        
        Trajectory newTrajectory = TrajectoriesList.getTrajectoryByControlPoint(activeControlPoint);

        if(newTrajectory == null) { return; }

        if(newTrajectory.isLocked() || !newTrajectory.isVisible()) { return; }

        ActiveEntities.activeControlPoint = activeControlPoint;
        ActiveEntities.activeTrajectory = newTrajectory;
        notifyActiveControlPointChanged();
        notifyActiveTrajectoryChanged();

    }

    /**
     * Adds a listener to the list of active listeners.
     * 
     * @param listener the listener to add
     */
    public static void addActiveListener(ActiveEntitiesListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list of active listeners.
     * 
     * @param listener the listener to remove
     */
    public static void removeActiveListener(ActiveEntitiesListener listener) {
        listeners.remove(listener);
    }

    private static void notifyActiveTrajectoryChanged() {
        for (ActiveEntitiesListener listener : listeners) {
            listener.activeTrajectoryChanged(activeTrajectory);
        }
    }

    private static void notifyActiveControlPointChanged() {
        for (ActiveEntitiesListener listener : listeners) {
            listener.activeControlPointChanged(activeControlPoint);
        }
    }

    public static void notifyActiveControlPointStateEdited(){
        for (ActiveEntitiesListener listener : listeners) {
            listener.activeControlPointStateEdited(activeControlPoint);
        }
    }

    public static void notifyActiveTrajectoryStateEdited(){
        for (ActiveEntitiesListener listener : listeners) {
            listener.activeTrajectoryStateEdited(activeTrajectory);
        }
    }
}