package blitz.models;

import java.util.ArrayList;

/**
 * Represents the active trajectory and control point with listener support for changes.
 * 
 * @author Valery
 */
public class Active {

    private static Trajectory activeTrajectory;
    private static ControlPoint activeControlPoint;
    private static ArrayList<ActiveListener> listeners = new ArrayList<>();

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
            Active.activeTrajectory = null;
            Active.activeControlPoint = null;
            notifyActiveTrajectoryChanged();
            notifyActiveControlPointChanged();
            return;
        }
        if(activeTrajectory.isLocked() || !activeTrajectory.isVisible()) { return; }
        Active.activeTrajectory = activeTrajectory;
        Active.activeControlPoint = null;
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
            Active.activeControlPoint = null;
            notifyActiveControlPointChanged();
            return;
        }

        if(activeControlPoint.isLocked()) { return; }
        
        Trajectory newTrajectory = TrajectoriesList.getTrajectoryByControlPoint(activeControlPoint);

        if(newTrajectory == null) { return; }

        if(newTrajectory.isLocked() || !newTrajectory.isVisible()) { return; }

        Active.activeControlPoint = activeControlPoint;
        Active.activeTrajectory = newTrajectory;
        notifyActiveControlPointChanged();
        notifyActiveTrajectoryChanged();

    }

    /**
     * Adds a listener to the list of active listeners.
     * 
     * @param listener the listener to add
     */
    public static void addActiveListener(ActiveListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list of active listeners.
     * 
     * @param listener the listener to remove
     */
    public static void removeActiveListener(ActiveListener listener) {
        listeners.remove(listener);
    }

    private static void notifyActiveTrajectoryChanged() {
        for (ActiveListener listener : listeners) {
            listener.activeTrajectoryChanged(activeTrajectory);
        }
    }

    private static void notifyActiveControlPointChanged() {
        for (ActiveListener listener : listeners) {
            listener.activeControlPointChanged(activeControlPoint);
        }
    }

    public static void notifyActiveControlPointStateEdited(){
        for (ActiveListener listener : listeners) {
            listener.activeControlPointStateEdited(activeControlPoint);
        }
    }

    public static void notifyActiveTrajectoryStateEdited(){
        for (ActiveListener listener : listeners) {
            listener.activeTrajectoryStateEdited(activeTrajectory);
        }
    }
}