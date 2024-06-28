package blitz.models;

import java.util.ArrayList;

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

    public static Trajectory getTrajectoryByControlPoint(ControlPoint cp) {

        for (Trajectory trajectory : trajectoriesList) {
            if(trajectory.contains(cp)){
                return trajectory;
            }
        }

        return null;

    }
    
}
