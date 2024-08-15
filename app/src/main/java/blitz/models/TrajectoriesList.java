package blitz.models;

import java.awt.TrayIcon;
import java.util.ArrayList;

import javax.sound.midi.Track;

public class TrajectoriesList {

    // -=-=-=- FIELDS -=-=-=-

    private static ArrayList<Trajectory> trajectoriesList = new ArrayList<Trajectory>();

    private static ArrayList<TrajectoriesListListener> listeners = new ArrayList<>();



    // -=-=-=- METHODS -=-=-=-

    public static void cutTrajectoryAtControlPoint(ControlPoint cp) {

        if(cp == null) {
            return;
        }
        if(cp.isLocked()) {
            return;
        }
    
        Trajectory tr1 = getTrajectoryByControlPoint(cp);
    
        if(tr1 == null) {
            return;
        }
        if(tr1.isLocked()) {
            return;
        }

        if(tr1.getAllControlPoints().getLast() == cp) {
            return;
        }

        if(tr1.getAllControlPoints().getFirst() == cp) {
            return;
        }
    
        int indexTR1 = trajectoriesList.indexOf(tr1);
        Trajectory tr2 = new Trajectory(getNextAvaliableName());
    
        int indexOfCP = tr1.indexOf(cp);
    
        for (int i = 0; i < indexOfCP; i++) {
            ControlPoint insertCP = tr1.getControlPoint(i);
            tr2.addControlPoint(insertCP);
        }
    
        ControlPoint copyOfCP = new ControlPoint(cp);
        tr2.addControlPoint(copyOfCP);
    
        for (int i = indexOfCP; i >= 0; i--) {
            tr1.removeControlPoint(tr1.getControlPoint(i));
        }
    
        if (indexTR1 + 1 <= trajectoriesList.size()) {
            trajectoriesList.add(indexTR1 + 1, tr2);
        } else {
            trajectoriesList.add(tr2);
        }

        notifyTrajectoriesListListeners();
        
    }
    

    public static void moveTrajectoryDown(Trajectory tr) {
        if (!trajectoriesList.contains(tr)) {
            return;
        }
    
        int indexTR = trajectoriesList.indexOf(tr);
        if (indexTR == trajectoriesList.size() - 1) {
            return;
        }
    
        int indexTR2 = indexTR + 1;
        Trajectory temp = trajectoriesList.get(indexTR2);
        trajectoriesList.set(indexTR2, tr);
        trajectoriesList.set(indexTR, temp);
        notifyTrajectoriesListListeners();
    }

    public static void moveTrajectoryUp(Trajectory tr) {
        if (!trajectoriesList.contains(tr)) {
            return;
        }
    
        int indexTR = trajectoriesList.indexOf(tr);
        if (indexTR == 0) {
            return;
        }
    
        int indexTR2 = indexTR - 1;
        Trajectory temp = trajectoriesList.get(indexTR2);
        trajectoriesList.set(indexTR2, tr);
        trajectoriesList.set(indexTR, temp);
        notifyTrajectoriesListListeners();
    }
    
    public static void addTrajecoriesListListener(TrajectoriesListListener listener){
        listeners.add(listener);
    }


    public static void removeTrajecoriesListListener(TrajectoriesListListener listener){
        listeners.remove(listener);
    }

    private static void notifyTrajectoriesListListeners(){
        for (TrajectoriesListListener listener : listeners) {
            listener.TrajectoryListChanged();
        }
    }

    /**
     * 
     * 
     * @return
     */
    public static ArrayList<Trajectory> getTrajectoriesList() {
        return trajectoriesList;
    }

    public static Trajectory getTrajectory(int index) {
        return trajectoriesList.get(index);
    }

    public static ArrayList<Trajectory> copyTrajectoriesList() {
        return new ArrayList<Trajectory>(trajectoriesList);
    }

    public static boolean contains(Trajectory tr){
        return trajectoriesList.contains(tr);
    }

    public static Trajectory getTrajectoryByControlPoint(ControlPoint cp) {

        for (Trajectory trajectory : trajectoriesList) {
            if(trajectory.contains(cp)){
                return trajectory;
            }
        }

        return null;

    }

    public static int getTrajectoryIndex(Trajectory tr){
        return trajectoriesList.indexOf(tr);
    }

    public static void setTrajectoriesList(ArrayList<Trajectory> trajectories){
        trajectoriesList = trajectories;
        notifyTrajectoriesListListeners();
        Active.setActiveTrajectory(null);
    }

    public static void addTrajectory(Trajectory tr){
        if(tr == null){
            throw new NullPointerException("Trajectory cannot be null!");
        }
        trajectoriesList.add(tr);
        notifyTrajectoriesListListeners();
    }

    public static void addTrajectory(){
        trajectoriesList.add(new Trajectory(getNextAvaliableName()));
        notifyTrajectoriesListListeners();
    }

    public static void removeTrajectory(Trajectory tr){
        if(tr == null){
            throw new NullPointerException("Trajectory cannot be null!");
        }

        trajectoriesList.remove(tr);
        notifyTrajectoriesListListeners();
    }

    public static String getNextAvaliableName(){
        
        String name = null;
        int i = 1;
        boolean nameIsTaken = true;
        while(nameIsTaken) { 
            name = "Trajectory " + i++;
            nameIsTaken = false;
            for (Trajectory tr : trajectoriesList)
                if(tr.getName().equals(name))
                    nameIsTaken = true;
        }
        return name;
    }
    
}
