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

    public static void addTrajectory(Trajectory tr){
        if(tr == null){
            throw new NullPointerException("Trajectory cannot be null!");
        }
        trajectoriesList.add(tr);
    }

    public static void addTrajectory(){
        trajectoriesList.add(new Trajectory(getNextAvaliableName()));
    }

    public static void removeTrajectory(Trajectory tr){
        if(tr == null){
            throw new NullPointerException("Trajectory cannot be null!");
        }

        trajectoriesList.remove(tr);
    }

    public static String getNextAvaliableName(){
        
        String name = "Trajectory 1";
        int i = 1;
        boolean nameIsTaken = true;
        while(nameIsTaken) { 
            name = "Trajectory " + ++i;
            nameIsTaken = false;
            for (Trajectory tr : trajectoriesList)
                if(tr.getName().equals(name))
                    nameIsTaken = true;
        }
        return name;
    }
    
}
