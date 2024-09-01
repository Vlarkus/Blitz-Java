package blitz.models.trajectories.visibleTrajectories;

import java.util.ArrayList;

public class VisibleTrajectories {
    
    private static ArrayList<VisibleTrajectoriesListener> listeners = new ArrayList<>();

    public static void addVisibleTrajectoriesListener(VisibleTrajectoriesListener listener){
        listeners.add(listener);
    }

    public static void removeVisibleTrajectoriesListener(VisibleTrajectoriesListener listener){
        listeners.remove(listener);
    }

    public static void notifyVisibleTrajectoriesChanged(){
        for (VisibleTrajectoriesListener listener : listeners) {
            listener.visibleTrajectoriesChanged();
        }
    }

}
