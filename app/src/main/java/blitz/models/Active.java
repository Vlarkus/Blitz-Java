package blitz.models;

import java.util.ArrayList;

/**
 * 
 * @author Valery
 */
public class Active {

    private static Trajectory activeTrajectory;
    private static ControlPoint activeControlPoint;
    private static ArrayList<ActiveListener> listeners = new ArrayList<ActiveListener>();

    /**
     * 
     * 
     * @return
     */
    public static Trajectory getActiveTrajectory() {
        return activeTrajectory;
    }

    /**
     * 
     * 
     * @return
     */
    public static ControlPoint getActiveControlPoint() {
        return activeControlPoint;
    }

    /**
     * 
     * @param activeTrajectory
     */
    public static void setActiveTrajectory(Trajectory activeTrajectory) {
        Active.activeTrajectory = activeTrajectory;
    }

    /**
     * 
     * 
     * @param activeControlPoint
     */
    public static void setActiveControlPoint(ControlPoint activeControlPoint) {
        Trajectory newTrajectory = TrajectoriesList.getTrajectoryByControlPoint(activeControlPoint);
        if(newTrajectory == null){
            throw new NullPointerException();
        }
        activeControlPoint = activeControlPoint;
        activeTrajectory = newTrajectory;
    }

    public void addActiveListener(ActiveListener listener) {
        listeners.add(listener);
    }

    public void removeActiveListener(ActiveListener listener) {
        listeners.remove(listener);
    }

    private void notifyActiveTrajectoryChanged() {
        for (ActiveListener listener : listeners) {
            listener.activeTrajectoryChanged(activeTrajectory);
        }
    }

    private void notifyActiveControlPointChanged() {
        for (ActiveListener listener : listeners) {
            listener.activeControlPointChanged(activeControlPoint);
        }
    }

}



/**
 * 
 */
interface ActiveListener {
    public void activeTrajectoryChanged(Trajectory tr);
    public void activeControlPointChanged(ControlPoint cp);
}
