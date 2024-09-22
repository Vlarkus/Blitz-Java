package blitz.models.trajectories.visibleTrajectories;

import java.util.ArrayList;

/**
 * Manages the list of listeners that are notified when visible trajectories change.
 * This class allows listeners to register and be notified of any changes in the visibility
 * of trajectories.
 * 
 * @see VisibleTrajectoriesListener
 * 
 * @author Valery
 */
public class VisibleTrajectories {
    
    // -=-=-=- FIELDS -=-=-=-
    
    private static ArrayList<VisibleTrajectoriesListener> listeners = new ArrayList<>();

    // -=-=-=- METHODS -=-=-=-

    /**
     * Adds a listener to the list of visible trajectories listeners.
     * 
     * @param listener the listener to add
     */
    public static void addVisibleTrajectoriesListener(VisibleTrajectoriesListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list of visible trajectories listeners.
     * 
     * @param listener the listener to remove
     */
    public static void removeVisibleTrajectoriesListener(VisibleTrajectoriesListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners that the visible trajectories have changed.
     */
    public static void notifyVisibleTrajectoriesChanged() {
        for (VisibleTrajectoriesListener listener : listeners) {
            listener.visibleTrajectoriesChanged();
        }
    }

}
