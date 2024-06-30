package blitz.models;

import java.util.ArrayList;

import javax.sound.midi.Track;

public class TrajectoriesList {

    // -=-=-=- FIELDS -=-=-=-

    private static ArrayList<Trajectory> trajectoriesList = new ArrayList<Trajectory>();



    // -=-=-=- METHODS -=-=-=-

    /**
     * 
     * 
     * @return
     */
    public static ArrayList<Trajectory> getTrajectoriesList() {
        return trajectoriesList;
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

    public static void addTrajectory(Trajectory tr){
        if(tr == null){
            throw new NullPointerException("Trajectory cannot be null!");
        }
        trajectoriesList.add(tr);
    }

    public static void removeTrajectory(Trajectory tr){
        if(tr == null){
            throw new NullPointerException("Trajectory cannot be null!");
        }

        trajectoriesList.remove(tr);
    }
    
}
