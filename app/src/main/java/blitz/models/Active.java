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
    private static ArrayList<ActiveListener> listeners = new ArrayList<ActiveListener>();

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
        Active.activeTrajectory = activeTrajectory;
        notifyActiveTrajectoryChanged();
    }

    /**
     * Sets the active control point and updates the active trajectory accordingly.
     * 
     * @param activeControlPoint the control point to set as active
     * @throws IllegalArgumentException if the corresponding trajectory is not found
     */
    public static void setActiveControlPoint(ControlPoint activeControlPoint) {
        Trajectory newTrajectory = TrajectoriesList.getTrajectoryByControlPoint(activeControlPoint);
        if(newTrajectory == null){
            throw new IllegalArgumentException("ControlPoint does not correspond to any trajectory.");
        }
        Active.activeControlPoint = activeControlPoint;
        Active.activeTrajectory = newTrajectory;
        notifyActiveControlPointChanged();
    }

    /**
     * Adds a listener to the list of active listeners.
     * 
     * @param listener the listener to add
     */
    public void addActiveListener(ActiveListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list of active listeners.
     * 
     * @param listener the listener to remove
     */
    public void removeActiveListener(ActiveListener listener) {
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
}

/**
 * Listener interface for receiving notifications about changes to the active trajectory and control point.
 */
interface ActiveListener {
    public void activeTrajectoryChanged(Trajectory tr);
    public void activeControlPointChanged(ControlPoint cp);
}
